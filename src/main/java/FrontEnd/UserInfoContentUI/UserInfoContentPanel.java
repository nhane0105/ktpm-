package FrontEnd.UserInfoContentUI;

import FrontEnd.EmployeeContentUI.UserInformationForm;
import FrontEnd.Redux.Redux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URL;
import javax.swing.ImageIcon;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfWriter;

import BackEnd.AccountManagement.Account;
import BackEnd.EmployeeManagement.Employee;

public class UserInfoContentPanel extends javax.swing.JPanel implements ActionListener {

    UserInformationForm userInformationFrom;
    BufferedImage image;
    static Object[] userData;
    static ArrayList<Object> dataList;

    public UserInfoContentPanel() {
        initComponents();

        updateButton.addActionListener(this);
        exportPDFButton.addActionListener(this);
        if(Redux.isAdmin){
            updateButton.setVisible(false);
        }
        setVisible(true);
    }

    public  void formInit() {
        for (Account account : Redux.accountBUS.getAccountList()) {
            if (account.getEmployee().getId().equals(Redux.userId)) {
                Employee employee = account.getEmployee();
                userData = new Object[] { employee.getId(), employee.getFullName(),
                        employee.getGender(),
                        Employee.formatBirthDateToStandardType(employee.getBirthDate()),
                        employee.getPhoneNumber(), employee.getEthnicGroup(),
                        employee.getType(),
                        employee.getReligion(),
                        employee.getNation(), employee.getDegree().getDegreeName(),
                        employee.getPosition().getPositionName(),
                        employee.getDepartment().getDepartmentName(),
                        employee.getSpecialty().getSpecialtyName(),
                        employee.getEmployStatus(),
                        account.getAvatar() };
                break;
            }
        }

        // Create a new ArrayList
        dataList = new ArrayList<>(userData.length);

        // Add all elements from the array to the ArrayList
        dataList.addAll(Arrays.asList(userData));

        showFormWithData();
    }

