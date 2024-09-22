package FrontEnd.SalaryContentUI;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BackEnd.EmployeesRewardsCriticismManagement.EmployeesRewardsCriticism;

public class DetailSalaryInfo extends javax.swing.JFrame {

    ArrayList<Object> salaryInfo;

    public DetailSalaryInfo() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        detailSalaryTabelLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);
    }

    public void tableInit() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int count = 0;
        model.addRow(new Object[]{++count, "Lương cơ bản", 1,
            NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
            .setRegion("DE").build())
            .format(this.salaryInfo.get(1))
            + " VNĐ"});
        ArrayList<EmployeesRewardsCriticism> list = (ArrayList<EmployeesRewardsCriticism>) this.salaryInfo.get(3);
        for (EmployeesRewardsCriticism item : list) {
            if (!item.getReward().getRewardId().equals("RE001")) {
                String reward = " + " + NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                        .setRegion("DE").build())
                        .format((item.getReward().getReward() * item.getRewardCount()))
                        + " VNĐ";
                model.addRow(new Object[]{++count, item.getReward().getRewardName(), item.getRewardCount(), reward});
            }

            if (!item.getCriticism().getCriticismId().equals("CR001")) {
                String judgement = " - " + NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                        .setRegion("DE").build())
                        .format((item.getCriticism().getJudgement() * item.getFaultCount()))
                        + " VNĐ";
                model.addRow(new Object[]{++count, item.getCriticism().getCriticismName(), item.getFaultCount(),
                    judgement});
            }
        }
        model.addRow(new Object[]{++count, "Trợ cấp chức vụ", 1,
            String.format("%.1f%%", ((double) this.salaryInfo.get(2)) * 100)});

        model.addRow(new Object[]{++count, "Tổng lương", 1,
            NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
            .setRegion("DE").build())
            .format(this.salaryInfo.get(4))
            + " VNĐ"});

        model.addRow(new Object[]{++count, "Bảo hiểm", 1, String.format("%.1f%%", 10.5)});

        model.addRow(new Object[]{++count, "Thực lãnh", 1,
            NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
            .setRegion("DE").build())
            .format(this.salaryInfo.get(5))
            + " VNĐ"});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        detailSalaryTabelLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        detailSalaryTabelLabel.setBackground(new java.awt.Color(255, 255, 255));
        detailSalaryTabelLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        detailSalaryTabelLabel.setForeground(new java.awt.Color(0, 0, 0));
        detailSalaryTabelLabel.setText("Thông Tin Chi Tiết Về Lương Tháng");
        detailSalaryTabelLabel.setName("detailSalaryTabelLabel"); // NOI18N
        detailSalaryTabelLabel.setOpaque(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên ", "Số lần", "Tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(detailSalaryTabelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailSalaryTabelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel detailSalaryTabelLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void receiveData(ArrayList<Object> salaryInfo) {
        this.salaryInfo = salaryInfo;
        tableInit();
    }
}
