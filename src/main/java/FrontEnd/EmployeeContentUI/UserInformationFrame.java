package FrontEnd.EmployeeContentUI;

import javax.swing.WindowConstants;

import BackEnd.EmployeeManagement.Employee;

public class UserInformationFrame extends javax.swing.JFrame {

        public UserInformationFrame() {
                initComponents();
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }

        public void showFormWithData(Employee employee) {
                employeeName1.setText(employee.getFullName());
                employeeID.setText(employee.getId());
                employeeBirthdate
                                .setText(Employee.formatBirthDateToStandardType(employee.getBirthDate()));
                employeeGender.setText(employee.getGender());
                employeePhoneNumber.setText(employee.getPhoneNumber());
                employeeReligion.setText(employee.getReligion());
                employeeEthicGroup.setText(employee.getEthnicGroup());
                employeeNation.setText(employee.getNation());
                employeeDegree.setText(employee.getDegree().getDegreeName());
                employeeType.setText(employee.getType());
                employeeSpecialty.setText(employee.getSpecialty().getSpecialtyName());
                employeePosition.setText(employee.getPosition().getPositionName());
                departmentNameTextField.setText(employee.getDepartment().getDepartmentName());
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                userInfoContainer = new javax.swing.JPanel();
                userInfoPanel = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                emloyeeAvatar = new javax.swing.JPanel();
                employeeNameLabel = new javax.swing.JLabel();
                employeePhoneNumberLabel = new javax.swing.JLabel();
                employeeReligionLabel = new javax.swing.JLabel();
                employeeSpecialtyLabel = new javax.swing.JLabel();
                employeeGenderLabel = new javax.swing.JLabel();
                employeePositionLabel = new javax.swing.JLabel();
                employeePhoneNumber = new javax.swing.JLabel();
                employeeName1 = new javax.swing.JLabel();
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
                departmentNameLabel = new javax.swing.JLabel();
                departmentNameTextField = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("THÔNG TIN NHÂN VIÊN");
                setResizable(false);

                userInfoContainer.setBackground(new java.awt.Color(237, 240, 247));
                userInfoContainer.setName("userInfoContainer"); // NOI18N

                userInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
                userInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
                userInfoPanel.setName("userInfoPanel"); // NOI18N

                jLabel1.setText("Thông Tin Nhân Viên");
                jLabel1.setBackground(new java.awt.Color(255, 255, 255));
                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(0, 0, 0));
                jLabel1.setOpaque(true);

