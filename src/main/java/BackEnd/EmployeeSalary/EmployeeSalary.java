package BackEnd.EmployeeSalary;

import java.util.Arrays;
import java.util.List;

import BackEnd.EmployeeManagement.Employee;

public class EmployeeSalary {
    private Employee employee;
    private double insurance;
    private double netSalary;
    private String createdAt;
    private boolean deleteStatus;

    public EmployeeSalary() {
    }

    public EmployeeSalary(Employee employee, double insurance, double netSalary, String createdAt,
            boolean deleteStatus) {
        this.employee = employee;
        this.insurance = insurance;
        this.netSalary = netSalary;
        this.createdAt = createdAt;
        this.deleteStatus = deleteStatus;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getInsurance() {
        return this.insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getNetSalary() {
        return this.netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public List<Object> toList() {
        try {
            return Arrays.asList(employee.getId(), insurance, netSalary, createdAt, deleteStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "{" +
                " employee='" + getEmployee().getId() + "'" +
                ", insurance='" + getInsurance() + "'" +
                ", netSalary='" + getNetSalary() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", deleteStatus='" + getDeleteStatus() + "'" +
                "}";
    }

}