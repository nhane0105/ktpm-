package FrontEnd.EmployeeContentUI;

import FrontEnd.AccountContentUI.AccountManagementContentPanel;
import FrontEnd.Redux.Redux;
import FrontEnd.UserInfoContentUI.UserInfoContentPanel;

import com.github.lgooddatepicker.components.DatePickerSettings;

import BackEnd.AccountManagement.Account;
import BackEnd.DegreeManagement.Degree;
import BackEnd.DepartmentManagement.Department;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.PositionManagement.Position;
import BackEnd.SpecialtyManagement.Specialty;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class UserInformationForm extends javax.swing.JFrame implements ActionListener, WindowListener {

    ArrayList<Object> formData;
    String employeeID;
    UserInfoContentPanel userInfoContentPanel = new UserInfoContentPanel();
    public UserInformationForm() {
        initComponents();

        formData = new ArrayList<>();
        formInit();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        DatePickerSettings pickerSettings = new DatePickerSettings();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        pickerSettings.setFormatForDatesCommonEra(dtf);
        birthdateDatePicker.setSettings(pickerSettings);
        birthdateDatePicker.setDateToToday();

        confirmButton.addActionListener(this);
        declineButton.addActionListener(this);
        addWindowListener(this);
    }

    public void formInit() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Degree degree : Redux.degreeBUS.getDegreeList()) {
            if (!degree.getDeleteStatus()) {
                model.addElement(degree.getDegreeName());
            }
        }
        degreeComboBox.setModel(model);

        for (Position position : Redux.positionBUS.getPositionList()) {
            if (!position.getDeleteStatus()) {
                positionComboBox.addItem(position.getPositionName());
            }
        }

        for (Specialty specialty : Redux.specialtyBUS.getSpecialtyList()) {
            if (!specialty.getDeleteStatus()) {
                specialtyComboBox.addItem(specialty.getSpecialtyName());
            }
        }
    }

    public void handleSubmitForm() {
        formData = getDataFromForm();
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Xác nhận thao tác ?",
                "XÁC NHẬN ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (this.getTitle().contains("THÊM MỚI")) {
                Redux.employeeBUS.addEmployee(
                        new Employee(
                                (String) Redux.employeeBUS.getNextID(),
                                (String) formData.get(0),
                                (String) formData.get(1),
                                (String) formData.get(2),
                                (String) formData.get(3),
                                (String) formData.get(4),
                                (String) formData.get(5),
                                (String) formData.get(6),
                                Redux.degreeBUS.getDegreeByName(
                                        (String) formData.get(7)),
                                (String) formData.get(8),
                                Redux.positionBUS.getPositionByName(
                                        (String) formData.get(9)),
                                new Department(),
                                Redux.specialtyBUS.getSpecialtyByName(
                                        (String) formData.get(10))));
            } else {
                Employee employee = new Employee(
                        (String) employeeID,
                        (String) formData.get(0),
                        (String) formData.get(1),
                        (String) formData.get(2),
                        (String) formData.get(3),
                        (String) formData.get(4),
                        (String) formData.get(5),
                        (String) formData.get(6),
                        Redux.degreeBUS.getDegreeByName(
                                (String) formData.get(7)),
                        (String) formData.get(8),
                        Redux.positionBUS.getPositionByName(
                                (String) formData.get(9)),
                        new Department(),
                        Redux.specialtyBUS.getSpecialtyByName(
                                (String) formData.get(10)),
                        (boolean) formData.get(11),
                        (boolean) formData.get(12));
                Redux.employeeBUS.updateEmployee(employee);
                if ((boolean) formData.get(12)) {
                    Account account = Redux.accountBUS.getAccountById(employeeID);
                    Redux.accountBUS.deleteAccount(account);
                    AccountManagementContentPanel.tableInit(Redux.accountBUS.getAccountList());
                } else {
                    Account account = Redux.accountBUS.getAccountById(employeeID);
                    account.setAccountStatus(true);
                    account.setDeleteStatus(false);
                    Redux.accountBUS.updateAccount(account);
                    AccountManagementContentPanel.tableInit(Redux.accountBUS.getAccountList());
                }
                // for (Account account : Redux.accountBUS.getAccountList()) {
                // if (account.getEmployee().getId().equals(employeeID)) {
                // account.setEmployee(employee);
                // }
                // }
            }
            // Redux.getAllEmployees();
            // EmployeeManagementContentPanel.tableInit(Redux.employeeList);
            EmployeeManagementContentPanel.tableInit(Redux.employeeBUS.getEmployeeList());
            userInfoContentPanel.formInit();
            userInfoContentPanel.showFormWithData();
            dispose();
        }
    }

    public void cancelSubmitForm() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Xác nhận thao tác ?",
                "HỦY BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            clearFormData();
            dispose();
        }
    }

    public boolean isFormFilled() {
        return !(employeeNameTextField.getText().equals("")
                || birthdateDatePicker.getText().equals("")
                || phoneNumberTextField.getText().equals(""));
    }

    public boolean isFormValid() {
        return Employee.isValidName(employeeNameTextField.getText())
                && Employee.isValidPhoneNumber(phoneNumberTextField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            if (isFormFilled()) {
                if (isFormValid()) {
                    handleSubmitForm();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hãy nhập thông tin trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == declineButton) {
            if (isFormFilled()) {
                cancelSubmitForm();

            } else {
                clearFormData();
                dispose();
            }
        }
    }

    public void showFormWithData(ArrayList<Object> data) {
        if (data != null) {
            employeeID = (String) data.get(0);
            employeeNameTextField.setText((String) data.get(1));
            genderComboBox.setSelectedItem(data.get(2));
            birthdateDatePicker.setText((String) data.get(3));
            phoneNumberTextField.setText((String) data.get(4));
            ethicGroupComboBox.setSelectedItem(data.get(5));
            employeeTypeComboBox.setSelectedItem(data.get(6));
            religionComboBox.setSelectedItem(data.get(7));
            nationComboBox.setSelectedItem(data.get(8));
            degreeComboBox.setSelectedItem(data.get(9));
            positionComboBox.setSelectedItem(data.get(10));
            specialtyComboBox.setSelectedItem(data.get(12));
            employStatusComboBox.setSelectedItem(data.get(13));
            if (data.get(13).equals("Đã nghỉ việc")){
                employeeNameTextField.setEnabled(false);
                genderComboBox.setEnabled(false);
                birthdateDatePicker.setEnabled(false);
                phoneNumberTextField.setEnabled(false);
                ethicGroupComboBox.setEnabled(false);
                employeeTypeComboBox.setEnabled(false);
                religionComboBox.setEnabled(false);
                nationComboBox.setEnabled(false);
                degreeComboBox.setEnabled(false);
                positionComboBox.setEnabled(false);
                specialtyComboBox.setEnabled(false);
                employStatusComboBox.setEnabled(false);
            }
            if (Redux.isAdmin){
                employeeTypeComboBox.setEnabled(false);
                degreeComboBox.setEnabled(false);
                positionComboBox.setEnabled(false);
                specialtyComboBox.setEnabled(false);
                employStatusComboBox.setEnabled(false);
            }
        }
    }

    public void clearFormData() {
        employeeNameTextField.setText("");
        genderComboBox.setSelectedItem("");
        birthdateDatePicker.setText("");
        phoneNumberTextField.setText("");
        ethicGroupComboBox.setSelectedItem("");
        religionComboBox.setSelectedItem("");
        nationComboBox.setSelectedItem("");
        specialtyComboBox.setSelectedItem("");
        degreeComboBox.setSelectedItem("");
        positionComboBox.setSelectedItem("");
        employeeTypeComboBox.setSelectedItem("");
        employStatusComboBox.setSelectedItem("");
    }

    public ArrayList<Object> getDataFromForm() {
        String employeeName = employeeNameTextField.getText(),
                gender = (String) genderComboBox.getSelectedItem(),
                birthdate = Employee.formatBirthDateToDatabaseType(birthdateDatePicker.getText()),
                phoneNumber = phoneNumberTextField.getText(),
                ethicGroup = (String) ethicGroupComboBox.getSelectedItem(),
                religion = (String) religionComboBox.getSelectedItem(),
                nation = (String) nationComboBox.getSelectedItem(),
                specialty = (String) specialtyComboBox.getSelectedItem(),
                degree = (String) degreeComboBox.getSelectedItem(),
                position = (String) positionComboBox.getSelectedItem(),
                employeeType = (String) employeeTypeComboBox.getSelectedItem(),
                employStatus = (String) employStatusComboBox.getSelectedItem();

        return new ArrayList<>(Arrays.asList(employeeName, gender, birthdate, phoneNumber,
                ethicGroup, employeeType, religion, degree, nation, position, specialty,
                employStatus.equalsIgnoreCase("Đang làm việc") ? true : false,
                employStatus.equalsIgnoreCase("Đang làm việc") ? false : true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        employeeNameLabel = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        genderLabel = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        birthdateLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        phoneNumberTextField = new javax.swing.JTextField();
        ethicGroupLabel = new javax.swing.JLabel();
        ethicGroupComboBox = new javax.swing.JComboBox<>();
        religionLabel = new javax.swing.JLabel();
        religionComboBox = new javax.swing.JComboBox<>();
        nationLabel = new javax.swing.JLabel();
        nationComboBox = new javax.swing.JComboBox<>();
        degreeLabel = new javax.swing.JLabel();
        degreeComboBox = new javax.swing.JComboBox<>();
        positionLabel = new javax.swing.JLabel();
        specialtyLabel = new javax.swing.JLabel();
        specialtyComboBox = new javax.swing.JComboBox<>();
        positionComboBox = new javax.swing.JComboBox<>();
        employeeTypeLabel = new javax.swing.JLabel();
        employeeTypeComboBox = new javax.swing.JComboBox<>();
        confirmButton = new javax.swing.JButton();
        declineButton = new javax.swing.JButton();
        birthdateDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        employStatusLabel = new javax.swing.JLabel();
        employStatusComboBox = new javax.swing.JComboBox<>();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("THÊM MỚI");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        employeeNameLabel.setLabelFor(employeeNameTextField);
        employeeNameLabel.setText("Họ và Tên :");
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N

        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setHighlighter(null);
        employeeNameTextField.setName("employeeNameTextField"); // NOI18N

        genderLabel.setLabelFor(genderComboBox);
        genderLabel.setText("Giới Tính :");
        genderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        genderLabel.setName("genderLabel"); // NOI18N

        genderComboBox.setBackground(new java.awt.Color(204, 204, 204));
        genderComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam ", "Nữ" }));
        genderComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        genderComboBox.setName("genderComboBox"); // NOI18N

        birthdateLabel.setLabelFor(birthdateDatePicker);
        birthdateLabel.setText("Ngày Sinh :");
        birthdateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        birthdateLabel.setName("birthdateLabel"); // NOI18N

        phoneNumberLabel.setLabelFor(phoneNumberTextField);
        phoneNumberLabel.setText("Số Điện Thoại :");
        phoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        phoneNumberLabel.setName("phoneNumberLabel"); // NOI18N

        phoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        phoneNumberTextField.setBackground(new java.awt.Color(204, 204, 204));
        phoneNumberTextField.setName("phoneNumberTextField"); // NOI18N
        phoneNumberTextField.setPreferredSize(new java.awt.Dimension(230, 26));

        ethicGroupLabel.setLabelFor(ethicGroupComboBox);
        ethicGroupLabel.setText("Dân Tộc :");
        ethicGroupLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ethicGroupLabel.setName("ethicGroupLabel"); // NOI18N

        ethicGroupComboBox.setBackground(new java.awt.Color(204, 204, 204));
        ethicGroupComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ethicGroupComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kinh", "Tày", "Mường" }));
        ethicGroupComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ethicGroupComboBox.setName("ethicGroupComboBox"); // NOI18N
        ethicGroupComboBox.setPreferredSize(new java.awt.Dimension(200, 40));

        religionLabel.setLabelFor(religionComboBox);
        religionLabel.setText("Tôn giáo :");
        religionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        religionLabel.setName("religionLabel"); // NOI18N

        religionComboBox.setBackground(new java.awt.Color(204, 204, 204));
        religionComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        religionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phật Giáo", "Thiên Chúa Giáo", "Công Giáo" }));
        religionComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        religionComboBox.setName("religionComboBox"); // NOI18N

        nationLabel.setLabelFor(nationComboBox);
        nationLabel.setText("Quốc Tịch :");
        nationLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nationLabel.setName("nationLabel"); // NOI18N

        nationComboBox.setBackground(new java.awt.Color(204, 204, 204));
        nationComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Việt Nam", "Hoa Kỳ", "Canada", "Nhật Bản", "Hàn Quốc" }));
        nationComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nationComboBox.setName("nationComboBox"); // NOI18N

        degreeLabel.setLabelFor(degreeComboBox);
        degreeLabel.setText("Bằng Cấp :");
        degreeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        degreeLabel.setName("degreeLabel"); // NOI18N

        degreeComboBox.setBackground(new java.awt.Color(204, 204, 204));
        degreeComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        degreeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        degreeComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        degreeComboBox.setName("degreeComboBox"); // NOI18N

        positionLabel.setLabelFor(positionComboBox);
        positionLabel.setText("Chức vụ :");
        positionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positionLabel.setName("positionLabel"); // NOI18N

        specialtyLabel.setLabelFor(specialtyComboBox);
        specialtyLabel.setText("Chuyên Môn :");
        specialtyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        specialtyLabel.setName("specialtyLabel"); // NOI18N

        specialtyComboBox.setBackground(new java.awt.Color(204, 204, 204));
        specialtyComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        specialtyComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        specialtyComboBox.setName("specialtyComboBox"); // NOI18N

        positionComboBox.setBackground(new java.awt.Color(204, 204, 204));
        positionComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        positionComboBox.setName("positionComboBox"); // NOI18N

        employeeTypeLabel.setLabelFor(employeeTypeComboBox);
        employeeTypeLabel.setText("Loại Nhân Viên :");
        employeeTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeTypeLabel.setName("employeeTypeLabel"); // NOI18N

        employeeTypeComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeTypeComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chính Thức", "Bán Thời Gian", "Thực Tập Sinh" }));
        employeeTypeComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeTypeComboBox.setName("employeeTypeComboBox"); // NOI18N

        confirmButton.setText("Xác Nhận");
        confirmButton.setBackground(new java.awt.Color(13, 110, 253));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setName("confirmButton"); // NOI18N
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        declineButton.setText("Hủy Bỏ");
        declineButton.setBackground(new java.awt.Color(108, 117, 125));
        declineButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        declineButton.setForeground(new java.awt.Color(255, 255, 255));
        declineButton.setName("declineButton"); // NOI18N

        birthdateDatePicker.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        birthdateDatePicker.setName("birthdateDatePicker"); // NOI18N

        employStatusLabel.setText("Trạng thái :");
        employStatusLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employStatusLabel.setName("employStatusLabel"); // NOI18N
        employStatusLabel.setToolTipText("");

        employStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang làm việc", "Đã nghỉ việc" }));
        employStatusComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employStatusComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employStatusComboBox.setName("employStatusComboBox"); // NOI18N
        employStatusComboBox.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(phoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(phoneNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(employeeNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(genderComboBox, 0, 235, Short.MAX_VALUE)
                            .addComponent(genderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ethicGroupLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ethicGroupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(specialtyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(specialtyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(positionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(positionComboBox, 0, 200, Short.MAX_VALUE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(degreeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(declineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(birthdateDatePicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(religionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(birthdateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(religionLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(degreeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employStatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(birthdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(birthdateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(ethicGroupLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ethicGroupComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(religionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(religionComboBox)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(specialtyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(specialtyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(degreeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(degreeComboBox)))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(employStatusComboBox)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(positionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(declineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker birthdateDatePicker;
    private javax.swing.JLabel birthdateLabel;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton declineButton;
    private javax.swing.JComboBox<String> degreeComboBox;
    private javax.swing.JLabel degreeLabel;
    private javax.swing.JComboBox<String> employStatusComboBox;
    private javax.swing.JLabel employStatusLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JComboBox<String> employeeTypeComboBox;
    private javax.swing.JLabel employeeTypeLabel;
    private javax.swing.JComboBox<String> ethicGroupComboBox;
    private javax.swing.JLabel ethicGroupLabel;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> nationComboBox;
    private javax.swing.JLabel nationLabel;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneNumberTextField;
    private javax.swing.JComboBox<String> positionComboBox;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JComboBox<String> religionComboBox;
    private javax.swing.JLabel religionLabel;
    private javax.swing.JComboBox<String> specialtyComboBox;
    private javax.swing.JLabel specialtyLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        clearFormData();
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
