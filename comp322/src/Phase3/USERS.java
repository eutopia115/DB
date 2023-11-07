package Phase3;

import java.io.*;
import java.sql.*;
import java.util.Random;

public class USERS {
    private static final char apx = '\'';
    protected static void SignUp() throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("----------------------------------------------------");
        System.out.print("Sign Up\n");
        System.out.println("----------------------------------------------------");
        System.out.println("Insert your \"Name, Password, Sex, Year of Birth, Job\"");
        String[] UserData = new String[6]; // ID, Name, Sex, Yob, Job, Passwd

        do {
            System.out.println("Insert your name in English. Number of Character have to under 30");
            System.out.print("Name : ");
            UserData[1] = bf.readLine();
        } while (UserData[1].length() >= 30);

        do{
            System.out.println("Insert new Password. Number of Character have to be between 9 and 20");
            System.out.print("Password : ");
            UserData[5] = bf.readLine();
        } while (UserData[5].length() > 20 || UserData[5].length() < 9);

        do {
            System.out.println("Insert your biological Sex. Male = M, Female = F");
            System.out.print("Sex : ");
            UserData[2] = bf.readLine();
        } while (!UserData[2].equals("M") && !UserData[2].equals("F") && !UserData[2].equals("m") && !UserData[2].equals("f"));

        do {
            System.out.println("Insert your Year of Birth. ex) 1998");
            System.out.print("Year of Birth : ");
            UserData[3] = bf.readLine();
        } while (UserData[3].length() != 4);

        System.out.println("Insert your Job in English. ex) computer engineer");
        System.out.print("Job : ");
        UserData[4] = bf.readLine();

        while (true){
            Random rand = new Random(System.currentTimeMillis());
            StringBuilder sb = new StringBuilder();
            StringBuilder where = new StringBuilder();
            sb.append("U").append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10));
            sb.append("-").append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10));
            sb.append("-").append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10)).append(Math.abs(rand.nextInt()%10));
            UserData[0] = sb.toString();
            String[] tables = {"USERS"};
            String[] attrs = {"ID_NUMBER"};
            where.append("ID_NUMBER = "+ apx + UserData[0]+ apx);
            if(SQLx.Selectx(attrs, tables, where.toString(), "")==0) break; // id중에 중복이 없으면 break;
        }
        System.out.println("-------------------------------------------------");
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
        SQLx.Insertx("USERS",UserData);
        if(flag) SignUpMgr(UserData[0]);
        else SignUpMem(UserData[0]);
        System.out.println("----------------------------------------------------");
        System.out.printf("Your ID_NUMBER is %s\n", UserData[0]);
        bf.close();
    }
    protected static void LogIn() throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder where = new StringBuilder();
        String[] attrs = {"ID_NUMBER", "PASSWD"};
        String[] table = {"USERS"};
        String[] idps = new String[2];
        System.out.println("----------------------------------------------------");
        System.out.println("Log In");
        System.out.println("----------------------------------------------------");
        do {
            System.out.print("ID : ");
            idps[0] = bf.readLine().toUpperCase();
            System.out.println("PASSWD : ");
            idps[1] = bf.readLine().toUpperCase();
            where.append("ID_NUMBER = " + apx + idps[0] + apx + " AND " + "PASSWD = " + apx + idps[1] + apx);
        } while (SQLx.Selectx(attrs, table, where.toString(), "") != 1);
        AfterLogIn(idps);
        bf.close();
    }
    private static void AfterLogIn(String[] idps) throws SQLException, IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        if(idps[0].equals("ADMIN") && idps[1].equals("SOCCERLINK")){
            System.out.println("----------------------------------------------------");
            System.out.println("Admin Screen");
            System.out.println("----------------------------------------------------");
        }
        else {
            String[] attrs = {"ID_NUMBER"};
            String[] tableMan = {"MANAGER"};
            String[] tableMem = {"MEMBER"};
            StringBuilder where = new StringBuilder();
            where.append("ID_NUMBER = "+apx+idps[0]+apx);
            if(SQLx.Selectx(attrs, tableMan, where.toString(), "") == 1){
                System.out.println("----------------------------------------------------");
                System.out.println("Manager Screen");
                System.out.println("----------------------------------------------------");
            }
            else if(SQLx.Selectx(attrs, tableMem, where.toString(), "") == 1){
                System.out.println("----------------------------------------------------");
                System.out.println("User Screen");
                System.out.println("----------------------------------------------------");
            }
            else {
                while (true){
                    System.out.println("----------------------------------------------------");
                    System.out.println("Your Role is missed, Pick your role");
                    System.out.println("1. Member, 2. Manager, 3. Quit");
                    System.out.print("Enter the number : ");
                    int role = Integer.parseInt(bf.readLine());
                    if(role == 1) SignUpMem(idps[0]);
                    else if (role == 2) SignUpMgr(idps[1]);
                    else if (role == 3) System.exit(0);
                    else System.out.println("Wrong number!, Re-enter");
                    System.out.println("Successfully Registered, Please Press Enter and Re-execute");
                    System.exit(0);
                }
            }
        }
        bf.close();
    }
    private static void SignUpMgr(String ID) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("----------------------------------------------------");
        System.out.println("Sign up : Manager page, Insert your Account Number");
        System.out.println("----------------------------------------------------");
        System.out.print("Account number : ");
        String[] data = new String[2];
        data[0] = ID;
        data[1] = bf.readLine();
        SQLx.Insertx("MANAGER", data);
        bf.close();
    }
    private static void SignUpMem(String ID) throws SQLException {
        String[] data = new String[2];
        data[0] = ID;
        data[1] = "0";
        SQLx.Insertx("MEMBER", data);
    }

}
