package BackEnd.EmployeesRewardsCriticismManagement;

import BackEnd.CriticismManagement.Criticism;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.RewardManagement.Reward;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeesRewardsCriticism {

    private String employeeId, rewardId, criticismId;
    private Employee employee;
    private Reward reward;
    private Criticism criticism;
    private String createdAt;
    private int faultCount, rewardCount;

    public EmployeesRewardsCriticism(String employeeId, String rewardId, String criticismId, int faultCount,
            int rewardCount, String createdAt) {
        this.employeeId = employeeId;
        this.rewardId = rewardId;
        this.criticismId = criticismId;
        this.createdAt = createdAt;
        this.faultCount = faultCount;
        this.rewardCount = rewardCount;
    }

    public EmployeesRewardsCriticism(Employee employee, Reward reward, int rewardCount, Criticism criticism,
            int faultCount, String createdAt) {
        this.employee = employee;
        this.reward = reward;
        this.criticism = criticism;
        this.createdAt = createdAt;
        this.faultCount = faultCount;
        this.rewardCount = rewardCount;
    }

    public EmployeesRewardsCriticism(Employee employee, Reward employeeReward, int rewardCount,
            Criticism employeeCriticism, int faultCount) {
        this.employee = employee;
        this.reward = employeeReward;
        this.rewardCount = rewardCount;
        this.criticism = employeeCriticism;
        this.faultCount = faultCount;
    }
    
    public static ArrayList<String> getHeaderReward() {
        return new ArrayList<>(Arrays.asList("Mã Nhân Viên","Tên nhân viên" ,"Tên Khen Thưởng", "Số Lần","Tiền Thưởng","Ngày Tạo"));
    }
     public static ArrayList<String> getHeaderCriticism() {
        return new ArrayList<>(Arrays.asList("Mã Nhân Viên","Tên nhân viên" ,"Tên Kỷ Luật", "Số Lần","Tiền Phạt","Ngày Tạo"));
    }
  
      public Object getPropertyByIndexCriticism(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getEmployee().getId();
                break;

//            case 1:
//                result = getReward().getRewardId();
//                break;
//            case 2:
//                result = getReward().getRewardName();
//                break;
            case 1:
                result = getEmployee().getFullName();
                break; 
            case 2:
                result = getCriticism().getCriticismName();
                break;     
            case 3:
                 result = String.valueOf(getFaultCount());
            break;
             case 4:
                 result = String.valueOf(getCriticism().getJudgement());
            break;
            case 5:
                 result = getCreatedAt();
            break;

            default:
                break;
        }
        return result;
    }
       public Object getPropertyByIndexReward(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getEmployee().getId();
                break;         
            case 1:
                result = getEmployee().getFullName();
                break; 
            case 2:
                result = getReward().getRewardName();
                break; 
            case 3:
                 result = String.valueOf(getRewardCount());
            break;
             case 4:
                 result = String.valueOf(getReward().getReward());
            break;
            case 5:
                 result = getCreatedAt();
            break;

            default:
                break;
        }
        return result;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public String getCriticismId() {
        return criticismId;
    }

    public void setCriticismId(String criticismId) {
        this.criticismId = criticismId;
    }

    public Criticism getCriticism() {
        return criticism;
    }

    public void setCriticism(Criticism criticism) {
        this.criticism = criticism;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(int faultCount) {
        this.faultCount = faultCount;
    }

    public int getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(int rewardCount) {
        this.rewardCount = rewardCount;
    }

    @Override
    public String toString() {
        return "{" +
                " employeeId='" + getEmployee().getId() + "'" +
                ", rewardId='" + getReward().getRewardId() + "'" +
                ", rewardName='" + getReward().getRewardName() + "'" +
                ", reward='" + getReward().getReward() + "'" +
                ", rewardCount='" + getRewardCount() + "'" +
                ", criticismId='" + getCriticism().getCriticismId() + "'" +
                ", criticismName='" + getCriticism().getCriticismName() + "'" +
                ", judgement='" + getCriticism().getJudgement() + "'" +
                ", faultCount='" + getFaultCount() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                "}";
    }

}
