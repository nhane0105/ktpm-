package BackEnd.AssignmentManagement;

import BackEnd.EmployeeManagement.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AssignmentBUS {
    private EmployeeBUS emB = new EmployeeBUS();
    private AssignmentDAO asmD = new AssignmentDAO();
    private ArrayList<Assignment> list = new ArrayList<>();
    private ArrayList<Assignment> employeeAssignmentList = new ArrayList<>();
    private ArrayList<Assignment> assignmentSearchresult = new ArrayList<>();

    public AssignmentBUS() {
        list = asmD.getAllAssignments();
    }

    public ArrayList<Assignment> getAssignmentsList() {
        return list;
    }

    public String getNextProjectID() {
        String lastID = list.get(list.size() - 1).getProjectId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Assignment getAssignmentByEmployeeId(String employeeId) {
        for (Assignment it : list) {
            if (it.getEmployee().getId().equals(employeeId))
                return it;
        }
        return null;
    }

    public Assignment getAssignmentByProjectId(String projectId) {
        for (Assignment it : list) {
            if (it.getProject().getProjectId().equals(projectId))
                return it;
        }
        return null;
    }

    public void searchAssignmentByName(String name){
        assignmentSearchresult.clear();
        for(Assignment asm : list){
            if(asm.getEmployee().getFullName().toLowerCase().contains(name.toLowerCase())){
                System.out.println(asm.getEmployee().getFullName());
                assignmentSearchresult.add(asm);
            }
        }
    }

    public void searchAssignmentByProjectId(String projectId){
        assignmentSearchresult.clear();
        for(Assignment asm : list){
            if(asm.getProject().getProjectId().toLowerCase().contains(projectId.toLowerCase())){
                System.out.println(asm.getProject().getProjectId());
                assignmentSearchresult.add(asm);
            }
        }
    }
    public void addNewAssignment(Assignment asm) {
        boolean flag = asmD.addNewAssignment(asm);
        if (flag) {
            list.add(asm);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showConfirmDialog(null, "Lỗi thêm mới phân công !","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void addAssignmentExcel(Assignment asm) {
        Boolean ok = asmD.addNewAssignment(asm);

        if (ok) {
            list.add(asm);
        }
    }

    public void updateAssignment(ArrayList<Object> prevState, Assignment newasm) {
       
        boolean flag = asmD.updateAssignment(prevState, newasm);
        if(flag){
            for(int i = 0 ; i < list.size() ; i++){
                if(prevState.get(1).toString().equals(list.get(i).getEmployee().getId()) && prevState.get(3).toString().equals(list.get(i).getProject().getProjectId())){
                    list.set(i, newasm);
                }
            }
        }  
    }

    public void deleteAssignment(Assignment asm) {
        if (asmD.deleteAssignment(asm)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProject().getProjectId().equals(asm.getProject().getProjectId())
                        && list.get(i).getEmployee().getId().equals(asm.getEmployee().getId())) {
                    list.remove(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean checkInList(String employeeID, String projectId) {
        for (Assignment it : list) {
            if (it.getEmployee().getId().equalsIgnoreCase(employeeID)
                    && it.getProject().getProjectId().equalsIgnoreCase(projectId)) {
                return true;
            }
        }
        return false;
    }
  
    public Assignment findAssignmentByEmployeeIdAndProjectId(String employeeId, String projectId) {
        return asmD.findAssignmentByEmployeeIdAndProjectId(employeeId, projectId);
    }

    public void getEmployeeAssignmentsByEmployeeId(String employeeId) {
        employeeAssignmentList = asmD.getEmployeeAssignmentsByEmployeeId(employeeId);
    }

    public ArrayList<Assignment> getEmployeeAssignmentList() {
        return employeeAssignmentList;
    }

    public ArrayList<Assignment> getAssignmentResultSearch(){
        return assignmentSearchresult;
    }
}
