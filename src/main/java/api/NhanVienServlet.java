/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 *
 * @author MaiTrinh
 */

import BackEnd.EmployeeManagement.Employee;
import BackEnd.EmployeeManagement.EmployeeBUS;
import BackEnd.EmployeeManagement.EmployeeDAO;
import static FrontEnd.Redux.Redux.employeeBUS;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class NhanVienServlet extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();  // DAO lấy dữ liệu nhân viên từ DB
    
    @Override
    public void init() throws ServletException {
        super.init();
        employeeBUS = new EmployeeBUS();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();  // Lấy phần URL sau "/api/nhanvien"
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Lấy tất cả nhân viên
            getAllEmployees(resp);
        } else {
            // Lấy nhân viên theo ID (ví dụ: /api/nhanvien/E001)
            getEmployeeById(pathInfo.substring(1), resp);  // Lấy phần ID sau dấu "/"
        }
    }

    // Lấy tất cả nhân viên
    private void getAllEmployees(HttpServletResponse resp) throws IOException {
        ArrayList<Employee> employeeList = employeeDAO.getAllEmployee();

        // Tạo response JSON với message và data
        Map<String, Object> responseMap = new HashMap<>();
        if (employeeList.isEmpty()) {
            responseMap.put("message", "No employees found.");
            responseMap.put("data", new ArrayList<>()); // Trả về danh sách rỗng
        } else {
            responseMap.put("message", "Employees retrieved successfully!");
            responseMap.put("data", employeeList);
        }

        // Thiết lập kiểu trả về là JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Chuyển đổi Map thành JSON
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseMap);

        // Gửi JSON về cho client
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }


    private void getEmployeeById(String employeeId, HttpServletResponse resp) throws IOException {
        // Thiết lập kiểu trả về là JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Kiểm tra nếu employeeId bị thiếu hoặc rỗng
        if (employeeId == null || employeeId.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Missing employee ID");
            errorResponse.put("data", null);

            Gson gson = new Gson();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(errorResponse));
            return;
        }

        // Lấy nhân viên theo ID từ cơ sở dữ liệu
        Employee employee = employeeDAO.getEmployeeById(employeeId);

        // Tạo response
        Map<String, Object> responseMap = new HashMap<>();
        if (employee == null) {
            responseMap.put("message", "Employee not found");
            responseMap.put("data", null);

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            responseMap.put("message", "Employee retrieved successfully!");
            responseMap.put("data", employee);

            resp.setStatus(HttpServletResponse.SC_OK);
        }

        // Chuyển response thành JSON và gửi về client
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseMap);
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    
    // Thêm mới nhân viên
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Đọc body từ request
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        if (jsonBody.toString().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Request body is empty!\"}");
            return;
        }

        // Parse JSON thành đối tượng Employee
        Gson gson = new Gson();
        Employee newEmployee = gson.fromJson(jsonBody.toString(), Employee.class);

        // Kiểm tra các trường dữ liệu hợp lệ
        Map<String, String> errors = validateEmployee(newEmployee);
        if (employeeDAO.isPhoneNumberExist(newEmployee.getPhoneNumber())) {
            errors.put("PhoneNumber", "Phone number existing!");
        }
        if (!errors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String errorResponse = gson.toJson(errors);
            resp.getWriter().write("{\"message\": \"Validation failed\", \"errors\": " + errorResponse + "}");
            return;
        }

        // Gán ID mới cho nhân viên
        String id = employeeBUS.getNextID();
        System.out.println("next id: " + id);
        newEmployee.setId(id);

        // Thêm nhân viên vào cơ sở dữ liệu
        boolean isAdded = employeeDAO.addNewEmployee(newEmployee);
        if (isAdded) {
            // Tạo response JSON với message và data
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Employee added successfully!");
            responseMap.put("data", newEmployee);

            String jsonResponse = gson.toJson(responseMap);
            resp.getWriter().write(jsonResponse);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Failed to add employee\"}");
        }
    }

    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        StringBuilder jsonBody = new StringBuilder();

        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Failed to read request body\"}");
            return;
        }

        if (jsonBody.toString().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Request body is empty!\"}");
            return;
        }

        Employee employeeToUpdate;
        try {
            employeeToUpdate = gson.fromJson(jsonBody.toString(), Employee.class);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Invalid JSON format\"}");
            return;
        }

        if (employeeToUpdate.getId() == null || employeeToUpdate.getId().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Missing or invalid employee ID\"}");
            return;
        }

        Employee existingEmployee = employeeBUS.getEmployeeById(employeeToUpdate.getId());
        if (existingEmployee == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"message\": \"Employee not found\"}");
            return;
        }

        Map<String, String> errors = validateEmployee(employeeToUpdate);
        if (existingEmployee.getPhoneNumber() != null && !existingEmployee.getPhoneNumber().equals(employeeToUpdate.getPhoneNumber())) {
            if (employeeDAO.isPhoneNumberExist(employeeToUpdate.getPhoneNumber())) {
                errors.put("PhoneNumber", "Phone number already exists!");
            }
        }

        if (!errors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String errorResponse = gson.toJson(errors);
            resp.getWriter().write("{\"message\": \"Validation failed\", \"errors\": " + errorResponse + "}");
            return;
        }

        try {
            boolean isUpdated = employeeDAO.updateEmployee(employeeToUpdate);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Employee updated successfully!");
                responseMap.put("data", employeeToUpdate);
                String jsonResponse = gson.toJson(responseMap);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"message\": \"Failed to update employee\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"An unexpected error occurred\"}");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Lấy mã nhân viên từ URL
        String pathInfo = req.getPathInfo(); // Ví dụ: "/EM045"
        String employeeId = null;
        if (pathInfo != null && pathInfo.startsWith("/")) {
            employeeId = pathInfo.substring(1); // Bỏ dấu "/"
        }

        // Kiểm tra mã nhân viên
        if (employeeId == null || employeeId.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Employee ID cannot be null\"}");
            return;
        }
        Employee employee = employeeBUS.getEmployeeById(employeeId);
        // Kiểm tra xem nhân viên có tồn tại không
        if (employee == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"message\": \"Employee ID not found\"}");
            return;
        }

        // Thực hiện xóa nhân viên
        boolean isDeleted = employeeBUS.deleteEmployeeFromDB(employee);
        if (isDeleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Employee deleted successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Failed to delete employee\"}");
        }
    }


    // Hàm kiểm tra định dạng mã nhân viên (tùy chỉnh theo yêu cầu)
    private boolean isValidEmployeeId(String employeeId) {
        // Ví dụ: mã nhân viên phải là số hoặc theo regex cụ thể
        return employeeId.matches("\\d+"); // Chỉ chứa số
    }


    private Map<String, String> validateEmployee(Employee employee) {
        Map<String, String> errors = new HashMap<>();

        if (employee.getFullName() == null || employee.getFullName().trim().isEmpty()) {
            errors.put("fullName", "Full name is required");
        }

        if (employee.getGender() == null || (!employee.getGender().equalsIgnoreCase("Nam") && !employee.getGender().equalsIgnoreCase("Nữ"))) {
            errors.put("gender", "Gender must be 'Nam' or 'Nữ'");
        }

        if (employee.getBirthDate() == null || employee.getBirthDate().isEmpty()) {
            errors.put("birthDate", "Birth date is required");
        } else {
            try {
                LocalDate.parse(employee.getBirthDate());
            } catch (DateTimeParseException e) {
                errors.put("birthDate", "Birth date is invalid (format: YYYY-MM-DD)");
            }
        }

        if (employee.getPhoneNumber() == null || !employee.getPhoneNumber().matches("\\d{10}")) {
            errors.put("phoneNumber", "Phone number must be 10 digits");
        }

        if (employee.getDegree() == null || employee.getDegree().getDegreeId() == null || employee.getDegree().getDegreeId().isEmpty()) {
            errors.put("degree", "Degree ID is required");
        }

        if (employee.getPosition() == null || employee.getPosition().getPositionId() == null || employee.getPosition().getPositionId().isEmpty()) {
            errors.put("position", "Position ID is required");
        }

        // Thêm các kiểm tra khác nếu cần
        return errors;
    }

}



