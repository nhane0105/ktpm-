package FrontEnd.EmployeeContentUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BackEnd.PositionManagement.Position;
import FrontEnd.Redux.Redux;

public class PositionContentPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;
    ArrayList<Object> formData;

    public PositionContentPanel() {
        initComponents();

        formData = new ArrayList<>();

        positionFormLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        positionTableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

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
        tableInit(Redux.positionBUS.getPositionList());
        jTable1.getSelectionModel().addListSelectionListener(this);
        jPanel1.addMouseListener(this);
        setVisible(true);
    }

    public void formInit() {
        positionIDTextField.setText(Redux.positionBUS.getNextID());
    }

    public void tableInit(ArrayList<Position> positionList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < positionList.size(); i++) {
            if (!positionList.get(i).getDeleteStatus()) {
                model.addRow(new Object[] {
                        i + 1,
                        positionList.get(i).getPositionId(),
                        positionList.get(i).getPositionName(),
                        String.format("%.0f%%",
                                positionList.get(i).getPositionSalaryAllowance() * 100)
                });
            }
        }
    }

    public ArrayList<Object> getDataFromForm() {
        String positionID = positionIDTextField.getText(),
                positionName = positionNameTextField.getText();
        double positionSalaryAllowance = 0.0;

        if (positionName.matches(".*\\d.*")) {
            // Show warning dialog
            JOptionPane.showMessageDialog(null, "Tên chức vụ không thể chứa số !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            positionSalaryAllowance = Double.parseDouble(positionSalaryAllowanceTextField.getText());
            if (positionSalaryAllowance >= 1 || positionSalaryAllowance <= 0) {
                // Show warning dialog
                JOptionPane.showMessageDialog(null,
                        "Trợ cấp phải là số thực nằm trong khoảng (0, 1) !",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return null;
            }
        } catch (NumberFormatException e) {
            // Show warning dialog
            JOptionPane.showMessageDialog(null,
                    "Trợ cấp không hợp lệ vì không phải là số !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new ArrayList<>(Arrays.asList(positionID, positionName, positionSalaryAllowance));
    }

    public void insertTableRow() {
        formData = getDataFromForm();

        if (formData == null) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu chức vụ với ID " + formData.get(0) + " ?",
                "THÊM MỚI ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.positionBUS.addPosition(
                    new Position((String) formData.get(0), (String) formData.get(1),
                            (double) formData.get(2)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllPositions();
            // tableInit(Redux.positionList);
            tableInit(Redux.positionBUS.getPositionList());
        }
    }

    public void updateTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn cập nhật dữ liệu chức vụ với ID " + formData.get(0) + " ?",
                "CẬP NHẬT ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.positionBUS.updatePosition(
                    new Position((String) formData.get(0), (String) formData.get(1),
                            (double) formData.get(2)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllPositions();
            // tableInit(Redux.positionList);
            tableInit(Redux.positionBUS.getPositionList());
        }
    }

    public void deleteTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu chức vụ với ID " + formData.get(0) + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.positionBUS.deletePosition(Redux.positionBUS.getPositionById((String) formData.get(0)));
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllPositions();
            // tableInit(Redux.positionList);
            tableInit(Redux.positionBUS.getPositionList());
        }
    }

    public void fillDataPositionForm(Object[] selectedRowData) {
        positionIDTextField.setText((String) selectedRowData[1]);
        positionNameTextField.setText((String) selectedRowData[2]);
        positionSalaryAllowanceTextField
                .setText(Double.toString(
                        Double.parseDouble(((String) selectedRowData[3]).replace("%", ""))
                                / 100));
        positionIDTextField.setEnabled(false);
    }

    public void clearFormContent() {
        formInit();
        positionNameTextField.setText("");
        positionSalaryAllowanceTextField.setText("");
        addButton.setEnabled(true);
    }

    public boolean isFormFilled() {
        return !(positionNameTextField.getText().equals(""));
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) { // Ensure selection is stable
            selectionConfirmed = true;
            selectedRow = jTable1.getSelectedRow();
            if (selectedRow >= 0) { // Check if a row is selected
                selectedRowData = new Object[jTable1.getColumnCount()];
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    selectedRowData[i] = jTable1.getValueAt(selectedRow, i);
                }
                fillDataPositionForm(selectedRowData);
                addButton.setEnabled(false);
            }
        }
    }

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        positionFormContainer = new javax.swing.JPanel();
        positionFormLabel = new javax.swing.JLabel();
        positionForm = new javax.swing.JPanel();
        positionIDLabel = new javax.swing.JLabel();
        positionIDTextField = new javax.swing.JTextField();
        positionNameLabel = new javax.swing.JLabel();
        positionNameTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        positionSalaryAllowanceLabel = new javax.swing.JLabel();
        positionSalaryAllowanceTextField = new javax.swing.JTextField();
        positionTableContainer = new javax.swing.JPanel();
        positionTableLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(204, 255, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        positionFormContainer.setBackground(new java.awt.Color(255, 255, 255));
        positionFormContainer
                .setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        positionFormContainer.setName("positionFormContainer"); // NOI18N
        positionFormContainer.setPreferredSize(new java.awt.Dimension(386, 336));

        positionFormLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        positionFormLabel.setForeground(new java.awt.Color(0, 0, 0));
        positionFormLabel.setText("Chức Vụ");
        positionFormLabel.setName("positionFormLabel"); // NOI18N

        positionForm.setBackground(new java.awt.Color(255, 255, 255));
        positionForm.setPreferredSize(new java.awt.Dimension(372, 464));

        positionIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        positionIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positionIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        positionIDLabel.setLabelFor(positionIDTextField);
        positionIDLabel.setText("Mã Chức Vụ :");
        positionIDLabel.setName("positionIDLabel"); // NOI18N
        positionIDLabel.setOpaque(true);

        positionIDTextField.setBackground(new java.awt.Color(204, 204, 204));
        positionIDTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        positionIDTextField.setForeground(new java.awt.Color(0, 0, 0));
        positionIDTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        positionIDTextField.setEnabled(false);
        positionIDTextField.setName("positionIDTextField"); // NOI18N
        positionIDTextField.setOpaque(true);

        positionNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        positionNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positionNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        positionNameLabel.setText("Tên Chức Vụ :");
        positionNameLabel.setName("positionNameLabel"); // NOI18N
        positionNameLabel.setOpaque(true);

        positionNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        positionNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        positionNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        positionNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        positionNameTextField.setName("positionNameTextField"); // NOI18N

        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy Bỏ");
        cancelButton.setName("cancelButton"); // NOI18N

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
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        updateButton.setText("Sửa");
        updateButton.setIconTextGap(10);
        updateButton.setName("updateButton"); // NOI18N
        updateButton.setOpaque(true);

        positionSalaryAllowanceLabel.setBackground(new java.awt.Color(255, 255, 255));
        positionSalaryAllowanceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positionSalaryAllowanceLabel.setForeground(new java.awt.Color(0, 0, 0));
        positionSalaryAllowanceLabel.setText("Trợ Cấp Chức Vụ :");
        positionSalaryAllowanceLabel.setName("positionSalaryAllowanceLabel"); // NOI18N
        positionSalaryAllowanceLabel.setOpaque(true);

        positionSalaryAllowanceTextField.setBackground(new java.awt.Color(204, 204, 204));
        positionSalaryAllowanceTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        positionSalaryAllowanceTextField.setForeground(new java.awt.Color(0, 0, 0));
        positionSalaryAllowanceTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        positionSalaryAllowanceTextField.setName("positionSalaryAllowanceTextField"); // NOI18N

        javax.swing.GroupLayout positionFormLayout = new javax.swing.GroupLayout(positionForm);
        positionForm.setLayout(positionFormLayout);
        positionFormLayout.setHorizontalGroup(
                positionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(positionIDTextField)
                        .addComponent(positionNameTextField)
                        .addComponent(positionIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(positionNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, positionFormLayout
                                .createSequentialGroup()
                                .addComponent(addButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        105,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updateButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        119,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(positionSalaryAllowanceTextField)
                        .addComponent(positionSalaryAllowanceLabel,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        positionFormLayout.setVerticalGroup(
                positionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(positionFormLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(positionIDLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(positionIDTextField,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(positionNameLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(positionNameTextField,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(positionSalaryAllowanceLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(positionSalaryAllowanceTextField,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        38,
                                        Short.MAX_VALUE)
                                .addGroup(positionFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(deleteButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(updateButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(addButton,
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        javax.swing.GroupLayout positionFormContainerLayout = new javax.swing.GroupLayout(
                positionFormContainer);
        positionFormContainer.setLayout(positionFormContainerLayout);
        positionFormContainerLayout.setHorizontalGroup(
                positionFormContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(positionFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(positionFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(positionFormLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(positionForm,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));
        positionFormContainerLayout.setVerticalGroup(
                positionFormContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(positionFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(positionFormLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(positionForm,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        468, Short.MAX_VALUE)
                                .addContainerGap()));

        positionTableContainer.setBackground(new java.awt.Color(255, 255, 255));
        positionTableContainer
                .setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        positionTableContainer.setName("positionTableContainer"); // NOI18N
        positionTableContainer.setPreferredSize(new java.awt.Dimension(537, 540));

        positionTableLabel.setBackground(new java.awt.Color(255, 255, 255));
        positionTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        positionTableLabel.setForeground(new java.awt.Color(0, 0, 0));
        positionTableLabel.setText("Chức Vụ");
        positionTableLabel.setName("positionTableLabel"); // NOI18N
        positionTableLabel.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã Chức Vụ", "Tên Chức Vụ", "Trợ Cấp Chức Vụ"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class
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
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        523, Short.MAX_VALUE)
                                .addGap(0, 0, 0)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1,
                                javax.swing.GroupLayout.Alignment.TRAILING));

        javax.swing.GroupLayout positionTableContainerLayout = new javax.swing.GroupLayout(
                positionTableContainer);
        positionTableContainer.setLayout(positionTableContainerLayout);
        positionTableContainerLayout.setHorizontalGroup(
                positionTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(positionTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(positionTableContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(positionTableLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel3,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        positionTableContainerLayout.setVerticalGroup(
                positionTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(positionTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(positionTableLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addContainerGap()));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(positionFormContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(positionTableContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(positionTableContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                544,
                                                Short.MAX_VALUE)
                                        .addComponent(positionFormContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                544,
                                                Short.MAX_VALUE))
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

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_deleteButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel positionForm;
    private javax.swing.JPanel positionFormContainer;
    private javax.swing.JLabel positionFormLabel;
    private javax.swing.JLabel positionIDLabel;
    private javax.swing.JTextField positionIDTextField;
    private javax.swing.JLabel positionNameLabel;
    private javax.swing.JTextField positionNameTextField;
    private javax.swing.JLabel positionSalaryAllowanceLabel;
    private javax.swing.JTextField positionSalaryAllowanceTextField;
    private javax.swing.JPanel positionTableContainer;
    private javax.swing.JLabel positionTableLabel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

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
