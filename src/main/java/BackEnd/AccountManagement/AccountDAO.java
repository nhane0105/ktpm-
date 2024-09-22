package BackEnd.AccountManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import BackEnd.ConnectDB.ConnectDB;
import BackEnd.EmployeeManagement.EmployeeDAO;

public class AccountDAO {

    ConnectDB dbConnection;

    public AccountDAO() {
    }

    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> listAccount = new ArrayList<>();
        dbConnection = new ConnectDB();
        try {
            String query = "SELECT * FROM Account";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String userId = rs.getString("userId"),
                            username = rs.getString("username"),
                            password = rs.getString("password"),
                            email = rs.getString("email"),
                            avatar = rs.getString("avatar"),
                            authorization = rs.getString("authorization");
                    boolean accountStatus = rs.getBoolean("accountStatus"),
                            deleteStatus = rs.getBoolean("deleteStatus");

                    listAccount.add(new Account(new EmployeeDAO().getEmployeeById(userId), username, password, email,
                            avatar, authorization, accountStatus, deleteStatus));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng tài khoản");
        } finally {
            dbConnection.closeConnect();
        }
        return listAccount;
    }

    public Boolean addNewAccount(Account account) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            // JOptionPane.showMessageDialog(, "im here", "CẢNH BÁO 12",
            // JOptionPane.INFORMATION_MESSAGE);
            String query = "INSERT INTO Account (userId, username, password, email,avatar, [authorization],accountStatus, deleteStatus) VALUES ('"
                    + account.getEmployee().getId() + "', '"
                    + account.getUsername() + "', '"
                    + account.getPassword() + "', '"
                    + account.getEmail() + "', '"
                    + account.getAvatar() + "', '"
                    + account.getAuthorization() + "', '"
                    + account.getAccountStatus() + "', '"
                    + account.getDeleteStatus() + "')";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;

    }

    public Boolean updateAccount(Account account) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Account SET username = '" + account.getUsername()
                    + "',password = '" + account.getPassword()
                    + "',email = '" + account.getEmail()
                    + "',avatar = '" + account.getAvatar()
                    + "',[authorization]  = '" + account.getAuthorization()
                    + "',accountStatus = '" + account.getAccountStatus()
                    + "',deleteStatus = '" + account.getDeleteStatus()
                    + "' WHERE userId = '"
                    + account.getEmployee().getId() + "'";
            ;
            ok = dbConnection.sqlUpdate(query);

        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Boolean deleteAccount(Account account) {
        dbConnection = new ConnectDB();
        Boolean ok = false;
        try {
            String query = "UPDATE Account SET deleteStatus = 1, accountStatus = 0 WHERE userId = '"
                    + account.getEmployee().getId()
                    + "'";
            ok = dbConnection.sqlUpdate(query);
        } finally {
            dbConnection.closeConnect();
        }
        return ok;
    }

    public Account getAccountById(String userId) {
        dbConnection = new ConnectDB();
        Account account = null;
        try {
            String query = "SELECT * FROM Account WHERE userId = '" + userId + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String username = rs.getString("username"),
                        password = rs.getString("password"),
                        email = rs.getString("email"),
                        avatar = rs.getString("avatar"),
                        authorization = rs.getString("authorization");
                boolean accountStatus = rs.getBoolean("accountStatus"),
                        deleteStatus = rs.getBoolean("deleteStatus");

                account = new Account(new EmployeeDAO().getEmployeeById(userId), username, password,
                        email, avatar, authorization, accountStatus, deleteStatus);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Account");
        } finally {
            dbConnection.closeConnect();
        }
        return account;
    }

    public Account getAccountByEmail(String email) {
        dbConnection = new ConnectDB();
        Account account = null;
        try {
            String query = "SELECT * FROM Account WHERE email = '" + email + "'";
            ResultSet rs = dbConnection.sqlQuery(query);

            if (rs.next()) {
                String userId = rs.getString("userId"),
                        username = rs.getString("username"),
                        password = rs.getString("password"),
                        avatar = rs.getString("avatar"),
                        authorization = rs.getString("authorization");
                boolean accountStatus = rs.getBoolean("accountStatus"),
                        deleteStatus = rs.getBoolean("deleteStatus");

                account = new Account(new EmployeeDAO().getEmployeeById(userId), username, password,
                        email, avatar, authorization, accountStatus, deleteStatus);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng Account");
        } finally {
            dbConnection.closeConnect();
        }
        return account;
    }
}
