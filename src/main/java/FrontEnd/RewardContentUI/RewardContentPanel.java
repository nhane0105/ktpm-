package FrontEnd.RewardContentUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.Redux.Redux;

public class RewardContentPanel extends javax.swing.JPanel implements ActionListener {

    TypeRewardPanel typeRewardPanel;
    RewardEmployeePanel rewardEmployeePanel;

    public RewardContentPanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            rewardEmployeeBtn.setVisible(false);
            typeRewardBtn.setVisible(false);
        }

        typeRewardPanel = new TypeRewardPanel();
        rewardEmployeePanel = new RewardEmployeePanel();

        rewardContainer.setLayout(new GridLayout(1, 1));

        showRewardEmployeeContentPanel();

        typeRewardBtn.addActionListener(this);
        rewardEmployeeBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typeRewardBtn) {
            showTypeRewardContentPanel();
        } else if (e.getSource() == rewardEmployeeBtn) {
            showRewardEmployeeContentPanel();
        }
    }

    private void clearPanel() {
        rewardContainer.removeAll();
        rewardContainer.revalidate();
        rewardContainer.repaint();
    }

    private void showTypeRewardContentPanel() {
        clearPanel();
        rewardContainer.add(typeRewardPanel);
        rewardContainer.revalidate();
        rewardContainer.repaint();
    }

    private void showRewardEmployeeContentPanel() {
        clearPanel();
        rewardContainer.add(rewardEmployeePanel);
        rewardContainer.revalidate();
        rewardContainer.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appSubMenu = new javax.swing.JPanel();
        rewardEmployeeBtn = new javax.swing.JButton();
        typeRewardBtn = new javax.swing.JButton();
        rewardContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 255, 204));
        setName("rewardContentPanel"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1055, 740));

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));
        appSubMenu.setName("appSubMenu"); // NOI18N
        appSubMenu.setPreferredSize(new java.awt.Dimension(1055, 100));

        rewardEmployeeBtn.setBackground(new java.awt.Color(45, 64, 80));
        rewardEmployeeBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rewardEmployeeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reward.png"))); // NOI18N
        rewardEmployeeBtn.setText("Quản Lý Khen Thưởng");
        rewardEmployeeBtn.setIconTextGap(10);
        rewardEmployeeBtn.setName("rewardEmployeeBtn"); // NOI18N
        rewardEmployeeBtn.setOpaque(true);

        typeRewardBtn.setBackground(new java.awt.Color(45, 64, 80));
        typeRewardBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        typeRewardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reward-type.png"))); // NOI18N
        typeRewardBtn.setText("Loại Khen Thưởng");
        typeRewardBtn.setIconTextGap(10);
        typeRewardBtn.setName("typeRewardBtn"); // NOI18N
        typeRewardBtn.setOpaque(true);

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(rewardEmployeeBtn)
                                .addGap(86, 86, 86)
                                .addComponent(typeRewardBtn)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        appSubMenuLayout.setVerticalGroup(
                appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appSubMenuLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(
                                        appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(typeRewardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(rewardEmployeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)));

        rewardContainer.setBackground(new java.awt.Color(255, 255, 255));
        rewardContainer.setName("rewardContainer"); // NOI18N
        rewardContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rewardContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(appSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(rewardContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JPanel rewardContainer;
    private javax.swing.JButton rewardEmployeeBtn;
    private javax.swing.JButton typeRewardBtn;
    // End of variables declaration//GEN-END:variables
}
