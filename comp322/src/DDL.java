import java.sql.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class DDL {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_UNIVERSITY = "university";
    public static final String USER_PASSWD = "comp322";
    public static final String create_table = "CREATE TABLE ";
    public static final String left_paren = "(";
    public static final String right_paren = ") ";
    public static final String primary_key = "PRIMARY KEY ";
    public static final String unique = "UNIQUE ";
    public static final String foreign_key = "FOREIGN KEY ";
    public static final String reference = "REFERENCES ";
    public static final String alter_table = "ALTER TABLE ";
    public static final String add = "ADD ";
    public static final String comma = ", ";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        String sql = "";

        try{ //jdbc 드라이버 불러오기
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Success!");
        }catch (ClassNotFoundException e){
            System.err.println("error : " + e.getMessage());
            System.exit(1);
        }

        try { // connection 설정
            conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
            System.out.println("Connected");
        }catch (SQLException e){
            System.err.println("Cannot get a connection : " + e.getLocalizedMessage());
            System.err.println("Cannot get a connection : " + e.getMessage());
            System.exit(1);
        }

        try{ // exec SQL for DDL
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            int res = 0;

            String[] table_name = new String[6];
            table_name[0] = "EMPLOYEE ";
            table_name[1] = "DEPARTMENT ";
            table_name[2] = "DEPT_LOCATIONS ";
            table_name[3] = "PROJECT ";
            table_name[4] = "WORKS_ON ";
            table_name[5] = "DEPENDENT ";

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
                    sb.append("Dnum NUMBER(3, 0) NOT NULL"+comma);
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
                    System.out.println("Table " + table_name[i] + "created");
                    conn.commit();
                }
            }

            for (int i=0; i<6; i++){
                StringBuilder sb = new StringBuilder();
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
                        sb.append(reference+"Project"+left_paren+"Pnumber"+right_paren);
                    } else {
                        sb.append(foreign_key+left_paren+"Essn"+right_paren);
                        sb.append(reference+"EMPLOYEE"+left_paren+"Ssn"+right_paren);
                    }
                    sb.append(right_paren);
                    sql = sb.toString();
                    res = stmt.executeUpdate(sql);
                    if(res == 0){
                        System.out.println("Table " + table_name[i] + "foreign key registered");
                        conn.commit();
                    }
                }
        } catch (SQLException e) {
            System.err.println("SQL error : " + e.getMessage());
            System.exit(1);
        }
    }
}