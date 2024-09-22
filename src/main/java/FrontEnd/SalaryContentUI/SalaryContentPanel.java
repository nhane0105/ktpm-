package FrontEnd.SalaryContentUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BackEnd.EmployeeManagement.Employee;
import BackEnd.EmployeeSalary.EmployeeSalary;
import BackEnd.EmployeeSalary.EmployeeSalaryBUS;
import FrontEnd.Redux.Redux;

public class SalaryContentPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;
    ArrayList<Object> formData;
    ArrayList<Object> salaryInfo;

    DetailSalaryInfo detailSalaryInfo;
    EmployeeSalaryBUS employeeSalaryBUS = new EmployeeSalaryBUS();

    public SalaryContentPanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            employeeSalaryBUS.getEmployeeSalariesByEmployeeId(Redux.userId);
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }

        formInit();
        formData = new ArrayList<>();

        detailSalaryInfo = new DetailSalaryInfo();

        salaryLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        salaryTableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        detailButton.addActionListener(this);
        cancelButton.addActionListener(this);

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Object.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);
        jTable1.setDefaultRenderer(Double.class, centerRenderer);

        if (!Redux.isAdmin && !Redux.isDirector) {
            tableInit(employeeSalaryBUS.getEmployeeSalaryListById());
        } else {
            tableInit(employeeSalaryBUS.getAllEmployeeSalary());
        }

        jTable1.getSelectionModel().addListSelectionListener(this);
        jPanel1.addMouseListener(this);

        setVisible(true);
    }

    public void formInit() {
        addButton.setEnabled(true);
        if (employeeIDComboBox.getItemCount() > 0) {
            employeeIDComboBox.removeAllItems();
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (!Redux.isAdmin && !Redux.isDirector) {
            model.addElement(Redux.userId);
        } else {
            if (Redux.employeeBUS.getEmployeeNotHaveSalaryList() != null) {
                for (Employee employee : Redux.employeeBUS.getEmployeeNotHaveSalaryList()) {
                    if (!employee.getDeleteStatus()) {
                        model.addElement(employee.getId());
                    }
                }
                employeeIDComboBox.setModel(model);
                employeeIDComboBox.setSelectedIndex(0);
                employeeNameTextField.setText(
                        Redux.employeeBUS.getEmployeeById(
                                (String) employeeIDComboBox.getSelectedItem())
                                .getFullName());
            }

            if (model.getSize() == 1) {
                String selectedEmployeeId = (String) model.getElementAt(0);
                for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                    if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                        employeeNameTextField.setText(employee.getFullName());
                        break; // Break the loop once found
                    }
                }
            }
            calculateNetSalary();

            employeeIDComboBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedEmployeeId = (String) employeeIDComboBox.getSelectedItem();
                    for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                        if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                            employeeNameTextField.setText(employee.getFullName());
                            calculateNetSalary();
                        }
                    }
                }
            });

            monthOfSalaryTextField.setText(Integer.toString(LocalDate.now().getMonthValue()));
        }
    }

    public void tableInit(ArrayList<EmployeeSalary> employeeSalaryList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < employeeSalaryList.size(); i++) {
            if (!employeeSalaryList.get(i).getDeleteStatus()) {
                String date[] = new String[3];
                date = employeeSalaryList.get(i).getCreatedAt().split("-");
                model.addRow(new Object[] {
                        i + 1,
                        employeeSalaryList.get(i).getEmployee().getId(),
                        employeeSalaryList.get(i).getEmployee().getFullName(),
                        date[1],
                        NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                                .setRegion("DE").build())
                                .format(employeeSalaryList.get(i).getNetSalary())
                                + " VNĐ",
                        Employee.formatBirthDateToStandardType(
                                employeeSalaryList.get(i).getCreatedAt())
                });
            }
        }
    }

    public ArrayList<Object> getDataFromForm() {
        String employeeID = (String) employeeIDComboBox.getSelectedItem(),
                employeeName = employeeNameTextField.getText(),
                monthOfSalary = (String) monthOfSalaryTextField.getText();
        double salary = Double.parseDouble(
                (String) salarySumTextField.getText().replace(" VNĐ", "").replace(".", ""));

        return new ArrayList<>(Arrays.asList(employeeID, employeeName, monthOfSalary, salary));
    }

    public void insertTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu lương của nhân viên với ID " + formData.get(0) + " ?",
                "THÊM MỚI ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (employeeSalaryBUS.getEmployeeSalaryById((String) formData.get(0)) != null) {
                JOptionPane.showMessageDialog(this,
                        "Dữ liệu lương của nhân viên trong tháng này đã tồn tại!",
                        "CẢNH BÁO",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                employeeSalaryBUS.addEmployeeSalary(new EmployeeSalary(
                        Redux.employeeBUS.getEmployeeById((String) formData.get(0)),
                        10.5, (double) formData.get(3), LocalDate.now().toString(), false));
            }
            jTable1.revalidate();
            formInit();
            tableInit(employeeSalaryBUS.getAllEmployeeSalary());
        }
    }

    public void deleteTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu lương của nhân viên với ID " + formData.get(0) + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            employeeSalaryBUS.deleteEmployeeSalary(
                    employeeSalaryBUS.getEmployeeSalaryById((String) formData.get(0)));
            clearFormContent();
            jTable1.revalidate();
            tableInit(employeeSalaryBUS.getAllEmployeeSalary());
        }
    }

    public void calculateNetSalary() {
        String employeeId = (String) employeeIDComboBox.getSelectedItem();
        salaryInfo = employeeSalaryBUS.calculateSalary(employeeId);

        salarySumTextField.setText(NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                .setRegion("DE").build())
                .format(salaryInfo.get(5))
                + " VNĐ");
    }

    public void fillDataSalaryForm(Object[] selectedRowData) {
        employeeIDComboBox.setSelectedItem((String) selectedRowData[1]);
        employeeNameTextField.setText((String) selectedRowData[2]);
        monthOfSalaryTextField.setText((String) selectedRowData[3]);
        salarySumTextField.setText(((String) selectedRowData[4]));
    }

    public void clearFormContent() {
        formInit();
        employeeIDComboBox.setSelectedItem("");
        employeeNameTextField.setText("");
        monthOfSalaryTextField.setText(Integer.toString(LocalDate.now().getMonthValue()));
        salarySumTextField.setText("");
    }

    public boolean isFormFilled() {
        return !(employeeNameTextField.getText().equals("")
                || monthOfSalaryTextField.getText().equals(""));
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
                employeeIDComboBox.removeAllItems();
                employeeIDComboBox.addItem((String) selectedRowData[1]);
                calculateNetSalary();
                fillDataSalaryForm(selectedRowData);
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
        } else if (e.getSource() == cancelButton) {
            clearFormContent();
        } else if (e.getSource() == detailButton) {
            detailSalaryInfo.setVisible(true);
            detailSalaryInfo.receiveData(salaryInfo);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component clickedComponent = jPanel1.getComponentAt(jPanel1.getMousePosition());
        if (clickedComponent != jTable1 && selectionConfirmed) {
            jTable1.getSelectionModel().clearSelection();
            selectionConfirmed = false;
            addButton.setEnabled(true);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        salaryFormContainer = new javax.swing.JPanel();
        salaryLabel = new javax.swing.JLabel();
        salaryForm = new javax.swing.JPanel();
        employeeNameLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        employeeNameTextField = new javax.swing.JTextField();
        detailButton = new javax.swing.JButton();
        employeeIDComboBox = new javax.swing.JComboBox<>();
        monthPickerLabel = new javax.swing.JLabel();
        salarySumLabel = new javax.swing.JLabel();
        employeeIDLabel = new javax.swing.JLabel();
        salarySumTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        monthOfSalaryTextField = new javax.swing.JTextField();
        salaryTableContainer = new javax.swing.JPanel();
        salaryTableLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(102, 255, 102));
        setName("salaryContentPanel"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        salaryFormContainer.setBackground(new java.awt.Color(255, 255, 255));
        salaryFormContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        salaryFormContainer.setName("salaryFormContainer"); // NOI18N
        salaryFormContainer.setPreferredSize(new java.awt.Dimension(386, 540));

        salaryLabel.setBackground(new java.awt.Color(255, 255, 255));
        salaryLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        salaryLabel.setForeground(new java.awt.Color(0, 0, 0));
        salaryLabel.setText("Bảng Tính Lương");
        salaryLabel.setName("salaryLabel"); // NOI18N
        salaryLabel.setOpaque(true);
        salaryLabel.setPreferredSize(new java.awt.Dimension(369, 25));

        salaryForm.setBackground(new java.awt.Color(255, 255, 255));
        salaryForm.setName("salaryForm"); // NOI18N

        employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameLabel.setLabelFor(employeeNameTextField);
        employeeNameLabel.setText("Tên Nhân Viên :");
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N
        employeeNameLabel.setOpaque(true);

        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy Bỏ");
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.setOpaque(true);

        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        employeeNameTextField.setEnabled(false);
        employeeNameTextField.setName("employeeNameTextField"); // NOI18N

        detailButton.setBackground(new java.awt.Color(13, 110, 253));
        detailButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        detailButton.setForeground(new java.awt.Color(255, 255, 255));
        detailButton.setText("Xem Chi Tiết");
        detailButton.setName("detailButton"); // NOI18N
        detailButton.setOpaque(true);

        employeeIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeIDComboBox.setForeground(new java.awt.Color(0, 0, 0));
        employeeIDComboBox.setName("employeeIDComboBox"); // NOI18N
        employeeIDComboBox.setOpaque(true);

        monthPickerLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthPickerLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        monthPickerLabel.setForeground(new java.awt.Color(0, 0, 0));
        monthPickerLabel.setText("Lương Tháng :");
        monthPickerLabel.setName("monthPickerLabel"); // NOI18N
        monthPickerLabel.setOpaque(true);

        salarySumLabel.setBackground(new java.awt.Color(255, 255, 255));
        salarySumLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        salarySumLabel.setForeground(new java.awt.Color(0, 0, 0));
        salarySumLabel.setLabelFor(salarySumTextField);
        salarySumLabel.setText("Tổng Lương :");
        salarySumLabel.setName("salarySumLabel"); // NOI18N
        salarySumLabel.setOpaque(true);
        salarySumLabel.setPreferredSize(new java.awt.Dimension(95, 20));

        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeIDLabel.setLabelFor(employeeIDComboBox);
        employeeIDLabel.setText("Mã Nhân Viên :");
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);
        employeeIDLabel.setPreferredSize(new java.awt.Dimension(101, 40));

        salarySumTextField.setBackground(new java.awt.Color(204, 204, 204));
        salarySumTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        salarySumTextField.setForeground(new java.awt.Color(0, 0, 0));
        salarySumTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        salarySumTextField.setEnabled(false);
        salarySumTextField.setName("salarySumTextField"); // NOI18N
        salarySumTextField.setOpaque(true);

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

        monthOfSalaryTextField.setBackground(new java.awt.Color(204, 204, 204));
        monthOfSalaryTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        monthOfSalaryTextField.setForeground(new java.awt.Color(0, 0, 0));
        monthOfSalaryTextField.setEnabled(false);

        javax.swing.GroupLayout salaryFormLayout = new javax.swing.GroupLayout(salaryForm);
        salaryForm.setLayout(salaryFormLayout);
        salaryFormLayout.setHorizontalGroup(
                salaryFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryFormLayout.createSequentialGroup()
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                false)
                                        .addComponent(monthPickerLabel,
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeNameLabel,
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(salarySumLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(
                                        salaryFormLayout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(employeeNameTextField)
                                                .addComponent(salarySumTextField)
                                                .addComponent(monthOfSalaryTextField)))
                        .addGroup(salaryFormLayout.createSequentialGroup()
                                .addComponent(employeeIDLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        109,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(employeeIDComboBox, 0,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .addGroup(salaryFormLayout.createSequentialGroup()
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                false)
                                        .addComponent(addButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(detailButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                160,
                                                Short.MAX_VALUE))
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(cancelButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                160,
                                                Short.MAX_VALUE)
                                        .addComponent(deleteButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))));
        salaryFormLayout.setVerticalGroup(
                salaryFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salaryFormLayout
                                .createSequentialGroup()
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                false)
                                        .addComponent(employeeIDLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeIDComboBox))
                                .addGap(18, 18, 18)
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(employeeNameLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeNameTextField,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                40,
                                                Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(monthPickerLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                40,
                                                Short.MAX_VALUE)
                                        .addComponent(monthOfSalaryTextField))
                                .addGap(18, 18, 18)
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(salarySumTextField,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                40,
                                                Short.MAX_VALUE)
                                        .addComponent(salarySumLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(salaryFormLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(detailButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                50,
                                                Short.MAX_VALUE)
                                        .addComponent(cancelButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        120,
                                        Short.MAX_VALUE)
                                .addGroup(
                                        salaryFormLayout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        50,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(deleteButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        50,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        javax.swing.GroupLayout salaryFormContainerLayout = new javax.swing.GroupLayout(salaryFormContainer);
        salaryFormContainer.setLayout(salaryFormContainerLayout);
        salaryFormContainerLayout.setHorizontalGroup(
                salaryFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(salaryFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(salaryLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                372,
                                                Short.MAX_VALUE)
                                        .addComponent(salaryForm,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        salaryFormContainerLayout.setVerticalGroup(
                salaryFormContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(salaryLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(salaryForm,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addContainerGap()));

        salaryTableContainer.setBackground(new java.awt.Color(255, 255, 255));
        salaryTableContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        salaryTableContainer.setName("salaryTableContainer"); // NOI18N
        salaryTableContainer.setPreferredSize(new java.awt.Dimension(537, 540));

        salaryTableLabel.setBackground(new java.awt.Color(255, 255, 255));
        salaryTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        salaryTableLabel.setForeground(new java.awt.Color(0, 0, 0));
        salaryTableLabel.setText("Bảng Lương");
        salaryTableLabel.setName("salaryTableLabel"); // NOI18N
        salaryTableLabel.setOpaque(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "ID", "Tên Nhân Viên", "Lương Tháng", "Thực Lãnh", "Ngày Tạo"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setName("degreeTable"); // NOI18N
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout salaryTableContainerLayout = new javax.swing.GroupLayout(salaryTableContainer);
        salaryTableContainer.setLayout(salaryTableContainerLayout);
        salaryTableContainerLayout.setHorizontalGroup(
                salaryTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(salaryTableContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(salaryTableLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(jScrollPane1,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                523,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        salaryTableContainerLayout.setVerticalGroup(
                salaryTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(salaryTableLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        464,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(salaryFormContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(salaryTableContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                .createSequentialGroup()
                                .addComponent(jPanel2,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(salaryTableContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(salaryFormContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton detailButton;
    private javax.swing.JComboBox<String> employeeIDComboBox;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField monthOfSalaryTextField;
    private javax.swing.JLabel monthPickerLabel;
    private javax.swing.JPanel salaryForm;
    private javax.swing.JPanel salaryFormContainer;
    private javax.swing.JLabel salaryLabel;
    private javax.swing.JLabel salarySumLabel;
    private javax.swing.JTextField salarySumTextField;
    private javax.swing.JPanel salaryTableContainer;
    private javax.swing.JLabel salaryTableLabel;
    // End of variables declaration//GEN-END:variables
}
