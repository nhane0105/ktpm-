package FrontEnd.Redux;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;
import BackEnd.CriticismManagement.CriticismBUS;
import BackEnd.DegreeManagement.DegreeBUS;
import BackEnd.DepartmentManagement.DepartmentBUS;
import BackEnd.EmployeeManagement.EmployeeBUS;
import BackEnd.EmployeesRewardsCriticismManagement.EmployeesRewardsCriticismBUS;
import BackEnd.PositionManagement.PositionBUS;
import BackEnd.ProjectsManagement.ProjectBUS;
import BackEnd.RewardManagement.RewardBUS;
import BackEnd.SpecialtyManagement.SpecialtyBUS;
import BackEnd.StatisticsManagement.StatisticsBUS;
import BackEnd.AccountManagement.Account;
import BackEnd.AccountManagement.AccountBUS;
import BackEnd.AssignmentManagement.AssignmentBUS;

public class Redux {

    public static ConnectDB dbConnection;

    public static boolean isLoggedIn = false;
    public static boolean isAdmin = false;
    public static boolean isDirector = false;
    public static String userId = "";
    public static String username = "";
    public static Account currentAccount;

    public static EmployeeBUS employeeBUS;
    public static DegreeBUS degreeBUS;
    public static PositionBUS positionBUS;
    public static SpecialtyBUS specialtyBUS;
    public static DepartmentBUS departmentBUS;
    public static ProjectBUS projectBUS;
    public static AssignmentBUS assignmentBUS;
    public static AccountBUS accountBUS;
    public static RewardBUS rewardBUS;
    public static CriticismBUS criticismBUS;
    public static EmployeesRewardsCriticismBUS employeesRewardsCriticismBUS;
    public static StatisticsBUS statisticsBUS;

    public Redux() {
        employeeBUS = new EmployeeBUS();
        degreeBUS = new DegreeBUS();
        positionBUS = new PositionBUS();
        specialtyBUS = new SpecialtyBUS();
        departmentBUS = new DepartmentBUS();
        projectBUS = new ProjectBUS();
        assignmentBUS = new AssignmentBUS();
        accountBUS = new AccountBUS();
        rewardBUS = new RewardBUS();
        criticismBUS = new CriticismBUS();
        employeesRewardsCriticismBUS = new EmployeesRewardsCriticismBUS();
        statisticsBUS = new StatisticsBUS();
    }

    public static void handleLogin(String email, String password) {
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Account WHERE email = '" + email + "' AND password = '"
                    + password + "' AND deleteStatus = 0";
            
            ResultSet rs = dbConnection.sqlQuery(query);
            if (rs.next()) {
                // User is authenticated
                userId = rs.getString("userId");
                Redux.username = rs.getString("username");
                isAdmin = userId.contains("ADM");
                isDirector = userId.contains("DIR");
                isLoggedIn = true;

                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
            } else {
                // Authentication failed
                JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!");
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Account");
        } finally {
            dbConnection.closeConnect();
        }
    }
}