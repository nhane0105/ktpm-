package BackEnd.EmployeesRewardsCriticismManagement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EmployeesRewardsCriticismBUS {
    private ArrayList<EmployeesRewardsCriticism> listEmployeeRC = new ArrayList<>();
    private ArrayList<EmployeesRewardsCriticism> listEmployeeReward = new ArrayList<>();
    private ArrayList<EmployeesRewardsCriticism> listEmployeeCriticism = new ArrayList<>();
    private ArrayList<EmployeesRewardsCriticism> employeeCriticismSearchResult = new ArrayList<>();

    private EmployeesRewardsCriticismDAO employeeRCDAO = new EmployeesRewardsCriticismDAO();

    public EmployeesRewardsCriticismBUS() {
        listEmployeeRC = employeeRCDAO.getAllEmployeesRewardsCriticism();
    }

    public void readDB() {
        listEmployeeRC = employeeRCDAO.getAllEmployeesRewardsCriticism();
    }

    public String getNextID() {
        return "CR" + String.valueOf(this.listEmployeeRC.size() + 1);
    }

    public ArrayList<EmployeesRewardsCriticism> getlistEmployeeRC() {
        return this.listEmployeeRC;
    }

    public void addEmployeesRewardsCriticismExcel(EmployeesRewardsCriticism employeerc) {
        Boolean ok = employeeRCDAO.addEmployeesRewardsCriticism(employeerc);

        if (ok) {
            listEmployeeRC.add(employeerc);
        }
    }

    public EmployeesRewardsCriticism getEmployeesRewardsCriticism(String employeeId, String rewardId,
            String criticismId,
            String createdAt) {
        for (EmployeesRewardsCriticism employeeRC : listEmployeeRC) {
            if (employeeRC.getEmployee().getId().equals(employeeId)
                    && employeeRC.getReward().getRewardId().equals(rewardId)
                    && employeeRC.getCriticism().getCriticismId().equals(criticismId)
                    && employeeRC.getCreatedAt().equals(createdAt)) {
                return employeeRC;
            }
        }
        return null;
    }

    public void addEmployeesRewardsCriticism(EmployeesRewardsCriticism employeesRewardsCriticism) {
        Boolean ok = employeeRCDAO.addEmployeesRewardsCriticism(employeesRewardsCriticism);

        if (ok) {
            listEmployeeRC.add(employeesRewardsCriticism);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateEmployeesRewardsCriticism(EmployeesRewardsCriticism employeesRewardsCriticism) {
        Boolean ok = employeeRCDAO.updateEmployeesRewardsCriticism(employeesRewardsCriticism);

        if (ok) {
            for (int i = 0; i < listEmployeeRC.size(); i++) {
                if (listEmployeeRC.get(i).getEmployee().getId().equals(employeesRewardsCriticism.getEmployee().getId())
                        &&
                        listEmployeeRC.get(i).getReward().getRewardId()
                                .equals(employeesRewardsCriticism.getReward().getRewardId())
                        &&
                        listEmployeeRC.get(i).getCriticism().getCriticismId()
                                .equals(employeesRewardsCriticism.getCriticism().getCriticismId())
                        &&
                        listEmployeeRC.get(i).getCreatedAt().equals(employeesRewardsCriticism.getCreatedAt())) {
                    listEmployeeRC.set(i, employeesRewardsCriticism);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteEmployeesRewardsCriticism(EmployeesRewardsCriticism employeesRewardsCriticism) {
        Boolean ok = employeeRCDAO.deleteEmployeesRewardsCriticism(employeesRewardsCriticism);

        if (ok) {
            for (int i = 0; i < listEmployeeRC.size(); i++) {
                if (listEmployeeRC.get(i).getEmployee().getId().equals(employeesRewardsCriticism.getEmployee().getId())
                        &&
                        listEmployeeRC.get(i).getReward().getRewardId()
                                .equals(employeesRewardsCriticism.getReward().getRewardId())
                        &&
                        listEmployeeRC.get(i).getCriticism().getCriticismId()
                                .equals(employeesRewardsCriticism.getCriticism().getCriticismId())
                        &&
                        listEmployeeRC.get(i).getCreatedAt().equals(employeesRewardsCriticism.getCreatedAt())) {
                    listEmployeeRC.remove(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void getEmployeesRewardsByEmployeeId(String employeeId) {
        listEmployeeReward = employeeRCDAO.getEmployeesRewardsByEmployeeId(employeeId);
    }

    public ArrayList<EmployeesRewardsCriticism> getListEmployeeReward() {
        return this.listEmployeeReward;
    }

    public void getEmployeesCriticismByEmployeeId(String employeeId) {
        listEmployeeCriticism = employeeRCDAO.getEmployeesCriticismByEmployeeId(employeeId);
    }

    public ArrayList<EmployeesRewardsCriticism> getListEmployeeCriticism() {
        return this.listEmployeeCriticism;
    }

    public void searchEmployeeCriticismByIDAndName(String searchValue) {
        // Xóa kết quả tìm kiếm cũ để chuẩn bị cho lần tìm kiếm mới
        employeeCriticismSearchResult.clear();
        // Duyệt qua danh sách các đối tượng EmployeesRewardsCriticism
        for (EmployeesRewardsCriticism employeeCriticism : listEmployeeRC) {
            // Kiểm tra xem ID hoặc tên đầy đủ của nhân viên có chứa giá trị tìm kiếm hay
            // không
            if (employeeCriticism.getEmployee().getId().toLowerCase().contains(searchValue.toLowerCase()) ||
                    employeeCriticism.getEmployee().getFullName().toLowerCase().contains(searchValue.toLowerCase())) {
                // Nếu có, thêm đối tượng này vào kết quả tìm kiếm
                employeeCriticismSearchResult.add(employeeCriticism);
            }
        }
    }

    public void searchEmployeeByCriticismName(String searchValue) {
        employeeCriticismSearchResult.clear();
        for (EmployeesRewardsCriticism employeeCriticism : listEmployeeRC) {
            if (employeeCriticism.getEmployee().getFullName().toLowerCase().contains(searchValue.toLowerCase())) {
                employeeCriticismSearchResult.add(employeeCriticism);
            }
        }
    }

    public void searchEmployeeCriticismByID(String searchValue) {
        employeeCriticismSearchResult.clear();
        for (EmployeesRewardsCriticism employeeCriticism : listEmployeeRC) {
            if (employeeCriticism.getEmployee().getId().toLowerCase().contains(searchValue.toLowerCase())) {
                employeeCriticismSearchResult.add(employeeCriticism);
            }
        }
    }

    public ArrayList<EmployeesRewardsCriticism> getEmployeeCriticismSearchResult() {
        return employeeCriticismSearchResult;
    }

}
