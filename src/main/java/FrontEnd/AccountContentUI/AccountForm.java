package FrontEnd.AccountContentUI;

import BackEnd.AccountManagement.Account;
import BackEnd.EmployeeManagement.Employee;
import FrontEnd.Redux.Redux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AccountForm extends javax.swing.JFrame implements ActionListener, WindowListener {

    JFileChooser fileChooser;
    ArrayList<Object> formData;
    File selectedFile = null;
    String userId;
    String fileName = "";
    ImageIcon imageIcon = null;

    public AccountForm() {
        initComponents();

        formData = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        fileChooser = new JFileChooser();

        // Optional: Set a filter for specific file types
        // Replace IMAGE_EXTENSIONS with a list of supported image formats (adjust as
        // needed)
        String[] IMAGE_EXTENSIONS = new String[] { "jpg", "jpeg", "png", "gif", "bmp" };

        // Filter for image files
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files", IMAGE_EXTENSIONS);

        fileChooser.setFileFilter(imageFilter);

        deleteFileButton.setVisible(false);

        fileChooserButton.addActionListener(this);
        confirmButton.addActionListener(this);
        declineButton.addActionListener(this);
        addWindowListener(this);
    }

    public void formInit() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (this.getTitle().contains("CẬP NHẬT")) {
            System.out.println("Check from CẬP NHẬT");
            if (model.getSize() > 0) {
                employeeIDComboBox.setModel(model);
                employeeIDComboBox.setSelectedIndex(0);
                employeeNameTextField.setText(
                        Redux.employeeBUS.getEmployeeById((String) employeeIDComboBox.getSelectedItem())
                                .getFullName());
            } else {
                employeeIDComboBox.setSelectedItem("");
                employeeNameTextField.setText("");
            }
        } else {
            if (Redux.employeeBUS.getEmployeeNotHaveAccountIdList() != null) {
                try {
                    for (Employee employee : Redux.employeeBUS.getEmployeeNotHaveAccountIdList()) {
                        if (!employee.getDeleteStatus()) {
                            model.addElement(employee.getId());
                        }
                    }
                    employeeIDComboBox.setModel(model);
                    if (model.getSize() > 0) {
                        employeeIDComboBox.setSelectedIndex(0);
                        employeeNameTextField.setText(
                                Redux.employeeBUS.getEmployeeById((String) employeeIDComboBox.getSelectedItem())
                                        .getFullName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (model.getSize() == 1) {
            String selectedEmployeeId = (String) model.getElementAt(0);
            for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                    employeeNameTextField.setText(employee.getFullName());
                    break; // Break the loop once found
                }
            }
        }

        employeeIDComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedEmployeeId = (String) employeeIDComboBox.getSelectedItem();
                for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                    if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                        employeeNameTextField.setText(employee.getFullName());
                    }
                }
            }
        });
    }

    public void handleSubmitForm() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Xác nhận thao tác ?",
                "XÁC NHẬN ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Lấy dữ liệu từ form
            if (this.getTitle().contains("THÊM MỚI")) {
                if (Redux.accountBUS.getAccountByEmail((String) formData.get(3))) {
                    JOptionPane.showMessageDialog(this, "Đã tồn tại tài khoản có email này!",
                            "CẢNH BÁO",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Redux.accountBUS.addAccount(
                        new Account(
                                Redux.employeeBUS.getEmployeeById(
                                        (String) formData.get(0)),
                                // (String) accountBUS.getNextID(),
                                (String) formData.get(1),
                                (String) formData.get(2),
                                (String) formData.get(3),
                                (String) formData.get(4),
                                (String) formData.get(6)));
            } else {
                Redux.accountBUS.updateAccount(
                        new Account(
                                Redux.employeeBUS.getEmployeeById(
                                        (String) formData.get(0)),
                                // (String) employeeID,
                                (String) formData.get(1),
                                (String) formData.get(2),
                                (String) formData.get(3),
                                (String) formData.get(4),
                                // (String) formData.get(5),
                                (String) formData.get(6),
                                (boolean) formData.get(7),
                                (boolean) formData.get(8)));
            }

            if (selectedFile != null) {
                // Save the selected file to the avatars folder
                File destinationFile = new File("src/main/resources/avatars/" + fileName);

                try {

                    System.out.println(
                            "Check destination file: " + destinationFile.getAbsolutePath());

                    Files.copy(selectedFile.toPath(), destinationFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            AccountManagementContentPanel.tableInit(Redux.accountBUS.getAccountList());
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
                || emailTextField.getText().equals("")
                || passwordField.getPassword().equals("")
                || confirmPasswordField.getPassword().equals("")
                || (!adminRadioButton.isSelected()
                        && !employeeRadioButton.isSelected()));
    }

    public boolean isFormValid() {
        System.out.println("Check password: " + String.valueOf(passwordField.getPassword()));
        System.out.println("Check confirm password: " + String.valueOf(confirmPasswordField.getPassword()));
        return Account.isValidEmail(emailTextField.getText())
                && String.valueOf(passwordField.getPassword())
                        .equals(String.valueOf(confirmPasswordField.getPassword()));
    }

    public void formSetEnable(boolean isEnable) {
        employeeIDComboBox.setEnabled(isEnable);
        employeeNameTextField.setEnabled(isEnable);
        emailTextField.setEnabled(isEnable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChooserButton) {
            // Show the file chooser dialog
            int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                selectedFile = fileChooser.getSelectedFile();

                // Get the file name with extension
                fileName = selectedFile.getName();

                // Display the file name in a message box
                avatarLabel.setText(fileName);

                // Read the image file
                String imagePath = selectedFile.getAbsolutePath(); // Use absolute path instead of
                // relative path
                imageIcon = new ImageIcon(imagePath);

                deleteFileButton.setVisible(true);

                // Set the image as the icon of avatarContainer
                avatarContainer.setIcon(imageIcon);
            }
        } else if (e.getSource() == deleteFileButton) {
            selectedFile = null;
            fileName = "Không có tệp nào được chọn";
            imageIcon = null;
            avatarContainer.setIcon(null);
            deleteFileButton.setVisible(false);
            avatarLabel.setText("Không có ảnh");
        } else if (e.getSource() == confirmButton) {
            if (isFormFilled()) {
                if (isFormValid()) {
                    handleSubmitForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!", "CẢNH BÁO",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hãy nhập đúng thông tin trước!", "CẢNH BÁO",
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
            formSetEnable(false);
            DefaultComboBoxModel<String> employeeModel = (DefaultComboBoxModel<String>) employeeIDComboBox
                    .getModel();
            employeeModel.removeAllElements();
            employeeModel.addElement((String) data.get(0));
            employeeIDComboBox.setSelectedItem((String) data.get(0));
            employeeNameTextField.setText((String) data.get(1));
            passwordField.setText((String) data.get(2));
            confirmPasswordField.setText((String) data.get(2));
            emailTextField.setText((String) data.get(3));
            avatarLabel.setText((String) data.get(4));
            String fileName = (String) data.get(4);
            if (fileName != null && !fileName.isEmpty()) {
                String imagePath = "/avatars/" + fileName;
                ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                avatarContainer.setIcon(icon);
            } else {
                avatarContainer.setIcon(null);
            }
            if (data.get(5).equals("Admin")) {
                ButtonModel adminRadioButtonModel = adminRadioButton.getModel();
                buttonGroup1.setSelected(adminRadioButtonModel, true);
            } else {
                ButtonModel employeeRadioButtonModel = employeeRadioButton.getModel();
                buttonGroup1.setSelected(employeeRadioButtonModel, true);
            }
            accountStatusComboBox.setSelectedItem(data.get(6));

        }
    }

    public void clearFormData() {
        formSetEnable(true);
        avatarLabel.setText("Không có tệp nào được chọn");
        avatarContainer.setIcon(null);
        emailTextField.setText("");
        employeeIDComboBox.setSelectedItem("");
        employeeNameTextField.setText("");
        ButtonModel adminRadioButtonModel = adminRadioButton.getModel();
        buttonGroup1.setSelected(adminRadioButtonModel, false);
        ButtonModel employeeRadioButtonModel = employeeRadioButton.getModel();
        buttonGroup1.setSelected(employeeRadioButtonModel, false);
        accountStatusComboBox.setSelectedItem("");
        DefaultComboBoxModel<String> employeeModel = (DefaultComboBoxModel<String>) employeeIDComboBox
                .getModel();
        employeeModel.removeAllElements();
    }

    public ArrayList<Object> getDataFromForm() {
        String employeeID = (String) employeeIDComboBox.getSelectedItem(),
                employeeName = employeeNameTextField.getText(),
                email = emailTextField.getText(),
                password = String.valueOf(passwordField.getPassword()),
                confirmPassword = String.valueOf(confirmPasswordField.getPassword()),
                selectedValue = "",
                fileName = avatarLabel.getText(),
                accountStatus = (String) accountStatusComboBox.getSelectedItem();

        Enumeration<AbstractButton> allRadioButton = buttonGroup1.getElements();
        while (allRadioButton.hasMoreElements()) {
            JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
            if (temp.isSelected()) {
                selectedValue = temp.getName();
            }
        }
        // String qlnv = employeeID + ' ' + employeeName +' '+ password+' ' + email +'
        // '+confirmPassword+' '+selectedValue;
        // JOptionPane.showMessageDialog(this,qlnv , "CẢNH BÁO",
        // JOptionPane.INFORMATION_MESSAGE);
        return new ArrayList<>(
                Arrays.asList(employeeID, employeeName, password, email, fileName, confirmPassword,
                        selectedValue,
                        accountStatus.equalsIgnoreCase("Đang hoạt động") ? true : false,
                        accountStatus.equalsIgnoreCase("Ngừng hoạt động") ? false : true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fileChooserPanel = new javax.swing.JPanel();
        fileChooserButton = new javax.swing.JButton();
        avatarLabel = new javax.swing.JLabel();
        employeeIDLabel = new javax.swing.JLabel();
        employeeNameLabel = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        confirmPasswordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPasswordField = new javax.swing.JPasswordField();
        authenticateLabel = new javax.swing.JLabel();
        adminRadioButton = new javax.swing.JRadioButton();
        employeeRadioButton = new javax.swing.JRadioButton();
        confirmButton = new javax.swing.JButton();
        declineButton = new javax.swing.JButton();
        avatarPanel = new javax.swing.JPanel();
        avatarContainer = new javax.swing.JLabel();
        deleteFileButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        accountStatusComboBox = new javax.swing.JComboBox<>();
        employeeIDComboBox = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(660, 700));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setLabelFor(fileChooserPanel);
        jLabel6.setText("Chọn Hình Ảnh :");

        fileChooserPanel.setBackground(new java.awt.Color(255, 255, 255));
        fileChooserPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        fileChooserPanel.setName("fileChooserPanel"); // NOI18N

        fileChooserButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        fileChooserButton.setForeground(new java.awt.Color(255, 255, 255));
        fileChooserButton.setText("Chọn tệp");
        fileChooserButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fileChooserButton.setName("fileChooserButton"); // NOI18N
        fileChooserButton.setPreferredSize(new java.awt.Dimension(91, 36));
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserButtonActionPerformed(evt);
            }
        });

        avatarLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        avatarLabel.setForeground(new java.awt.Color(0, 0, 0));
        avatarLabel.setText("Không có tệp nào được chọn");
        avatarLabel.setName("avatarLabel"); // NOI18N

        javax.swing.GroupLayout fileChooserPanelLayout = new javax.swing.GroupLayout(fileChooserPanel);
        fileChooserPanel.setLayout(fileChooserPanelLayout);
        fileChooserPanelLayout.setHorizontalGroup(
                fileChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(fileChooserPanelLayout.createSequentialGroup()
                                .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(avatarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 169,
                                        Short.MAX_VALUE)));
        fileChooserPanelLayout.setVerticalGroup(
                fileChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(avatarLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileChooserButton, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE));

        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeIDLabel.setText("Mã Nhân Viên :");
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);

        employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameLabel.setLabelFor(employeeNameTextField);
        employeeNameLabel.setText("Tên Nhân Viên :");
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N
        employeeNameLabel.setOpaque(true);

        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameTextField.setEnabled(false);
        employeeNameTextField.setName("employeeNameTextField"); // NOI18N
        employeeNameTextField.setOpaque(true);

        emailLabel.setBackground(new java.awt.Color(255, 255, 255));
        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(0, 0, 0));
        emailLabel.setLabelFor(emailTextField);
        emailLabel.setText("Email :");
        emailLabel.setName("emailLabel"); // NOI18N
        emailLabel.setOpaque(true);

        emailTextField.setBackground(new java.awt.Color(204, 204, 204));
        emailTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        emailTextField.setForeground(new java.awt.Color(0, 0, 0));
        emailTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        emailTextField.setName("emailTextField"); // NOI18N
        emailTextField.setOpaque(true);

        passwordLabel.setBackground(new java.awt.Color(255, 255, 255));
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(0, 0, 0));
        passwordLabel.setLabelFor(passwordField);
        passwordLabel.setText("Mật Khẩu :");
        passwordLabel.setName("passwordLabel"); // NOI18N
        passwordLabel.setOpaque(true);

        confirmPasswordLabel.setBackground(new java.awt.Color(255, 255, 255));
        confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmPasswordLabel.setForeground(new java.awt.Color(0, 0, 0));
        confirmPasswordLabel.setLabelFor(confirmPasswordField);
        confirmPasswordLabel.setText("Xác Nhận Mật Khẩu :");
        confirmPasswordLabel.setName("confirmPasswordLabel"); // NOI18N
        confirmPasswordLabel.setOpaque(true);

        passwordField.setBackground(new java.awt.Color(204, 204, 204));
        passwordField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        passwordField.setForeground(new java.awt.Color(0, 0, 0));
        passwordField.setCaretColor(new java.awt.Color(0, 0, 0));
        passwordField.setName("passwordField"); // NOI18N
        passwordField.setOpaque(true);

        confirmPasswordField.setBackground(new java.awt.Color(204, 204, 204));
        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        confirmPasswordField.setForeground(new java.awt.Color(0, 0, 0));
        confirmPasswordField.setCaretColor(new java.awt.Color(0, 0, 0));
        confirmPasswordField.setName("confirmPasswordField"); // NOI18N
        confirmPasswordField.setOpaque(true);

        authenticateLabel.setBackground(new java.awt.Color(255, 255, 255));
        authenticateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        authenticateLabel.setForeground(new java.awt.Color(0, 0, 0));
        authenticateLabel.setText("Phân Quyền :");
        authenticateLabel.setName("authenticateLabel"); // NOI18N
        authenticateLabel.setOpaque(true);

        adminRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(adminRadioButton);
        adminRadioButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adminRadioButton.setForeground(new java.awt.Color(0, 0, 0));
        adminRadioButton.setText("Quản Trị");
        adminRadioButton.setIconTextGap(10);
        adminRadioButton.setName("admin"); // NOI18N
        adminRadioButton.setOpaque(true);

        employeeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(employeeRadioButton);
        employeeRadioButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeRadioButton.setForeground(new java.awt.Color(0, 0, 0));
        employeeRadioButton.setText("Nhân Viên :");
        employeeRadioButton.setIconTextGap(10);
        employeeRadioButton.setName("employee"); // NOI18N
        employeeRadioButton.setOpaque(true);
        employeeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeRadioButtonActionPerformed(evt);
            }
        });

        confirmButton.setBackground(new java.awt.Color(13, 110, 253));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Xác Nhận");
        confirmButton.setName("confirmButton"); // NOI18N
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        declineButton.setBackground(new java.awt.Color(108, 117, 125));
        declineButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        declineButton.setForeground(new java.awt.Color(255, 255, 255));
        declineButton.setText("Hủy Bỏ");
        declineButton.setName("declineButton"); // NOI18N

        avatarPanel.setBackground(new java.awt.Color(255, 255, 255));
        avatarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        avatarPanel.setPreferredSize(new java.awt.Dimension(150, 150));

        avatarContainer.setBackground(new java.awt.Color(255, 255, 255));
        avatarContainer.setOpaque(true);
        avatarContainer.setPreferredSize(new java.awt.Dimension(136, 136));

        javax.swing.GroupLayout avatarPanelLayout = new javax.swing.GroupLayout(avatarPanel);
        avatarPanel.setLayout(avatarPanelLayout);
        avatarPanelLayout.setHorizontalGroup(
                avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(avatarPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        avatarPanelLayout.setVerticalGroup(
                avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(avatarPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        deleteFileButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteFileButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteFileButton.setText("Xóa Ảnh");
        deleteFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFileButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Trạng thái");
        jLabel1.setMaximumSize(new java.awt.Dimension(139, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(139, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(139, 20));

        accountStatusComboBox.setBackground(new java.awt.Color(204, 204, 204));
        accountStatusComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        accountStatusComboBox.setForeground(new java.awt.Color(0, 0, 0));
        accountStatusComboBox
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang hoạt động", "Ngừng hoạt động" }));
        accountStatusComboBox.setMinimumSize(new java.awt.Dimension(64, 22));
        accountStatusComboBox.setPreferredSize(new java.awt.Dimension(64, 22));

        employeeIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeIDComboBox.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(emailTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(emailLabel,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 261,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel6,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(deleteFileButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(fileChooserPanel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(employeeIDLabel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(employeeIDComboBox, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(9, 9, 9)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(employeeNameLabel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(avatarPanel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(employeeNameTextField)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(passwordLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(76, 76, 76)
                                                                .addComponent(confirmPasswordLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(76, 76, 76)
                                                                .addComponent(authenticateLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 138, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addComponent(accountStatusComboBox,
                                                                javax.swing.GroupLayout.Alignment.LEADING, 0, 258,
                                                                Short.MAX_VALUE)
                                                        .addComponent(passwordField,
                                                                javax.swing.GroupLayout.Alignment.LEADING))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(confirmPasswordField)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(adminRadioButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 129,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(employeeRadioButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 118,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(32, 32, 32))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(declineButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40,
                                                                Short.MAX_VALUE)
                                                        .addComponent(deleteFileButton,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fileChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(employeeIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(employeeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(employeeIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(authenticateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(accountStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(adminRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(employeeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(declineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE)));

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE));

        setSize(new java.awt.Dimension(694, 727));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void employeeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeRadioButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeRadioButtonActionPerformed

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileChooserButtonActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_fileChooserButtonActionPerformed

    private void deleteFileButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteFileButtonActionPerformed
        // TODO add your handling code here:
        selectedFile = null;
        fileName = "Không có tệp nào được chọn";
        imageIcon = null;
        avatarContainer.setIcon(null);
        deleteFileButton.setVisible(false);
        avatarLabel.setText("Không có ảnh");
    }// GEN-LAST:event_deleteFileButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_confirmButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accountStatusComboBox;
    private javax.swing.JRadioButton adminRadioButton;
    private javax.swing.JLabel authenticateLabel;
    private javax.swing.JLabel avatarContainer;
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JPanel avatarPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton confirmButton;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JButton declineButton;
    private javax.swing.JButton deleteFileButton;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JComboBox<String> employeeIDComboBox;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JRadioButton employeeRadioButton;
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JPanel fileChooserPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
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
