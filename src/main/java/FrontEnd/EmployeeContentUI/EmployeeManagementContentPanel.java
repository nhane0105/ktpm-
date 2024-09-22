package FrontEnd.EmployeeContentUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

import BackEnd.AccountManagement.Account;
import BackEnd.DegreeManagement.Degree;
import BackEnd.DepartmentManagement.Department;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.PositionManagement.Position;
import BackEnd.SpecialtyManagement.Specialty;
import FrontEnd.AccountContentUI.AccountManagementContentPanel;
import FrontEnd.Redux.Redux;

public class EmployeeManagementContentPanel extends javax.swing.JPanel
        implements MouseListener, ActionListener, ListSelectionListener {

    UserInformationForm userInfoForm;
    UserInformationFrame userInfoFrame;
    int selectedRow = -1;
    Object[] selectedRowData;
    boolean selectionConfirmed;

    public EmployeeManagementContentPanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
            importExcel.setVisible(false);
            exportExcel.setVisible(false);
        }

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        importExcel.addActionListener(this);
        exportExcel.addActionListener(this);
        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Line color and stroke size
                "Tìm kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 12),
                Color.BLACK // Text color
        );
        jPanel1.setBorder(titledBorder);

        tableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);

        // tableInit(Redux.employeeList);
        tableInit(Redux.employeeBUS.getEmployeeList());
        jTable1.getSelectionModel().addListSelectionListener(this);
        jTable1.addMouseListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    public static void tableInit(ArrayList<Employee> employeeList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
             if (employee.getId().contains("DIR")){
                continue;
            }
            model.addRow(new Object[] {
                    i + 1,
                    employee.getId(),
                    employee.getFullName(),
                    employee.getGender(),
                    Employee.formatBirthDateToStandardType(employee.getBirthDate()),
                    employee.getPosition().getPositionName(),
                    employee.getEmployStatus() ? "Đang làm việc" : "Đã nghỉ việc"
            });
        }
    }

    public void insertTableRow() {
        userInfoForm = new UserInformationForm();
        userInfoForm.setTitle("THÊM MỚI THÔNG TIN CÁ NHÂN CỦA NHÂN VIÊN");
        userInfoForm.setVisible(true);
    }

    public void updateTableRow() {
        Employee selectedEmployee = Redux.employeeBUS.getEmployeeById((String) selectedRowData[1]);
        List<Object> employeePropertiesValue = selectedEmployee.toList();

        for (int i = 0; i < employeePropertiesValue.size(); i++) {
            Object value = employeePropertiesValue.get(i);

            if (value instanceof String) {
                switch (i) {
                    case 3:
                        selectedEmployee.setBirthDate(Employee
                                .formatBirthDateToStandardType(
                                        selectedEmployee.getBirthDate()));
                        employeePropertiesValue.set(i, selectedEmployee.getBirthDate());
                        break;

                    case 9:
                        selectedEmployee.setDegree(
                                Redux.degreeBUS.getDegreeById((String) value));
                        employeePropertiesValue.set(i,
                                selectedEmployee.getDegree().getDegreeName());
                        break;

                    case 10:
                        selectedEmployee.setPosition(
                                Redux.positionBUS.getPositionById((String) value));
                        employeePropertiesValue.set(i,
                                selectedEmployee.getPosition().getPositionName());
                        break;

                    case 12:
                        selectedEmployee.setSpecialty(
                                Redux.specialtyBUS.getSpecialtyById((String) value));
                        employeePropertiesValue.set(i,
                                selectedEmployee.getSpecialty().getSpecialtyName());
                        break;

                    default:
                        break;
                }

            } else {
                switch (i) {
                    case 13:
                        System.out.println("Check value employStatus: " + (boolean) value);
                        employeePropertiesValue.set(i,
                                (boolean) value ? "Đang làm việc" : "Đã nghỉ việc");
                        break;

                    default:
                        break;
                }
            }
        }

        // Create a new ArrayList
        ArrayList<Object> dataList = new ArrayList<>();

        // Add all elements from the array to the ArrayList
        dataList.addAll(employeePropertiesValue);

        userInfoForm = new UserInformationForm();
        userInfoForm.setTitle("CẬP NHẬT THÔNG TIN CÁ NHÂN CỦA NHÂN VIÊN");
        userInfoForm.setVisible(true);
        userInfoForm.showFormWithData(dataList);
    }

    public void deleteTableRow() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ nhân viên với ID " + selectedRowData[1] + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.employeeBUS
                    .deleteEmployee(Redux.employeeBUS.getEmployeeById((String) selectedRowData[1]));
            Redux.accountBUS.deleteAccount(Redux.accountBUS.getAccountById((String) selectedRowData[1]));
            AccountManagementContentPanel.tableInit(Redux.accountBUS.getAccountList());
            jTable1.revalidate();
            tableInit(Redux.employeeBUS.getEmployeeList());
        }
    }

    public Boolean checkValidData(Degree degree, Position position, Department department,
            Specialty specialty) {
        boolean flag = true;
        if (degree == null || position == null || specialty == null) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
            flag = false;
        }

        if (department == null) {
            flag = false;
        }

        return flag;
    }

    public void handleImportExcel() {
        try {
            FileInputStream file = new FileInputStream(new File("src/main/resources/files/ImportFile.xlsx"));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet("Employee Sheet");

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                ArrayList<Object> dataList = new ArrayList<>();

                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    // Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            dataList.add(cell.getNumericCellValue());
                            break;

                        case STRING:
                            dataList.add(cell.getStringCellValue());
                            break;

                        default:
                            break;
                    }
                }

                Degree degree = Redux.degreeBUS.getDegreeByName((String) dataList.get(9));
                Position position = Redux.positionBUS.getPositionByName((String) dataList.get(10));
                Department department = Redux.departmentBUS.getDepartmentByName((String) dataList.get(11));
                Specialty specialty = Redux.specialtyBUS.getSpecialtyByName((String) dataList.get(12));

                if (!checkValidData(degree, position, department, specialty)) {
                    department = (boolean) dataList.get(11).equals("Không có phòng ban") ? new Department()
                            : Redux.departmentBUS.getDepartmentByName((String) dataList.get(11));

                    Employee employee = new Employee((String) dataList.get(0), (String) dataList.get(1),
                            (String) dataList.get(2), (String) dataList.get(3),
                            (String) dataList.get(4), (String) dataList.get(5), (String) dataList.get(6),
                            (String) dataList.get(7), degree, (String) dataList.get(8), position,
                            department, specialty);

                    Redux.employeeBUS.addEmployeeExcel(employee);
                }

            }
            file.close();

            tableInit(Redux.employeeBUS.getEmployeeList());
            JOptionPane.showMessageDialog(null, "Data Imported Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleExportExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Employee Sheet");

                // Write column names to the first row of the sheet
                XSSFRow headerRow = sheet.createRow(0);
                for (int j = 0; j < Employee.getHeader().size(); j++) {
                    XSSFCell cell = headerRow.createCell(j);
                    cell.setCellValue(Employee.getHeader().get(j));
                }

                for (int i = 0; i < Redux.employeeBUS.getEmployeeList().size() - 1; i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    Employee employee = Redux.employeeBUS.getEmployeeList().get(i);
                    for (int j = 0; j < Employee.getHeader().size(); j++) {
                        Object value = employee.getPropertyByIndex(j);
                        String cellValue = (value != null) ? value.toString() : "";
                        XSSFCell cell = row.createCell(j); // Tạo ô trong bảng tính
                        cell.setCellValue(cellValue); // Ghi giá trị vào ô
                    }
                }

                // Write the workbook to the file
                FileOutputStream out = new FileOutputStream(new File(filePath));
                workbook.write(out);
                out.close();
                workbook.close();

                JOptionPane.showMessageDialog(null, "Data Exported Successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleSearch() {
        String searchOption = (String) searchOptionComboBox.getSelectedItem();
        String searchValue = searchTextField.getText().trim();
        System.out.println("Check search value: " + searchValue);

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập từ khóa tìm kiếm!", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ArrayList<Employee> searchResult = new ArrayList<>();
        switch (searchOption) {
            case "Theo Tên":
                Redux.employeeBUS.searchEmployeeByName(searchValue);
                searchResult = Redux.employeeBUS.getEmployeeSearchResult();
                break;

            case "Theo Mã":
                Redux.employeeBUS.searchEmployeeById(searchValue);
                searchResult = Redux.employeeBUS.getEmployeeSearchResult();
                break;

            default:
                break;
        }

        for (Employee employee : searchResult) {
            System.out.println(employee.toString());
        }

        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableInit(searchResult);
        }
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
                addButton.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            insertTableRow();
        } else if (e.getSource() == deleteButton) {
            if (selectedRow >= 0) {
                deleteTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == editButton) {
            if (selectedRow >= 0) {
                updateTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == importExcel) {
            handleImportExcel();
        } else if (e.getSource() == exportExcel) {
            handleExportExcel();
        } else if (e.getSource() == searchButton) {
            handleSearch();
        } else if (e.getSource() == refreshButton) {
            searchTextField.setText("");
            tableInit(Redux.employeeBUS.getEmployeeList());
        }
        addButton.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        importExcel = new javax.swing.JButton();
        exportExcel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        searchOptionComboBox = new javax.swing.JComboBox<>();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        tableContainer = new javax.swing.JPanel();
        tableLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setPreferredSize(new java.awt.Dimension(1055, 640));

        addButton.setBackground(new java.awt.Color(25, 135, 84));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        addButton.setText("Thêm");
        addButton.setIconTextGap(10);
        addButton.setName("addButton"); // NOI18N

        deleteButton.setBackground(new java.awt.Color(220, 53, 69));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteButton.setText("Xóa");
        deleteButton.setIconTextGap(10);
        deleteButton.setName("deleteButton"); // NOI18N

        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        editButton.setText("Sửa");
        editButton.setIconTextGap(10);
        editButton.setName("editButton"); // NOI18N

        importExcel.setBackground(new java.awt.Color(13, 110, 253));
        importExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        importExcel.setForeground(new java.awt.Color(255, 255, 255));
        importExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel.png"))); // NOI18N
        importExcel.setText("Nhập ");
        importExcel.setIconTextGap(10);
        importExcel.setName("importExcel"); // NOI18N

        exportExcel.setBackground(new java.awt.Color(13, 202, 240));
        exportExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exportExcel.setForeground(new java.awt.Color(255, 255, 255));
        exportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel.png"))); // NOI18N
        exportExcel.setText("Xuất");
        exportExcel.setIconTextGap(10);
        exportExcel.setName("exportExcel"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        searchOptionComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchOptionComboBox.setForeground(new java.awt.Color(255, 255, 255));
        searchOptionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo Tên", "Theo Mã" }));
        searchOptionComboBox.setName("searchOptionComboBox"); // NOI18N
        searchOptionComboBox.setOpaque(true);

        searchTextField.setBackground(new java.awt.Color(204, 204, 204));
        searchTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(0, 0, 0));
        searchTextField.setName("searchTextField"); // NOI18N
        searchTextField.setOpaque(true);

        searchButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        searchButton.setText("Tìm Kiếm");
        searchButton.setIconTextGap(10);
        searchButton.setName("searchButton"); // NOI18N

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Làm Mới");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(searchOptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 454,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton)
                                .addGap(14, 14, 14)
                                .addComponent(refreshButton)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                                        Short.MAX_VALUE))
                                        .addComponent(searchOptionComboBox)
                                        .addComponent(searchTextField))
                                .addContainerGap()));

        tableContainer.setBackground(new java.awt.Color(255, 255, 255));
        tableContainer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tableContainer.setName("tableContainer"); // NOI18N
        tableContainer.setPreferredSize(new java.awt.Dimension(863, 364));

        tableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tableLabel.setForeground(new java.awt.Color(0, 0, 0));
        tableLabel.setText("Danh sách nhân viên");
        tableLabel.setName("tableLabel"); // NOI18N

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "ID", "Họ và Tên", "Giới Tính", "Ngày Sinh", "Chức Vụ", "Trạng Thái"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(3);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout tableContainerLayout = new javax.swing.GroupLayout(tableContainer);
        tableContainer.setLayout(tableContainerLayout);
        tableContainerLayout.setHorizontalGroup(
                tableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tableContainerLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(tableContainerLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 811,
                                                Short.MAX_VALUE)
                                        .addComponent(tableLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)));
        tableContainerLayout.setVerticalGroup(
                tableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tableContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(21, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(63, 63, 63)
                                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(importExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(62, 62, 62)
                                                .addComponent(exportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tableContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(94, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(importExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(exportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)));
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton exportExcel;
    private javax.swing.JButton importExcel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable jTable1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchOptionComboBox;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel tableContainer;
    private javax.swing.JLabel tableLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == jTable1) {
            if (Redux.isAdmin) {
                if (e.getClickCount() == 2) {
                    // Get the selected row index
                    selectedRow = jTable1.getSelectedRow();
                    if (selectedRow != -1) {
                        Employee employee = Redux.employeeBUS
                                .getEmployeeById((String) selectedRowData[1]);
                        // Open a new frame with information from the selected row
                        userInfoFrame = new UserInformationFrame();
                        userInfoFrame.setVisible(true);
                        userInfoFrame.showFormWithData(employee);
                    }
                }
            }
        } else {
            Component clickedComponent = this.getComponentAt(this.getMousePosition());
            if (clickedComponent != jTable1 && selectionConfirmed) {
                jTable1.getSelectionModel().clearSelection();
                selectionConfirmed = false;
                addButton.setEnabled(true);
            }
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
