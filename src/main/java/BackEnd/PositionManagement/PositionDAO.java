package BackEnd.PositionManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;

public class PositionDAO {

    ConnectDB dbConnection;

    public PositionDAO() {
    }

    public ArrayList<Position> getAllPosition() {
        ArrayList<Position> positionList = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Positions";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String positionId = rs.getString("positionId"),
                            positionName = rs.getString("positionName");
                    double positionSalaryAllowance = rs.getDouble("positionSalaryAllowance");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    positionList.add(new Position(positionId, positionName, positionSalaryAllowance, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Positions");
        } finally {
            dbConnection.closeConnect();
        }
        return positionList;
    }

    public Boolean addNewPosition(Position position) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "INSERT INTO Positions VALUES ('" + position.getPositionId() + "', '"
                    + position.getPositionName() + "', '" + position.getPositionSalaryAllowance() + "', '"
                    + position.getDeleteStatus() + "')";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean updatePosition(Position position) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Positions SET positionName = '" + position.getPositionName()
                    + "', positionSalaryAllowance = '" + position.getPositionSalaryAllowance()
                    + "' WHERE positionId = '" + position.getPositionId() + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean deletePosition(Position position) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Positions SET deleteStatus = 1 WHERE positionId = '" + position.getPositionId()
                    + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Position getPositionById(String positionId) {
        dbConnection = new ConnectDB();
        Position position = null;
        try {
            String query = "SELECT * FROM Positions WHERE positionId = '" + positionId + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String positionName = rs.getString("positionName");
                double positionSalaryAllowance = rs.getDouble("positionSalaryAllowance");
                boolean deleteStatus = rs.getBoolean("deleteStatus");
                position = new Position(positionId, positionName, positionSalaryAllowance, deleteStatus);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Positions");
        } finally {
            dbConnection.closeConnect();
        }
        return position;
    }
}