    public void showFormWithData() {
        try {
            String fileName = (String) dataList.get(14);
            if (fileName != null && !fileName.isEmpty()) {
                String imagePath = "/avatars/" + fileName;
                URL location = UserInfoContentPanel.class.getResource(imagePath);
                if (location != null) {
                    ImageIcon icon = new ImageIcon(location);
                    avatarLabel.setIcon(icon);
                } else {
                    avatarLabel.setIcon(null);
                }
            } else {
                avatarLabel.setIcon(null);
            }

            employeeID.setText((String) dataList.get(0));
            employeeName.setText((String) dataList.get(1));
            employeeGender.setText((String) dataList.get(2));
            employeeBirthdate.setText((String) dataList.get(3));
            employeePhoneNumber.setText((String) dataList.get(4));
            employeeEthicGroup.setText((String) dataList.get(5));
            employeeType.setText((String) dataList.get(6));
            employeeReligion.setText((String) dataList.get(7));
            employeeNation.setText((String) dataList.get(8));
            employeeDegree.setText((String) dataList.get(9));
            employeePosition.setText((String) dataList.get(10));
            departmentName.setText((String) dataList.get(11));
            employeeSpecialty.setText((String) dataList.get(12));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateTableRow() {
        userInformationFrom = new UserInformationForm();
        userInformationFrom.setTitle("CẬP NHẬT THÔNG TIN CÁ NHÂN CỦA NHÂN VIÊN");
        userInformationFrom.setVisible(true);
        userInformationFrom.showFormWithData(dataList);

    }

    public ArrayList<String> getDataFromForm() {
        ArrayList<String> data = new ArrayList<>();
        data.add(employeeName.getText());
        data.add(employeeID.getText());
        data.add(employeeGender.getText());
        data.add(employeeBirthdate.getText());
        data.add(employeePhoneNumber.getText());
        data.add(employeeEthicGroup.getText());
        data.add(employeeType.getText());
        data.add(employeeReligion.getText());
        data.add(employeeNation.getText());
        data.add(employeeDegree.getText());
        data.add(employeePosition.getText());
        data.add(departmentName.getText());
        data.add(employeeSpecialty.getText());
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            updateTableRow();
        } else if (e.getSource() == exportPDFButton) {
            WorkingWithPDF workingWithPDF = new WorkingWithPDF();
            workingWithPDF.createPDF(getDataFromForm());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        userInfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        employeeNameLabel = new javax.swing.JLabel();
        employeePhoneNumberLabel = new javax.swing.JLabel();
        employeeReligionLabel = new javax.swing.JLabel();
        employeeSpecialtyLabel = new javax.swing.JLabel();
        employeeGenderLabel = new javax.swing.JLabel();
        employeePositionLabel = new javax.swing.JLabel();
        employeePhoneNumber = new javax.swing.JLabel();
        employeeName = new javax.swing.JLabel();
        employeeGender = new javax.swing.JLabel();
        employeeReligion = new javax.swing.JLabel();
        employeeSpecialty = new javax.swing.JLabel();
        employeePosition = new javax.swing.JLabel();
        employeeIDLabel = new javax.swing.JLabel();
        employeeBirthdateLabel = new javax.swing.JLabel();
        employeeEthicGroupLabel = new javax.swing.JLabel();
        employeeNationLabel = new javax.swing.JLabel();
        employeeDegreeLabel = new javax.swing.JLabel();
        employeeTypeLabel = new javax.swing.JLabel();
        employeeID = new javax.swing.JLabel();
        employeeBirthdate = new javax.swing.JLabel();
        employeeEthicGroup = new javax.swing.JLabel();
        employeeNation = new javax.swing.JLabel();
        employeeDegree = new javax.swing.JLabel();
        employeeType = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        exportPDFButton = new javax.swing.JButton();
        avatarPanel = new javax.swing.JPanel();
        avatarLabel = new javax.swing.JLabel();
        departmentLabel = new javax.swing.JLabel();
        departmentName = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setBackground(java.awt.Color.white);
        setName("userInfoContentPanel"); // NOI18N

        userInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        userInfoPanel.setName("userInfoPanel"); // NOI18N
        userInfoPanel.setPreferredSize(new java.awt.Dimension(987, 676));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thông Tin Nhân Viên");
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(163, 197, 221)));
        jPanel1.setPreferredSize(new java.awt.Dimension(987, 590));

        employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setText("Tên Nhân Viên :");
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N
        employeeNameLabel.setOpaque(true);
        employeeNameLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeePhoneNumberLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeePhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeePhoneNumberLabel.setText("Số Điện Thoại :");
        employeePhoneNumberLabel.setName("employeePhoneNumberLabel"); // NOI18N
        employeePhoneNumberLabel.setOpaque(true);
        employeePhoneNumberLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeReligionLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeReligionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeReligionLabel.setText("Tôn Giáo :");
        employeeReligionLabel.setName("employeeReligionLabel"); // NOI18N
        employeeReligionLabel.setOpaque(true);
        employeeReligionLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeSpecialtyLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeSpecialtyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeSpecialtyLabel.setText("Chuyên Môn :");
        employeeSpecialtyLabel.setName("employeeSpecialtyLabel"); // NOI18N
        employeeSpecialtyLabel.setOpaque(true);
        employeeSpecialtyLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeGenderLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeGenderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeGenderLabel.setText("Giới Tính :");
        employeeGenderLabel.setName("employeeGenderLabel"); // NOI18N
        employeeGenderLabel.setOpaque(true);
        employeeGenderLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeePositionLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeePositionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeePositionLabel.setText("Chức Vụ :");
        employeePositionLabel.setName("employeePositionLabel"); // NOI18N
        employeePositionLabel.setOpaque(true);
        employeePositionLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeePhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
        employeePhoneNumber.setText("123456789");
        employeePhoneNumber.setName("employeePhoneNumber"); // NOI18N
        employeePhoneNumber.setOpaque(true);
        employeePhoneNumber.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeName.setBackground(new java.awt.Color(255, 255, 255));
        employeeName.setText("Huỳnh Nguyên Khánh Linh");
        employeeName.setName("employeeName"); // NOI18N
        employeeName.setOpaque(true);
        employeeName.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeGender.setBackground(new java.awt.Color(255, 255, 255));
        employeeGender.setText("Nam");
        employeeGender.setName("employeeGender"); // NOI18N
        employeeGender.setOpaque(true);
        employeeGender.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeReligion.setText("Phật Giáo");
        employeeReligion.setName("employeeReligion"); // NOI18N
        employeeReligion.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeSpecialty.setBackground(new java.awt.Color(255, 255, 255));
        employeeSpecialty.setText("BackEnd Dev");
        employeeSpecialty.setName("employeeSpecialty"); // NOI18N
        employeeSpecialty.setOpaque(true);
        employeeSpecialty.setPreferredSize(new java.awt.Dimension(150, 50));

        employeePosition.setBackground(new java.awt.Color(255, 255, 255));
        employeePosition.setText("Trưởng Phòng");
        employeePosition.setName("employeePosition"); // NOI18N
        employeePosition.setOpaque(true);
        employeePosition.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setText("Mã Nhân Viên :");
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);
        employeeIDLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeBirthdateLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeBirthdateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeBirthdateLabel.setText("Ngày Sinh :");
        employeeBirthdateLabel.setName("employeeBirthdateLabel"); // NOI18N
        employeeBirthdateLabel.setOpaque(true);
        employeeBirthdateLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeEthicGroupLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeEthicGroupLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeEthicGroupLabel.setText("Dân Tộc :");
        employeeEthicGroupLabel.setName("employeeEthicGroupLabel"); // NOI18N
        employeeEthicGroupLabel.setOpaque(true);
        employeeEthicGroupLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeNationLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNationLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNationLabel.setText("Quốc Tịch :");
        employeeNationLabel.setName("employeeNationLabel"); // NOI18N
        employeeNationLabel.setOpaque(true);
        employeeNationLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeDegreeLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeDegreeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeDegreeLabel.setText("Bằng Cấp :");
        employeeDegreeLabel.setName("employeeDegreeLabel"); // NOI18N
        employeeDegreeLabel.setOpaque(true);
        employeeDegreeLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeTypeLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeTypeLabel.setText("Loại Nhân Viên :");
        employeeTypeLabel.setName("employeeTypeLabel"); // NOI18N
        employeeTypeLabel.setOpaque(true);
        employeeTypeLabel.setPreferredSize(new java.awt.Dimension(130, 50));

