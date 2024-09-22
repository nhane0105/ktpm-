package BackEnd.StatisticsManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;

public class StatisticsDAO {
    ConnectDB dbConnection;

    public StatisticsDAO() {
    }

    public ArrayList<Object> getEmployeeGender() {
        dbConnection = new ConnectDB();
        ArrayList<Object> listGender = new ArrayList<>();
        try {
            String query = "SELECT " +
                    "    SUM(CASE WHEN gender = 'Nam' THEN 1 ELSE 0 END) AS male," +
                    "    SUM(CASE WHEN gender = 'Nữ' THEN 1 ELSE 0 END) AS female," +
                    "    COUNT(gender) AS total_count " +
                    "FROM Employees";
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    listGender.add(rs.getInt("male"));
                    double malePercent = rs.getInt("male") * 100.0 / rs.getInt("total_count");
                    listGender.add(malePercent);
                    listGender.add(rs.getInt("female"));
                    double femalePercent = rs.getInt("female") * 100.0 / rs.getInt("total_count");
                    listGender.add(femalePercent);
                    listGender.add(rs.getInt("total_count"));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Employees");
        } finally {
            dbConnection.closeConnect();
        }
        return listGender;
    }

    public ArrayList<Object> getTop5HighestSalaryEmployees() {
        dbConnection = new ConnectDB();
        ArrayList<Object> topEmployees = new ArrayList<>();
        try {
            String query = "SELECT TOP 5 employeeId, fullname, netSalary " +
                    "FROM EmployeeSalaries " +
                    "INNER JOIN Employees on EmployeeSalaries.employeeId = Employees.id " +
                    "WHERE MONTH(createdAt) = MONTH(GETDATE()) AND YEAR(createdAt) = YEAR(GETDATE()) " +
                    "ORDER BY netSalary DESC;";
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    String employeeId = rs.getString("employeeId"),
                            employeeName = rs.getString("fullname");
                    double netSalary = rs.getDouble("netSalary");
                    ArrayList<Object> employee = new ArrayList<>();
                    employee.add(employeeId);
                    employee.add(employeeName);
                    employee.add(netSalary);
                    topEmployees.add(employee);
                }
            }
//            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Employees và EmployeeSalaries");
        } finally {
            dbConnection.closeConnect();
        }
        return topEmployees;
    }

    public ArrayList<Object> getEmployeeDegree() {
        dbConnection = new ConnectDB();
        ArrayList<Object> listDegree = new ArrayList<>();
        try {
            String query = "SELECT degreeName, COUNT(Employees.degreeId) as count " +
                    "FROM Degrees " +
                    "INNER JOIN Employees on Degrees.degreeId = Employees.degreeId " +
                    "GROUP BY degreeName " +
                    "ORDER BY COUNT(Employees.degreeId) DESC;";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                int sum = 0;
                while (rs.next()) {
                    String degreeName = rs.getString("degreeName");
                    int count = rs.getInt("count");
                    sum += count;
                    ArrayList<Object> degree = new ArrayList<>();
                    degree.add(degreeName);
                    degree.add(count);
                    listDegree.add(degree);
                }
                listDegree.add(sum);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Employees và EmployeeSalaries");
        } finally {
            dbConnection.closeConnect();
        }
        return listDegree;
    }
}
