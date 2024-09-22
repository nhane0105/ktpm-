package BackEnd.DepartmentManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BackEnd.EmployeeManagement.EmployeeDAO;
import BackEnd.ConnectDB.ConnectDB;

public class DepartmentDAO {

    private ConnectDB dbConnection;

    public DepartmentDAO() {
        dbConnection = new ConnectDB();
    }

    public ArrayList<Department> getAllDepartments() {
        dbConnection = new ConnectDB();
        ArrayList<Department> departmentList = new ArrayList<>();
        String query = "SELECT * FROM Departments WHERE departmentId != 'DP000' ";
        try {
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    Department department = new Department();
                    department.setDepartmentId(rs.getString("departmentId"));
                    department.setDepartmentName(rs.getString("departmentName"));
                    department.setDepartmentLeader(new EmployeeDAO().getEmployeeById(rs.getString("departmentLeader")));
                    department.setDeleteStatus(rs.getBoolean("deleteStatus"));
                    department.setNumberOfMembers(getNumberOfMembersInDepartment(department));
                    departmentList.add(department);
                }
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Degrees");
        } finally {
            dbConnection.closeConnect();
        }
        return departmentList;
    }

    public int getNumberOfMembersInDepartment(Department department) {
        dbConnection = new ConnectDB();
        int numberOfMembers = 0;
        String query = "SELECT COUNT(departmentId) FROM Employees WHERE departmentId= '" + department.getDepartmentId()
                + "' AND deleteStatus= 0";
        try {
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs.next()) {
                numberOfMembers = rs.getInt(1);

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfMembers;
    }

    public Department getDepartmentById(String departmentId) {
        dbConnection = new ConnectDB();
        Department department = null;
        String query = "SELECT * FROM Departments WHERE departmentId= '" + departmentId + "'";
        try {
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs.next()) {
                department = new Department();
                department.setDepartmentId(rs.getString("departmentId"));
                department.setDepartmentName(rs.getString("departmentName"));
                department.setDepartmentLeader(new EmployeeDAO().getEmployeeById(rs.getString("departmentLeader")));
                department.setDeleteStatus(rs.getBoolean("deleteStatus"));
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Degrees");
        } finally {
            dbConnection.closeConnect();
        }
        return department;
    }

    public boolean addNewDepartment(Department department) {
        dbConnection = new ConnectDB();
        boolean success = false;
        String query = "INSERT INTO Departments (departmentId, departmentName, departmentLeader, deleteStatus) VALUES ('"
                + department.getDepartmentId() + "', '" + department.getDepartmentName() + "', '"
                + department.getDepartmentLeader().getId() + "', '" + department.isDeleteStatus() + "')";
        try {
            success = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return success;
    }

    public boolean updateDepartment(Department department) {
        dbConnection = new ConnectDB();
        boolean success = false;
        String query = "UPDATE Departments SET departmentName= '" + department.getDepartmentName()
                + "', departmentLeader= '" + department.getDepartmentLeader().getId()
                + "', deleteStatus= '" + department.isDeleteStatus() + "' WHERE departmentId= '"
                + department.getDepartmentId() + "'";
        try {
            success = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return success;
    }

    public boolean deleteDepartment(Department department) {
        dbConnection = new ConnectDB();
        boolean success = false;
        String query = "UPDATE Departments SET deleteStatus = 1 WHERE departmentId= '" + department.getDepartmentId()
                + "'";
        try {
            success = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return success;
    }
}
