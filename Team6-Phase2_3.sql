-- Type 1: A single-table query => Selection + Projection
-- 1.1
SELECT u.ID_NUMBER, u.NAME
FROM USERS u
WHERE SEX = 'F';
-- 1.2
SELECT T.CLASS_ID, T.DATE_TIME
FROM TRAINING T
WHERE T.TUTOR_ID LIKE '%00%';

-- Type 2: Multi-way join with join predicates in WHERE
-- 2.1
SELECT U.ID_NUMBER, U.NAME, U.JOB, M.PREPAID_MONEY
FROM USERS U, MEMBER M
WHERE U.SEX = 'M' AND M.PREPAID_MONEY > 10000 AND U.ID_NUMBER = M.ID_NUMBER;
-- 2.2
SELECT MATCH_ID, PLACE_ID, NAME
FROM MATCH, FIELD
WHERE FIELD.OWNER_HP LIKE '%8%' AND FIELD.FIELD_ID = MATCH.PLACE_ID

-- Typ  e 3: Aggregation + multi-way join with join predicates + with GROUP BY
-- 3.1
SELECT TEAM.TEAM_ID, TEAM_NAME, COUNT(TEAM_MEM.MEM_ID) AS TEAM_MEM_CNT
FROM TEAM INNER JOIN TEAM_MEM ON TEAM.TEAM_ID = TEAM_MEM.TEAM_ID
GROUP BY (TEAM.TEAM_ID, TEAM_NAME)
HAVING COUNT(TEAM_MEM.MEM_ID) > 5;
-- 3.2
SELECT TRAINING.CLASS_ID, COUNT(TRAIN_ENROLLS.TUTEE_ID) AS CLASS_MEM_CNT
FROM TRAINING INNER JOIN TRAIN_ENROLLS ON TRAINING.CLASS_ID = TRAIN_ENROLLS.CLASS_ID
WHERE ABS(TRAINING.DATE_TIME - SYSDATE) > 50
GROUP BY TRAINING.CLASS_ID
HAVING COUNT(TRAIN_ENROLLS.TUTEE_ID) > 5;

-- Type 4: Subquery
-- 4.1
SELECT T1.CLASS_ID, T1.SUBJECT
FROM TRAIN_REG T1
WHERE NOT EXISTS(
    SELECT CLASS_ID
    FROM (
        SELECT T2.CLASS_ID
        FROM TRAIN_REG T2
        WHERE T2.MAX_NUM > 15) X
    WHERE X.CLASS_ID = T1.CLASS_ID);
-- 4.2
SELECT F.NAME, F.ADDRESS
FROM FIELD F
WHERE F.FIELD_ID NOT IN (
    SELECT FIELD_ID
    FROM FIELD
    WHERE OWNER_HP LIKE '%00%');

-- Type 5: EXISTS를 포함하는 Subquery
-- 5.1
SELECT T1.CLASS_ID, T1.MAX_NUM
FROM TRAIN_REG T1
WHERE EXISTS(
    SELECT CLASS_ID
    FROM (
        SELECT T2.CLASS_ID
        FROM TRAIN_REG T2
        WHERE T2.SUBJECT = 'Dribbling') X
    WHERE X.CLASS_ID = T1.CLASS_ID);
-- 5.2
SELECT TEAM_NAME
FROM TEAM
WHERE EXISTS(
    SELECT *
    FROM (
        SELECT TEAM_ID
        FROM TEAM_MEM
        WHERE MEM_ID LIKE '%00%') X
    WHERE TEAM.TEAM_ID = X.TEAM_ID);

-- Type 6: Selection + Projection + IN predicates
-- 6.1
SELECT T1.CLASS_ID, T1.MAX_NUM
FROM TRAIN_REG T1
WHERE T1.CLASS_ID IN (
    SELECT T2.CLASS_ID
    FROM TRAIN_REG T2
    WHERE T2.SUBJECT = 'Dribbling');
