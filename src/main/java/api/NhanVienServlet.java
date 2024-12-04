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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
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

        // Log body để kiểm tra
        System.out.println("Received POST request with body: " + jsonBody.toString());

        // Parse JSON và thêm nhân viên
        Gson gson = new Gson();
        Employee newEmployee = gson.fromJson(jsonBody.toString(), Employee.class);
        String id = employeeBUS.getNextID();
        newEmployee.setId(id);
        
        boolean isAdded = employeeDAO.addNewEmployee(newEmployee);
        if (isAdded) {
            // Tạo response JSON với message và data
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Employee added successfully!");
            responseMap.put("data", newEmployee);

            String jsonResponse = gson.toJson(responseMap);

            // Trả về response
            resp.getWriter().write(jsonResponse);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Failed to add employee\"}");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Thiết lập kiểu trả về là JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ request body
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

        // Chuyển đổi JSON thành đối tượng Employee
        Gson gson = new Gson();
        Employee employeeToUpdate = gson.fromJson(jsonBody.toString(), Employee.class);

        // Kiểm tra xem Employee có ID hay không
        if (employeeToUpdate.getId() == null || employeeToUpdate.getId().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Missing or invalid employee ID\"}");
            return;
        }

        // Gọi DAO để cập nhật nhân viên
        boolean isUpdated = employeeDAO.updateEmployee(employeeToUpdate);

        // Tạo phản hồi JSON
        Map<String, Object> responseMap = new HashMap<>();
        if (isUpdated) {
            responseMap.put("message", "Employee updated successfully!");
            responseMap.put("data", employeeToUpdate);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            responseMap.put("message", "Failed to update employee");
            responseMap.put("data", null);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // Chuyển đổi map thành JSON và gửi phản hồi
        String jsonResponse = gson.toJson(responseMap);
        PrintWriter out = resp.getWriter();
        out.write(jsonResponse);
        out.flush();
    }



}



