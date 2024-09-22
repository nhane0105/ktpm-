package BackEnd.EmployeeManagement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EmployeeBUS {

    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ArrayList<Employee> employeeNotDepartmentLeaderIdList = new ArrayList<>();
    private ArrayList<Employee> employeeNotHaveAccountIdList = new ArrayList<>();
    private ArrayList<Employee> employeeNotHaveSalaryList = new ArrayList<>();
    private ArrayList<Employee> employeeSearchResult = new ArrayList<>();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public EmployeeBUS() {
        employeeList = employeeDAO.getAllEmployee();
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public ArrayList<Employee> getEmployeeIdList() {
        getNotDepartmentLeaderEmployees();
        return employeeNotDepartmentLeaderIdList;
    }

    public void getNotDepartmentLeaderEmployees() {
        employeeNotDepartmentLeaderIdList = employeeDAO.getNotDepartmentLeaderEmployees();
    }

    public ArrayList<Employee> getEmployeeNotHaveAccountIdList() {
        getNotHaveAccount();
        return employeeNotHaveAccountIdList.size() > 0 ? employeeNotHaveAccountIdList : null;
    }

    public void getNotHaveAccount() {
        employeeNotHaveAccountIdList = employeeDAO.getNotHaveAccount();
    }

    public ArrayList<Employee> getEmployeeNotHaveSalaryList() {
        getNotHaveSalary();
        return employeeNotHaveSalaryList.size() > 0 ? employeeNotHaveSalaryList : null;
    }

    public void getNotHaveSalary() {
        employeeNotHaveSalaryList = employeeDAO.getNotHaveSalary();
    }

    public void readDB() {
        employeeList = employeeDAO.getAllEmployee();
    }

    public String getNextID() {
        String lastID = employeeList.get(employeeList.size() - 1).getId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Employee getEmployeeById(String employeeId) {
        Employee employee = null;
        for (Employee emp : employeeList) {
            if (emp.getId().equals(employeeId)) {
                employee = emp;
            }
        }
        return employee;
    }

    public void addEmployee(Employee employee) {
        Boolean ok = employeeDAO.addNewEmployee(employee);

        if (ok) {
            employeeList.add(employee);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void addEmployeeExcel(Employee employee) {
        Boolean ok = employeeDAO.addNewEmployee(employee);

        if (ok) {
            employeeList.add(employee);
        }
    }

    public void updateEmployee(Employee employee) {
        Boolean ok = employeeDAO.updateEmployee(employee);

        if (ok) {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getId().equals(employee.getId())) {
                    employeeList.set(i, employee);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteEmployee(Employee employee) {
        Boolean ok = employeeDAO.deleteEmployee(employee);

        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void searchEmployeeByName(String searchValue) {
        employeeSearchResult.clear();
        for (Employee employee : employeeList) {
            if (employee.getFullName().toLowerCase().contains(searchValue.toLowerCase())) {
                employeeSearchResult.add(employee);
            }
        }
    }

    public void searchEmployeeById(String searchValue) {
        employeeSearchResult.clear();
        for (Employee employee : employeeList) {
            if (employee.getId().toLowerCase().contains(searchValue.toLowerCase())) {
                employeeSearchResult.add(employee);
            }
        }
    }

    public ArrayList<Employee> getEmployeeSearchResult() {
        return employeeSearchResult;
    }
}
