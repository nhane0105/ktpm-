package BackEnd.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConnectDB {
    static int countConection = 0;
    static int countQuery = 0;
    static int countUpdate = 0;

    // Tự setting theo máy của mỗi người
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLNV;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456789";
    private static final String DB_Name = "QLNV";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;

    public ConnectDB() {
        checkDriver();
        setupConnect();
    }

    private void checkDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Không tìm thấy Driver mySql");
        }
    }

    public Boolean checkConnect() {
        if (conn == null) {
            JOptionPane.showMessageDialog(null,
                    "-- ERROR! Chưa thiết lập kết nối tới '" + DB_Name + "'. Vui lòng đăng nhập để thiết lập kết nối!");
            return false;
        }
        return true;
    }

    private void setupConnect() {

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("-- ERROR! Không thể kết nối tới '" + DB_Name + "'");
            JOptionPane.showMessageDialog(null, "-- ERROR! Không thể kết nối tới '" + DB_Name + "'");
        }
    }

    public Connection getConnection() {
        if (checkConnect()) {
            return conn;
        }
        return null;
    }

    public ResultSet sqlQuery(String qry) {
        if (checkConnect()) {
            try {
                pstmt = conn.prepareStatement(qry);
                rset = pstmt.executeQuery();
                return rset;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "-- ERROR! Không thể lấy dữ liệu từ " + DB_Name + "\n" + ex.getLocalizedMessage());
                return null;
            }
        }
        return null;
    }

    public Boolean sqlUpdate(String qry) {
        if (checkConnect()) {
            try {
                pstmt = conn.prepareStatement(qry);
                pstmt.executeUpdate();
                return true;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "-- ERROR! Không thể ghi dữ liệu xuống " + DB_Name + "\n" + ex.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public void closeConnect() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "-- ERROR! Không thể đóng kết nối tới " + DB_Name + "\n" + ex.getLocalizedMessage());
        }
    }
    
 }

