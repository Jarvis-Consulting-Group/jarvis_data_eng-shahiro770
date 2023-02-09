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


###### Question 1: Show all members 



###### Questions 2: Lorem ipsum...



