package FrontEnd.DepartmentContentUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BackEnd.DepartmentManagement.Department;
import BackEnd.EmployeeManagement.Employee;
import FrontEnd.Redux.Redux;

public class AddEmployeeToDepartmentContentPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;
    ArrayList<Object> formData;

    public AddEmployeeToDepartmentContentPanel() {
        initComponents();

        formData = new ArrayList<>();

        projectLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        projectTableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Object.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);
        jTable1.setDefaultRenderer(Double.class, centerRenderer);

        tableInit(Redux.employeeBUS.getEmployeeList());

        jTable1.getSelectionModel().addListSelectionListener(this);
        jPanel1.addMouseListener(this);
        setVisible(true);
    }

    public void formInit() {
        DefaultComboBoxModel<String> departmentModel = new DefaultComboBoxModel<>();
        for (Department department : Redux.departmentBUS.getDepartmentList()) {
            if (!department.isDeleteStatus()) {
                departmentModel.addElement(department.getDepartmentId());
            }
        }
        departmentIDComboBox.setModel(departmentModel);

        if (departmentModel.getSize() > 0) {
            departmentIDComboBox.setSelectedIndex(0);
            Department firstDepartment = Redux.departmentBUS.getDepartmentList().get(0);
            departmentNameTextField.setText(firstDepartment.getDepartmentName());
            departmentManagerNameTextField.setText(firstDepartment.getDepartmentLeader().getFullName());
        } else {
            departmentNameTextField.setText("");
            departmentManagerNameTextField.setText("");
        }

        departmentIDComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedDepartmentId = (String) departmentIDComboBox.getSelectedItem();
                for (Department department : Redux.departmentBUS.getDepartmentList()) {
                    if (department.getDepartmentId().equalsIgnoreCase(selectedDepartmentId)) {
                        departmentNameTextField.setText(department.getDepartmentName());
                        departmentManagerNameTextField.setText(
                                department.getDepartmentLeader().getFullName());
                    }
                }
                tableInit(Redux.employeeBUS.getEmployeeList());
            }
        });

        DefaultComboBoxModel<String> employeeModel = new DefaultComboBoxModel<>();
        for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
            if (!employee.getDeleteStatus()) {
                if (employee.getDepartment().getDepartmentId().equalsIgnoreCase("DP000")) {
                    employeeModel.addElement(employee.getId());
                }
            }
        }
        employeeIDComboBox.setModel(employeeModel);

        if (employeeModel.getSize() > 0) {
            employeeIDComboBox.setSelectedIndex(0);
            Employee firstEmployee = Redux.employeeBUS
                    .getEmployeeById((String) employeeIDComboBox.getSelectedItem());
            employeeNameTextField.setText(firstEmployee.getFullName());
        } else {
            employeeNameTextField.setText("");
        }

        if (employeeModel.getSize() == 1) {
            String selectedEmployeeId = (String) employeeModel.getElementAt(0);
            for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                    employeeNameTextField.setText(employee.getFullName());
                    break; // Break the loop once found
                }
            }
        }

        if (departmentModel.getSize() > 0) {
            departmentIDComboBox.setSelectedIndex(0);
            Department firstDepartment = Redux.departmentBUS.getDepartmentList().get(0);
            departmentNameTextField.setText(firstDepartment.getDepartmentName());
            departmentManagerNameTextField.setText(firstDepartment.getDepartmentLeader().getFullName());

        } else {
            departmentNameTextField.setText("");
            departmentManagerNameTextField.setText("");
        }
        
        if (employeeModel.getSize() > 0) {
            employeeIDComboBox.setSelectedIndex(0);
            Employee firstEmployee = Redux.employeeBUS
                    .getEmployeeById((String) employeeIDComboBox.getSelectedItem());
            employeeNameTextField.setText(firstEmployee.getFullName());

        } else {
            employeeNameTextField.setText("");
        }

        employeeIDComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedEmployeeId = (String) employeeIDComboBox.getSelectedItem();
                for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                    if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                        employeeNameTextField.setText(employee.getFullName());
                    }
                }
            }
        });
    }

    public void tableInit(ArrayList<Employee> employeeList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < employeeList.size(); i++) {
            if (!employeeList.get(i).getDeleteStatus()) {
                if (employeeList.get(i).getDepartment().getDepartmentId().equalsIgnoreCase(
                        (String) departmentIDComboBox.getSelectedItem())) {
                    Object[] newRowData = {
                            i + 1, employeeList.get(i).getId(),
                            employeeList.get(i).getFullName(),
                            employeeList.get(i).getDepartment().getDepartmentId(),
                            employeeList.get(i).getDepartment().getDepartmentName(),
                            employeeList.get(i).getDepartment().getDepartmentLeader()
                                    .getFullName()
                    };
                    model.addRow(newRowData);
                }
            }
        }
    }

    public ArrayList<Object> getDataFromForm() {
        String departmentID = (String) departmentIDComboBox.getSelectedItem(),
                departmentName = departmentNameTextField.getText(),
                departmentManagerName = departmentManagerNameTextField.getText(),
                employeeID = (String) employeeIDComboBox.getSelectedItem(),
                employeeName = employeeNameTextField.getText();

        return new ArrayList<>(
                Arrays.asList(departmentID, departmentName, departmentManagerName, employeeID,
                        employeeName));
    }

    public void insertTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu phòng ban nhân viên với ID " + formData.get(3) + " ?",
                "THÊM MỚI ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {

            if (Redux.employeeBUS.getEmployeeById((String) employeeIDComboBox.getSelectedItem())
                    .getDepartment()
                    .getDepartmentId()
                    .equalsIgnoreCase("DP000")) {

                Department department = Redux.departmentBUS.getDepartmentList()
                        .get(departmentIDComboBox.getSelectedIndex());
                Employee employee = Redux.employeeBUS
                        .getEmployeeById((String) employeeIDComboBox.getSelectedItem());
                employee.setDepartment(department);

                Redux.employeeBUS.updateEmployee(employee);
                clearFormContent();
                jTable1.revalidate();
                // Redux.getAllEmployees();
                formInit();
                // tableInit(Redux.employeeList);
                tableInit(Redux.employeeBUS.getEmployeeList());
            } else {
                JOptionPane.showMessageDialog(this, "Nhân viên đã thuộc 1 phòng ban!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteTableRow() {
        formData = getDataFromForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu phòng ban nhân viên với ID " + formData.get(3) + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Department department = new Department();
            Employee employee = Redux.employeeBUS.getEmployeeById((String) formData.get(3));
            employee.setDepartment(department);
            Redux.employeeBUS.updateEmployee(employee);
            clearFormContent();
            jTable1.revalidate();
            // Redux.getAllEmployees();
            formInit();
            // tableInit(Redux.employeeList);
            tableInit(Redux.employeeBUS.getEmployeeList());
        }
    }

    public void fillDataDepartmentForm(Object[] selectedRowData) {
        DefaultComboBoxModel<String> employeeModel = (DefaultComboBoxModel<String>) employeeIDComboBox
                .getModel();
        employeeModel.removeAllElements();
        employeeModel.addElement((String) selectedRowData[1]);
        employeeIDComboBox.setSelectedItem(selectedRowData[1]);
        employeeNameTextField.setText((String) selectedRowData[2]);
        departmentIDComboBox.setSelectedItem(selectedRowData[3]);
        departmentNameTextField.setText((String) selectedRowData[4]);
        departmentManagerNameTextField.setText((String) selectedRowData[5]);
    }

    public void clearFormContent() {
        departmentIDComboBox.setSelectedItem("");
        departmentNameTextField.setText("");
        departmentManagerNameTextField.setText("");
        DefaultComboBoxModel<String> employeeModel = (DefaultComboBoxModel<String>) employeeIDComboBox
                .getModel();
        employeeModel.removeAllElements();
        formInit();
    }

    public boolean isFormFilled() {
        return !employeeNameTextField.getText().equals("");
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
                fillDataDepartmentForm(selectedRowData);
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
        }
        addButton.setEnabled(true);
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        projectFormContainer = new javax.swing.JPanel();
        projectLabel = new javax.swing.JLabel();
        departmentIDLabel = new javax.swing.JLabel();
        departmentNameLabel = new javax.swing.JLabel();
        departmentNameTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        employeeIDLabel = new javax.swing.JLabel();
        departmentManagerNameLabel = new javax.swing.JLabel();
        departmentManagerNameTextField = new javax.swing.JTextField();
        departmentIDComboBox = new javax.swing.JComboBox<>();
        employeeIDComboBox = new javax.swing.JComboBox<>();
        employeeNameTextField = new javax.swing.JTextField();
        employeeNameLabel = new javax.swing.JLabel();
        projectTableContainer = new javax.swing.JPanel();
        projectTableLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(102, 255, 102));
        setName("salaryContentPanel"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        projectFormContainer.setBackground(new java.awt.Color(255, 255, 255));
        projectFormContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        projectFormContainer.setName("projectFormContainer"); // NOI18N
        projectFormContainer.setPreferredSize(new java.awt.Dimension(396, 545));

        projectLabel.setBackground(new java.awt.Color(255, 255, 255));
        projectLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        projectLabel.setForeground(new java.awt.Color(0, 0, 0));
        projectLabel.setText("Nhân Viên Phòng Ban");
        projectLabel.setName("projectLabel"); // NOI18N
        projectLabel.setOpaque(true);

        departmentIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        departmentIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        departmentIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        departmentIDLabel.setText("Mã Phòng :");
        departmentIDLabel.setName("departmentIDLabel"); // NOI18N
        departmentIDLabel.setOpaque(true);

        departmentNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        departmentNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        departmentNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        departmentNameLabel.setLabelFor(departmentNameTextField);
        departmentNameLabel.setText("Tên Phòng :");
        departmentNameLabel.setName("departmentNameLabel"); // NOI18N
        departmentNameLabel.setOpaque(true);

        departmentNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        departmentNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        departmentNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        departmentNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        departmentNameTextField.setEnabled(false);
        departmentNameTextField.setName("departmentNameTextField"); // NOI18N
        departmentNameTextField.setOpaque(true);

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

        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy Bỏ");
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.setOpaque(true);

        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeIDLabel.setText("Mã NV :");
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);

        departmentManagerNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        departmentManagerNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        departmentManagerNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        departmentManagerNameLabel.setLabelFor(departmentNameTextField);
        departmentManagerNameLabel.setText("Quản Lý :");
        departmentManagerNameLabel.setName("employeeNameLabel"); // NOI18N
        departmentManagerNameLabel.setOpaque(true);

        departmentManagerNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        departmentManagerNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        departmentManagerNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        departmentManagerNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        departmentManagerNameTextField.setEnabled(false);
        departmentManagerNameTextField.setName("projectNameTextField"); // NOI18N
        departmentManagerNameTextField.setOpaque(true);
        departmentManagerNameTextField.setPreferredSize(new java.awt.Dimension(299, 40));

        departmentIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        departmentIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        departmentIDComboBox.setForeground(new java.awt.Color(0, 0, 0));
        departmentIDComboBox.setPreferredSize(new java.awt.Dimension(299, 40));

        employeeIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeIDComboBox.setForeground(new java.awt.Color(0, 0, 0));
        employeeIDComboBox.setOpaque(true);
        employeeIDComboBox.setPreferredSize(new java.awt.Dimension(299, 40));

        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameTextField.setCaretColor(new java.awt.Color(0, 0, 0));
        employeeNameTextField.setEnabled(false);
        employeeNameTextField.setName("projectNameTextField"); // NOI18N
        employeeNameTextField.setOpaque(true);
        employeeNameTextField.setPreferredSize(new java.awt.Dimension(299, 40));

        employeeNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        employeeNameLabel.setLabelFor(departmentNameTextField);
        employeeNameLabel.setText("Tên NV");
        employeeNameLabel.setName("employeeNameLabel"); // NOI18N
        employeeNameLabel.setOpaque(true);

        javax.swing.GroupLayout projectFormContainerLayout = new javax.swing.GroupLayout(projectFormContainer);
        projectFormContainer.setLayout(projectFormContainerLayout);
        projectFormContainerLayout.setHorizontalGroup(
                projectFormContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(projectFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cancelButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(projectLabel,
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addGroup(projectFormContainerLayout
                                                .createSequentialGroup()
                                                .addComponent(departmentNameLabel)
                                                .addGap(2, 2, 2)
                                                .addComponent(departmentNameTextField))
                                        .addGroup(projectFormContainerLayout
                                                .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(projectFormContainerLayout
                                                        .createSequentialGroup()
                                                        .addComponent(addButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                160,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                        .addComponent(deleteButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                160,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(projectFormContainerLayout
                                                        .createSequentialGroup()
                                                        .addGroup(projectFormContainerLayout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(projectFormContainerLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(departmentIDLabel)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(departmentIDComboBox,
                                                                                0,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(projectFormContainerLayout
                                                                        .createSequentialGroup()
                                                                        .addGroup(projectFormContainerLayout
                                                                                .createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                        false)
                                                                                .addComponent(
                                                                                        departmentManagerNameLabel,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        82,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(employeeIDLabel,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(employeeNameLabel,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE))
                                                                        .addGap(2, 2, 2)
                                                                        .addGroup(projectFormContainerLayout
                                                                                .createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(
                                                                                        departmentManagerNameTextField,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(employeeIDComboBox,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(employeeNameTextField,
                                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGap(0, 0, Short.MAX_VALUE))))
                                .addContainerGap()));
        projectFormContainerLayout.setVerticalGroup(
                projectFormContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(projectFormContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(projectLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(departmentIDLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(departmentIDComboBox,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(departmentNameLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(departmentNameTextField,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                40,
                                                Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(departmentManagerNameLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(departmentManagerNameTextField,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(employeeIDLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeIDComboBox,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(employeeNameLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeNameTextField,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        88,
                                        Short.MAX_VALUE)
                                .addGroup(projectFormContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(deleteButton,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addButton,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        projectTableContainer.setBackground(new java.awt.Color(255, 255, 255));
        projectTableContainer
                .setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        projectTableContainer.setName("projectTableContainer"); // NOI18N

        projectTableLabel.setBackground(new java.awt.Color(255, 255, 255));
        projectTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        projectTableLabel.setForeground(new java.awt.Color(0, 0, 0));
        projectTableLabel.setText("Danh Sách Nhân Viên Của Phòng Ban");
        projectTableLabel.setName("projectTableLabel"); // NOI18N
        projectTableLabel.setOpaque(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã Nhân Viên", "Tên Nhân Viên", "Mã Phòng Ban", "Tên Phòng Ban",
                        "Quản Lý"
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
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setName("degreeTable"); // NOI18N
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout projectTableContainerLayout = new javax.swing.GroupLayout(
                projectTableContainer);
        projectTableContainer.setLayout(projectTableContainerLayout);
        projectTableContainerLayout.setHorizontalGroup(
                projectTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(projectTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(projectTableContainerLayout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(projectTableLabel,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(jScrollPane1,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                513,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        projectTableContainerLayout.setVerticalGroup(
                projectTableContainerLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(projectTableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(projectTableLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        465,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(projectFormContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        32,
                                        Short.MAX_VALUE)
                                .addComponent(projectTableContainer,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                        .addComponent(projectFormContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                554,
                                                Short.MAX_VALUE)
                                        .addComponent(projectTableContainer,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> departmentIDComboBox;
    private javax.swing.JLabel departmentIDLabel;
    private javax.swing.JLabel departmentManagerNameLabel;
    private javax.swing.JTextField departmentManagerNameTextField;
    private javax.swing.JLabel departmentNameLabel;
    private javax.swing.JTextField departmentNameTextField;
    private javax.swing.JComboBox<String> employeeIDComboBox;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel projectFormContainer;
    private javax.swing.JLabel projectLabel;
    private javax.swing.JPanel projectTableContainer;
    private javax.swing.JLabel projectTableLabel;
    // End of variables declaration//GEN-END:variables
}
