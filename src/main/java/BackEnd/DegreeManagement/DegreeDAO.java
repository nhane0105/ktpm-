package BackEnd.DegreeManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;

public class DegreeDAO {

    ConnectDB dbConnection;

    public DegreeDAO() {
    }

    public ArrayList<Degree> getAllDegree() {
        ArrayList<Degree> degreeList = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Degrees";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String degreeId = rs.getString("degreeId"),
                            degreeName = rs.getString("degreeName");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    degreeList.add(new Degree(degreeId, degreeName, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Degrees");
        } finally {
            dbConnection.closeConnect();
        }
        return degreeList;
    }

    public Boolean addNewDegree(Degree degree) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "INSERT INTO Degrees VALUES ('" + degree.getDegreeId() + "', '" + degree.getDegreeName()
                    + "', '" + degree.getDeleteStatus() + "')";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean updateDegree(Degree degree) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Degrees SET degreeName = '" + degree.getDegreeName() + "' WHERE degreeId = '"
                    + degree.getDegreeId() + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteDegree(Degree degree) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Degrees SET deleteStatus = 1 WHERE degreeId = '" + degree.getDegreeId() + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Degree getDegreeById(String degreeId) {
        dbConnection = new ConnectDB();
        Degree degree = null;
        try {
            String query = "SELECT * FROM Degrees WHERE degreeId = '" + degreeId + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String degreeName = rs.getString("degreeName");
                boolean deleteStatus = rs.getBoolean("deleteStatus");
                degree = new Degree(degreeId, degreeName, deleteStatus);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Degrees");
        } finally {
            dbConnection.closeConnect();
        }
        return degree;
    }
}
