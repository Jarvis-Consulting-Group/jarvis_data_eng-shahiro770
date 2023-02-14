# Introduction

# SQL Quries

###### Table Setup (DDL)

```SQL
CREATE TABLE IF NOT EXISTS cd.members (
                                          memid INTEGER,
                                          surname VARCHAR(200) NOT NULL,
                                          firstname VARCHAR(200) NOT NULL,
                                          address VARCHAR(300) NOT NULL,
                                          zipcode INTEGER NOT NULL,
                                          telephone VARCHAR(20) NOT NULL,
                                          recommendedby INTEGER,
                                          joindate TIMESTAMP NOT NULL,
                                          CONSTRAINT members_pk PRIMARY KEY (memid),
                                          CONSTRAINT members_fk FOREIGN KEY (recommendedby) REFERENCES cd.members(memid) ON DELETE
                                              SET
                                              NULL
);

CREATE TABLE IF NOT EXISTS cd.bookings (
                                           bookid INTEGER NOT NULL,
                                           facid INTEGER NOT NULL,
                                           memid INTEGER NOT NULL,
                                           starttime TIMESTAMP NOT NULL,
                                           SLOTS INTEGER NOT NULL,
                                           CONSTRAINT bookings_pk PRIMARY KEY (bookid),
                                           CONSTRAINT bookings_facid_fk FOREIGN KEY (facid) REFERENCES cd.facilities(facid) ON DELETE
                                               SET
                                               NULL,
                                           CONSTRAINT bookings_memid_fk FOREIGN KEY (memid) REFERENCES cd.members(memid) ON DELETE
                                               SET
                                               NULL
);

CREATE TABLE IF NOT EXISTS cd.facilities (
                                             facid INTEGER NOT NULL,
                                             name VARCHAR(100) NOT NULL,
                                             membercost INTEGER NOT NULL,
                                             guestcost INTEGER NOT NULL,
                                             initialoutlay INTEGER NOT NULL,
                                             monthlymaintenance INTEGER NOT NULL,
                                             CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
```

### Modifying Data

###### Question 1: Add an entry

```SQL
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Insert calculated data into a table

```SQL
INSERT INTO cd.facilities (
  SELECT 
    (
      SELECT 
        MAX(facid) 
      FROM 
        cd.facilities
    ) + 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
);
```

Notice how VALUES can't be used here given the data being inserted is dynamic.

###### Question 3: Update some existing data

```SQL
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;
```

###### Question 4: Update a row based on the contents of another row

```SQL
UPDATE 
  cd.facilities 
SET 
  membercost = (
    SELECT 
      membercost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ), 
  guestcost = (
    SELECT 
      guestcost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) 
WHERE 
  facid = 1;
```

###### Question 5:  Delete all bookings

```SQL
TRUNCATE TABLE cd.bookings;
```

###### Question 6:

```SQL
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
```

### Basics

###### Question 1: Control which rows are retrieved

```SQL
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  (
    membercost * 50 < monthlymaintenance 
    AND membercost > 0
  );
```

###### Question 2: Basic string searches

```SQL
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';
```

###### Question 3: Matching against multiple possible values

```SQL
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);
```

###### Question 4: Working with dates

```SQL
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate >= '2012-09-01';
```

###### Question 5: Combining results from multiple queries

```SQL
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
```

### Join

###### Question 1:  Retrieve the start times of members' bookings

```SQL
SELECT 
  cd.bookings.starttime 
FROM 
  cd.bookings 
  INNER JOIN cd.members ON cd.members.memid = cd.bookings.memid 
WHERE 
  cd.members.surname = 'Farrell' 
  AND cd.members.firstname = 'David';
```

###### Question 2: Work out the start times of bookings for tennis courts

```SQL
SELECT 
  book.starttime, 
  fac.name 
FROM 
  cd.bookings book 
  INNER JOIN cd.facilities fac ON book.facid = fac.facid 
WHERE 
  book.starttime >= '2012-09-21' 
  AND book.starttime < '2012-09-22' 
  AND fac.name LIKE '%Tennis Court%' 
