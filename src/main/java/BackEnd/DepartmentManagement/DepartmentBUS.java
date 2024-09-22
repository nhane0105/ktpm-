package BackEnd.DepartmentManagement;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DepartmentBUS {

    private ArrayList<Department> departmentList = new ArrayList<>();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    public DepartmentBUS() {
        departmentList = departmentDAO.getAllDepartments();
    }

    public ArrayList<Department> getDepartmentList() {
        return departmentList;
    }

    public void readDB() {
        departmentList = departmentDAO.getAllDepartments();
    }

    public String getNextID() {
        String lastID = departmentList.get(departmentList.size() - 1).getDepartmentId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Department getDepartmentById(String departmentId) {
        return departmentDAO.getDepartmentById(departmentId);
    }

    public void addDepartment(Department department) {
        Boolean ok = departmentDAO.addNewDepartment(department);

        if (ok) {
            departmentList.add(department);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void addDepartmentExcel(Department department) {
        Boolean ok = departmentDAO.addNewDepartment(department);

        if (ok) {
            departmentList.add(department);
        }
    }

    public void updateDepartment(Department department) {
        Boolean ok = departmentDAO.updateDepartment(department);

        if (ok) {
            for (int i = 0; i < departmentList.size(); i++) {
                if (departmentList.get(i).getDepartmentId().equals(department.getDepartmentId())) {
                    departmentList.set(i, department);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteDepartment(Department department) {
        Boolean ok = departmentDAO.deleteDepartment(department);

        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Department getDepartmentByName(String departmentName) {
        for (Department department : departmentList) {
            if (department.getDepartmentName().equalsIgnoreCase(departmentName)) {
                return department;
            }
        }
        return null;
    }
}
