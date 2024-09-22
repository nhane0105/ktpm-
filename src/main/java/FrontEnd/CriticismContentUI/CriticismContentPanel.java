package FrontEnd.CriticismContentUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.Redux.Redux;

public class CriticismContentPanel extends javax.swing.JPanel implements ActionListener {

    TypeCriticismPanel typeCriticismPanel;
    CriticismEmployeePanel criticismEmployeePanel;

    public CriticismContentPanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            criticismEmployeeBtn.setVisible(false);
            typeCriticismBtn.setVisible(false);
        }

        criticismEmployeePanel = new CriticismEmployeePanel();
        typeCriticismPanel = new TypeCriticismPanel();

        criticismContainer.setLayout(new GridLayout(1, 1));

        showCriticismEmployeeContentPanel();

        typeCriticismBtn.addActionListener(this);
        criticismEmployeeBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typeCriticismBtn) {
            showTypeCriticismContentPanel();
        } else if (e.getSource() == criticismEmployeeBtn) {
            showCriticismEmployeeContentPanel();
        }
    }

    private void clearPanel() {
        criticismContainer.removeAll();
        criticismContainer.revalidate();
        criticismContainer.repaint();
    }

    private void showTypeCriticismContentPanel() {
        clearPanel();
        criticismContainer.add(typeCriticismPanel);
        criticismContainer.revalidate();
        criticismContainer.repaint();
    }

    private void showCriticismEmployeeContentPanel() {
        clearPanel();
        criticismContainer.add(criticismEmployeePanel);
        criticismContainer.revalidate();
        criticismContainer.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        criticismContainer = new javax.swing.JPanel();
        appSubMenu = new javax.swing.JPanel();
        criticismEmployeeBtn = new javax.swing.JButton();
        typeCriticismBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 255));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        criticismContainer.setBackground(new java.awt.Color(255, 255, 255));
        criticismContainer.setName("criticismContainer"); // NOI18N
        criticismContainer.setPreferredSize(new java.awt.Dimension(1055, 640));

        appSubMenu.setBackground(new java.awt.Color(204, 204, 204));
        appSubMenu.setName("appSubMenu"); // NOI18N
        appSubMenu.setPreferredSize(new java.awt.Dimension(1055, 100));

        criticismEmployeeBtn.setBackground(new java.awt.Color(45, 64, 80));
        criticismEmployeeBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        criticismEmployeeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/criticism.png"))); // NOI18N
        criticismEmployeeBtn.setText("Quản Lý Kỷ Luật");
        criticismEmployeeBtn.setIconTextGap(10);
        criticismEmployeeBtn.setName("criticismEmployeeBtn"); // NOI18N
        criticismEmployeeBtn.setOpaque(true);

        typeCriticismBtn.setBackground(new java.awt.Color(45, 64, 80));
        typeCriticismBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        typeCriticismBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/criticism-type.png"))); // NOI18N
        typeCriticismBtn.setText("Loại Kỷ Luật");
        typeCriticismBtn.setIconTextGap(10);
        typeCriticismBtn.setName("typeCriticismBtn"); // NOI18N
        typeCriticismBtn.setOpaque(true);

        javax.swing.GroupLayout appSubMenuLayout = new javax.swing.GroupLayout(appSubMenu);
        appSubMenu.setLayout(appSubMenuLayout);
        appSubMenuLayout.setHorizontalGroup(
            appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appSubMenuLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(criticismEmployeeBtn)
                .addGap(86, 86, 86)
                .addComponent(typeCriticismBtn)
                .addGap(0, 0, 0))
        );
        appSubMenuLayout.setVerticalGroup(
            appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appSubMenuLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(appSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeCriticismBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(criticismEmployeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(appSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(criticismContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(appSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(criticismContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appSubMenu;
    private javax.swing.JPanel criticismContainer;
    private javax.swing.JButton criticismEmployeeBtn;
    private javax.swing.JButton typeCriticismBtn;
    // End of variables declaration//GEN-END:variables
}
