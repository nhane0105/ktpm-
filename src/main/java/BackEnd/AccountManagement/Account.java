package BackEnd.AccountManagement;

import BackEnd.EmployeeManagement.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class Account {

    private Employee employee;
    private String username, password, email, avatar, authorization;
    private boolean accountStatus, deleteStatus;

    public Account() {
    }

    public Account(Employee employee, String username, String password, String email, String url,
            String authorization) {
        this.employee = employee;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = url;
        this.authorization = authorization;
        this.accountStatus = true;
        this.deleteStatus = false;
    }

    public Account(Employee employee, String username, String password, String email, String avatar,
            String authorization, boolean accountStatus, boolean deleteStatus) {
        this.employee = employee;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.authorization = authorization;
        this.accountStatus = accountStatus;
        this.deleteStatus = deleteStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public static boolean isValidName(String name) {
        boolean flag = true;
        if (name.matches(
                "(^[A-Z][a-zÃ€ÃÃ‚ÃƒÃˆÃ‰ÃŠÃŒÃÃ’Ã“Ã”Ã•Ã™ÃšÄ‚ÄÄ¨Å¨Æ Ã Ã¡Ã¢Ã£Ã¨Ã©ÃªÃ¬Ã­Ã²Ã³Ã´ÃµÃ¹ÃºÄƒÄ‘Ä©Å©Æ¡Æ¯Ä‚áº áº¢áº¤áº¦áº¨áºªáº¬áº®áº°áº²áº´áº¶áº¸áººáº¼á»€á»€á»‚áº¾Æ°Äƒáº¡áº£áº¥áº§áº©áº«áº­áº¯áº±áº³áºµáº·áº¹áº»áº½á»á»á»ƒáº¿á»„á»†á»ˆá»Šá»Œá»Žá»á»’á»”á»–á»˜á»šá»œá»žá» á»¢á»¤á»¦á»¨á»ªá»…á»‡á»‰á»‹á»á»á»‘á»“á»•á»—á»™á»›á»á»Ÿá»¡á»£á»¥á»§á»©á»«á»¬á»®á»°á»²á»´Ãá»¶á»¸á»­á»¯á»±á»³á»µá»·á»¹\\s\\W]+)([A-Z][a-zÃ€ÃÃ‚ÃƒÃˆÃ‰ÃŠÃŒÃÃ’Ã“Ã”Ã•Ã™ÃšÄ‚ÄÄ¨Å¨Æ Ã Ã¡Ã¢Ã£Ã¨Ã©ÃªÃ¬Ã­Ã²Ã³Ã´ÃµÃ¹ÃºÄƒÄ‘Ä©Å©Æ¡Æ¯Ä‚áº áº¢áº¤áº¦áº¨áºªáº¬áº®áº°áº²áº´áº¶áº¸áººáº¼á»€á»€á»‚áº¾Æ°Äƒáº¡áº£áº¥áº§áº©áº«áº­áº¯áº±áº³áºµáº·áº¹áº»áº½á»á»á»ƒáº¿á»„á»†á»ˆá»Šá»Œá»Žá»á»’á»”á»–á»˜á»šá»œá»žá» á»¢á»¤á»¦á»¨á»ªá»…á»‡á»‰á»‹á»á»á»‘á»“á»•á»—á»™á»›á»á»Ÿá»¡á»£á»¥á»§á»©á»«á»¬á»®á»°á»²á»´Ãá»¶á»¸á»­á»¯á»±á»³á»µá»·á»¹\\W]+){0,3}$")) {
        } else

        {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "Tên không hợp lệ ! (Tên phải bắt đầu bằng chữ hoa, không được chứa ký số)", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }

    public static boolean isValidEmail(String email) {
        boolean flag = true;
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            flag = false;
            JOptionPane.showMessageDialog(null, "Email không hợp lệ !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }

    public List<Object> toList() {
        try {
            return Arrays.asList(employee.getId(), username, password, email, avatar,
                    authorization, accountStatus, deleteStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<String> getHeader() {
        return new ArrayList<>(Arrays.asList("Mã Nhân Viên", "Tên tài khoản", "Mật khẩu", "Email", "avatar",
                "Phân quyền", "Trạng thái"));
    }

    public Object getPropertyByIndex(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getEmployee().getId();
                break;

            case 1:
                result = getUsername();
                break;

            case 2:
                result = getPassword();
                break;

            case 3:
                result = getEmail();
                break;

            case 4:
                result = getAvatar();
                break;

            case 5:
                result = getAuthorization();
                break;

            case 6:
                result = getAccountStatus() ? "Đang hoạt động" : "Ngừng hoạt động";
                break;

            default:
                break;
        }
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                " employee='" + getEmployee() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", email='" + getEmail() + "'" +
                ", avatar='" + getAvatar() + "'" +
                ", authorization='" + getAuthorization() + "'" +
                ", accountStatus='" + getAccountStatus() + "'" +
                ", deleteStatus='" + getDeleteStatus() + "'" +
                "}";
    }

}
