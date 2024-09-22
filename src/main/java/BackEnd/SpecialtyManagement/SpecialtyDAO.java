package BackEnd.SpecialtyManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;

public class SpecialtyDAO {

    ConnectDB dbConnection;

    public SpecialtyDAO() {
    }

    public ArrayList<Specialty> getAllSpecialty() {
        ArrayList<Specialty> specialtyList = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Specialties";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String specialtyId = rs.getString("specialtyId"),
                            specialtyName = rs.getString("specialtyName");
                    int specialtyBaseSalary = rs.getInt("baseSalary");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    specialtyList.add(new Specialty(specialtyId, specialtyName, specialtyBaseSalary, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Specialties");
        } finally {
            dbConnection.closeConnect();
        }
        return specialtyList;
    }

    public Boolean addNewSpecialty(Specialty specialty) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "INSERT INTO Specialties VALUES ('" + specialty.getSpecialtyId() + "', '"
                    + specialty.getSpecialtyName()
                    + "', '" + specialty.getSpecialtyBaseSalary() + "', '" + specialty.getDeleteStatus() + "')";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean updateSpecialty(Specialty specialty) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Specialties SET specialtyName = '" + specialty.getSpecialtyName()
                    + "', baseSalary = '" + specialty.getSpecialtyBaseSalary()
                    + "' WHERE specialtyId = '"
                    + specialty.getSpecialtyId() + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteSpecialty(Specialty specialty) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Specialties SET deleteStatus = 1 WHERE specialtyId = '" + specialty.getSpecialtyId()
                    + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Specialty getSpecialtyById(String specialtyId) {
        dbConnection = new ConnectDB();
        Specialty specialty = null;
        try {
            String query = "SELECT * FROM Specialties WHERE specialtyId = '" + specialtyId + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String specialtyName = rs.getString("specialtyName");
                int specialtyBaseSalary = rs.getInt("baseSalary");
                boolean deleteStatus = rs.getBoolean("deleteStatus");
                specialty = new Specialty(specialtyId, specialtyName, specialtyBaseSalary, deleteStatus);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Specialties");
        } finally {
            dbConnection.closeConnect();
        }
        return specialty;
    }
}