        employeeID.setBackground(new java.awt.Color(255, 255, 255));
        employeeID.setText("NV001");
        employeeID.setName("employeeID"); // NOI18N
        employeeID.setOpaque(true);
        employeeID.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeBirthdate.setBackground(new java.awt.Color(255, 255, 255));
        employeeBirthdate.setText("23/09/2004");
        employeeBirthdate.setName("employeeBirthdate"); // NOI18N
        employeeBirthdate.setOpaque(true);
        employeeBirthdate.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeEthicGroup.setBackground(new java.awt.Color(255, 255, 255));
        employeeEthicGroup.setText("Kinh");
        employeeEthicGroup.setName("employeeEthicGroup"); // NOI18N
        employeeEthicGroup.setOpaque(true);
        employeeEthicGroup.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeNation.setBackground(new java.awt.Color(255, 255, 255));
        employeeNation.setText("Việt Nam");
        employeeNation.setName("employeeNation"); // NOI18N
        employeeNation.setOpaque(true);
        employeeNation.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeDegree.setBackground(new java.awt.Color(255, 255, 255));
        employeeDegree.setText("Kỹ Sư");
        employeeDegree.setName("employeeDegree"); // NOI18N
        employeeDegree.setOpaque(true);
        employeeDegree.setPreferredSize(new java.awt.Dimension(150, 50));

