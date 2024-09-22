package BackEnd.EmployeeManagement;

import BackEnd.ConnectDB.ConnectDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import BackEnd.DegreeManagement.DegreeDAO;
import BackEnd.DepartmentManagement.Department;
import BackEnd.DepartmentManagement.DepartmentDAO;
import BackEnd.PositionManagement.PositionDAO;
import BackEnd.SpecialtyManagement.SpecialtyDAO;

public class EmployeeDAO {

    ConnectDB dbConnection;

    public EmployeeDAO() {
    }

    public ArrayList<Employee> getAllEmployee() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Employees WHERE id NOT LIKE 'ADM%'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String id = rs.getString("id"),
                            fullName = rs.getString("fullName"),
                            gender = rs.getString("gender"),
                            birthDate = rs.getString("birthDate"),
                            phoneNumber = rs.getString("phoneNumber"),
                            ethnicGroup = rs.getString("ethnicGroup"),
                            type = rs.getString("type"),
                            religion = rs.getString("religion"),
                            degreeId = rs.getString("degreeId"),
                            nation = rs.getString("nation"),
                            positionId = rs.getString("positionId"),
                            departmentId = rs.getString("departmentId"),
                            specialtyId = rs.getString("specialtyId");
                    boolean employStatus = rs.getBoolean("employStatus"),
                            deleteStatus = rs.getBoolean("deleteStatus");

                    employeeList.add(
                            new Employee(id, fullName, gender, birthDate, phoneNumber, ethnicGroup, type, religion,
                                    new DegreeDAO().getDegreeById(degreeId), nation,
                                    new PositionDAO().getPositionById(positionId),
                                    new DepartmentDAO().getDepartmentById(departmentId),
                                    new SpecialtyDAO().getSpecialtyById(specialtyId), employStatus, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Employees ");
        } finally {
            dbConnection.closeConnect();
        }
        return employeeList;
    }

    public Boolean addNewEmployee(Employee employee) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "INSERT INTO Employees (id, fullName, gender, birthDate, phoneNumber, ethnicGroup, type, religion, degreeId, nation, positionId, departmentId, specialtyId, employStatus, deleteStatus) VALUES ('"
                    + employee.getId() + "', '"
                    + employee.getFullName() + "', '"
                    + employee.getGender() + "', '"
                    + employee.getBirthDate() + "', '"
                    + employee.getPhoneNumber() + "', '"
                    + employee.getEthnicGroup() + "', '"
                    + employee.getType() + "', '"
                    + employee.getReligion() + "', '"
                    + employee.getDegree().getDegreeId() + "', '"
                    + employee.getNation() + "', '"
                    + employee.getPosition().getPositionId() + "', '"
                    + employee.getDepartment().getDepartmentId() + "', '"
                    + employee.getSpecialty().getSpecialtyId() + "', '"
                    + employee.getEmployStatus() + "', '"
                    + employee.getDeleteStatus() + "')";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean updateEmployee(Employee employee) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Employees SET fullName = '" + employee.getFullName()
                    + "', gender = '" + employee.getGender()
                    + "', birthDate = '" + employee.getBirthDate()
                    + "', phoneNumber = '" + employee.getPhoneNumber()
                    + "', ethnicGroup = '" + employee.getEthnicGroup()
                    + "', type = '" + employee.getType()
                    + "', religion = '" + employee.getReligion()
                    + "', degreeId = '" + employee.getDegree().getDegreeId()
                    + "', nation = '" + employee.getNation()
                    + "', positionId = '" + employee.getPosition().getPositionId()
                    + "', departmentId = '" + employee.getDepartment().getDepartmentId()
                    + "', specialtyId = '" + employee.getSpecialty().getPositionId()
                    + "', employStatus = '" + employee.getEmployStatus()
                    + "', deleteStatus = '" + employee.getDeleteStatus()
                    + "' WHERE id = '"
                    + employee.getId() + "'";
            ;
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteEmployee(Employee employee) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Employees SET deleteStatus = 1, employStatus = 0 WHERE id = '"
                    + employee.getId()
                    + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Employee getEmployeeById(String id) {
        dbConnection = new ConnectDB();
        Employee employee = null;
        try {
            String query = "SELECT * FROM Employees WHERE id = '" + id + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String fullName = rs.getString("fullName"),
                        gender = rs.getString("gender"),
                        birthDate = rs.getString("birthDate"),
                        phoneNumber = rs.getString("phoneNumber"),
                        ethnicGroup = rs.getString("ethnicGroup"),
                        type = rs.getString("type"),
                        religion = rs.getString("religion"),
                        degreeId = rs.getString("degreeId"),
                        nation = rs.getString("nation"),
                        positionId = rs.getString("positionId"),
                        specialtyId = rs.getString("specialtyId");

                employee = new Employee(id, fullName, gender, birthDate, phoneNumber, ethnicGroup, type, religion,
                        new DegreeDAO().getDegreeById(degreeId), nation,
                        new PositionDAO().getPositionById(positionId), new Department(),
                        new SpecialtyDAO().getSpecialtyById(specialtyId));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Employees ");
        } finally {
            dbConnection.closeConnect();
        }
        return employee;
    }

    public ArrayList<Employee> getNotDepartmentLeaderEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT E.id, E.deleteStatus" +
                    " FROM Employees E" +
                    " LEFT JOIN Departments D ON E.departmentId = D.departmentId" +
                    " WHERE (E.id NOT LIKE 'ADM%')" +
                    " AND E.id NOT IN (SELECT departmentLeader FROM Departments WHERE departmentLeader IS NOT NULL);";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    employeeList.add(
                            new Employee(id, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error reading data from Employees and Departments table: " + ex.getMessage());
        } finally {
            dbConnection.closeConnect();
        }
        return employeeList;

    }

    public ArrayList<Employee> getNotHaveAccount() {
        dbConnection = new ConnectDB();
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            String query = "SELECT E.id, E.deleteStatus " +
                    "FROM Employees E " +
                    "LEFT JOIN Account A ON A.userId = E.id " +
                    "WHERE A.userId IS NULL;";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    employeeList.add(
                            new Employee(id, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error reading data from Employees and Account table: " + ex.getMessage());
        } finally {
            dbConnection.closeConnect();
        }
        return employeeList;
    }

    public ArrayList<Employee> getNotHaveSalary() {
        dbConnection = new ConnectDB();
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT E.id, E.deleteStatus" +
                    " FROM Employees E" +
                    " WHERE E.id NOT LIKE 'ADM%' AND E.deleteStatus = 0 " +
                    " AND NOT EXISTS (" +
                    "     SELECT 1" +
                    "     FROM EmployeeSalaries t1" +
                    "     WHERE t1.employeeId = E.id" +
                    "     AND MONTH(t1.createdAt) = MONTH(GETDATE())" +
                    "     AND YEAR(t1.createdAt) = YEAR(GETDATE())" +
                    " );";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    employeeList.add(
                            new Employee(id, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error reading data from Employees and EmployeeSalaries table: " + ex.getMessage());
        } finally {
            dbConnection.closeConnect();
        }
        return employeeList;
    }
}