ORDER BY 
  book.starttime ASC;
```

###### Question 3: Produce a list of all members, along with their recommender

```SQL
SELECT 
  mems.firstname, 
  mems.surname, 
  refs.firstname, 
  refs.surname 
FROM 
  cd.members mems 
  LEFT JOIN cd.members refs ON refs.memid = mems.recommendedby 
ORDER BY 
  mems.surname, 
  mems.firstname;
```

###### Question 4: Produce a list of all members who have recommended another member


```SQL
SELECT 
  DISTINCT refs.firstname, 
  refs.surname 
FROM 
  cd.members refs 
  INNER JOIN cd.members mems ON refs.memid = mems.recommendedby 
ORDER BY 
  refs.surname, 
  refs.firstname;
```

###### Question 5: Produce a list of all members, along with their recommender, using no joins.

```SQL
SELECT 
  DISTINCT mems.firstname || ' ' || mems.surname AS member, 
  (
    SELECT 
      recs.firstname || ' ' || recs.surname AS recommender 
    FROM 
      cd.members recs 
    WHERE 
      recs.memid = mems.recommendedby
  ) 
FROM 
  cd.members mems 
ORDER BY 
  member;
```

### Aggregation

###### Question 1: Count the number of recommendations each member makes.

```SQL
SELECT 
  recommendedby, 
  COUNt(recommendedby) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER by 
  recommendedby;
```

###### Question 2: List the total slots booked per facility

```SQL
SELECT 
  facid, 
  SUM(slots) 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;
```

###### Question 3: List the total slots booked per facility in a given month

```SQL
SELECT 
  facid, 
  SUM(slots) 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012/09/01' 
  AND starttime < '2012/10/01' 
GROUP BY 
  facid 
ORDER BY 
  SUM(slots);
```

###### Question 4: List the total slots booked per facility per month

```SQL
SELECT 
  facid, 
  extract(
    month 
    FROM 
      starttime
  ) AS month, 
  SUM(slots) 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012/01/01' 
  AND starttime < '2013/01/01' 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;
```

###### Question 5: Find the count of members who have made at least one booking

```SQL
SELECT 
  COUNT(*) 
FROM 
  (
    SELECT 
      DISTINCT memid 
    FROM 
      cd.bookings
  ) AS distinctMemBookings;
```

###### Question 6: List each member's first booking after September 1st 2012

```SQL
SELECT 
  surname, 
  firstname, 
  mems.memid, 
  MIN(starttime) 
FROM 
  cd.members mems 
  JOIN cd.bookings books ON (books.memid = mems.memid) 
WHERE 
  starttime >= '2012-09-01' 
GROUP BY 
  (mems.memid) 
ORDER BY 
  (mems.memid);
```

###### Question 7: Produce a list of member names, with each row containing the total member count

```SQL
SELECT 
  COUNT(*) OVER(), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
```

###### Question 8: Produce a numbered list of members

```SQL
SELECT 
  ROW_NUMBER(*) OVER(
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members;
```

###### Question 9: Output the facility id that has the highest number of slots booked, again

```SQL
SELECT 
  facid, 
  total 
FROM 
  (
    SELECT 
      facid, 
      SUM(slots) AS total, 
      RANK() OVER (
        ORDER BY 
          SUM(slots) DESC
      ) AS rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) AS ranked 
WHERE 
  rank = 1;
```

### String

###### Question 1: Format the names of members

```SQL
SELECT 
  surname || ', ' || firstname 
FROM 
  cd.members;
```

###### Question 2: Find telephone numbers with parentheses
```SQL
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone SIMILAR TO '%[()]%';
```

###### Question 3: Count the number of members whose surname starts with each letter of the alphabet
```SQL
SELECT 
  SUBSTR (cd.members.surname, 1, 1) AS first, 
  COUNT(memid) 
FROM 
  cd.members 
GROUP BY 
  first 
ORDER by 
  first;
```


