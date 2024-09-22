package BackEnd.ProjectsManagement;

import BackEnd.DepartmentManagement.Department;

public class Project {
    private String projectId, projectName, departmentId, beginAt, completeAt, place;
    private Department department;
    private boolean deleteStatus;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(String completeAt) {
        this.completeAt = completeAt;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Project(String projectId, String projectName, String departmentId, String beginAt, String completeAt,
            String place, boolean deleteStatus) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.departmentId = departmentId;
        this.beginAt = beginAt;
        this.completeAt = completeAt;
        this.place = place;
        this.deleteStatus = deleteStatus;
    }

    public Project(String projectId, String projectName, Department department, String beginAt, String completeAt,
            String place, boolean deleteStatus) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.department = department;
        this.beginAt = beginAt;
        this.completeAt = completeAt;
        this.place = place;
        this.deleteStatus = deleteStatus;
    }
}