                jPanel1.setBackground(new java.awt.Color(255, 255, 255));
                jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0,
                                new java.awt.Color(163, 197, 221)));

                emloyeeAvatar.setBackground(new java.awt.Color(255, 255, 255));
                emloyeeAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                emloyeeAvatar.setMinimumSize(new java.awt.Dimension(300, 300));
                emloyeeAvatar.setName("emloyeeAvatar"); // NOI18N
                emloyeeAvatar.setPreferredSize(new java.awt.Dimension(150, 150));
                emloyeeAvatar.setToolTipText("");

                javax.swing.GroupLayout emloyeeAvatarLayout = new javax.swing.GroupLayout(emloyeeAvatar);
                emloyeeAvatar.setLayout(emloyeeAvatarLayout);
                emloyeeAvatarLayout.setHorizontalGroup(
                                emloyeeAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));
                emloyeeAvatarLayout.setVerticalGroup(
                                emloyeeAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));

                employeeNameLabel.setLabelFor(employeePhoneNumber);
                employeeNameLabel.setText("Tên Nhân Viên :");
                employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeNameLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeNameLabel.setName("employeeNameLabel"); // NOI18N
                employeeNameLabel.setOpaque(true);

                employeePhoneNumberLabel.setLabelFor(employeePhoneNumber);
                employeePhoneNumberLabel.setText("Số Điện Thoại :");
                employeePhoneNumberLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeePhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeePhoneNumberLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeePhoneNumberLabel.setName("employeePhoneNumberLabel"); // NOI18N
                employeePhoneNumberLabel.setOpaque(true);
                employeePhoneNumberLabel.setPreferredSize(new java.awt.Dimension(130, 50));

                employeeReligionLabel.setLabelFor(employeeReligion);
                employeeReligionLabel.setText("Tôn Giáo :");
                employeeReligionLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeReligionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeReligionLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeReligionLabel.setName("employeeReligionLabel"); // NOI18N
                employeeReligionLabel.setOpaque(true);

                employeeSpecialtyLabel.setLabelFor(employeeSpecialty);
                employeeSpecialtyLabel.setText("Chuyên Môn :");
                employeeSpecialtyLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeSpecialtyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeSpecialtyLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeSpecialtyLabel.setName("employeeSpecialtyLabel"); // NOI18N
                employeeSpecialtyLabel.setOpaque(true);

                employeeGenderLabel.setLabelFor(employeeGender);
                employeeGenderLabel.setText("Giới Tính :");
                employeeGenderLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeGenderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeGenderLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeGenderLabel.setName("employeeGenderLabel"); // NOI18N
                employeeGenderLabel.setOpaque(true);

                employeePositionLabel.setLabelFor(employeePosition);
                employeePositionLabel.setText("Chức Vụ :");
                employeePositionLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeePositionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeePositionLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeePositionLabel.setName("employeePositionLabel"); // NOI18N
                employeePositionLabel.setOpaque(true);

                employeePhoneNumber.setText("123456789");
                employeePhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
                employeePhoneNumber.setForeground(new java.awt.Color(0, 0, 0));
                employeePhoneNumber.setName("employeePhoneNumber"); // NOI18N
                employeePhoneNumber.setOpaque(true);

                employeeName1.setText("Kagami");
                employeeName1.setBackground(new java.awt.Color(255, 255, 255));
                employeeName1.setForeground(new java.awt.Color(0, 0, 0));
                employeeName1.setName("employeeName"); // NOI18N
                employeeName1.setOpaque(true);

                employeeGender.setText("Nam");
                employeeGender.setBackground(new java.awt.Color(255, 255, 255));
                employeeGender.setForeground(new java.awt.Color(0, 0, 0));
                employeeGender.setName("employeeGender"); // NOI18N
                employeeGender.setOpaque(true);

                employeeReligion.setText("Phật Giáo");
                employeeReligion.setForeground(new java.awt.Color(0, 0, 0));
                employeeReligion.setName("employeeReligion"); // NOI18N

                employeeSpecialty.setText("BackEnd Dev");
                employeeSpecialty.setBackground(new java.awt.Color(255, 255, 255));
                employeeSpecialty.setForeground(new java.awt.Color(0, 0, 0));
                employeeSpecialty.setName("employeeSpecialty"); // NOI18N
                employeeSpecialty.setOpaque(true);

                employeePosition.setText("Trưởng Phòng");
                employeePosition.setBackground(new java.awt.Color(255, 255, 255));
                employeePosition.setForeground(new java.awt.Color(0, 0, 0));
                employeePosition.setName("employeePosition"); // NOI18N
                employeePosition.setOpaque(true);

                employeeIDLabel.setLabelFor(employeeID);
                employeeIDLabel.setText("Mã Nhân Viên :");
                employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeIDLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeIDLabel.setName("employeeIDLabel"); // NOI18N
                employeeIDLabel.setOpaque(true);

                employeeBirthdateLabel.setLabelFor(employeeBirthdate);
                employeeBirthdateLabel.setText("Ngày Sinh :");
                employeeBirthdateLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeBirthdateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeBirthdateLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeBirthdateLabel.setName("employeeBirthdateLabel"); // NOI18N
                employeeBirthdateLabel.setOpaque(true);

                employeeEthicGroupLabel.setLabelFor(employeeEthicGroup);
                employeeEthicGroupLabel.setText("Dân Tộc :");
                employeeEthicGroupLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeEthicGroupLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeEthicGroupLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeEthicGroupLabel.setName("employeeEthicGroupLabel"); // NOI18N
                employeeEthicGroupLabel.setOpaque(true);

                employeeNationLabel.setLabelFor(employeeNation);
                employeeNationLabel.setText("Quốc Tịch :");
                employeeNationLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeNationLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeNationLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeNationLabel.setName("employeeNationLabel"); // NOI18N
                employeeNationLabel.setOpaque(true);

                employeeDegreeLabel.setLabelFor(employeeDegree);
                employeeDegreeLabel.setText("Bằng Cấp :");
                employeeDegreeLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeDegreeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeDegreeLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeDegreeLabel.setName("employeeDegreeLabel"); // NOI18N
                employeeDegreeLabel.setOpaque(true);

                employeeTypeLabel.setLabelFor(employeeType);
                employeeTypeLabel.setText("Loại Nhân Viên :");
                employeeTypeLabel.setBackground(new java.awt.Color(255, 255, 255));
                employeeTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                employeeTypeLabel.setForeground(new java.awt.Color(0, 0, 0));
                employeeTypeLabel.setName("employeeTypeLabel"); // NOI18N
                employeeTypeLabel.setOpaque(true);

                employeeID.setText("NV001");
                employeeID.setBackground(new java.awt.Color(255, 255, 255));
                employeeID.setForeground(new java.awt.Color(0, 0, 0));
                employeeID.setName("employeeID"); // NOI18N
                employeeID.setOpaque(true);

                employeeBirthdate.setText("23/09/2004");
                employeeBirthdate.setBackground(new java.awt.Color(255, 255, 255));
                employeeBirthdate.setForeground(new java.awt.Color(0, 0, 0));
                employeeBirthdate.setName("employeeBirthdate"); // NOI18N
                employeeBirthdate.setOpaque(true);

                employeeEthicGroup.setText("Kinh");
                employeeEthicGroup.setBackground(new java.awt.Color(255, 255, 255));
                employeeEthicGroup.setForeground(new java.awt.Color(0, 0, 0));
                employeeEthicGroup.setName("employeeEthicGroup"); // NOI18N
                employeeEthicGroup.setOpaque(true);

                employeeNation.setText("Việt Nam");
                employeeNation.setBackground(new java.awt.Color(255, 255, 255));
                employeeNation.setForeground(new java.awt.Color(0, 0, 0));
                employeeNation.setName("employeeNation"); // NOI18N
                employeeNation.setOpaque(true);

                employeeDegree.setText("Kỹ Sư");
                employeeDegree.setBackground(new java.awt.Color(255, 255, 255));
                employeeDegree.setForeground(new java.awt.Color(0, 0, 0));
                employeeDegree.setName("employeeDegree"); // NOI18N
                employeeDegree.setOpaque(true);

                employeeType.setText("Chính Thức");
                employeeType.setBackground(new java.awt.Color(255, 255, 255));
                employeeType.setForeground(new java.awt.Color(0, 0, 0));
                employeeType.setName("employeeType"); // NOI18N
                employeeType.setOpaque(true);

                departmentNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                departmentNameLabel.setForeground(new java.awt.Color(0, 0, 0));
                departmentNameLabel.setText("Phòng Ban :");
                departmentNameLabel.setName("departmentNameLabel"); // NOI18N

                departmentNameTextField.setForeground(new java.awt.Color(0, 0, 0));
                departmentNameTextField.setText("Kế Toán");
                departmentNameTextField.setName("departmentNameTextField"); // NOI18N

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(emloyeeAvatar,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(employeeReligionLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(employeeReligion,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(employeeNationLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(employeeNation,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeeNameLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeeName1,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeeGenderLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeeGender,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeePhoneNumberLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeePhoneNumber,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(employeeEthicGroupLabel,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(employeeBirthdateLabel,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                130,
                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(employeeBirthdate,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(employeeEthicGroup,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeeIDLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeeID,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(departmentNameLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(departmentNameTextField,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeePositionLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeePosition,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(employeeTypeLabel,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(employeeSpecialtyLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(employeeSpecialty,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(employeeDegreeLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                130,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(employeeDegree,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                130,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(employeeType,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                130,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                .addContainerGap(12, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(30, 30, 30)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(employeeNameLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeName1,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeIDLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                50,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeID,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(employeeBirthdateLabel,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeGender,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeBirthdate,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeGenderLabel,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                50,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(employeePhoneNumberLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                50,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addComponent(employeePhoneNumber,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                50,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(employeeEthicGroupLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(employeeEthicGroup,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)))
                                                                                .addComponent(emloyeeAvatar,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(employeeReligionLabel,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                50,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(employeeReligion,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(employeeNationLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                50,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(employeeNation,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                50,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(employeeSpecialtyLabel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(employeeSpecialty,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(employeeDegreeLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(employeeDegree,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                50,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(employeePositionLabel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(employeePosition,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(employeeTypeLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(employeeType,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                50,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(departmentNameLabel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                50,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(departmentNameTextField,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));

                javax.swing.GroupLayout userInfoPanelLayout = new javax.swing.GroupLayout(userInfoPanel);
                userInfoPanel.setLayout(userInfoPanelLayout);
                userInfoPanelLayout.setHorizontalGroup(
                                userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                userInfoPanelLayout.createSequentialGroup()
                                                                                .addContainerGap()
                                                                                .addGroup(userInfoPanelLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addComponent(jPanel1,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jLabel1,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addContainerGap()));
                userInfoPanelLayout.setVerticalGroup(
                                userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(userInfoPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout userInfoContainerLayout = new javax.swing.GroupLayout(userInfoContainer);
                userInfoContainer.setLayout(userInfoContainerLayout);
                userInfoContainerLayout.setHorizontalGroup(
                                userInfoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(userInfoContainerLayout.createSequentialGroup()
                                                                .addGap(15, 15, 15)
                                                                .addComponent(userInfoPanel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(15, 15, 15)));
                userInfoContainerLayout.setVerticalGroup(
                                userInfoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                userInfoContainerLayout
                                                                                .createSequentialGroup()
                                                                                .addGap(35, 35, 35)
                                                                                .addComponent(userInfoPanel,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(35, 35, 35)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(userInfoContainer,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(userInfoContainer,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel departmentNameLabel;
        private javax.swing.JLabel departmentNameTextField;
        private javax.swing.JPanel emloyeeAvatar;
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
        private javax.swing.JLabel employeeName1;
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
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel userInfoContainer;
        private javax.swing.JPanel userInfoPanel;
        // End of variables declaration//GEN-END:variables
}
