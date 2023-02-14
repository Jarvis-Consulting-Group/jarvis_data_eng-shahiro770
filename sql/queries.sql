\connect exercises;

INSERT INTO cd.facilities
VALUES
    (9, 'Spa', 20, 30, 100000, 800);

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

UPDATE
    cd.facilities
SET
    initialoutlay = 10000
WHERE
        facid = 1;

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

TRUNCATE TABLE cd.bookings;

DELETE FROM
    cd.members
WHERE
        memid = 37;

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

SELECT
    *
FROM
    cd.facilities
WHERE
        name LIKE '%Tennis%';

SELECT
    *
FROM
    cd.facilities
WHERE
        facid IN (1, 5);

SELECT
    memid,
    surname,
    firstname,
    joindate
FROM
    cd.members
WHERE
        joindate >= '2012-09-01';

SELECT
    surname
FROM
    cd.members
UNION
SELECT
    name
FROM
    cd.facilities;

SELECT
    cd.bookings.starttime
FROM
    cd.bookings
        INNER JOIN cd.members ON cd.members.memid = cd.bookings.memid
WHERE
        cd.members.surname = 'Farrell'
  AND cd.members.firstname = 'David';

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

SELECT
    DISTINCT refs.firstname,
             refs.surname
FROM
    cd.members refs
        INNER JOIN cd.members mems ON refs.memid = mems.recommendedby
ORDER BY
    refs.surname,
    refs.firstname;

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

SELECT
    facid,
    SUM(slots)
FROM
    cd.bookings
GROUP BY
    facid
ORDER BY
    facid;

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

SELECT
    COUNT(*)
FROM
    (
        SELECT
            DISTINCT memid
        FROM
            cd.bookings
    ) AS distinctMemBookings;

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

SELECT
            COUNT(*) OVER(),
            firstname,
            surname
FROM
    cd.members
ORDER BY
    joindate;

SELECT
            ROW_NUMBER(*) OVER(
        ORDER BY
            joindate
        ),
            firstname,
            surname
FROM
    cd.members;

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

SELECT
            surname || ', ' || firstname
FROM
    cd.members;

SELECT
    memid,
    telephone
FROM
    cd.members
WHERE
        telephone SIMILAR TO '%[()]%';

SELECT
    SUBSTR (cd.members.surname, 1, 1) AS first,
    COUNT(memid)
FROM
    cd.members
GROUP BY
    first
ORDER by
    first;