-- 6.2
SELECT TEAM_NAME
FROM TEAM
WHERE TEAM_ID IN (
    SELECT TEAM_ID
    FROM TEAM_MEM
    WHERE MEM_ID LIKE '%00%');

-- Type 7: In-line view를 활용한 Query
-- 7.1
SELECT TEAM.TEAM_NAME
FROM TEAM NATURAL JOIN (
    SELECT TEAM_MEM.TEAM_ID, COUNT(TEAM_MEM.MEM_ID) AS TEAM_MEM_CNT
    FROM TEAM_MEM
    GROUP BY TEAM_MEM.TEAM_ID
    HAVING COUNT(TEAM_MEM.MEM_ID) > 3);
-- 7.2
SELECT DATE_TIME, PLACE, SUBJECT
FROM TRAINING NATURAL JOIN (
    SELECT TRAIN_REG.CLASS_ID, PLACE, SUBJECT
    FROM TRAIN_REG
    WHERE SUBJECT = 'Shooting');

--Type 8: Multi-way join with join predicates in WHERE + ORDER BY
-- 8.1
SELECT U.ID_NUMBER, U.NAME, U.JOB, M.PREPAID_MONEY
FROM USERS U, MEMBER M
WHERE U.SEX = 'M' AND M.PREPAID_MONEY > 10000 AND U.ID_NUMBER = M.ID_NUMBER
ORDER BY PREPAID_MONEY DESC;
-- 8.2
SELECT T1.CLASS_ID, T1.MAX_NUM
FROM TRAIN_REG T1
WHERE EXISTS(
    SELECT CLASS_ID
    FROM (
        SELECT T2.CLASS_ID
        FROM TRAIN_REG T2
        WHERE T2.SUBJECT = 'Dribbling') X
    WHERE X.CLASS_ID = T1.CLASS_ID)
ORDER BY MAX_NUM;

-- Type 9: Aggregation + multi-way join with join predicates + with GROUP BY + ORDER BY
-- 9.1
SELECT TEAM.TEAM_ID, TEAM_NAME, COUNT(TEAM_MEM.MEM_ID) AS TEAM_MEM_CNT
FROM TEAM INNER JOIN TEAM_MEM ON TEAM.TEAM_ID = TEAM_MEM.TEAM_ID
GROUP BY (TEAM.TEAM_ID, TEAM_NAME)
HAVING COUNT(TEAM_MEM.MEM_ID) > 5
ORDER BY COUNT(TEAM_MEM.MEM_ID);
-- 9.2
SELECT TRAINING.CLASS_ID, COUNT(TRAIN_ENROLLS.TUTEE_ID) AS CLASS_MEM_CNT
FROM TRAINING INNER JOIN TRAIN_ENROLLS ON TRAINING.CLASS_ID = TRAIN_ENROLLS.CLASS_ID
WHERE ABS(TRAINING.DATE_TIME - SYSDATE) > 50
GROUP BY TRAINING.CLASS_ID
HAVING COUNT(TRAIN_ENROLLS.TUTEE_ID) > 5
ORDER BY CLASS_MEM_CNT DESC;

-- Type 10: SET operation (UNION, SET DIFFERENCE, INTERSECT 등중 하나)를 활용한 query
-- 10.1
SELECT TEAM_NAME
FROM TEAM
WHERE TEAM_ID IN (
    (SELECT DISTINCT TEAM_ID
    FROM TEAM_MEM
    WHERE MEM_ID LIKE '%11%')
    UNION
    (SELECT DISTINCT TEAM_ID
    FROM TEAM_MEM
    WHERE MEM_ID LIKE '%22%'));
-- 10.2
SELECT NAME, SEX, JOB
FROM USERS INNER JOIN (
    (SELECT ID_NUMBER
    FROM USERS
    WHERE NAME LIKE 'Kim%')
    MINUS
    (SELECT ID_NUMBER
    FROM MEMBER
    WHERE PREPAID_MONEY > 50000)) X ON USERS.ID_NUMBER = X.ID_NUMBER;



