package FrontEnd.ProjectContentUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.Redux.Redux;

public class AssignmentContentPanel extends javax.swing.JPanel implements ActionListener {

    AssignmentManagementContentPanel assignmentManagementContentPanel;
    ProjectManagementContentPanel projectManagementContentPanel;

    public AssignmentContentPanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            assignmentButton.setVisible(false);
            projectButton.setVisible(false);
        }

        assignmentContainer.setLayout(new GridLayout(1, 1));

        showAssignmentContentPanel();

        assignmentButton.addActionListener(this);
        projectButton.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == assignmentButton) {
            showAssignmentContentPanel();
        } else if (e.getSource() == projectButton) {
            showProjectContentPanel();
        }
    }

    private void clearAppContentPanel() {
        assignmentContainer.removeAll();
        validate();
        repaint();
    }

    private void showAssignmentContentPanel() {
        clearAppContentPanel();
        assignmentManagementContentPanel = new AssignmentManagementContentPanel();
        assignmentContainer.add(assignmentManagementContentPanel);
        validate();
        repaint();
    }

    private void showProjectContentPanel() {
        clearAppContentPanel();
        projectManagementContentPanel = new ProjectManagementContentPanel();
        projectManagementContentPanel.formInit();
        assignmentContainer.add(projectManagementContentPanel);
        validate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appSubMenu = new javax.swing.JPanel();
        assignmentButton = new javax.swing.JButton();
        projectButton = new javax.swing.JButton();
        assignmentContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("travelContentPanel"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1055, 740));

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));
        appSubMenu.setAlignmentX(0.0F);
        appSubMenu.setAlignmentY(0.0F);

        assignmentButton.setBackground(new java.awt.Color(45, 64, 80));
        assignmentButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        assignmentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/assignment.png"))); // NOI18N
        assignmentButton.setText("Thêm Công Tác");
        assignmentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        assignmentButton.setIconTextGap(10);
        assignmentButton.setName("assignmentButton"); // NOI18N

        projectButton.setBackground(new java.awt.Color(45, 64, 80));
        projectButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        projectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/project.png"))); // NOI18N
        projectButton.setText("Thêm Dự Án");
        projectButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectButton.setIconTextGap(10);
        projectButton.setName("projectButton"); // NOI18N
        projectButton.setOpaque(true);

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(assignmentButton)
                                .addGap(86, 86, 86)
                                .addComponent(projectButton)
                                .addGap(529, 529, 529)));
        appSubMenuLayout.setVerticalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(
                                        appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(assignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(projectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));

        assignmentContainer.setBackground(new java.awt.Color(255, 255, 255));
        assignmentContainer.setAlignmentX(0.0F);
        assignmentContainer.setAlignmentY(0.0F);
        assignmentContainer.setName("assignmentContainer"); // NOI18N
        assignmentContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        javax.swing.GroupLayout assignmentContainerLayout = new javax.swing.GroupLayout(assignmentContainer);
        assignmentContainer.setLayout(assignmentContainerLayout);
        assignmentContainerLayout.setHorizontalGroup(
                assignmentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1055, Short.MAX_VALUE));
        assignmentContainerLayout.setVerticalGroup(
                assignmentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 640, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(assignmentContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(assignmentContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JButton assignmentButton;
    private javax.swing.JPanel assignmentContainer;
    private javax.swing.JButton projectButton;
    // End of variables declaration//GEN-END:variables

}
