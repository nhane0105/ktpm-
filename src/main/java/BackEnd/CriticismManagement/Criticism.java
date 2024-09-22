
package BackEnd.CriticismManagement;

import java.util.ArrayList;
import java.util.Arrays;

public class Criticism {

    private String criticismId, criticismName;
    private int judgement;
    private boolean deleteStatus;

    public Criticism(String criticismId, String criticismName, int judgement) {
        this.criticismId = criticismId;
        this.criticismName = criticismName;
        this.judgement = judgement;
        this.deleteStatus = false;

    }
     public static ArrayList<String> getHeader() {
        return new ArrayList<>(Arrays.asList("Mã Kỷ Luật", "Tên Kỷ Luật", "Tiền Phạt"));
        
    }

    public Criticism() {
        this.criticismId = "CR001";
        this.criticismName = "Không có lỗi";
        this.judgement = 0;
        this.deleteStatus = false;
    }

    public String getCriticismId() {
        return criticismId;
    }

    public void setCriticismId(String criticismId) {
        this.criticismId = criticismId;
    }

    public String getCriticismName() {
        return criticismName;
    }

    public void setCriticismName(String criticismName) {
        this.criticismName = criticismName;
    }

    public int getJudgement() {
        return judgement;
    }

    public void setJudgement(int judgement) {
        this.judgement = judgement;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
    
      public Object getPropertyByIndex(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getCriticismId();
                break;

            case 1:
                result = getCriticismName();
                break;

//            case 2:
//                result = getJudgement()();
//                break;

       
            default:
                break;
        }
        return result;
    }
}
