package FrontEnd.ProjectContentUI;

import BackEnd.AssignmentManagement.Assignment;
import FrontEnd.Redux.Redux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;

public class AssignmentForm extends javax.swing.JFrame implements ActionListener, WindowListener {

    public boolean btnconfirmClicked = false;
    ArrayList<Object> formData;
    ArrayList<Object> prevState;

    public AssignmentForm() {
        initComponents();   

        formData = new ArrayList<>();
        prevState = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
        for (int i = 0; i < Redux.employeeBUS.getEmployeeList().size(); i++) {
            employeeIDComboBox.addItem(Redux.employeeBUS.getEmployeeList().get(i).getId());
        }
        for (int i = 0; i < Redux.projectBUS.getProjectList().size(); i++) {
            projectIDComboBox.addItem(Redux.projectBUS.getProjectList().get(i).getProjectId());
        }
        addWindowListener(this);
    }

    public ArrayList<Object> getDataFromForm() {
        String employeeID = (String) employeeIDComboBox.getSelectedItem(),
                employeeName = employeeNameTextField.getText(),
                projectID = (String) projectIDComboBox.getSelectedItem(),
                projectName = employeeNameTextField.getText();

        return new ArrayList<>(Arrays.asList(employeeID, employeeName, projectID, projectName));
    }

    public void showFormWithData(ArrayList<Object> data) {
        if (data != null) {
            employeeIDComboBox.setSelectedItem(data.get(1));
            employeeNameTextField.setText((String) data.get(2));
            projectIDComboBox.setSelectedItem(data.get(3));
            projectNameTextField.setText((String) data.get(4));
            prevState = data;
        }
    }

    public void clearFormData() {
        employeeIDComboBox.setSelectedItem("");
        employeeNameTextField.setText("");
        projectIDComboBox.setSelectedItem("");
        projectNameTextField.setText("");
    }

    public static String getEmployeeIdFromAssignmentForm() {
        return employeeIDComboBox.getSelectedItem().toString();
    }

    public static String getProjectIdFromAssignmentForm() {
        return projectIDComboBox.getSelectedItem().toString();
    }

    public static JButton getConfirmButton() {
        return confirmButton;
    }

    public void handleSubmitForm() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Xác nhận thao tác ?",
                "XÁC NHẬN ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (this.getTitle().contains("THÊM MỚI")) {
                if (Redux.assignmentBUS.checkInList(employeeIDComboBox.getSelectedItem().toString(),
                        projectIDComboBox.getSelectedItem().toString())) {
                    JOptionPane.showMessageDialog(null, "Nhân viên này đã được đăng kí công tác này.");
                }

                Assignment newasm = new Assignment(
                        Redux.employeeBUS.getEmployeeById(employeeIDComboBox.getSelectedItem().toString()),
                        Redux.projectBUS.getProjectById(projectIDComboBox.getSelectedItem().toString()), false);