        employeeType.setBackground(new java.awt.Color(255, 255, 255));
        employeeType.setText("Chính Thức");
        employeeType.setName("employeeType"); // NOI18N
        employeeType.setOpaque(true);
        employeeType.setPreferredSize(new java.awt.Dimension(150, 50));

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        updateButton.setText("Chỉnh Sửa");
        updateButton.setIconTextGap(10);
        updateButton.setName("updateButton"); // NOI18N
        updateButton.setOpaque(true);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        exportPDFButton.setBackground(new java.awt.Color(220, 53, 69));
        exportPDFButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exportPDFButton.setForeground(new java.awt.Color(255, 255, 255));
        exportPDFButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pdf.png"))); // NOI18N
        exportPDFButton.setText("Xuất PDF");
        exportPDFButton.setIconTextGap(10);
        exportPDFButton.setName("exportPDFButton"); // NOI18N
        exportPDFButton.setOpaque(true);
        exportPDFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPDFButtonActionPerformed(evt);
            }
        });

        avatarPanel.setBackground(new java.awt.Color(255, 255, 255));
        avatarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        avatarPanel.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout avatarPanelLayout = new javax.swing.GroupLayout(avatarPanel);
        avatarPanel.setLayout(avatarPanelLayout);
        avatarPanelLayout.setHorizontalGroup(
            avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avatarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );
        avatarPanelLayout.setVerticalGroup(
            avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avatarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        departmentLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        departmentLabel.setText("Phòng Ban :");

        departmentName.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(employeeSpecialtyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(employeePositionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(employeeSpecialty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(employeePosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeePhoneNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeePhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeReligionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeReligion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeGenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeGender, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeEthicGroupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeEthicGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeBirthdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(employeeNationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(employeeNation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(employeeIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(employeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(employeeDegreeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(employeeTypeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(employeeDegree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(employeeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(86, 86, 86))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(exportPDFButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(departmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(departmentName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(employeeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeGenderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeePhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeePhoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(employeeIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeBirthdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeEthicGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeEthicGroupLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(employeeReligionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeReligion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeNation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeNationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeSpecialtyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeSpecialty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeDegreeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeDegree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeePositionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeePosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(departmentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(departmentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportPDFButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout userInfoPanelLayout = new javax.swing.GroupLayout(userInfoPanel);
        userInfoPanel.setLayout(userInfoPanelLayout);
        userInfoPanelLayout.setHorizontalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE))
                .addContainerGap())
        );
        userInfoPanelLayout.setVerticalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(userInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(userInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButtonActionPerformed

    private void exportPDFButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exportPDFButtonActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_exportPDFButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JPanel avatarPanel;
    private javax.swing.JLabel departmentLabel;
    private javax.swing.JLabel departmentName;
    private javax.swing.JLabel employeeBirthdate;
    private javax.swing.JLabel employeeBirthdateLabel;
    private javax.swing.JLabel employeeDegree;
    private javax.swing.JLabel employeeDegreeLabel;
    private javax.swing.JLabel employeeEthicGroup;
    private javax.swing.JLabel employeeEthicGroupLabel;
    private javax.swing.JLabel employeeGender;
    private javax.swing.JLabel employeeGenderLabel;
    private javax.swing.JLabel employeeID;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeName;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JLabel employeeNation;
    private javax.swing.JLabel employeeNationLabel;
    private javax.swing.JLabel employeePhoneNumber;
    private javax.swing.JLabel employeePhoneNumberLabel;
    private javax.swing.JLabel employeePosition;
    private javax.swing.JLabel employeePositionLabel;
    private javax.swing.JLabel employeeReligion;
    private javax.swing.JLabel employeeReligionLabel;
    private javax.swing.JLabel employeeSpecialty;
    private javax.swing.JLabel employeeSpecialtyLabel;
    private javax.swing.JLabel employeeType;
    private javax.swing.JLabel employeeTypeLabel;
    private javax.swing.JButton exportPDFButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton updateButton;
    private javax.swing.JPanel userInfoPanel;
    // End of variables declaration//GEN-END:variables

}
