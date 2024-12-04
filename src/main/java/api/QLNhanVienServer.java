/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 *
 * @author MaiTrinh
 */
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class QLNhanVienServer {
    public static void main(String[] args) throws Exception {
        // Tạo một server và thiết lập cổng 8080
        Server server = new Server(8081);

        // Tạo một handler để xử lý request
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // Tạo một ServletHolder và ánh xạ servlet
        ServletHolder servletHolder = new ServletHolder(new NhanVienServlet());
        handler.addServletWithMapping(servletHolder, "/api/nhanvien/*");

        // Khởi động server
        server.start();
        server.join();
    }
}



