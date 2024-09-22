package BackEnd.AssignmentManagement;

import BackEnd.ConnectDB.ConnectDB;
import BackEnd.EmployeeManagement.EmployeeDAO;
import BackEnd.ProjectsManagement.ProjectDAO;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AssignmentDAO {
    private ConnectDB con;

    public AssignmentDAO() {
    }

    public ArrayList<Assignment> getAllAssignments() {
        con = new ConnectDB();
        ArrayList<Assignment> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Assignments WHERE deleteStatus = 0";
            ResultSet rs = con.sqlQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    list.add(new Assignment(new EmployeeDAO().getEmployeeById(rs.getString(1)),
                            new ProjectDAO().searchInProject(rs.getString(2)),
                            rs.getBoolean(3)));
                }
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng phân công");
        } finally {
            con.closeConnect();
        }
        return list;
    }

    public boolean addNewAssignment(Assignment asm) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "insert into Assignments(employeeId,projectId,deleteStatus) values ('" + asm.getEmployee().getId() + "','"
                    + asm.getProject().getProjectId()
                    + "','"
                    + asm.isDeleteStatus() + "')";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }

    public boolean updateAssignment(ArrayList<Object> prevState, Assignment newasm) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "update Assignments set employeeId = '" + newasm.getEmployee().getId() + "', projectId = '"
                    + newasm.getProject().getProjectId() + "', deleteStatus = '" + newasm.isDeleteStatus()
                    + "' where employeeId = '" + (String) prevState.get(1) + "' and projectId = '"
                    + (String) prevState.get(3) + "'";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }

    public boolean deleteAssignment(Assignment asm) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "update Assignments set deleteStatus = 1 where employeeId = '" + asm.getEmployee().getId()
                    + "' and projectId = '" + asm.getProject().getProjectId() + "'";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }

    public Assignment findAssignmentByEmployeeIdAndProjectId(String employeeId, String projectId) {
        con = new ConnectDB();
        Assignment assignment = null;
        try {
            String sql = "SELECT * FROM Assignments WHERE employeeId = '" + employeeId + "' AND projectId = '"
                    + projectId + "'";
            ResultSet rs = con.sqlQuery(sql);
            if (rs != null && rs.next()) {
                assignment = new Assignment(new EmployeeDAO().getEmployeeById(rs.getString(1)),
                        new ProjectDAO().searchInProject(rs.getString(2)),
                        rs.getBoolean(3));
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng phân công");
        } finally {
            con.closeConnect();
        }
        return assignment;
    }

    public ArrayList<Assignment> getEmployeeAssignmentsByEmployeeId(String employeeId) {
        con = new ConnectDB();
        ArrayList<Assignment> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Assignments WHERE employeeId = '" + employeeId + "' AND deleteStatus = 0";
            ResultSet rs = con.sqlQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    list.add(new Assignment(new EmployeeDAO().getEmployeeById(employeeId),
                            new ProjectDAO().searchInProject(rs.getString(2)),
                            rs.getBoolean(3)));
                }
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng phân công");
        } finally {
            con.closeConnect();
        }
        return list;
    }
}
