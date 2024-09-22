package BackEnd.CriticismManagement;

import BackEnd.ConnectDB.ConnectDB;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CriticismDAO {
    ConnectDB connection;

    public CriticismDAO() {
    }

    public ArrayList<Criticism> getAllCriticism() {
        connection = new ConnectDB();
        ArrayList<Criticism> listCriticism = new ArrayList<>();

        try {
            String qry = "SELECT * FROM Criticism";
            ResultSet rs = connection.sqlQuery(qry);

            if (rs != null) {
                while (rs.next()) {

                    String criticismId = rs.getString("criticismId");
                    String criticismName = rs.getString("criticismName");
                    int judgement = rs.getInt("judgement");

                    Criticism criticism = new Criticism(criticismId, criticismName, judgement);
                    listCriticism.add(criticism);
                }
                rs.close(); // Đóng ResultSet sau khi sử dụng
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng kỷ luật");
        } finally {
            connection.closeConnect();
        }

        return listCriticism;
    }
    // public ArrayList<Criticism> search(String columnName, String value) {
    // connection = new ConnectDB();
    // ArrayList<Criticism> listCriticism = new ArrayList<>();
    //
    // try {
    // String qry = "SELECT * FROM Criticism WHERE " + columnName + " LIKE '%" +
    // value + "%'";
    // ResultSet rs = connection.sqlQuery(qry);
    // if (rs != null) {
    // while (rs.next()) {
    // String criticismId = rs.getString("criticismId");
    // String criticismName = rs.getString("criticismName");
    // int judgement = rs.getInt("judgement");
    // listCriticism.add(new Criticism(criticismId, criticismName, judgement));
    // }
    // }
    //
    // } catch (SQLException ex) {
    // JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName
    // + " = " + value + " bảng kỷ luật");
    // } finally {
    // connection.closeConnect();
    // }
    //
    // return listCriticism;
    //
    // }
    //

    public Boolean addNewCriticism(Criticism cr) {
        connection = new ConnectDB();
        // Boolean ok = connection.sqlUpdate("Criticism(criticismId, critismName,
        // judgement, deteleStatus) VALUES ('"
        // + cr.getCriticismId() + "','"
        // + cr.getCriticismName() + "','"
        // + cr.getJudgement() + "','"
        // + cr.getDeleteStatus() + "');");
        //
        // connection.closeConnect();
        // return ok;

        Boolean ok = false;
        try {
            String query = "INSERT INTO Criticism VALUES ('" + cr.getCriticismId() + "', '" + cr.getCriticismName()
                    + "', '" + cr.getJudgement() + "', '" + cr.getDeleteStatus() + "')";
            ok = connection.sqlUpdate(query);
        } finally {
            connection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteCriticism(Criticism cr) {
        connection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Criticism SET deleteStatus = 1 WHERE criticismId = '" + cr.getCriticismId() + "'";
            ok = connection.sqlUpdate(query);
        } finally {
            connection.closeConnect();
        }
        return ok;
    }

    // public Boolean deleteAll(String _crsid) {
    // connection = new ConnectDB();
    // Boolean success = connection.sqlUpdate("DELETE FROM Criticism WHERE
    // criticismId='" + _crsid + "';");
    // connection.closeConnect();
    // return success;
    // }

    public Boolean updateCriticism(Criticism cr) {
        connection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Criticism SET criticismName = '" + cr.getCriticismName() + "', judgement = '"
                    + cr.getJudgement() + "' WHERE criticismId = '"
                    + cr.getCriticismId() + "'";
            ok = connection.sqlUpdate(query);
        } finally {
            connection.closeConnect();
        }
        return ok;
        // Boolean success = connection.sqlUpdate("UPDATE Criticism set "
        // + "criticismId='" + cr.getCriticismId()
        // + "', criticismName ='" + cr.getCriticismName()
        // + "' WHERE judgement='" + cr.getJudgement() + "'");
        // connection.closeConnect();
        // return success;
    }

    public Boolean update(String criticismId, String criticismName, int judgement) {
        Criticism cr = new Criticism(criticismId, criticismName, judgement);
        return updateCriticism(cr);
    }

    public void closeConnection() {
        connection.closeConnect();
    }

    public Criticism getCriticismById(String criticismId) {
        connection = new ConnectDB();
        Criticism criticism = null;
        try {
            String query = "SELECT * FROM Criticism WHERE criticismId= '" + criticismId + "'";
            ResultSet rs = connection.sqlQuery(query);
            if (rs.next()) {
                criticism = new Criticism();
                criticism.setCriticismId(rs.getString("criticismId"));
                criticism.setCriticismName(rs.getString("criticismName"));
                criticism.setJudgement(rs.getInt("judgement"));
                criticism.setDeleteStatus(rs.getBoolean("deleteStatus"));
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Criticism");
        } finally {
            connection.closeConnect();
        }
        return criticism;
    }
}
