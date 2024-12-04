package FrontEnd.RewardContentUI;

import BackEnd.CriticismManagement.Criticism;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.EmployeesRewardsCriticismManagement.EmployeesRewardsCriticism;
import BackEnd.RewardManagement.Reward;
import static FrontEnd.CriticismContentUI.CriticismEmployeePanel.tableInit;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RewardEmployeePanel extends javax.swing.JPanel
        implements ActionListener, ListSelectionListener, MouseListener {

    RewardEmployeeForm rewardEmployeeForm;
    int selectedRow = -1;
    boolean selectionConfirmed;
    Object[] selectedRowData;

    public RewardEmployeePanel() {
        initComponents();

        if (!Redux.isAdmin && !Redux.isDirector) {
            Redux.employeesRewardsCriticismBUS.getEmployeesRewardsByEmployeeId(Redux.userId);
            addButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            importExcel.setVisible(false);
            exportExcel.setVisible(false);
        }

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
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
        jPanel2.setBorder(titledBorder);

        tableLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(String.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);

        if (!Redux.isAdmin && !Redux.isDirector) {
            tableInit(Redux.employeesRewardsCriticismBUS.getListEmployeeReward());
        } else {
            tableInit(Redux.employeesRewardsCriticismBUS.getlistEmployeeRC());
        }

        jTable1.getSelectionModel().addListSelectionListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    public static void tableInit(ArrayList<EmployeesRewardsCriticism> employeeRCBUS) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < employeeRCBUS.size(); i++) {
            if (!employeeRCBUS.get(i).getReward().getRewardId().equalsIgnoreCase("RE001")) {
                if (employeeRCBUS.get(i).getRewardCount() != 0) {
                    model.addRow(new Object[] {
                            i + 1,
                            employeeRCBUS.get(i).getEmployee().getId(),
                            employeeRCBUS.get(i).getEmployee().getFullName(),
                            employeeRCBUS.get(i).getReward().getRewardName(),
                            employeeRCBUS.get(i).getRewardCount(),
                            NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                                    .setRegion("DE").build())
                                    .format(employeeRCBUS.get(i).getRewardCount()
                                            * employeeRCBUS.get(i)
                                                    .getReward()
                                                    .getReward())
                                    + " VNĐ",
                            Employee.formatBirthDateToStandardType(
                                    employeeRCBUS.get(i).getCreatedAt()), });
                }
            }
        }
    }

    public void insertTableRow() {
        rewardEmployeeForm = new RewardEmployeeForm();
        rewardEmployeeForm.setTitle("THÊM MỚI THÔNG TIN KHEN THƯỞNG CỦA NHÂN VIÊN");
        rewardEmployeeForm.setVisible(true);
//        rewardEmployeeForm.startDatePicker.setEnable(false);
    }

    public void updateTableRow() {
        selectedRowData[5] = selectedRowData[5].toString().replace(" VNĐ", "").replace(".", "");

        // Create a new ArrayList
        ArrayList<Object> dataList = new ArrayList<>(selectedRowData.length);

        // Add all elements from the array to the ArrayList
        dataList.addAll(Arrays.asList(selectedRowData));
        rewardEmployeeForm = new RewardEmployeeForm();
        rewardEmployeeForm.setTitle("CẬP NHẬT THÔNG TIN KHEN THƯỞNG CỦA NHÂN VIÊN");
        rewardEmployeeForm.employeeIDComboBox.setEnabled(false);
        rewardEmployeeForm.employeeNameTextField.setEnabled(false);
        rewardEmployeeForm.showFormWithData(dataList);
    }

    public void deleteTableRow() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa bỏ dữ liệu khen thưởng này của nhân viên với ID " + selectedRowData[1]
                        + " ?",
                "XÓA BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Redux.employeesRewardsCriticismBUS
                    .deleteEmployeesRewardsCriticism(Redux.employeesRewardsCriticismBUS
                            .getEmployeesRewardsCriticism((String) selectedRowData[1],
                                    Redux.rewardBUS.getRewardByName(
                                            (String) selectedRowData[3])
                                            .getRewardId(),
                                    new Criticism().getCriticismId(),
                                    Employee.formatBirthDateToDatabaseType(
                                            (String) selectedRowData[6])));
            jTable1.revalidate();
            tableInit(Redux.employeesRewardsCriticismBUS.getlistEmployeeRC());
        }
    }

    public Boolean checkValidData(Employee employee, Criticism criticism, Reward reward) {
        boolean flag = true;
        if (employee == null || reward == null || criticism == null) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
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
            XSSFSheet sheet = workbook.getSheet("Reward Employee Sheet");

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                ArrayList<Object> dataList = new ArrayList<>();

                Row row = rowIterator.next();

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

                Employee employee = Redux.employeeBUS.getEmployeeById((String) dataList.get(0));
                Reward reward = Redux.rewardBUS.getRewardByName((String) dataList.get(1));
                int rewardCount = dataList.get(2) instanceof Number ? ((Number) dataList.get(2)).intValue() : 0;
                Criticism criticism = new Criticism();
                int faultCount = 0;
                String createdAt = (String) dataList.get(3);

                if (checkValidData(employee, criticism, reward)) {
                    EmployeesRewardsCriticism employeeRC = new EmployeesRewardsCriticism(employee, reward, rewardCount,
                            criticism, faultCount, createdAt);
                    Redux.employeesRewardsCriticismBUS.addEmployeesRewardsCriticismExcel(employeeRC);
                }

            }
            file.close();
            tableInit(Redux.employeesRewardsCriticismBUS.getlistEmployeeRC());
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
                XSSFSheet sheet = workbook.createSheet("Reward Employee Sheet");
                if (workbook.getSheet("Reward Employee Sheet") == null) {
                    JOptionPane.showMessageDialog(null, "Sheet không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Write column names to the first row of the sheet
                XSSFRow headerRow = sheet.createRow(0);
                for (int j = 0; j < EmployeesRewardsCriticism.getHeaderReward().size(); j++) {
                    XSSFCell cell = headerRow.createCell(j);
                    cell.setCellValue(EmployeesRewardsCriticism.getHeaderReward().get(j));
                }

                for (int i = 0; i < Redux.employeesRewardsCriticismBUS.getlistEmployeeRC().size() - 1; i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    EmployeesRewardsCriticism employeeRC = Redux.employeesRewardsCriticismBUS.getlistEmployeeRC()
                            .get(i);
                    for (int j = 0; j < EmployeesRewardsCriticism.getHeaderReward().size(); j++) {
                        Object value = employeeRC.getPropertyByIndexReward(j);
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

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập từ khóa tìm kiếm!", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ArrayList<EmployeesRewardsCriticism> searchResult = new ArrayList<>();
        switch (searchOption) {
            case "Tất cả":
                Redux.employeesRewardsCriticismBUS.searchEmployeeCriticismByIDAndName(searchValue);
                searchResult = Redux.employeesRewardsCriticismBUS.getEmployeeCriticismSearchResult();
                break;
            case "Mã NV":
                Redux.employeesRewardsCriticismBUS.searchEmployeeCriticismByID(searchValue);
                searchResult = Redux.employeesRewardsCriticismBUS.getEmployeeCriticismSearchResult();
                break;

            case "Tên NV":
                Redux.employeesRewardsCriticismBUS.searchEmployeeByCriticismName(searchValue);
                searchResult = Redux.employeesRewardsCriticismBUS.getEmployeeCriticismSearchResult();
                break;

            default:
                break;
        }

        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "CẢNH BÁO",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableInit(searchResult);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        searchOptionComboBox = new javax.swing.JComboBox<>();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        importExcel = new javax.swing.JButton();
        tableContainer = new javax.swing.JPanel();
        tableLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        exportExcel = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1055, 640));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        searchOptionComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchOptionComboBox.setForeground(new java.awt.Color(255, 255, 255));
        searchOptionComboBox
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã NV", "Tên NV" }));
        searchOptionComboBox.setName("searchOptionComboBox"); // NOI18N
        searchOptionComboBox.setOpaque(true);

        searchTextField.setBackground(new java.awt.Color(204, 204, 204));
        searchTextField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(0, 51, 51));
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

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refreshButton.setText("Làm Mới");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(searchOptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchButton)
                                .addGap(18, 18, 18)
                                .addComponent(refreshButton)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(searchTextField)
                                        .addComponent(searchOptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        importExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importExcelActionPerformed(evt);
            }
        });

        tableContainer.setBackground(new java.awt.Color(255, 255, 255));
        tableContainer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tableContainer.setName("tableContainer"); // NOI18N
        tableContainer.setPreferredSize(new java.awt.Dimension(863, 364));

        tableLabel.setBackground(new java.awt.Color(255, 255, 255));
        tableLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tableLabel.setForeground(new java.awt.Color(0, 51, 51));
        tableLabel.setText("Danh sách khen thưởng của nhân viên");
        tableLabel.setName("tableLabel"); // NOI18N
        tableLabel.setOpaque(true);

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã Nhân Viên", "Tên Nhân Viên", "Tên Khen Thưởng", "Số Lần", "Tiền Thưởng", "Ngày Tạo"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        updateButton.setText("Sửa");
        updateButton.setIconTextGap(10);
        updateButton.setName("updateButton"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(63, 63, 63)
                                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(importExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(62, 62, 62)
                                                .addComponent(exportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tableContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(96, 96, 96)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(importExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(exportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
    }// </editor-fold>//GEN-END:initComponents

    private void importExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_importExcelActionPerformed
        handleImportExcel();
    }// GEN-LAST:event_importExcelActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshButtonActionPerformed
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Redux.isAdmin && !Redux.isDirector) {
                    tableInit(Redux.employeesRewardsCriticismBUS.getListEmployeeReward());
                } else {
                    tableInit(Redux.employeesRewardsCriticismBUS.getlistEmployeeRC());
                }
                searchTextField.setText("");
            }
        });
    }// GEN-LAST:event_refreshButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        // String searchType = searchOptionComboBox.getSelectedItem().toString();
        // String searchText = searchTextField.getText();
        // ArrayList<EmployeesRewardsCriticism> searchResult =
        // Redux.employeesRewardsCriticismBUS.search(
        // searchType,
        // searchText);
        // tableInit(searchResult);
    }// GEN-LAST:event_searchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exportExcel;
    private javax.swing.JButton importExcel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable jTable1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchOptionComboBox;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel tableContainer;
    private javax.swing.JLabel tableLabel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

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
        } else if (e.getSource() == updateButton) {
            if (selectedRow >= 0) {
                updateTableRow();
                rewardEmployeeForm.setVisible(true);
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

        }
        addButton.setEnabled(true);
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
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this) {
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
