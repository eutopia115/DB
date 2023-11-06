    CREATE OR REPLACE PROCEDURE ComputeAvgHours(
        DEPT_ID IN NUMBER,
        MAX_AVG_HOURS OUT NUMBER
    ) AS
    BEGIN
        DECLARE
            CURSOR CUR IS
            SELECT P.DNUM, P.PNUMBER, AVG(CAST(W.HOURS AS DECIMAL(10,2))) AS AVERAGE
            FROM WORKS_ON W INNER JOIN PROJECT P ON W.PNO = P.PNUMBER
            WHERE P.DNUM = DEPT_ID
            GROUP BY P.DNUM, P.PNUMBER
            ORDER BY P.PNUMBER;
            V_DNO PROJECT.DNUM%TYPE;
            V_PNO PROJECT.PNUMBER%TYPE;
            V_AVG DECIMAL(10,2);
        BEGIN
            OPEN CUR;
            DBMS_OUTPUT.PUT_LINE('----------------------');
            DBMS_OUTPUT.PUT('RECEIVED DEPT NO : ');
            DBMS_OUTPUT.PUT_LINE(DEPT_ID);
            DBMS_OUTPUT.PUT_LINE('----------------------');
            DBMS_OUTPUT.PUT_LINE('DNUM | PNUM | AVERAGE');
            DBMS_OUTPUT.PUT_LINE('----------------------');
            LOOP
                FETCH CUR INTO V_DNO, V_PNO, V_AVG;
                EXIT WHEN CUR%NOTFOUND;
                DBMS_OUTPUT.PUT_LINE(V_DNO||'    | '||V_PNO||'    | '||V_AVG);
            END LOOP;
            DBMS_OUTPUT.PUT_LINE('----------------------');
            CLOSE CUR;
        END;
        DECLARE
            MAX_AVG NUMBER := 0;
        BEGIN
            DBMS_OUTPUT.PUT('CALC MAX HOURS : ');
            SELECT MAX(X.AVERAGE)
            INTO MAX_AVG
            FROM (
                SELECT W.PNO, AVG(W.HOURS) AS AVERAGE
                FROM WORKS_ON W INNER JOIN PROJECT P ON W.PNO = P.PNUMBER
                WHERE P.DNUM = DEPT_ID
                GROUP BY W.PNO) X;
            DBMS_OUTPUT.PUT_LINE(MAX_AVG);
            DBMS_OUTPUT.PUT_LINE('----------------------');
        END;
    END;
    /
