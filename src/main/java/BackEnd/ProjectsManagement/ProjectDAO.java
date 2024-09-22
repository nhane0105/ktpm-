package BackEnd.ProjectsManagement;

import BackEnd.ConnectDB.ConnectDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProjectDAO {
    private ConnectDB con;

    public ProjectDAO() {
    }

//    public ArrayList<Project> getAllProject() {
//        ArrayList<Project> list = new ArrayList<>();
//        con = new ConnectDB();
//        try {
//            String sql = "select * from Projects";
//            ResultSet rs = con.sqlQuery(sql);
//            if (rs != null) {
//                while (rs.next()) {
//                    list.add(new Project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
//                            rs.getString(5), rs.getString(6), rs.getBoolean(7)));
//                }
//            }
//            rs.close();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu bảng Projects");
//        } finally {
//            con.closeConnect();
//        }
//        return list;
//    }
    public ArrayList<Project> getAllProject() {
        ArrayList<Project> list = new ArrayList<>();
        con = new ConnectDB();
        try {
            // Định dạng ngày tháng (chỉ lấy ngày tháng năm)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Lấy danh sách project từ database
            String sql = "SELECT * FROM Projects";
            ResultSet rs = con.sqlQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    // Lấy thời gian completeAt từ database và chuyển thành LocalDate
                    String completeAtString = rs.getString("completedAt");
                    LocalDate completeAt = LocalDate.parse(completeAtString, formatter);

                    // Lấy ngày hiện tại (chỉ lấy ngày, không giờ)
                    LocalDate now = LocalDate.now();

                    if (completeAt.isBefore(now)) {
                        // Nếu thời gian hoàn thành nhỏ hơn ngày hiện tại, xóa project khỏi database
                        deleteProject(rs.getString("projectId"));
                    } else {
                        // Nếu project còn hạn, thêm vào danh sách
                        list.add(new Project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6), rs.getBoolean(7)));
                    }
                }
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu bảng Projects");
        } finally {
            con.closeConnect();
        }
        return list;
    }


    public boolean updateProject(Project pj) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "update Projects set projectId = '" + pj.getProjectId() + "', projectName = '"
                    + pj.getProjectName() + "', departmentId = '" + pj.getDepartmentId() + "', beginAt = '"
                    + pj.getBeginAt() + "', completedAt = '" + pj.getCompleteAt() + "', place = '" + pj.getPlace()
                    + "' where projectId = '" + pj.getProjectId() + "'";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }

    public boolean deleteProject(String pjId) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "update Projects set deleteStatus = 1 where projectId = '" + pjId + "'";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }

    public Project searchInProject(String pjId) {
        con = new ConnectDB();
        Project pj = null;
        try {
            String sql = "SELECT * FROM Projects WHERE projectId = '" + pjId + "'";
            ResultSet rs = con.sqlQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    pj = new Project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getString(6), rs.getBoolean(7));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.closeConnect();
        }
        return pj;
    }

    public boolean addNewProject(Project pj) {
        con = new ConnectDB();
        boolean flag = false;
        try {
            String sql = "INSERT INTO [QLNV].[dbo].[Projects] ([projectId], [projectName], [departmentId], [beginAt], [completedAt], [place], [deleteStatus]) VALUES ('"
                    + pj.getProjectId() + "','" + pj.getProjectName() + "','" + pj.getDepartmentId() + "','"
                    + pj.getBeginAt() + "','" + pj.getCompleteAt() + "','" + pj.getPlace() + "', '"
                    + pj.isDeleteStatus()
                    + "')";
            flag = con.sqlUpdate(sql);
        } finally {
            con.closeConnect();
        }
        return flag;
    }
}
