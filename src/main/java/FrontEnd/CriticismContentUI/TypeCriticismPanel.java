package FrontEnd.CriticismContentUI;

import BackEnd.CriticismManagement.Criticism;
import BackEnd.CriticismManagement.CriticismBUS;
import FrontEnd.Redux.Redux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TypeCriticismPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;
    ArrayList<Object> formData;

    public TypeCriticismPanel() {
        initComponents();

        formData = new ArrayList<>();

        typeCriticismLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        typeCriticismTableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        typeCriticismTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        typeCriticismTable.setDefaultRenderer(String.class, centerRenderer);
        typeCriticismTable.setDefaultRenderer(Object.class, centerRenderer);
        typeCriticismTable.setDefaultRenderer(Integer.class, centerRenderer);
        typeCriticismTable.setDefaultRenderer(Double.class, centerRenderer);

        typeCriticismTable.getSelectionModel().addListSelectionListener(this);
        formInit();
        tableInit(Redux.criticismBUS.getlistcriticism());

        jPanel1.addMouseListener(this);
    }

    public void formInit() {
        typeCriticismIDTextField.setText(Redux.criticismBUS.getNextID());
    }

    public void tableInit(ArrayList<Criticism> criticismBUS) {
        DefaultTableModel model = (DefaultTableModel) typeCriticismTable.getModel();
        model.setRowCount(0);

        for (int i = 0; i < criticismBUS.size(); i++) {
            if (!criticismBUS.get(i).getDeleteStatus()) {
                model.addRow(new Object[] {
                        i + 1,
                        criticismBUS.get(i).getCriticismId(),
                        criticismBUS.get(i).getCriticismName(),
                        NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                                .setRegion("DE").build())
                                .format(criticismBUS.get(i).getJudgement())
                                + " VNĐ", });
            }
        }
    }

    public ArrayList<Object> getDataFromForm() {
        String criticismId = (String) typeCriticismIDTextField.getText(),
                criticismName = typeCriticismNameTextField.getText();
        int judgement = Integer.parseInt((String) moneyTextField.getText());

        return new ArrayList<>(Arrays.asList(criticismId, criticismName, judgement));
    }

    public void insertTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu loại khen thưởng với ID " + formData.get(0) + " ?",
                "THÊM MỚI ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // .add(new Criticism((String) formData.get(0), (String) formData.get(1)));
            Redux.criticismBUS.addCriticism(
                    new Criticism((String) formData.get(0), (String) formData.get(1), (int) formData.get(2)));
            clearFormContent();
            typeCriticismTable.revalidate();
            tableInit(Redux.criticismBUS.getlistcriticism());

        }
    }

    public void updateTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn cập nhật dữ liệu loại khen thưởng với ID " + formData.get(0) + " ?",
                "CẬP NHẬT ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.criticismBUS.updateCriticism(
                    new Criticism((String) formData.get(0), (String) formData.get(1), (int) formData.get(2)));
            clearFormContent();
            typeCriticismTable.revalidate();
            tableInit(Redux.criticismBUS.getlistcriticism());
        }
    }

    public void deleteTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu loại kỷ luật với ID " + formData.get(0) + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.criticismBUS
                    .delete(new Criticism((String) formData.get(0), (String) formData.get(1), (int) formData.get(2)));
            clearFormContent();
            typeCriticismTable.revalidate();
            tableInit(Redux.criticismBUS.getlistcriticism());
        }
    }

    public void fillDataTypeCriticismForm(Object[] selectedRowData) {
        typeCriticismIDTextField.setText((String) selectedRowData[1]);
        typeCriticismNameTextField.setText((String) selectedRowData[2]);
        moneyTextField
                .setText(((String) selectedRowData[3]).replace(" VNĐ", "").replace(".", ""));
    }

    public void clearFormContent() {
        typeCriticismIDTextField.setText("");
        typeCriticismNameTextField.setText("");
        moneyTextField.setText("");
    }

    public boolean isFormFilled() {
        return !(typeCriticismNameTextField.getText().equals("") || moneyTextField.getText().equals(""));
    }
    //

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        typeRewardTableContainer = new javax.swing.JPanel();
        typeCriticismTableLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        typeCriticismTable = new javax.swing.JTable();
        typeRewardFormContainer = new javax.swing.JPanel();
        typeCriticismLabel = new javax.swing.JLabel();
        typeRewardForm = new javax.swing.JPanel();
        typeCriticismIDLabel = new javax.swing.JLabel();
        typeCriticismIDTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        typeCriticismNameLabel = new javax.swing.JLabel();
        typeCriticismNameTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        moneyLabel = new javax.swing.JLabel();
        moneyTextField = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1055, 640));

        typeRewardTableContainer.setBackground(new java.awt.Color(255, 255, 255));
        typeRewardTableContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        typeRewardTableContainer.setName("typeRewardTableContainer"); // NOI18N
        typeRewardTableContainer.setPreferredSize(new java.awt.Dimension(537, 540));

        typeCriticismTableLabel.setText("Loại Kỷ Luật");
        typeCriticismTableLabel.setBackground(new java.awt.Color(255, 255, 255));
        typeCriticismTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        typeCriticismTableLabel.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismTableLabel.setName("typeCriticismTableLabel"); // NOI18N
        typeCriticismTableLabel.setOpaque(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        typeCriticismTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Loại Kỷ Luật", "Tên Kỷ Luật", "Số Tiền Phạt", "Ngày Tạo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        typeCriticismTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        typeCriticismTable.setName("typeCriticismTable"); // NOI18N
        typeCriticismTable.setRowHeight(40);
        jScrollPane1.setViewportView(typeCriticismTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout typeRewardTableContainerLayout = new javax.swing.GroupLayout(typeRewardTableContainer);
        typeRewardTableContainer.setLayout(typeRewardTableContainerLayout);
        typeRewardTableContainerLayout.setHorizontalGroup(
            typeRewardTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, typeRewardTableContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(typeRewardTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeCriticismTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        typeRewardTableContainerLayout.setVerticalGroup(
            typeRewardTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeRewardTableContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeCriticismTableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        typeRewardFormContainer.setBackground(new java.awt.Color(255, 255, 255));
        typeRewardFormContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        typeRewardFormContainer.setName("typeRewardFormContainer"); // NOI18N
        typeRewardFormContainer.setPreferredSize(new java.awt.Dimension(386, 540));

        typeCriticismLabel.setText("Tạo Loại Kỷ Luật");
        typeCriticismLabel.setBackground(new java.awt.Color(255, 255, 255));
        typeCriticismLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        typeCriticismLabel.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismLabel.setName("typeCriticismLabel"); // NOI18N
        typeCriticismLabel.setOpaque(true);

        typeRewardForm.setBackground(new java.awt.Color(255, 255, 255));
        typeRewardForm.setName("typeRewardForm"); // NOI18N

        typeCriticismIDLabel.setText("Mã Loại Kỷ Luật :");
        typeCriticismIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        typeCriticismIDLabel.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismIDLabel.setName("typeCriticismIDLabel"); // NOI18N

        typeCriticismIDTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        typeCriticismIDTextField.setBackground(new java.awt.Color(204, 204, 204));
        typeCriticismIDTextField.setEnabled(false);
        typeCriticismIDTextField.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismIDTextField.setName("typeCriticismIDTextField"); // NOI18N

        addButton.setBackground(new java.awt.Color(25, 135, 84));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        addButton.setText("Thêm");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.setIconTextGap(10);
        addButton.setName("addButton"); // NOI18N

        deleteButton.setBackground(new java.awt.Color(220, 53, 69));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteButton.setText("Xóa");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.setIconTextGap(10);
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.setOpaque(true);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        updateButton.setText("Sửa");
        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setIconTextGap(10);
        updateButton.setName("updateButton"); // NOI18N
        updateButton.setOpaque(true);

        typeCriticismNameLabel.setText("Tên Loại Kỷ Luật :");
        typeCriticismNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        typeCriticismNameLabel.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismNameLabel.setName("typeCriticismNameLabel"); // NOI18N

        typeCriticismNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        typeCriticismNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        typeCriticismNameTextField.setForeground(new java.awt.Color(0, 51, 51));
        typeCriticismNameTextField.setName("typeCriticismNameTextField"); // NOI18N

        cancelButton.setText("Hủy Bỏ");
        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setName("cancelButton"); // NOI18N

        moneyLabel.setLabelFor(moneyTextField);
        moneyLabel.setText("Số Tiền Phạt :");
        moneyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        moneyLabel.setForeground(new java.awt.Color(0, 51, 51));
        moneyLabel.setName("moneyLabel"); // NOI18N
        moneyLabel.setPreferredSize(new java.awt.Dimension(118, 40));

        moneyTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        moneyTextField.setBackground(new java.awt.Color(204, 204, 204));
        moneyTextField.setForeground(new java.awt.Color(0, 51, 51));
        moneyTextField.setName("moneyTextField"); // NOI18N

        javax.swing.GroupLayout typeRewardFormLayout = new javax.swing.GroupLayout(typeRewardForm);
        typeRewardForm.setLayout(typeRewardFormLayout);
        typeRewardFormLayout.setHorizontalGroup(
            typeRewardFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(typeCriticismNameTextField)
            .addComponent(typeCriticismNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(typeCriticismIDTextField, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(typeCriticismIDLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(typeRewardFormLayout.createSequentialGroup()
                .addComponent(addButton)
                .addGap(18, 18, 18)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(typeRewardFormLayout.createSequentialGroup()
                .addComponent(moneyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moneyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        typeRewardFormLayout.setVerticalGroup(
            typeRewardFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeRewardFormLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(typeCriticismIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(typeCriticismIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(typeCriticismNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(typeCriticismNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(typeRewardFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(moneyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moneyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(typeRewardFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(typeRewardFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout typeRewardFormContainerLayout = new javax.swing.GroupLayout(typeRewardFormContainer);
        typeRewardFormContainer.setLayout(typeRewardFormContainerLayout);
        typeRewardFormContainerLayout.setHorizontalGroup(
            typeRewardFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeRewardFormContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(typeRewardFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeRewardForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeCriticismLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        typeRewardFormContainerLayout.setVerticalGroup(
            typeRewardFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeRewardFormContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeCriticismLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(typeRewardForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(typeRewardFormContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(typeRewardTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeRewardTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addComponent(typeRewardFormContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteButtonActionPerformed

    }// GEN-LAST:event_deleteButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moneyLabel;
    private javax.swing.JTextField moneyTextField;
    private javax.swing.JLabel typeCriticismIDLabel;
    private javax.swing.JTextField typeCriticismIDTextField;
    private javax.swing.JLabel typeCriticismLabel;
    private javax.swing.JLabel typeCriticismNameLabel;
    private javax.swing.JTextField typeCriticismNameTextField;
    private javax.swing.JTable typeCriticismTable;
    private javax.swing.JLabel typeCriticismTableLabel;
    private javax.swing.JPanel typeRewardForm;
    private javax.swing.JPanel typeRewardFormContainer;
    private javax.swing.JPanel typeRewardTableContainer;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if (isFormFilled()) {
                insertTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy nhập thông tin trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == deleteButton) {
            if (selectedRow >= 0) {
                deleteTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == updateButton) {
            if (selectedRow >= 0) {
                if (isFormFilled()) {
                    updateTableRow();
                } else {
                    JOptionPane.showMessageDialog(this, "Hãy nhập thông tin trước!", "CẢNH BÁO",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            clearFormContent();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) { // Ensure selection is stable
            selectionConfirmed = true;
            selectedRow = typeCriticismTable.getSelectedRow();
            if (selectedRow >= 0) { // Check if a row is selected
                selectedRowData = new Object[typeCriticismTable.getColumnCount()];
                for (int i = 0; i < typeCriticismTable.getColumnCount(); i++) {
                    selectedRowData[i] = typeCriticismTable.getValueAt(selectedRow, i);
                }
                fillDataTypeCriticismForm(selectedRowData);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component clickedComponent = jPanel1.getComponentAt(jPanel1.getMousePosition());
        if (clickedComponent != typeCriticismTable && selectionConfirmed) {
            typeCriticismTable.getSelectionModel().clearSelection();
            selectionConfirmed = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