                Redux.assignmentBUS.addNewAssignment(newasm);
            } else {
                Assignment newasm = new Assignment(
                        Redux.employeeBUS.getEmployeeById(employeeIDComboBox.getSelectedItem().toString()),
                        Redux.projectBUS.getProjectById(projectIDComboBox.getSelectedItem().toString()), false);

                Redux.assignmentBUS.updateAssignment(prevState, newasm);
            }
            AssignmentManagementContentPanel.tableInit(Redux.assignmentBUS.getAssignmentsList());
            dispose();
        }
    }

    public void cancelSubmitForm() {
        formData = getDataFromForm();

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
        String tmp = "1.2";
        float t = (Float.parseFloat(tmp));
        return !(employeeNameTextField.getText().equals("")
                || projectNameTextField.getText().equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            if (isFormFilled()) {
                handleSubmitForm();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy nhập thông tin trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            if (isFormFilled()) {
                cancelSubmitForm();
            } else {
                clearFormData();
                dispose();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        employeeIDLabel = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        projectIDLabel = new javax.swing.JLabel();
        projectIDComboBox = new javax.swing.JComboBox<>();
        projectNameLabel = new javax.swing.JLabel();
        employeeIDComboBox = new javax.swing.JComboBox<>();
        employeeNameLabel = new javax.swing.JLabel();
        projectNameTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("THÊM MỚI THÔNG TIN CÔNG TÁC CỦA NHÂN VIÊN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 600));

        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setLabelFor(projectIDComboBox);
        employeeIDLabel.setText("Mã Nhân Viên :");
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);

        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setEnabled(false);
        employeeNameTextField.setName("employeeNameTextField"); // NOI18N
        employeeNameTextField.setOpaque(true);

        projectIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        projectIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        projectIDLabel.setLabelFor(employeeNameTextField);
        projectIDLabel.setText("Mã Dự Án :");
        projectIDLabel.setName("projectIDLabel"); // NOI18N
        projectIDLabel.setOpaque(true);

        projectIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        projectIDComboBox.setEditable(true);
        projectIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        projectIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectIDComboBox.setName("projectIDComboBox"); // NOI18N
        projectIDComboBox.setOpaque(true);
        projectIDComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                projectIDComboBoxItemStateChanged(evt);
            }
        });
        projectIDComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectIDComboBoxMouseClicked(evt);
            }
        });

        projectNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        projectNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        projectNameLabel.setLabelFor(employeeNameTextField);
        projectNameLabel.setText("Tên Dự Án :");
        projectNameLabel.setName("projectNameLabel"); // NOI18N
        projectNameLabel.setOpaque(true);

        employeeIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeIDComboBox.setEditable(true);
        employeeIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeIDComboBox.setName("employeeIDComboBox"); // NOI18N
        employeeIDComboBox.setOpaque(true);
        employeeIDComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                employeeIDComboBoxItemStateChanged(evt);
            }
        });
        employeeIDComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeIDComboBoxMouseClicked(evt);
            }
        });

        employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setLabelFor(employeeNameTextField);
        employeeNameLabel.setText("Tên Nhân Viên :");
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N
        employeeNameLabel.setOpaque(true);

        projectNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        projectNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        projectNameTextField.setEnabled(false);
        projectNameTextField.setName("projectNameTextField"); // NOI18N
        projectNameTextField.setOpaque(true);

        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy Bỏ");
        cancelButton.setToolTipText("cancelButton");
        cancelButton.setOpaque(true);

        confirmButton.setBackground(new java.awt.Color(13, 110, 253));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Xác Nhận");
        confirmButton.setName("confirmButton"); // NOI18N
        confirmButton.setOpaque(true);
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(projectNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(projectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(employeeIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(employeeNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addComponent(projectIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectIDComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeNameTextField)
                            .addComponent(employeeIDComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(187, 187, 187))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void employeeIDComboBoxMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_employeeIDComboBoxMouseClicked
    }// GEN-LAST:event_employeeIDComboBoxMouseClicked

    private void projectIDComboBoxMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_projectIDComboBoxMouseClicked

    }// GEN-LAST:event_projectIDComboBoxMouseClicked

    private void employeeIDComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_employeeIDComboBoxItemStateChanged
        // TODO add your handling code here:
        String emId = employeeIDComboBox.getSelectedItem().toString();
        for (int i = 0; i < Redux.employeeBUS.getEmployeeList().size(); i++) {
            if (emId.equalsIgnoreCase(Redux.employeeBUS.getEmployeeList().get(i).getId())) {
                employeeNameTextField.setText(Redux.employeeBUS.getEmployeeList().get(i).getFullName());
            }
        }
    }// GEN-LAST:event_employeeIDComboBoxItemStateChanged

    private void projectIDComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_projectIDComboBoxItemStateChanged
        // TODO add your handling code here:
        String pjId = projectIDComboBox.getSelectedItem().toString();
        for (int i = 0; i < Redux.projectBUS.getProjectList().size(); i++) {
            if (pjId.equalsIgnoreCase(Redux.projectBUS.getProjectList().get(i).getProjectId())) {
                projectNameTextField.setText(Redux.projectBUS.getProjectList().get(i).getProjectName());
            }
        }
    }// GEN-LAST:event_projectIDComboBoxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    public static javax.swing.JButton confirmButton;
    public static javax.swing.JComboBox<String> employeeIDComboBox;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JComboBox<String> projectIDComboBox;
    private javax.swing.JLabel projectIDLabel;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
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
