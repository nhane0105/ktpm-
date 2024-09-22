package BackEnd.ProjectsManagement;

import com.github.lgooddatepicker.components.DatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import javax.swing.JOptionPane;

public class ProjectBUS {
    private ArrayList<Project> list = new ArrayList<>();
    private ProjectDAO pjD = new ProjectDAO();
    LocalDate endTime;

    public ProjectBUS() {
        list = pjD.getAllProject();
    }

    public void readProject() {
        list = pjD.getAllProject();
    }

   
    public ArrayList<Project> getProjectList() {
        return list;
    }

    public String getNextID() {
        String lastID = list.get(list.size()-1).getProjectId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Project getProjectById(String projectId) {
        for (Project pj : list) {
            if (pj.getProjectId().equalsIgnoreCase(projectId)) {
                return pj;
            }
        }
        return null;
    }

    public void addProject(Project pj) {
        Boolean ok = pjD.addNewProject(pj);

        if (ok) {
            list.add(pj);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateProject(Project pj) {
        Boolean ok = pjD.updateProject(pj);

        if (ok) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProjectId().equals(pj.getProjectId())) {
                    list.set(i, pj);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteProject(String pj) {
        Boolean ok = pjD.deleteProject(pj);

        if (ok) {
            this.readProject();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Project searchInProject(String pjId) {
        if (pjD.searchInProject(pjId) != null) {
            return pjD.searchInProject(pjId);
        }
        return null;
    }

}
