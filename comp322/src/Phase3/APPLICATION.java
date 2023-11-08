package Phase3;

public class APPLICATION {
    protected static void MyPage(String id, boolean role){
        // false -> Manager, true -> Member
        ChangeMyInfo();
        CashCharge();
    }
    protected static void UserEval(String id){

    }
    protected static void Screen(String id, boolean role, int opt){
        // false -> Manager, true -> Member
        // opt = 2. Training, 3. Match, 4. Team
        Make();
        Cancel();
        Delete();
        Apply();
    }
    private static void ChangeMyInfo(){

    }
    private static void CashCharge(){

    }
    private static void Make(){

        } // insert entity rela on cascade
    private static void Cancel(){

        } // delete relationship rela
    private static void Delete(){

        } // delete entitiy rela on cascade
    private static void Apply(){

    } // insert relationship rela

}
