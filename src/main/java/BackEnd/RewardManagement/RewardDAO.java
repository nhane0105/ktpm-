package BackEnd.RewardManagement;

import BackEnd.ConnectDB.ConnectDB;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class RewardDAO {
    ConnectDB connection;

    public RewardDAO() {
    }

    public ArrayList<Reward> getAllReward() {
        connection = new ConnectDB();
        ArrayList<Reward> listReward = new ArrayList<>();

        try {
            String qry = "SELECT * FROM Rewards";
            ResultSet rs = connection.sqlQuery(qry);

            if (rs != null) {
                while (rs.next()) {
                    String rewardId = rs.getString("rewardId");
                    String rewardName = rs.getString("rewardName");
                    int reward = rs.getInt("reward");

                    Reward rw = new Reward(rewardId, rewardName, reward);
                    listReward.add(rw);
                }
                rs.close(); // Đóng ResultSet sau khi sử dụng
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng khen thưởng");
        } finally {
            connection.closeConnect();
        }

        return listReward;
    }

    public Boolean addNewReward(Reward rw) {
        connection = new ConnectDB();
        // Boolean ok = connection.sqlUpdate("Criticism(criticismId, critismName,
        // judgement, deteleStatus) VALUES ('"
        // + cr.getCriticismId() + "','"
        // + cr.getCriticismName() + "','"
        // + cr.getJudgement() + "','"
        // + cr.getDeleteStatus() + "');");
        //
        // connection.closeConnect();s
        // return ok;

        Boolean ok = false;
        try {
            String query = "INSERT INTO Rewards VALUES ('" + rw.getRewardId() + "', '" + rw.getRewardName()
                    + "', '" + rw.getReward() + "', '" + rw.getDeleteStatus() + "')";

            ok = connection.sqlUpdate(query);
        } finally {
            connection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteReward(Reward rw) {
        connection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Rewards SET deleteStatus = 1 WHERE rewardId = '" + rw.getRewardId() + "'";
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

    public Boolean updateReward(Reward rw) {
        connection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Rewards SET rewardName = '" + rw.getRewardName() + "', reward = '" + rw.getReward()
                    + "' WHERE rewardId = '"
                    + rw.getRewardId() + "'";
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

    public Boolean update(String rewardId, String rewardName, int reward) {
        Reward rw = new Reward(rewardId, rewardName, reward);
        return updateReward(rw);
    }

    public void closeConnection() {
        connection.closeConnect();
    }

    public Reward getRewardById(String rewardId) {
        connection = new ConnectDB();
        Reward reward = null;
        try {
            String query = "SELECT * FROM Rewards WHERE rewardId = '" + rewardId + "'";
            ResultSet rs = connection.sqlQuery(query);
            if (rs.next()) {
                reward = new Reward();
                reward.setRewardId(rs.getString("rewardId"));
                reward.setRewardName(rs.getString("rewardName"));
                reward.setReward(rs.getInt("reward"));
                reward.setDeleteStatus(rs.getBoolean("deleteStatus"));
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Rewards");
        } finally {
            connection.closeConnect();
        }
        return reward;
    }
}
