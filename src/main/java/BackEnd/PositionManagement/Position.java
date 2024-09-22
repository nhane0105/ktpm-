package BackEnd.PositionManagement;

public class Position {

    private String positionId, positionName;
    private double positionSalaryAllowance;
    private boolean deleteStatus;

    public Position(String positionId, String positionName, double positionSalaryAllowance) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionSalaryAllowance = positionSalaryAllowance;
        this.deleteStatus = false;
    }

    public Position(String positionId, String positionName, double positionSalaryAllowance, boolean deleteStatus) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionSalaryAllowance = positionSalaryAllowance;
        this.deleteStatus = deleteStatus;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getPositionSalaryAllowance() {
        return this.positionSalaryAllowance;
    }

    public void setPositionSalaryAllowance(double positionSalaryAllowance) {
        this.positionSalaryAllowance = positionSalaryAllowance;
    }

    public boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
