package BackEnd.EmployeeManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import BackEnd.DegreeManagement.Degree;
import BackEnd.DepartmentManagement.Department;
import BackEnd.PositionManagement.Position;
import BackEnd.SpecialtyManagement.Specialty;

public class Employee {

    private String id, fullName, gender,
            birthDate, phoneNumber,
            ethnicGroup, type,
            religion, nation;
    private Degree degree;
    private Position position;
    private Department department;
    private Specialty specialty;
    private boolean employStatus,
            deleteStatus;

    public Employee(String id) {
        this.id = id;
    }

    public Employee(String id, String fullName, String gender, String birthDate, String phoneNumber, String ethnicGroup,
            String type, String religion, Degree degree, String nation, Position position, Department department,
            Specialty specialty) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.ethnicGroup = ethnicGroup;
        this.type = type;
        this.religion = religion;
        this.degree = degree;
        this.nation = nation;
        this.position = position;
        this.department = department;
        this.specialty = specialty;
        this.employStatus = true;
        this.deleteStatus = false;
    }

    public Employee(String id, String fullName, String gender, String birthDate, String phoneNumber, String ethnicGroup,
            String type, String religion, Degree degree, String nation, Position position, Department department,
            Specialty specialty, boolean employStatus, boolean deleteStatus) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.ethnicGroup = ethnicGroup;
        this.type = type;
        this.religion = religion;
        this.degree = degree;
        this.nation = nation;
        this.position = position;
        this.department = department;
        this.specialty = specialty;
        this.employStatus = employStatus;
        this.deleteStatus = deleteStatus;
    }

    public Employee(String id, boolean deleteStatus) {
        this.id = id;
        this.deleteStatus = deleteStatus;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEthnicGroup() {
        return this.ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String typeId) {
        this.type = typeId;
    }

    public String getReligion() {
        return this.religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Degree getDegree() {
        return this.degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Specialty getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public boolean getEmployStatus() {
        return this.employStatus;
    }

    public void setEmployStatus(boolean employeeStatus) {
        this.employStatus = employeeStatus;
    }

    public boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public static String formatBirthDateToStandardType(String birthDateString) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        String newBirthDateString = "";
        try {
            Date birthDate = oldFormat.parse(birthDateString);
            newBirthDateString = newFormat.format(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newBirthDateString;
    }

    public static String formatBirthDateToDatabaseType(String birthDateString) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBirthDateString = "";
        try {
            Date birthDate = oldFormat.parse(birthDateString);
            newBirthDateString = newFormat.format(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newBirthDateString;
    }

    public static boolean isValidName(String name) {
        boolean flag = true;
        if (!name.matches(
                "(^[A-Z][a-zÃ€ÃÃ‚ÃƒÃˆÃ‰ÃŠÃŒÃÃ’Ã“Ã”Ã•Ã™ÃšÄ‚ÄÄ¨Å¨Æ Ã Ã¡Ã¢Ã£Ã¨Ã©ÃªÃ¬Ã­Ã²Ã³Ã´ÃµÃ¹ÃºÄƒÄ‘Ä©Å©Æ¡Æ¯Ä‚áº áº¢áº¤áº¦áº¨áºªáº¬áº®áº°áº²áº´áº¶áº¸áººáº¼á»€á»€á»‚áº¾Æ°Äƒáº¡áº£áº¥áº§áº©áº«áº­áº¯áº±áº³áºµáº·áº¹áº»áº½á»á»á»ƒáº¿á»„á»†á»ˆá»Šá»Œá»Žá»á»’á»”á»–á»˜á»šá»œá»žá» á»¢á»¤á»¦á»¨á»ªá»…á»‡á»‰á»‹á»á»á»‘á»“á»•á»—á»™á»›á»á»Ÿá»¡á»£á»¥á»§á»©á»«á»¬á»®á»°á»²á»´Ãá»¶á»¸á»­á»¯á»±á»³á»µá»·á»¹\\s\\W]+)([A-Z][a-zÃ€ÃÃ‚ÃƒÃˆÃ‰ÃŠÃŒÃÃ’Ã“Ã”Ã•Ã™ÃšÄ‚ÄÄ¨Å¨Æ Ã Ã¡Ã¢Ã£Ã¨Ã©ÃªÃ¬Ã­Ã²Ã³Ã´ÃµÃ¹ÃºÄƒÄ‘Ä©Å©Æ¡Æ¯Ä‚áº áº¢áº¤áº¦áº¨áºªáº¬áº®áº°áº²áº´áº¶áº¸áººáº¼á»€á»€á»‚áº¾Æ°Äƒáº¡áº£áº¥áº§áº©áº«áº­áº¯áº±áº³áºµáº·áº¹áº»áº½á»á»á»ƒáº¿á»„á»†á»ˆá»Šá»Œá»Žá»á»’á»”á»–á»˜á»šá»œá»žá» á»¢á»¤á»¦á»¨á»ªá»…á»‡á»‰á»‹á»á»á»‘á»“á»•á»—á»™á»›á»á»Ÿá»¡á»£á»¥á»§á»©á»«á»¬á»®á»°á»²á»´Ãá»¶á»¸á»­á»¯á»±á»³á»µá»·á»¹\\W]+){0,3}$")) {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "Tên không hợp lệ ! (Tên phải bắt đầu bằng chữ hoa, không được chứa ký số)", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean flag = true;
        if (!phoneNumber.matches("^0\\d{9,10}$")) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại không hợp lệ ! (SĐT phải bắt đầu bằng số 0, chiều dài từ 10 - 11 số, không được chứa ký tự)",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }

    public List<Object> toList() {
        try {
            return Arrays.asList(id, fullName, gender, birthDate, phoneNumber, ethnicGroup, type, religion, nation,
                    degree.getDegreeId(),
                    position.getPositionId(), department, specialty.getPositionId(), employStatus, deleteStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<String> getHeader() {
        return new ArrayList<>(Arrays.asList("Mã Nhân Viên", "Họ và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại",
                "Dân Tộc", "Loại Nhân Viên", "Tôn Giáo", "Quốc Tịch", "Trình Độ", "Chức Vụ", "Phòng Ban", "Chuyên Môn",
                "Tình Trạng Làm Việc"));
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", fullName='" + getFullName() + "'" +
                ", gender='" + getGender() + "'" +
                ", birthDate='" + getBirthDate() + "'" +
                ", phoneNumber='" + getPhoneNumber() + "'" +
                ", ethnicGroup='" + getEthnicGroup() + "'" +
                ", typeId='" + getType() + "'" +
                ", religion='" + getReligion() + "'" +
                ", nation='" + getNation() + "'" +
                ", degree='" + getDegree().getDegreeId() + "'" +
                ", position='" + getPosition().getPositionId() + "'" +
                ", department='" + getDepartment().getDepartmentId() + "'" +
                ", specialty='" + getSpecialty().getSpecialtyId() + "'" +
                ", employStatus='" + getEmployStatus() + "'" +
                ", deleteStatus='" + getDeleteStatus() + "'" +
                "}";
    }

    public Object getPropertyByIndex(int option) {
        String result = "";
        switch (option) {
            case 0:
                result = getId();
                break;

            case 1:
                result = getFullName();
                break;

            case 2:
                result = getGender();
                break;

            case 3:
                result = Employee.formatBirthDateToStandardType(getBirthDate());
                break;

            case 4:
                result = getPhoneNumber();
                break;

            case 5:
                result = getEthnicGroup();
                break;

            case 6:
                result = getType();
                break;

            case 7:
                result = getReligion();
                break;

            case 8:
                result = getNation();
                break;

            case 9:
                result = getDegree().getDegreeName();
                break;

            case 10:
                result = getPosition().getPositionName();
                break;

            case 11:
                result = getDepartment().getDepartmentName();
                break;

            case 12:
                result = getSpecialty().getSpecialtyName();
                break;

            case 13:
                result = getEmployStatus() ? "Đang làm việc" : "Đã nghỉ việc";
                break;

            default:
                break;
        }
        return result;
    }
}
