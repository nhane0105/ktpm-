package FrontEnd.StatisticsContentUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsContentPanel extends javax.swing.JPanel implements ActionListener {

    GenderStatisticsContentPanel genderStatistics;
    SalaryStatisticsContentPanel salaryStatisticsContentPanel;
    DegreeStatisticsContentPanel degreeStatisticsContentPanel;

    public StatisticsContentPanel() {
        initComponents();

        statisticsContainer.setLayout(new GridLayout(1, 1));

        showGenderStatisticsContentPanel();

        genderButton.addActionListener(this);
        salaryButton.addActionListener(this);
        degreeButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == genderButton) {
            showGenderStatisticsContentPanel();
        } else if (e.getSource() == salaryButton) {
            showSalaryStatisticsContentPanel();
        } else if (e.getSource() == degreeButton) {
            showDegreeStatisticsContentPanel();
        }
    }

    private void clearAppContentPanel() {
        statisticsContainer.removeAll();
        validate();
        repaint();
    }

    private void showGenderStatisticsContentPanel() {
        clearAppContentPanel();
        genderStatistics = new GenderStatisticsContentPanel();
        statisticsContainer.add(genderStatistics);
        validate();
        repaint();
    }

    private void showSalaryStatisticsContentPanel() {
        clearAppContentPanel();
        salaryStatisticsContentPanel = new SalaryStatisticsContentPanel();
        statisticsContainer.add(salaryStatisticsContentPanel);
        validate();
        repaint();
    }

    private void showDegreeStatisticsContentPanel() {
        clearAppContentPanel();
        degreeStatisticsContentPanel = new DegreeStatisticsContentPanel();
        statisticsContainer.add(degreeStatisticsContentPanel);
        validate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appSubMenu = new javax.swing.JPanel();
        genderButton = new javax.swing.JButton();
        degreeButton = new javax.swing.JButton();
        salaryButton = new javax.swing.JButton();
        statisticsContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 153, 153));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));

        genderButton.setBackground(new java.awt.Color(45, 64, 80));
        genderButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        genderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gender.png"))); // NOI18N
        genderButton.setText("Giới Tính");
        genderButton.setIconTextGap(15);
        genderButton.setOpaque(true);

        degreeButton.setBackground(new java.awt.Color(45, 64, 80));
        degreeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        degreeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/degree.png"))); // NOI18N
        degreeButton.setText("Bằng Cấp");
        degreeButton.setIconTextGap(15);

        salaryButton.setBackground(new java.awt.Color(45, 64, 80));
        salaryButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        salaryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/money.png"))); // NOI18N
        salaryButton.setText("Lương");
        salaryButton.setIconTextGap(15);

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(genderButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(salaryButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(degreeButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(205, 205, 205)));
        appSubMenuLayout.setVerticalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appSubMenuLayout
                                .createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(
                                        appSubMenuLayout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(genderButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(degreeButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(salaryButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));

        statisticsContainer.setBackground(new java.awt.Color(255, 153, 153));
        statisticsContainer.setToolTipText("");
        statisticsContainer.setAlignmentX(0.0F);
        statisticsContainer.setAlignmentY(0.0F);
        statisticsContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        javax.swing.GroupLayout statisticsContainerLayout = new javax.swing.GroupLayout(statisticsContainer);
        statisticsContainer.setLayout(statisticsContainerLayout);
        statisticsContainerLayout.setHorizontalGroup(
                statisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        statisticsContainerLayout.setVerticalGroup(
                statisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 640, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(statisticsContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(appSubMenu,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(statisticsContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JButton degreeButton;
    private javax.swing.JButton genderButton;
    private javax.swing.JButton salaryButton;
    private javax.swing.JPanel statisticsContainer;
    // End of variables declaration//GEN-END:variables
}
