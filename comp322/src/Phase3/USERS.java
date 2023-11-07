package Phase3;

import java.io.*;
import java.sql.*;
import java.util.Random;

public class USERS {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_TERMPROJECT = "university";
    public static final String USER_PASSWD = "comp322";
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    protected static void SignUp() throws IOException, SQLException {
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
            conn = DriverManager.getConnection(URL, USER_TERMPROJECT, USER_PASSWD);
            System.out.println("Connected");
        }catch (SQLException e){
            System.err.println("Cannot get a connection : " + e.getLocalizedMessage());
            System.err.println("Cannot get a connection : " + e.getMessage());
            System.exit(1);
        }
        StringBuilder sb = new StringBuilder();
        System.out.print("SignUp\n");
        System.out.print("Insert your \"Name, Password, Sex, Year of Birth, Job\"");
        String[] UserData = new String[6]; // ID, Name, Sex, Yob, Job, Passwd

        System.out.println("Insert your name in English");
        System.out.print("Name : ");
        UserData[1] = bf.readLine();

        System.out.println("Insert new Password. Number of Character have to be between 9 and 20");
        System.out.print("Password : ");
        UserData[5] = bf.readLine();

        System.out.println("Insert your biological Sex. Male = M, Female = F");
        System.out.print("Sex : ");
        UserData[2] = bf.readLine();

        System.out.println("Insert your Year of Birth. ex) 1998");
        System.out.print("Year of Birth : ");
        UserData[3] = bf.readLine();

        System.out.println("Insert your Job in English. ex) computer engineer");
        System.out.print("Job : ");
        UserData[4] = bf.readLine();

        while (true){
            int[] randID = new int[3];
            Random rand = new Random(System.currentTimeMillis());
            String qy = "SELECT ID_NUMBER FROM USERS WHERE ID_NUMBER = ";
            randID[0] = rand.nextInt() % 1000;
            randID[1] = rand.nextInt() % 100;
            randID[2] = rand.nextInt() % 10000;
            sb.append(qy);
            sb.append("U").append(randID[0]);
            sb.append("-").append(randID[1]);
            sb.append("-").append(randID[2]);
            sql = sb.toString();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.getRow() == 0) break; // id중에 중복이 없으면 break;
        }
        System.out.println("Select your Role.");
        System.out.println("for Manager Role : Enter \"Manager\" or \"MGR\"");
        System.out.println("for Member Role : Enter \"Member\" or \"MEM\"");
        System.out.println("If you want to Quit Sign up, then enter \'q\'");
        boolean flag = false;
        while (true){
            System.out.print("Role : ");
            String UserRole = bf.readLine();
            if(UserRole.equals("MGR")||UserRole.equals("Manager")) {
                flag = true;
                break;
            }
            else if(UserRole.equals("MEM")||UserRole.equals("Member")) break;
            else if(UserRole.equals("q")) return;
            else continue;
        }
        InsertUser(UserData);
        if(flag) SignUpMgr();
        else SignUpMem();
    }
    private static void SignUpMgr(){

    }

    private static void SignUpMem(){

    }
    protected static void LogIn() throws IOException {
        System.out.print("SignUp\n");
        System.out.println("Insert your \"Name, Password, Sex, Year of Birth, Job\"");

        System.out.println("Insert your name in English");
        System.out.printf("%15s", "Name : ");
        String UserName = bf.readLine();

        System.out.println("Insert new Password. Number of Character have to be between 9 and 20");
        System.out.printf("%15s","Password : ");
        String UserPasswd = bf.readLine();

        System.out.println("Insert your biological Sex. Male = M, Female = F");
        System.out.printf("%15s","Sex : ");
        String UserSex = bf.readLine();

        System.out.println("Insert your Year of Birth. ex) 1998");
        System.out.printf("%15s","Year of Birth : ");
        String UserYob = bf.readLine();

        System.out.println("Insert your Job in English. ex) computer engineer");
        System.out.printf("%15s","Job : ");
        String UserJob = bf.readLine();

        System.out.println("Select your Role. Manager : MGR, Member : MEM");
        System.out.printf("%15s","Role : ");
        String UserRole = bf.readLine();


    }
    private static void InsertUser(String[] data){

    }
}
