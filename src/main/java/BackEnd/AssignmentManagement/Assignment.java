package BackEnd.AssignmentManagement;

import BackEnd.EmployeeManagement.Employee;
import BackEnd.ProjectsManagement.Project;
import java.util.*;

public class Assignment {
    private Employee employee;
    private Project project;
    private String employeeId, projectId;
    private boolean deleteStatus;

    public Assignment(String employeeId, String projectId, boolean deleteStatus) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.deleteStatus = deleteStatus;
    }

    public Assignment(Employee employee, String projectId, boolean deleteStatus) {
        this.employee = employee;
        this.projectId = projectId;
        this.deleteStatus = deleteStatus;
    }

    public Assignment(Employee employee, Project project, boolean deleteStatus) {
        this.employee = employee;
        this.project = project;
        this.deleteStatus = deleteStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employyeeId) {
        this.employeeId = employyeeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "{" +
                ", employeeId='" + getEmployee().getId() + "'" +
                ", employeeName='" + getEmployee().getFullName() + "'" +
                ", projectId='" + getProject().getProjectId() + "'" +
                ", projectName='" + getProject().getProjectName() + "'" +
                ", projectPlace='" + getProject().getPlace() + "'" +
                "}";
    }

    public static ArrayList<String> getHeader() {
        return new ArrayList<>(Arrays.asList("Mã Nhân Viên", "Tên Nhân Viên", "Mã Dự Án", "Tên Dự Án", "Nơi Công Tác"));
    }

    public Object getPropertyByIndex(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getEmployee().getId();
                break;

            case 1:
                result = getEmployee().getFullName();
                break;

            case 2:
                result = getProject().getProjectId();
                break;

            case 3:
                result = getProject().getProjectName();
                break;

            case 4:
                result = getProject().getPlace();
                break;

            default:
                break;
        }
        return result;
    }
}
