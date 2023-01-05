###### Question 1: create facilities, members, and bookings table

```sql
CREATE TABLE facilities(
  facid INT PRIMARY KEY,
  name VARCHAR,
  membercost NUMERIC,
  guestcost NUMERIC,
  initialoutlay NUMERIC,
  monthlymaintenance NUMERIC
)

CREATE TABLE members(
  memid INT PRIMARY KEY,
  sername VARCHAR,
  firstname VARCHAR,
  address VARCHAR,
  zipcode INT,
  telephone VARCHAR,
  recommendedby INT,
  joindate TIMESTAMP
  FOREIGN KEY (recommendedby)
    REFERENCES members(memid)
)

CREATE TABLE bookings(
  bookid INT PRIMARY KEY,
  facid INT,
  memid INT,
  starttime TIMESTAMP,
  slots INT,
  FOREIGN KEY (facid)
    REFERENCES facilities(facid)
    ON DELETE CASCADE,
  FOREIGN KEY (memid)
    REFERENCES members(memid)
    ON DELETE CASCADE
)
```

###### Question 2: Add a record to facilities table

```sql
INSERT INTO cd.facilities VALUES (9,'Spa',20,30,100000,800)
```

###### Question 3: Add a record to facilities table by automatically generating the ID

```sql
INSERT INTO cd.facilities
VALUES((SELECT facid 
		  FROM cd.facilities 
		  ORDER BY facid 
		  DESC limit 1)+1,
	   'Spa',20, 30, 100000, 800)
```

###### Question 4: Update existing data

```sql
UPDATE cd.facilities 
  SET initialoutlay=10000 
  WHERE initialoutlay=8000
```

###### Question 5: Update a row based on the contents of another row

```sql
UPDATE cd.facilities f
  SET membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE name='Tennis Court 1'),
	  guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE name='Tennis Court 1')
  WHERE f.facid = 1
```

###### Question 6: Delete all rows

```sql
DELETE FROM cd.bookings
``` 

###### Question 7: Delte a member from the members table

```sql
DELETE FROM cd.members WHERE memid=37
```

###### Question 8: Classify results into buckets

```sql
SELECT name, 
  (CASE WHEN monthlymaintenance > 100 THEN 'expensive'
  	ELSE 'cheap' END) as cost
  FROM
  cd.facilities
``` 

###### Question 9: Combining results from multiple queries

```sql
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities
``` 

###### Question 10: Retrieve the start time of member's bookings

```sql
SELECT starttime 
  FROM cd.bookings b
  JOIN cd.members m
  ON b.memid = m.memid
  WHERE m.firstname = 'David' AND m.surname = 'Farrell'
``` 

###### Question 11: Work out the start time sof bookings for tennis courts 

```sql
SELECT b.starttime as start, f.name
  FROM cd.bookings b
  JOIN cd.facilities f
  USING (facid)
  WHERE b.starttime >= '2012-09-21' AND b.starttime < '2012-09-22' AND f.name LIKE 'Tennis Court%'
  ORDER BY starttime
```
###### Question 12: Produce a list of all members, along with their recommender 

```sql
SELECT m1.firstname, m1.surname, m2.firstname, m2.surname
  FROM cd.members m1
  LEFT JOIN cd.members m2
  ON m1.recommendedby = m2.memid
  ORDER BY m1.surname, m1.firstname
  
```
###### Questioin 13: Produce a list of all members who have recommended another member 

```sql
SELECT DISTINCT m1.firstname as firstname, m1.surname as surname
  FROM cd.members m1
  INNER JOIN cd.members m2
  ON m1.memid = m2.recommendedby
  ORDER BY surname, firstname
```
###### Question 14: Produce a list of all members, along with their recommender, using no joins

```sql
SELECT DISTINCT CONCAT(firstname, ' ', surname) as member, 
  (SELECT CONCAT(firstname, ' ', surname) as recommender 
     FROM cd.members m
     WHERE m1.recommendedby = m.memid)
  FROM cd.members m1
  ORDER BY member
```
###### Question 15: Count the number of recommendations each member makes

```sql
SELECT recommendedby, COUNT(*)
  FROM cd.members
  WHERE recommendedby IS NOT NULL
  GROUP BY recommendedby
  ORDER BY recommendedby

```
###### Question 16: List the total slots booked per facility 

```sql
SELECT facid, SUM(slots) as "Total Slots"
  FROM cd.bookings
  GROUP BY facid
  ORDER BY facid
```
###### Question 17: List the total slots booked per facility in a given month 

```sql
SELECT facid, SUM(slots) as "Total Slots"
  FROM cd.bookings
  WHERE starttime >= '2012-09-01' AND starttime < '2012-10-01'
  GROUP BY facid
  ORDER BY "Total Slots"
```
###### Question 18: List the total slots booked per facility per month

```sql
SELECT facid, EXTRACT(MONTH FROM starttime) as month, SUM(slots) as "Total Slots"
  FROM cd.bookings
  WHERE EXTRACT(YEAR FROM starttime) = 2012
  GROUP BY facid, month
  ORDER BY facid, month
```
###### Question 19: Find the count of members who have made at least one booking

```sql
SELECT COUNT(DISTINCT b.memid)
  FROM cd.bookings b
```
###### Question 20: List each member's first booking after September 1st 2012 

```sql
SELECT m.surname, m.firstname, m.memid, MIN(b.starttime) as starttime
  FROM cd.members m
  JOIN cd.bookings b
  USING (memid)
  WHERE b.starttime >= '2012-09-01'
  GROUP BY memid
  ORDER BY m.memid
```
###### Question 21: Produce a list of member names, with each row containing the total member count

```sql
SELECT (SELECT COUNT(*) FROM cd.members), firstname, surname
  FROM cd.members
```
###### Question 22: Produce a numbered list of members 

```sql
SELECT ROW_NUMBER() OVER(ORDER BY joindate), firstname, surname
  FROM cd.members
```
###### Question 23: Output the facility id that has the highest number of slots booked, again

```sql
SELECT facid, SUM(slots)
  FROM cd.bookings
  GROUP BY facid
  ORDER BY RANK() OVER (ORDER BY SUM(slots)) DESC
  limit 1
```
###### Question 24: Format the names of members
 
```sql
SELECT CONCAT(surname, ', ', firstname)
  FROM cd.members
```
###### Question 25: Find telephone numbers with parentheses
 
```sql
SELECT memid, telephone
  FROM cd.members
  WHERE telephone LIKE '(%)%'
```
###### Question 26: Count the number of members whose surname starts with each letter of the alphabet
 
```sql
SELECT substring(surname from 1 for 1) as letter, COUNT(*)
FROM cd.members
GROUP BY letter
ORDER BY letter
```


