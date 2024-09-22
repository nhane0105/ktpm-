package BackEnd.DegreeManagement;

public class Degree {

    private String degreeId, degreeName;
    private boolean deleteStatus;

    public Degree(String degreeId, String degreeName) {
        this.degreeId = degreeId;
        this.degreeName = degreeName;
        this.deleteStatus = false;
    }

    public Degree(String degreeId, String degreeName, boolean deleteStatus) {
        this.degreeId = degreeId;
        this.degreeName = degreeName;
        this.deleteStatus = deleteStatus;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
}
