import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Lab7JDBC {
    // Commons
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_UNIVERSITY = "university";
    public static final String USER_PASSWD = "comp322";
    public static final String left_paren = "(";
    public static final String right_paren = ") ";
    public static final String comma = ", ";
    // DDLs
    public static final String create_table = "CREATE TABLE ";
    public static final String not_null = "NOT NULL";
    public static final String primary_key = "PRIMARY KEY ";
    public static final String unique = "UNIQUE ";
    public static final String foreign_key = "FOREIGN KEY ";
    public static final String reference = "REFERENCES ";
    public static final String alter_table = "ALTER TABLE ";
    public static final String add = " ADD ";
    // DMLs
    public static final String insert_into = "INSERT INTO ";
    public static final String values = " VALUES";

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        String sql = "";

        //jdbc 드라이버 불러오기
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Success!");
        }catch (ClassNotFoundException e){
            System.err.println("error : " + e.getMessage());
            System.exit(1);
        }
        // connection 설정
        try {
            conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
            System.out.println("Connected");
        }catch (SQLException e){
            System.err.println("Cannot get a connection : " + e.getLocalizedMessage());
            System.err.println("Cannot get a connection : " + e.getMessage());
            System.exit(1);
        }

        String[] table_name = new String[6];
        table_name[0] = "EMPLOYEE";
        table_name[1] = "DEPARTMENT";
        table_name[2] = "DEPT_LOCATIONS";
        table_name[3] = "PROJECT";
        table_name[4] = "WORKS_ON";
        table_name[5] = "DEPENDENT";

        // insert type : String(false) vs int, decimal, number(true)
        boolean[][] type = new boolean[6][10];
        type[0][9] = true;
        type[1][1] = true;
        type[2][0] = true;
        type[3][1] = true;
        type[3][3] = true;
        type[4][1] = true;
        type[4][2] = true;


        // exec SQL for DDL (create)
        try{
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            int res = 0;

            for(int i=0; i<6; i++){
                StringBuilder sb = new StringBuilder();
                sb.append(create_table).append(table_name[i]).append(left_paren);
                if(i == 0){ // EMPLOYEE
                    sb.append("Fname VARCHAR(15) NOT NULL"+comma);
                    sb.append("Minit CHAR"+comma);
                    sb.append("Lname VARCHAR(15) NOT NULL"+comma);
                    sb.append("Ssn CHAR(9) NOT NULL"+comma);
                    sb.append("Bdate DATE"+comma);
                    sb.append("Address VARCHAR(30)"+comma);
                    sb.append("Sex CHAR"+comma);
                    sb.append("Salary DECIMAL(10,2)"+comma);
                    sb.append("Super_ssn CHAR(9)"+comma);
                    sb.append("Dno INT NOT NULL"+comma);
                    sb.append(primary_key+left_paren+"SSN"+right_paren);
                } else if(i==1){ // DEPARTMENT
                    sb.append("Dname VARCHAR(15) NOT NULL"+comma);
                    sb.append("Dnumber INT NOT NULL"+comma);
                    sb.append("Mgr_ssn CHAR(9) NOT NULL"+comma);
                    sb.append("Mgr_start_date DATE"+comma);
                    sb.append(primary_key+left_paren+"Dnumber"+right_paren+comma);
                    sb.append(unique+left_paren+"dname"+right_paren);
                } else if(i==2){ // DEPT_LOCATIONS
                    sb.append("Dnumber INT NOT NULL,");
                    sb.append("Dlocation VARCHAR(20) NOT NULL,");
                    sb.append(primary_key+left_paren+"Dnumber"+comma+"Dlocation"+right_paren);
                } else if(i==3){ // PROJECT
                    sb.append("Pname VARCHAR(15) NOT NULL"+comma);
                    sb.append("Pnumber INT NOT NULL"+comma);
                    sb.append("Plocation VARCHAR(15)"+comma);
                    sb.append("Dnum INT NOT NULL"+comma);
                    sb.append(primary_key+left_paren+"Pnumber"+right_paren+comma);
                    sb.append(unique+left_paren+"Pname"+right_paren);
                } else if(i==4){ //WORKS_ON
                    sb.append("Essn CHAR(9) NOT NULL"+comma);
                    sb.append("Pno INT NOT NULL"+comma);
                    sb.append("Hours DECIMAL(3,1)"+comma);
                    sb.append(primary_key+left_paren+"ESSN"+comma+"Pno"+right_paren);
                } else {
                    sb.append("Essn CHAR(9) NOT NULL"+comma);
                    sb.append("Dependent_name VARCHAR(15) NOT NULL"+comma);
                    sb.append("Sex CHAR"+comma);
                    sb.append("Bdate DATE"+comma);
                    sb.append("Relationship VARCHAR(8)"+comma);
                    sb.append(primary_key+left_paren+"ESSN"+comma+"Dependent_name"+right_paren);
                }
                sb.append(right_paren);
                sql = sb.toString();
                res = stmt.executeUpdate(sql);
                if(res == 0){
                    System.out.println("Table " + table_name[i] + " created");
                    conn.commit();
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }
        // exec SQL for DML by File I/O
        try{
            File fp = new File("company.txt");
            BufferedReader bf = new BufferedReader(new FileReader(fp));
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            int res = 0;
            String table_name_file = "";
            while ((table_name_file = bf.readLine())!=null) {
                String data = bf.readLine();
                String trim_table_name = table_name_file.substring(1);
                int type_index = 0;
                if(trim_table_name.equals(table_name[0])) type_index = 0;
                else if(trim_table_name.equals(table_name[1])) type_index = 1;
                else if(trim_table_name.equals(table_name[2])) type_index = 2;
                else if(trim_table_name.equals(table_name[3])) type_index = 3;
                else if(trim_table_name.equals(table_name[4])) type_index = 4;
                else if(trim_table_name.equals(table_name[5])) type_index = 5;

                String[] data_array = data.split("#");
                StringBuilder sb= new StringBuilder();
                sb.append(insert_into + trim_table_name + values +left_paren);
                for (int i = 0; i < data_array.length; i++) {
                    if(data_array[i].equals("NULL")) sb.append(data_array[i] + " ");
                    else if(!type[type_index][i]) sb.append("\'" + data_array[i] + "\' ");
                    else sb.append(data_array[i] + " ");
                    if (i != data_array.length - 1) sb.append(comma);
                }
                sb.append(right_paren);
                System.out.println(sb);
                sql = sb.toString();
                stmt.addBatch(sql);
            }
            int[] coubnt = stmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // exec SQL for DDL (foreign key)
        try {
            for (int i=0; i<6; i++){
                StringBuilder sb = new StringBuilder();
                int res = 0;
                sb.append(alter_table).append(table_name[i]).append(add).append(left_paren);
                if(i == 0){ // EMPLOYEE
                    continue; // foreign key not exist
                } else if(i==1){ // DEPARTMENT
                    sb.append(foreign_key+left_paren+"Mgr_ssn"+right_paren);
                    sb.append(reference+"EMPLOYEE"+left_paren+"Ssn"+right_paren);
                } else if(i==2){ // DEPT_LOCATIONS
                    sb.append(foreign_key+left_paren+"Dnumber"+right_paren);
                    sb.append(reference+"DEPARTMENT"+left_paren+"Dnumber"+right_paren);
                } else if(i==3){ // PROJECT
                    sb.append(foreign_key+left_paren+"Dnum"+right_paren);
                    sb.append(reference+"DEPARTMENT"+left_paren+"Dnumber"+right_paren);
                } else if(i==4){ //WORKS_ON
                    sb.append(foreign_key+left_paren+"Essn"+right_paren);
                    sb.append(reference+"EMPLOYEE"+left_paren+"Ssn"+right_paren+comma);
                    sb.append(foreign_key+left_paren+"Pno"+right_paren);
                    sb.append(reference+"PROJECT"+left_paren+"Pnumber"+right_paren);
                } else {
                    sb.append(foreign_key+left_paren+"Essn"+right_paren);
                    sb.append(reference+"EMPLOYEE"+left_paren+"Ssn"+right_paren);
                }
                sb.append(right_paren+"\n");
                sql = sb.toString();
                System.out.print(sql);
                res = stmt.executeUpdate(sql);
                conn.commit();
            }
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }
        // exec Q1
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT SEX, AVG(SALARY) ");
            sb.append("FROM EMPLOYEE ");
            sb.append("WHERE SSN IN "+left_paren);
            sb.append("SELECT DISTINCT ESSN ");
            sb.append("FROM DEPENDENT ");
            sb.append("WHERE RELATIONSHIP != \'Spouse\' "+right_paren);
            sb.append("GROUP BY SEX");
            sql = sb.toString();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rx = rs.getMetaData();
            System.out.printf("<Query 1 result>\n");
            System.out.printf("|%-15s|%-15s|\n",rx.getColumnName(1),rx.getColumnName(2));
            System.out.printf("---------------------------------\n");
            while (rs.next()){
                System.out.printf("|%-15s|%-15.2f|",rs.getString(1), rs.getFloat(2));
                System.out.printf("\n");
            }
            conn.commit();
            System.out.printf("\n");
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }

        // exec Q2
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT E1.FNAME, E1.LNAME, EX.SUPER_FNAME, EX.SUPER_LNAME ");
            sb.append("FROM EMPLOYEE E1 INNER JOIN "+left_paren);
            sb.append("SELECT E2.FNAME AS SUPER_FNAME, E2.LNAME AS SUPER_LNAME, E2.SSN ");
            sb.append("FROM EMPLOYEE E2 "+right_paren);
            sb.append("EX ON E1.SUPER_SSN = EX.SSN ");
            sb.append("WHERE E1.SSN IN "+left_paren);
            sb.append("SELECT W.ESSN ");
            sb.append("FROM WORKS_ON W ");
            sb.append("WHERE PNO NOT IN "+left_paren);
            sb.append("SELECT P.PNUMBER ");
            sb.append("FROM PROJECT P ");
            sb.append("WHERE DNUM != 1 "+right_paren+right_paren);
            sb.append("ORDER BY E1.ADDRESS ASC");
            sql = sb.toString();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rx = rs.getMetaData();
            System.out.printf("<Query 2 result>\n");
            System.out.printf("|%-15s|%-15s|%-15s|%-15s|\n",rx.getColumnName(1),rx.getColumnName(2),
                    rx.getColumnName(3),rx.getColumnName(4));
            System.out.printf("-----------------------------------------------------------------\n");
            while (rs.next()){
                System.out.printf("|%-15s|%-15s|%-15s|%-15s|", rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getString(4));
                System.out.printf("\n");
            }
            conn.commit();
            System.out.printf("\n");
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }

        // exec Q3
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT X.DNAME, X.PNAME, E.LNAME, E.FNAME, E.SALARY ");
            sb.append("FROM EMPLOYEE E INNER JOIN "+left_paren);
            sb.append("SELECT P.DNUM, P.PNAME, D.DNAME ");
            sb.append("FROM PROJECT P FULL OUTER JOIN DEPARTMENT D ON D.DNUMBER = P.DNUM ");
            sb.append("WHERE P.PLOCATION = \'Houston\'"+right_paren+" X ");
            sb.append("ON E.DNO = X.DNUM ");
            sb.append("ORDER BY X.DNAME ASC, SALARY DESC ");
            sql = sb.toString();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rx = rs.getMetaData();
            System.out.printf("<Query 3 result>\n");
            System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-15s|\n",rx.getColumnName(1),rx.getColumnName(2),
                    rx.getColumnName(3),rx.getColumnName(4),rx.getColumnName(5));
            System.out.printf("-----------------------------------------------------------------\n");
            while (rs.next()){
                System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-15d|", rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getInt(5));
                System.out.printf("\n");
            }
            conn.commit();
            System.out.printf("\n");
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }
        stmt.close();
        conn.close();
    }
}