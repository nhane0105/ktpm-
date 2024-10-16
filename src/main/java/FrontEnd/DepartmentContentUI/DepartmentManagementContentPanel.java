package FrontEnd.DepartmentContentUI;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

import BackEnd.DegreeManagement.Degree;
import BackEnd.DepartmentManagement.Department;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.PositionManagement.Position;
import BackEnd.SpecialtyManagement.Specialty;
import FrontEnd.Redux.Redux;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DepartmentManagementContentPanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    DepartmentForm departmentForm;
    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;

    public DepartmentManagementContentPanel() {
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
        exportExcel.addActionListener(this);
        importExcel.addActionListener(this);

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

        tblDepartment.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblDepartment.setDefaultRenderer(String.class, centerRenderer);
        tblDepartment.setDefaultRenderer(Integer.class, centerRenderer);

        tableInit(Redux.departmentBUS.getDepartmentList());

        tblDepartment.getSelectionModel().addListSelectionListener(this);
        tblDepartment.addMouseListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            insertTableRow();
        } else if (e.getSource() == deleteButton) {
            if (selectedRow >= 0) {
                deleteTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn một dòng trước!", "Cảnh Báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == editButton) {
            if (selectedRow >= 0) {
                updateTableRow();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn một dòng trước!", "Cảnh Báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == exportExcel) {
            exportToExcel();
        } else if (e.getSource() == importExcel) {
            importFromExcel();
        }
        addButton.setEnabled(true);
    }

    public void importFromExcel() {
        try {
            FileInputStream file = new FileInputStream(new File("src/main/resources/files/ImportFile.xlsx"));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet("Department Sheet");

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

                String employeeId = dataList.get(2).toString();

                // Kiểm tra xem Employee có tồn tại không
                Employee employee = Redux.employeeBUS.getEmployeeById(employeeId);
                if (employee == null) {
                    continue; // Bỏ qua nếu không có Employee
                }

                Department department = new Department((String) dataList.get(0), (String) dataList.get(1),
                        employee, false);

                Redux.departmentBUS.addDepartmentExcel(department);

            }
            file.close();

            tableInit(Redux.departmentBUS.getDepartmentList());
            JOptionPane.showMessageDialog(null, "Data Imported Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void exportToExcel() {
        DefaultTableModel model = (DefaultTableModel) tblDepartment.getModel();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Department Sheet");

            // Tiêu đề của các cột
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < Department.getHeader().size(); i++) {
                headerRow.createCell(i).setCellValue(Department.getHeader().get(i));
            }

            // Dữ liệu từ bảng
            for (int row = 0; row < Redux.departmentBUS.getDepartmentList().size(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                Department department = Redux.departmentBUS.getDepartmentList().get(row);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object cellValue = department.getPropertyByIndex(col);
                    if (cellValue != null) {
                        excelRow.createCell(col).setCellValue(cellValue.toString());
                    } else {
                        excelRow.createCell(col).setCellValue("");
                    }
                }
            }

            // Lưu workbook vào file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lưu file Excel!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi tạo workbook!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void search(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tblDepartment.getModel();
        int check = 0;
        for (int i = 0; i < tblDepartment.getRowCount(); i++) {
            String btid = model.getValueAt(i, 1).toString().trim().toLowerCase();
            String name = model.getValueAt(i, 3).toString().trim().toLowerCase(); // Lấy tên từ dòng hiện
            // tại và chuyển
            // thành chữ thường
            String id = model.getValueAt(i, 2).toString().trim().toLowerCase(); // Lấy ID từ dòng hiện tại
            // và chuyển
            // thành chữ thường
            if (name.contains(searchText.toLowerCase()) || id.contains(searchText.toLowerCase())
                    || btid.contains(searchText.toLowerCase())) { // So sánh tên hoặc ID với nội
                // dung tìm kiếm

                tblDepartment.getSelectionModel().setSelectionInterval(i, i); // Chọn dòng tìm thấy
                Rectangle rect = tblDepartment.getCellRect(i, 0, true);
                tblDepartment.scrollRectToVisible(rect); // Cuộn tới dòng tìm thấy
                check = 1;
                break; // Kết thúc vòng lặp khi tìm thấy dòng phù hợp
            }
        }
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu.");
        }
    }

    public static void tableInit(ArrayList<Department> departmentList) {

        DefaultTableModel model = (DefaultTableModel) tblDepartment.getModel();

        model.setRowCount(0);

        for (int i = 0; i < departmentList.size(); i++) {
            Department department = departmentList.get(i);
            if (!department.isDeleteStatus()) {
                model.addRow(new Object[] {
                        i + 1,
                        department.getDepartmentId(),
                        department.getDepartmentName(),
                        department.getDepartmentLeader().getId(),
                        department.getDepartmentLeader().getFullName(),
                        department.getNumberOfMembers()
                });
            }
        }
    }

    public void insertTableRow() {
        departmentForm = new DepartmentForm();
        departmentForm.setTitle("THÊM MỚI PHÒNG BAN");
        departmentForm.setVisible(true);
    }

    public void updateTableRow() {
        Department selectedDepartment = Redux.departmentBUS.getDepartmentById((String) selectedRowData[1]);
        List<Object> departmentPropertiesValue = selectedDepartment.toList();
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.addAll(departmentPropertiesValue);

        departmentForm = new DepartmentForm();
        departmentForm.setTitle("CÂP NHẬT PHÒNG BAN");
        departmentForm.setVisible(true);
        departmentForm.showFormWithData(dataList);
        departmentForm.departmentIDTextField.setEnabled(false);
        departmentForm.departmentNameTextField.setEnabled(false);
    }

    public void deleteTableRow() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ phòng ban với ID " + selectedRowData[1] + " ?",
                "Xóa Phòng Ban",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.departmentBUS.deleteDepartment(
                    Redux.departmentBUS.getDepartmentById((String) selectedRowData[1]));

            tblDepartment.revalidate();

            // Redux.getAllDepartments();
            // tableInit(Redux.departmentList);
            tableInit(Redux.departmentBUS.getDepartmentList());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) { // Ensure selection is stable
            selectionConfirmed = true;
            selectedRow = tblDepartment.getSelectedRow();
            if (selectedRow >= 0) { // Check if a row is selected
                selectedRowData = new Object[tblDepartment.getColumnCount()];
                for (int i = 0; i < tblDepartment.getColumnCount(); i++) {
                    selectedRowData[i] = tblDepartment.getValueAt(selectedRow, i);
                }
                addButton.setEnabled(false);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        exportExcel = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        searchOptionComboBox = new javax.swing.JComboBox<>();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        importExcel = new javax.swing.JButton();
        tableContainer = new javax.swing.JPanel();
        tableLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDepartment = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
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

        exportExcel.setBackground(new java.awt.Color(13, 202, 240));
        exportExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exportExcel.setForeground(new java.awt.Color(255, 255, 255));
        exportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel.png"))); // NOI18N
        exportExcel.setText("Xuất");
        exportExcel.setIconTextGap(10);
        exportExcel.setName("exportExcel"); // NOI18N

        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        editButton.setText("Sửa");
        editButton.setIconTextGap(10);
        editButton.setName("editButton"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        searchOptionComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchOptionComboBox.setForeground(new java.awt.Color(255, 255, 255));
        searchOptionComboBox
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo Tên", "Theo Mã Công Tác" }));
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
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
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
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 544,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(searchOptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        importExcel.setBackground(new java.awt.Color(13, 110, 253));
        importExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        importExcel.setForeground(new java.awt.Color(255, 255, 255));
        importExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel.png"))); // NOI18N
        importExcel.setText("Nhập ");
        importExcel.setIconTextGap(10);
        importExcel.setName("importExcel"); // NOI18N

        tableContainer.setBackground(new java.awt.Color(255, 255, 255));
        tableContainer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tableContainer.setName("tableContainer"); // NOI18N

        tableLabel.setBackground(new java.awt.Color(255, 255, 255));
        tableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tableLabel.setForeground(new java.awt.Color(0, 0, 0));
        tableLabel.setText("Danh sách công tác của nhân viên");
        tableLabel.setName("tableLabel"); // NOI18N
        tableLabel.setOpaque(true);

        tblDepartment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblDepartment.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã Phòng Ban", "Tên Phòng Ban", "Mã Quản Lý", "Tên Quản Lý", "Số Thành Viên"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
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
        tblDepartment.setRowHeight(40);
        jScrollPane2.setViewportView(tblDepartment);

        javax.swing.GroupLayout tableContainerLayout = new javax.swing.GroupLayout(tableContainer);
        tableContainer.setLayout(tableContainerLayout);
        tableContainerLayout.setHorizontalGroup(
                tableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tableContainerLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(tableContainerLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
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
                                        .addComponent(tableContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(96, 96, 96)));
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

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        String searchText = searchTextField.getText().trim();
        String searchOption = (String) searchOptionComboBox.getSelectedItem();

        if (searchText.isEmpty()) {
            tableInit(Redux.departmentBUS.getDepartmentList());
        } else {
            ArrayList<Department> searchResults = new ArrayList<>();
            for (Department department : Redux.departmentBUS.getDepartmentList()) {
                switch (searchOption) {
                    case "Theo Tên":
                        if (department.getDepartmentName().toLowerCase().contains(searchText.toLowerCase())) {
                            searchResults.add(department);
                        }
                        break;
                    case "Theo Mã Công Tác":
                        if (department.getDepartmentId().toLowerCase().contains(searchText.toLowerCase())) {
                            searchResults.add(department);
                        }
                        break;
                    default:
                        break;
                }
            }
            tableInit(searchResults);
        }
    }// GEN-LAST:event_searchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton exportExcel;
    private javax.swing.JButton importExcel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchOptionComboBox;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel tableContainer;
    private javax.swing.JLabel tableLabel;
    private static javax.swing.JTable tblDepartment;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tblDepartment) {

        } else {
            Component clickedComponent = this.getComponentAt(this.getMousePosition());
            if (clickedComponent != tblDepartment && selectionConfirmed) {
                tblDepartment.getSelectionModel().clearSelection();
                selectionConfirmed = false;
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
