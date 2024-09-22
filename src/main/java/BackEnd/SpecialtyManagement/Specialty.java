package BackEnd.SpecialtyManagement;

public class Specialty {

    private String specialtyId, specialtyName;
    private int specialtyBaseSalary;
    private boolean deleteStatus;

    public Specialty(String specialtyId, String specialtyName, int specialtyBaseSalary) {
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.specialtyBaseSalary = specialtyBaseSalary;
        this.deleteStatus = false;
    }

    public Specialty(String specialtyId, String specialtyName, int specialtyBaseSalary, boolean deleteStatus) {
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.specialtyBaseSalary = specialtyBaseSalary;
        this.deleteStatus = deleteStatus;
    }

    public String getPositionId() {
        return specialtyId;
    }

    public void setPositionId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getPositionName() {
        return specialtyName;
    }

    public void setPositionName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyId() {
        return this.specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return this.specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public int getSpecialtyBaseSalary() {
        return this.specialtyBaseSalary;
    }

    public void setSpecialtyBaseSalary(int specialtyBaseSalary) {
        this.specialtyBaseSalary = specialtyBaseSalary;
    }

    public boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
