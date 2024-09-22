package BackEnd.EmployeeSalary;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import BackEnd.EmployeesRewardsCriticismManagement.EmployeesRewardsCriticism;

public class EmployeeSalaryBUS {
    ArrayList<EmployeeSalary> employeeSalaryList = new ArrayList<>();
    ArrayList<EmployeeSalary> employeeSalaryListById = new ArrayList<>();
    EmployeeSalaryDAO employeeSalaryDAO = new EmployeeSalaryDAO();

    public EmployeeSalaryBUS() {
        employeeSalaryList = employeeSalaryDAO.getAllEmployeeSalary();
    }

    public ArrayList<EmployeeSalary> getAllEmployeeSalary() {
        return employeeSalaryList;
    }

    public void readDB() {
        employeeSalaryList = employeeSalaryDAO.getAllEmployeeSalary();
    }

    public void addEmployeeSalary(EmployeeSalary employeeSalary) {
        Boolean ok = employeeSalaryDAO.addEmployeeSalary(employeeSalary);
        if (ok) {
            employeeSalaryList.add(employeeSalary);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteEmployeeSalary(EmployeeSalary employeeSalary) {
        Boolean ok = employeeSalaryDAO.deleteEmployeeSalary(employeeSalary);
        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public EmployeeSalary getEmployeeSalaryById(String id) {
        return employeeSalaryDAO.getEmployeeSalaryById(id);
    }

    public ArrayList<Object> calculateSalary(String employeeId) {
        ArrayList<Object> salaryInfo = new ArrayList<>();
        salaryInfo = employeeSalaryDAO.getSalaryDetail(employeeId);

        int baseSalary = (int) salaryInfo.get(1);
        double positionSalaryAllowance = (double) salaryInfo.get(2);
        double insurance = 10.5;
        int netSalary = baseSalary;

        ArrayList<EmployeesRewardsCriticism> list = (ArrayList<EmployeesRewardsCriticism>) salaryInfo.get(3);

        for (EmployeesRewardsCriticism object : list) {
            if (!object.getReward().getRewardId().equals("RE001")) {
                netSalary += object.getReward().getReward() * object.getRewardCount();
            }

            if (!object.getCriticism().getCriticismId().equals("CR001")) {
                netSalary -= object.getCriticism().getJudgement() * object.getFaultCount();
            }
        }
        netSalary += (int) (netSalary * positionSalaryAllowance);
        salaryInfo.add(netSalary);
        netSalary -= (int) (netSalary * insurance / 100);
        salaryInfo.add(netSalary);

        return salaryInfo;
    }

    public void getEmployeeSalariesByEmployeeId(String employeeId) {
        employeeSalaryListById = employeeSalaryDAO.getEmployeeSalariesByEmployeeId(employeeId);
    }

    public ArrayList<EmployeeSalary> getEmployeeSalaryListById() {
        return employeeSalaryListById;
    }
}
