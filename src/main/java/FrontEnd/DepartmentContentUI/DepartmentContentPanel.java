package FrontEnd.DepartmentContentUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.Redux.Redux;

public class DepartmentContentPanel extends javax.swing.JPanel implements ActionListener {

    DepartmentManagementContentPanel departmentManagementContentPanel;
    AddEmployeeToDepartmentContentPanel employeeDepartmentContentPanel;

    public DepartmentContentPanel() {
        initComponents();

        departmentContainer.setLayout(new GridLayout(1, 1));

        if (!Redux.isAdmin && !Redux.isDirector) {
            departmentButton.setVisible(false);
            employeeButton.setVisible(false);
        }
        showDepartmentManagementContentPanel();

        departmentButton.addActionListener(this);
        employeeButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == departmentButton) {
            showDepartmentManagementContentPanel();
        } else if (e.getSource() == employeeButton) {
            showEmployeeDepartmentContentPanel();
        }
    }

    private void clearAppContentPanel() {
        departmentContainer.removeAll();
        validate();
        repaint();
    }

    private void showDepartmentManagementContentPanel() {
        clearAppContentPanel();
        departmentManagementContentPanel = new DepartmentManagementContentPanel();
        departmentContainer.add(departmentManagementContentPanel);
        validate();
        repaint();
    }

    private void showEmployeeDepartmentContentPanel() {
        clearAppContentPanel();
        employeeDepartmentContentPanel = new AddEmployeeToDepartmentContentPanel();
        employeeDepartmentContentPanel.formInit();
        departmentContainer.add(employeeDepartmentContentPanel);
        validate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appSubMenu = new javax.swing.JPanel();
        departmentButton = new javax.swing.JButton();
        employeeButton = new javax.swing.JButton();
        departmentContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));
        appSubMenu.setAlignmentX(0.0F);
        appSubMenu.setAlignmentY(0.0F);
        appSubMenu.setPreferredSize(new java.awt.Dimension(1055, 100));

        departmentButton.setBackground(new java.awt.Color(45, 64, 80));
        departmentButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        departmentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/assignment.png"))); // NOI18N
        departmentButton.setText("Quản Lý Phòng Ban");
        departmentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        departmentButton.setIconTextGap(10);
        departmentButton.setName("departmentButton"); // NOI18N

        employeeButton.setBackground(new java.awt.Color(45, 64, 80));
        employeeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/project.png"))); // NOI18N
        employeeButton.setText("Quản Lý Nhân Viên");
        employeeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeButton.setIconTextGap(10);
        employeeButton.setName("employeeButton"); // NOI18N
        employeeButton.setOpaque(true);

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(departmentButton)
                                .addGap(77, 77, 77)
                                .addComponent(employeeButton)
                                .addGap(465, 465, 465)));
        appSubMenuLayout.setVerticalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(
                                        appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(departmentButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(employeeButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));

        departmentContainer.setBackground(new java.awt.Color(255, 255, 255));
        departmentContainer.setAlignmentX(0.0F);
        departmentContainer.setAlignmentY(0.0F);
        departmentContainer.setName("departmentContainer"); // NOI18N
        departmentContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        javax.swing.GroupLayout departmentContainerLayout = new javax.swing.GroupLayout(departmentContainer);
        departmentContainer.setLayout(departmentContainerLayout);
        departmentContainerLayout.setHorizontalGroup(
                departmentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1055, Short.MAX_VALUE));
        departmentContainerLayout.setVerticalGroup(
                departmentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 640, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(departmentContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(appSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(departmentContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JButton departmentButton;
    private javax.swing.JPanel departmentContainer;
    private javax.swing.JButton employeeButton;
    // End of variables declaration//GEN-END:variables
}
