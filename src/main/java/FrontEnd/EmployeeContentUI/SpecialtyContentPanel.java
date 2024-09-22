package FrontEnd.EmployeeContentUI;

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

import BackEnd.SpecialtyManagement.Specialty;
import FrontEnd.Redux.Redux;

public class SpecialtyContentPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;
    ArrayList<Object> formData;

    public SpecialtyContentPanel() {
        initComponents();

        formData = new ArrayList<>();

        specialtyLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        specialtyTableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);
        jTable1.setDefaultRenderer(Object.class, centerRenderer);

        formInit();
        tableInit(Redux.specialtyBUS.getSpecialtyList());
        jTable1.getSelectionModel().addListSelectionListener(this);
        jPanel1.addMouseListener(this);

        setVisible(true);
    }

    public void formInit() {
        specialtyIDTextField.setText(Redux.specialtyBUS.getNextID());
    }

    public void tableInit(ArrayList<Specialty> specialtyList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < specialtyList.size(); i++) {
            if (!specialtyList.get(i).getDeleteStatus()) {
                model.addRow(new Object[] {
                        i + 1,
                        specialtyList.get(i).getSpecialtyId(),
                        specialtyList.get(i).getSpecialtyName(),
                        NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                                .setRegion("DE").build())
                                .format(specialtyList.get(i).getSpecialtyBaseSalary())
                                + " VNĐ"
                });
            }
        }
    }

    public ArrayList<Object> getDataFromForm() {
        String specialtyID = specialtyIDTextField.getText(),
                specialtyName = specialtyNameTextField.getText();
        int specialtyBaseSalary = 0;

        if (specialtyName.matches(".*\\d.*")) {
            // Show warning dialog
            JOptionPane.showMessageDialog(null, "Tên chuyên môn không thể chứa số !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            specialtyBaseSalary = Integer.parseInt(specialtyBaseSalaryTextField.getText());
        } catch (NumberFormatException e) {
            // Show warning dialog
            JOptionPane.showMessageDialog(null, "Lương cứng không hợp lệ vì không phải là số !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new ArrayList<>(Arrays.asList(specialtyID, specialtyName, specialtyBaseSalary));
    }

    public void insertTableRow() {
        formData = getDataFromForm();

        if (formData == null) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu chuyên môn với ID " + formData.get(0) + " ?",
                "THÊM MỚI ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.specialtyBUS.addSpecialty(
                    new Specialty((String) formData.get(0), (String) formData.get(1),
                            (int) formData.get(2)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllSpecialties();
            // tableInit(Redux.specialtyList);
            tableInit(Redux.specialtyBUS.getSpecialtyList());
        }
    }

    public void updateTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn cập nhật dữ liệu chuyên môn với ID " + formData.get(0) + " ?",
                "CẬP NHẬT ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.specialtyBUS.updateSpecialty(
                    new Specialty((String) formData.get(0), (String) formData.get(1),
                            (int) formData.get(2)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllSpecialties();
            // tableInit(Redux.specialtyList);
            tableInit(Redux.specialtyBUS.getSpecialtyList());
        }

    }

    public void deleteTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu chuyên môn với ID " + formData.get(0) + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.specialtyBUS.deleteSpecialty(Redux.specialtyBUS.getSpecialtyById((String) formData.get(0)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllSpecialties();
            // tableInit(Redux.specialtyList);
            tableInit(Redux.specialtyBUS.getSpecialtyList());
        }
    }

    public void fillDataSpecialtyForm(Object[] selectedRowData) {
        specialtyIDTextField.setText((String) selectedRowData[1]);
        specialtyNameTextField.setText((String) selectedRowData[2]);
        specialtyBaseSalaryTextField
                .setText(((String) selectedRowData[3]).replace(" VNĐ", "").replace(".", ""));
        specialtyIDTextField.setEnabled(false);
    }

    public void clearFormContent() {
        formInit();
        specialtyNameTextField.setText("");
        specialtyBaseSalaryTextField.setText("");
        addButton.setEnabled(true);
    }

    public boolean isFormFilled() {
        return !(specialtyNameTextField.getText().equals(""));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        specialtyFormContainer = new javax.swing.JPanel();
        specialtyLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        specialtyIDLabel = new javax.swing.JLabel();
        specialtyIDTextField = new javax.swing.JTextField();
        specialtyNameLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        specialtyNameTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        specialtyBaseSalaryLabel = new javax.swing.JLabel();
        specialtyBaseSalaryTextField = new javax.swing.JTextField();
        specialtyTableContainer = new javax.swing.JPanel();
        specialtyTableLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(102, 102, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        specialtyFormContainer.setBackground(new java.awt.Color(255, 255, 255));
        specialtyFormContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        specialtyFormContainer.setName("specialtyFormContainer"); // NOI18N

        specialtyLabel.setBackground(new java.awt.Color(255, 255, 255));
        specialtyLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        specialtyLabel.setForeground(new java.awt.Color(0, 0, 0));
        specialtyLabel.setText("Chuyên Môn");
        specialtyLabel.setName("specialtyLabel"); // NOI18N
        specialtyLabel.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(372, 540));

        specialtyIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        specialtyIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        specialtyIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        specialtyIDLabel.setLabelFor(specialtyIDTextField);
        specialtyIDLabel.setText("Mã Chuyên Môn :");
        specialtyIDLabel.setName("specialtyIDLabel"); // NOI18N
        specialtyIDLabel.setOpaque(true);

        specialtyIDTextField.setBackground(new java.awt.Color(204, 204, 204));
        specialtyIDTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        specialtyIDTextField.setForeground(new java.awt.Color(0, 0, 0));
        specialtyIDTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        specialtyIDTextField.setEnabled(false);
        specialtyIDTextField.setName("specialtyIDTextField"); // NOI18N
        specialtyIDTextField.setOpaque(true);

        specialtyNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        specialtyNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        specialtyNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        specialtyNameLabel.setText("Tên Chuyên Môn : ");
        specialtyNameLabel.setName("specialtyNameLabel"); // NOI18N
        specialtyNameLabel.setOpaque(true);
        specialtyNameLabel.setPreferredSize(new java.awt.Dimension(119, 40));

        addButton.setBackground(new java.awt.Color(25, 135, 84));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        addButton.setText("Thêm");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.setIconTextGap(10);
        addButton.setName("addButton"); // NOI18N
        addButton.setPreferredSize(new java.awt.Dimension(112, 40));

        deleteButton.setBackground(new java.awt.Color(220, 53, 69));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteButton.setText("Xóa");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.setIconTextGap(10);
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.setOpaque(true);
        deleteButton.setPreferredSize(new java.awt.Dimension(100, 40));

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        updateButton.setText("Sửa");
        updateButton.setIconTextGap(10);
        updateButton.setName("updateButton"); // NOI18N
        updateButton.setOpaque(true);

        specialtyNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        specialtyNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        specialtyNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        specialtyNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        specialtyNameTextField.setName("specialtyNameTextField"); // NOI18N
        specialtyNameTextField.setOpaque(true);

        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy Bỏ");
        cancelButton.setName("cancelButton"); // NOI18N

        specialtyBaseSalaryLabel.setBackground(new java.awt.Color(255, 255, 255));
        specialtyBaseSalaryLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        specialtyBaseSalaryLabel.setForeground(new java.awt.Color(0, 0, 0));
        specialtyBaseSalaryLabel.setText("Lương Cứng :");
        specialtyBaseSalaryLabel.setName("specialtyBaseSalaryLabel"); // NOI18N
        specialtyBaseSalaryLabel.setOpaque(true);
        specialtyBaseSalaryLabel.setPreferredSize(new java.awt.Dimension(119, 40));

        specialtyBaseSalaryTextField.setBackground(new java.awt.Color(204, 204, 204));
        specialtyBaseSalaryTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        specialtyBaseSalaryTextField.setForeground(new java.awt.Color(0, 0, 0));
        specialtyBaseSalaryTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        specialtyBaseSalaryTextField.setName("specialtyBaseSalaryTextField"); // NOI18N
        specialtyBaseSalaryTextField.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(specialtyIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specialtyIDTextField)
                        .addComponent(specialtyNameTextField)
                        .addComponent(specialtyNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specialtyBaseSalaryTextField)
                        .addComponent(specialtyBaseSalaryLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(specialtyIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(specialtyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(specialtyNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(specialtyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(specialtyBaseSalaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(specialtyBaseSalaryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                                Short.MAX_VALUE)
                                        .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));

        javax.swing.GroupLayout specialtyFormContainerLayout = new javax.swing.GroupLayout(specialtyFormContainer);
        specialtyFormContainer.setLayout(specialtyFormContainerLayout);
        specialtyFormContainerLayout.setHorizontalGroup(
                specialtyFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                specialtyFormContainerLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(specialtyFormContainerLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(specialtyLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap()));
        specialtyFormContainerLayout.setVerticalGroup(
                specialtyFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(specialtyFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(specialtyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                                .addContainerGap()));

        specialtyTableContainer.setBackground(new java.awt.Color(255, 255, 255));
        specialtyTableContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        specialtyTableContainer.setName("specialtyTableContainer"); // NOI18N
        specialtyTableContainer.setPreferredSize(new java.awt.Dimension(537, 540));

        specialtyTableLabel.setBackground(new java.awt.Color(255, 255, 255));
        specialtyTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        specialtyTableLabel.setForeground(new java.awt.Color(0, 0, 0));
        specialtyTableLabel.setText("Chuyên Môn");
        specialtyTableLabel.setName("specialtyTableLabel"); // NOI18N
        specialtyTableLabel.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã Chuyên Môn", "Tên Chuyên Môn", "Lương Cứng"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE));

        javax.swing.GroupLayout specialtyTableContainerLayout = new javax.swing.GroupLayout(specialtyTableContainer);
        specialtyTableContainer.setLayout(specialtyTableContainerLayout);
        specialtyTableContainerLayout.setHorizontalGroup(
                specialtyTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                specialtyTableContainerLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(specialtyTableContainerLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(specialtyTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap()));
        specialtyTableContainerLayout.setVerticalGroup(
                specialtyTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(specialtyTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(specialtyTableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(specialtyFormContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(32, 32, 32)
                                .addComponent(specialtyTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(specialtyTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                544, Short.MAX_VALUE)
                                        .addComponent(specialtyFormContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(46, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel specialtyBaseSalaryLabel;
    private javax.swing.JTextField specialtyBaseSalaryTextField;
    private javax.swing.JPanel specialtyFormContainer;
    private javax.swing.JLabel specialtyIDLabel;
    private javax.swing.JTextField specialtyIDTextField;
    private javax.swing.JLabel specialtyLabel;
    private javax.swing.JLabel specialtyNameLabel;
    private javax.swing.JTextField specialtyNameTextField;
    private javax.swing.JPanel specialtyTableContainer;
    private javax.swing.JLabel specialtyTableLabel;
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
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { // Ensure selection is stable
            selectionConfirmed = true;
            selectedRow = jTable1.getSelectedRow();
            if (selectedRow >= 0) { // Check if a row is selected
                selectedRowData = new Object[jTable1.getColumnCount()];
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    selectedRowData[i] = jTable1.getValueAt(selectedRow, i);
                }
                fillDataSpecialtyForm(selectedRowData);
                addButton.setEnabled(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component clickedComponent = jPanel1.getComponentAt(jPanel1.getMousePosition());
        if (clickedComponent != jTable1 && selectionConfirmed) {
            jTable1.getSelectionModel().clearSelection();
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
