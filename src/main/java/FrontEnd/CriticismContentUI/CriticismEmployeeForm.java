package FrontEnd.CriticismContentUI;

import com.github.lgooddatepicker.components.DatePickerSettings;

import BackEnd.CriticismManagement.Criticism;
import BackEnd.EmployeeManagement.Employee;
import BackEnd.EmployeesRewardsCriticismManagement.EmployeesRewardsCriticism;
import BackEnd.RewardManagement.Reward;
import FrontEnd.Redux.Redux;
import FrontEnd.RewardContentUI.RewardEmployeeForm;
import FrontEnd.RewardContentUI.RewardEmployeePanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CriticismEmployeeForm extends javax.swing.JFrame implements ActionListener, WindowListener {

    String criticismId;
    ArrayList<Object> formData;

    public CriticismEmployeeForm() {
        initComponents();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        formInit();
        DatePickerSettings pickerSettingsBegin = new DatePickerSettings();
        pickerSettingsBegin.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDatePicker.setSettings(pickerSettingsBegin);
        startDatePicker.setDateToToday();

        // Get the default editor associated with the spinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) countSpinner.getEditor();
        countSpinner.setModel(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));

        // Access the text field component within the editor
        JTextField textField = editor.getTextField();

        // Set the foreground color (text color) of the text field
        textField.setForeground(Color.WHITE); // Set your desired color

        countSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedValue = (int) countSpinner.getValue();
                // Perform actions based on the selected value
                if (selectedValue == 0) {
                    // Handle case when selected value is 0
                    JOptionPane.showMessageDialog(CriticismEmployeeForm.this,
                            "Số lần bằng 0 dữ liệu sẽ chuyển về dạng thích hợp!", "CẢNH BÁO",
                            JOptionPane.WARNING_MESSAGE);
                } else if (selectedValue > 0) {
                    // Handle case when selected value is greater than 0
                    int money = Redux.criticismBUS.getCriticismById(criticismId).getJudgement() * selectedValue;
                    moneyTextField.setText(NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                            .setRegion("DE").build())
                            .format(money)
                            + " VNĐ");
                }
            }
        });

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        addWindowListener(this);
    }

    public void formInit() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
            if (!employee.getDeleteStatus()) {
                model.addElement(employee.getId());
            }
        }
        employeeIDComboBox.setModel(model);
        employeeIDComboBox.setSelectedIndex(0);
        employeeNameTextField.setText(Redux.employeeBUS.getEmployeeById((String) employeeIDComboBox.getSelectedItem())
                .getFullName());

        if (model.getSize() == 1) {
            String selectedEmployeeId = (String) model.getElementAt(0);
            for (Employee employee : Redux.employeeBUS.getEmployeeList()) {
                if (employee.getId().equalsIgnoreCase(selectedEmployeeId)) {
                    employeeNameTextField.setText(employee.getFullName());
                    break; // Break the loop once found
                }
            }
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

        DefaultComboBoxModel<String> criticismModel = new DefaultComboBoxModel<>();
        for (Criticism criticism : Redux.criticismBUS.getlistcriticism()) {
            if (!criticism.getDeleteStatus()) {
                criticismModel.addElement(criticism.getCriticismId());
            }
        }
        criticsimIDComboBox.setModel(criticismModel);
        criticsimIDComboBox.setSelectedIndex(0);
        criticismId = (String) criticsimIDComboBox.getSelectedItem();
        criticismNameTextField
                .setText(Redux.criticismBUS.getCriticismById((String) criticsimIDComboBox.getSelectedItem())
                        .getCriticismName());

        moneyTextField
                .setText(NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                        .setRegion("DE").build())
                        .format(Redux.criticismBUS.getCriticismById((String) criticsimIDComboBox.getSelectedItem())
                                .getJudgement())
                        + " VNĐ");

        if (criticismModel.getSize() == 1) {
            String selectedRewardId = (String) criticismModel.getElementAt(0);
            for (Criticism criticism : Redux.criticismBUS.getlistcriticism()) {
                if (criticism.getCriticismId().equalsIgnoreCase(selectedRewardId)) {
                    criticismId = criticism.getCriticismId();
                    criticismNameTextField.setText(criticism.getCriticismName());
                    break; // Break the loop once found
                }
            }
        }

        criticsimIDComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedEmployeeId = (String) criticsimIDComboBox.getSelectedItem();
                for (Criticism criticism : Redux.criticismBUS.getlistcriticism()) {
                    if (criticism.getCriticismId().equalsIgnoreCase(selectedEmployeeId)) {
                        criticismId = criticism.getCriticismId();
                        criticismNameTextField.setText(criticism.getCriticismName());
                        moneyTextField.setText(NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                                .setRegion("DE").build())
                                .format(criticism.getJudgement())
                                + " VNĐ");
                    }
                }
            }
        });
    }

    // public void showFormWithData(ArrayList<Object> data) {
    // if (data != null) {
    // employeeIDComboBox.setSelectedItem(data.get(1));
    // criticsimIDComboBox.setSelectedItem(data.get(3));
    // criticismNameTextField.setText((String) data.get(4));
    // countSpinner.setValue(data.get(5));
    // moneyTextField.setText(Integer.toString((int) data.get(6)));
    // startDatePicker.setText((String) data.get(7));
    // }
    // }
    public void showFormWithData(ArrayList<Object> data) {
        if (data != null) {
            employeeIDComboBox.setSelectedItem(data.get(1));
            employeeNameTextField.setText((String) data.get(2));
            criticsimIDComboBox
                    .setSelectedItem(Redux.criticismBUS.getCriticismByName((String) data.get(3)).getCriticismId());
            criticismNameTextField.setText((String) data.get(3));
            countSpinner.setValue(data.get(4));
            moneyTextField.setText(NumberFormat.getInstance(new Locale.Builder().setLanguage("de")
                    .setRegion("DE").build())
                    .format(Integer.parseInt((String) data.get(5)))
                    + " VNĐ");
            startDatePicker.setText((String) data.get(6)); // Giả sử định dạng ngày là "yyyy-MM-dd"

        }
    }

    public void clearFormData() {
        employeeIDComboBox.setSelectedItem("");
        employeeNameTextField.setText("");
        criticsimIDComboBox.setSelectedItem("");
        criticismNameTextField.setText("");
        countSpinner.setValue(1);
        moneyTextField.setText("");
        startDatePicker.setText("");
    }

    public ArrayList<Object> getDataFormForm() {
        String employeeID = (String) employeeIDComboBox.getSelectedItem(),
                employeeName = employeeNameTextField.getText(),
                criticismId = (String) criticsimIDComboBox.getSelectedItem(),
                criticismName = criticismNameTextField.getText(),
                startDate = Employee.formatBirthDateToDatabaseType(startDatePicker.getText());
        int money = Integer.parseInt(moneyTextField.getText().toString().replace(" VNĐ", "").replace(".", "")),
                faultCount = (int) countSpinner.getValue();
        return new ArrayList<>(Arrays.asList(employeeID, employeeName, criticismId, criticismName, faultCount,
                startDate, money));
    }

    public void handleSubmitForm() {
        formData = getDataFormForm();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thêm mới dữ liệu khen thưởng nhân viên với ID " + formData.get(0) + " ?",
                "XÁC NHẬN ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (this.getTitle().contains("THÊM MỚI")) {
                Redux.employeesRewardsCriticismBUS.addEmployeesRewardsCriticism(
                        new EmployeesRewardsCriticism(Redux.employeeBUS.getEmployeeById((String) formData.get(0)),
                                new Reward(), 0,
                                Redux.criticismBUS.getCriticismById((String) formData.get(2)),
                                (int) formData.get(4), (String) formData.get(5)));
            } else {
                Redux.employeesRewardsCriticismBUS.updateEmployeesRewardsCriticism(
                        new EmployeesRewardsCriticism(Redux.employeeBUS.getEmployeeById((String) formData.get(0)),
                                new Reward(), 0,
                                Redux.criticismBUS.getCriticismById((String) formData.get(2)),
                                (int) formData.get(4), (String) formData.get(5)));
            }
            Redux.employeesRewardsCriticismBUS.readDB();
            CriticismEmployeePanel.tableInit(Redux.employeesRewardsCriticismBUS.getlistEmployeeRC());
            dispose();
        }
    }

    public void cancelSubmitForm() {

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Xác nhận thao tác ?",
                "HỦY BỎ ?",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            clearFormData();
            dispose();
        }
    }

    public boolean isFormFilled() {
        return !(criticismNameTextField.getText().equals("")
                || startDatePicker.getText().equals("")
                || ((int) countSpinner.getValue() == 0)
                || moneyTextField.getText().equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            if (isFormFilled()) {
                handleSubmitForm();
            } else {
                JOptionPane.showMessageDialog(this, "Hãy nhập thông tin trước!", "CẢNH BÁO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            if (isFormFilled()) {
                cancelSubmitForm();
            } else {
                clearFormData();
                dispose();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        startDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        criticismIDLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        criticsimIDComboBox = new javax.swing.JComboBox<>();
        criticismNameLabel = new javax.swing.JLabel();
        employeeIDComboBox = new javax.swing.JComboBox<>();
        criticismNameTextField = new javax.swing.JTextField();
        startDatePickerLabel = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        employeeIDLabel = new javax.swing.JLabel();
        countSpinner = new javax.swing.JSpinner();
        moneyLabel = new javax.swing.JLabel();
        moneyTextField = new javax.swing.JTextField();
        employeeNameLabel = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        startDatePicker.setForeground(new java.awt.Color(0, 51, 51));
        startDatePicker.setName("startDatePicker"); // NOI18N

        criticismIDLabel.setLabelFor(criticsimIDComboBox);
        criticismIDLabel.setText("Mã Kỷ Luật :");
        criticismIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        criticismIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        criticismIDLabel.setForeground(new java.awt.Color(0, 51, 51));
        criticismIDLabel.setName("criticismIDLabel"); // NOI18N
        criticismIDLabel.setOpaque(true);

        cancelButton.setText("Hủy Bỏ");
        cancelButton.setBackground(new java.awt.Color(108, 117, 125));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setOpaque(true);
        cancelButton.setToolTipText("cancelButton");

        criticsimIDComboBox.setEditable(true);
        criticsimIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        criticsimIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        criticsimIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        criticsimIDComboBox.setForeground(new java.awt.Color(0, 51, 51));
        criticsimIDComboBox.setName("criticsimIDComboBox"); // NOI18N
        criticsimIDComboBox.setOpaque(true);

        criticismNameLabel.setLabelFor(criticismNameTextField);
        criticismNameLabel.setText("Tên Kỷ Luật :");
        criticismNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        criticismNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        criticismNameLabel.setForeground(new java.awt.Color(0, 51, 51));
        criticismNameLabel.setName("criticismNameLabel"); // NOI18N
        criticismNameLabel.setOpaque(true);

        employeeIDComboBox.setEditable(true);
        employeeIDComboBox.setBackground(new java.awt.Color(204, 204, 204));
        employeeIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeIDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeIDComboBox.setForeground(new java.awt.Color(0, 51, 51));
        employeeIDComboBox.setName("employeeIDComboBox"); // NOI18N
        employeeIDComboBox.setOpaque(true);

        criticismNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        criticismNameTextField.setEnabled(false);
        criticismNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        criticismNameTextField.setName("criticismNameTextField"); // NOI18N
        criticismNameTextField.setOpaque(true);

        startDatePickerLabel.setText("Ngày Tạo :");
        startDatePickerLabel.setBackground(new java.awt.Color(255, 255, 255));
        startDatePickerLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        startDatePickerLabel.setForeground(new java.awt.Color(0, 51, 51));
        startDatePickerLabel.setOpaque(true);
        startDatePickerLabel.setToolTipText("startDatePickerLabel");

        countLabel.setLabelFor(countSpinner);
        countLabel.setText("Số Lần :");
        countLabel.setBackground(new java.awt.Color(255, 255, 255));
        countLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        countLabel.setForeground(new java.awt.Color(0, 51, 51));
        countLabel.setName("countLabel"); // NOI18N
        countLabel.setOpaque(true);

        employeeIDLabel.setText("Mã Nhân Viên :");
        employeeIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeIDLabel.setForeground(new java.awt.Color(0, 51, 51));
        employeeIDLabel.setName("employeeIDLabel"); // NOI18N
        employeeIDLabel.setOpaque(true);

        countSpinner.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        countSpinner.setOpaque(true);

        moneyLabel.setText("Tiền Phạt :");
        moneyLabel.setBackground(new java.awt.Color(255, 255, 255));
        moneyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        moneyLabel.setForeground(new java.awt.Color(0, 51, 51));
        moneyLabel.setName("moneyLabel"); // NOI18N
        moneyLabel.setOpaque(true);

        moneyTextField.setBackground(new java.awt.Color(204, 204, 204));
        moneyTextField.setEnabled(false);
        moneyTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        moneyTextField.setName("moneyTextField"); // NOI18N
        moneyTextField.setOpaque(true);

        employeeNameLabel.setText("Tên Nhân Viên :");
        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        employeeNameLabel.setForeground(new java.awt.Color(0, 0, 0));

        employeeNameTextField.setBackground(new java.awt.Color(204, 204, 204));
        employeeNameTextField.setEnabled(false);
        employeeNameTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeNameTextField.setForeground(new java.awt.Color(0, 0, 0));

        confirmButton.setBackground(new java.awt.Color(13, 110, 253));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Xác Nhận");
        confirmButton.setName("confirmButton"); // NOI18N
        confirmButton.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(criticismIDLabel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(criticismNameLabel,
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(employeeNameLabel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                150,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(criticsimIDComboBox, 0,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(criticismNameTextField)
                                                                        .addComponent(employeeNameTextField)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(startDatePickerLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 221,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        69, Short.MAX_VALUE)
                                                                .addComponent(countLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(moneyLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 150,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(moneyTextField))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(startDatePicker,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                221,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(countSpinner,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                250,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(employeeIDLabel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 150,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(employeeIDComboBox, 0,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)))))
                                .addGap(30, 30, 30)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(employeeIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40,
                                                Short.MAX_VALUE)
                                        .addComponent(employeeIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(employeeNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(employeeNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 40,
                                                Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(criticismIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(criticsimIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(criticismNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(criticismNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startDatePickerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(countLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(moneyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(moneyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel countLabel;
    private javax.swing.JSpinner countSpinner;
    private javax.swing.JLabel criticismIDLabel;
    private javax.swing.JLabel criticismNameLabel;
    private javax.swing.JTextField criticismNameTextField;
    private javax.swing.JComboBox<String> criticsimIDComboBox;
    private javax.swing.JComboBox<String> employeeIDComboBox;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JLabel employeeNameLabel;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel moneyLabel;
    private javax.swing.JTextField moneyTextField;
    private com.github.lgooddatepicker.components.DatePicker startDatePicker;
    private javax.swing.JLabel startDatePickerLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        clearFormData();
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
