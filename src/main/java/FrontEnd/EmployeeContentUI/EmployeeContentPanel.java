package FrontEnd.EmployeeContentUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.Redux.Redux;

public class EmployeeContentPanel extends javax.swing.JPanel implements ActionListener {

    DegreeContentPanel degreeContentPanel;
    PositionContentPanel positionContentPanel;
    SpecialtyContentPanel specialtyContentPanel;
    EmployeeManagementContentPanel employeeManagementContentPanel;

    public EmployeeContentPanel() {
        initComponents();

        degreeContentPanel = new DegreeContentPanel();
        positionContentPanel = new PositionContentPanel();
        specialtyContentPanel = new SpecialtyContentPanel();
        employeeManagementContentPanel = new EmployeeManagementContentPanel();

        employeeContainer.setLayout(new GridLayout(1, 1));

        if (!Redux.isAdmin && !Redux.isDirector) {
            employeeButton.setVisible(false);
            degreeButton.setVisible(false);
            positionButton.setVisible(false);
            specialtyButton.setVisible(false);
        }

        showEmployeeManagementContentPanel();

        degreeButton.addActionListener(this);
        positionButton.addActionListener(this);
        specialtyButton.addActionListener(this);
        employeeButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == degreeButton) {
            showDegreeContentPanel();
        } else if (e.getSource() == positionButton) {
            showPositionContentPanel();
        } else if (e.getSource() == specialtyButton) {
            showSpecialtyContentPanel();
        } else if (e.getSource() == employeeButton) {
            showEmployeeManagementContentPanel();
        }
    }

    private void clearAppContentPanel() {
        employeeContainer.removeAll();
        validate();
        repaint();
    }

    private void showDegreeContentPanel() {
        clearAppContentPanel();
        employeeContainer.add(degreeContentPanel);
        validate();
        repaint();
    }

    private void showPositionContentPanel() {
        clearAppContentPanel();
        employeeContainer.add(positionContentPanel);
        validate();
        repaint();
    }

    private void showSpecialtyContentPanel() {
        clearAppContentPanel();
        employeeContainer.add(specialtyContentPanel);
        validate();
        repaint();
    }

    private void showEmployeeManagementContentPanel() {
        clearAppContentPanel();
        employeeContainer.add(employeeManagementContentPanel);
        validate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appSubMenu = new javax.swing.JPanel();
        specialtyButton = new javax.swing.JButton();
        degreeButton = new javax.swing.JButton();
        employeeButton = new javax.swing.JButton();
        positionButton = new javax.swing.JButton();
        employeeContainer = new javax.swing.JPanel();

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setName("employeeContentPanel"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1055, 740));

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));
        appSubMenu.setAlignmentX(0.0F);
        appSubMenu.setAlignmentY(0.0F);
        appSubMenu.setName("appSubMenu"); // NOI18N
        appSubMenu.setPreferredSize(new java.awt.Dimension(1055, 100));

        specialtyButton.setBackground(new java.awt.Color(45, 64, 80));
        specialtyButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        specialtyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/specialty.png"))); // NOI18N
        specialtyButton.setText("Chuyên Môn");
        specialtyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        specialtyButton.setIconTextGap(10);
        specialtyButton.setName("specialtyButton"); // NOI18N
        specialtyButton.setPreferredSize(new java.awt.Dimension(160, 43));

        degreeButton.setBackground(new java.awt.Color(45, 64, 80));
        degreeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        degreeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/degree.png"))); // NOI18N
        degreeButton.setText("Bằng Cấp");
        degreeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        degreeButton.setIconTextGap(10);
        degreeButton.setName("degreeButton"); // NOI18N

        employeeButton.setBackground(new java.awt.Color(45, 64, 80));
        employeeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/employee.png"))); // NOI18N
        employeeButton.setText("Nhân Viên");
        employeeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeButton.setIconTextGap(10);
        employeeButton.setName("employeeButton"); // NOI18N

        positionButton.setBackground(new java.awt.Color(45, 64, 80));
        positionButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/position.png"))); // NOI18N
        positionButton.setText("Chức Vụ");
        positionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        positionButton.setIconTextGap(10);
        positionButton.setName("positionButton"); // NOI18N

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
            appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appSubMenuLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(employeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(positionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(specialtyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(degreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        appSubMenuLayout.setVerticalGroup(
            appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appSubMenuLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(specialtyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(degreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(positionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        employeeContainer.setBackground(new java.awt.Color(255, 0, 255));
        employeeContainer.setAlignmentX(0.0F);
        employeeContainer.setAlignmentY(0.0F);
        employeeContainer.setName("employeeContainer"); // NOI18N
        employeeContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        javax.swing.GroupLayout employeeContainerLayout = new javax.swing.GroupLayout(employeeContainer);
        employeeContainer.setLayout(employeeContainerLayout);
        employeeContainerLayout.setHorizontalGroup(
            employeeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1055, Short.MAX_VALUE)
        );
        employeeContainerLayout.setVerticalGroup(
            employeeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(employeeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(appSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(employeeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JButton degreeButton;
    private javax.swing.JButton employeeButton;
    private javax.swing.JPanel employeeContainer;
    private javax.swing.JButton positionButton;
    private javax.swing.JButton specialtyButton;
    // End of variables declaration//GEN-END:variables

}
