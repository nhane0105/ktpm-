package BackEnd.SpecialtyManagement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SpecialtyBUS {

    private ArrayList<Specialty> specialtyList = new ArrayList<>();
    private SpecialtyDAO specialtyDAO = new SpecialtyDAO();

    public SpecialtyBUS() {
        specialtyList = specialtyDAO.getAllSpecialty();
    }

    public ArrayList<Specialty> getSpecialtyList() {
        return specialtyList;
    }

    public void readDB() {
        specialtyList = specialtyDAO.getAllSpecialty();
    }

    public String getNextID() {
        String lastID = specialtyList.get(specialtyList.size() - 1).getSpecialtyId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Specialty getSpecialtyById(String specialtyId) {
        for (Specialty specialty : specialtyList) {
            if (specialty.getSpecialtyId().equals(specialtyId)) {
                return specialty;
            }
        }
        return null;
    }

    public Specialty getSpecialtyByName(String specialtyName) {
        for (Specialty specialty : specialtyList) {
            if (specialty.getSpecialtyName().equalsIgnoreCase(specialtyName)) {
                return specialty;
            }
        }
        return null;
    }

    public void addSpecialty(Specialty specialty) {
        Boolean ok = specialtyDAO.addNewSpecialty(specialty);

        if (ok) {
            specialtyList.add(specialty);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateSpecialty(Specialty specialty) {
        Boolean ok = specialtyDAO.updateSpecialty(specialty);

        if (ok) {
            for (int i = 0; i < specialtyList.size(); i++) {
                if (specialtyList.get(i).getSpecialtyId().equals(specialty.getSpecialtyId())) {
                    specialtyList.set(i, specialty);
                    break;
                }
            }

            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteSpecialty(Specialty specialty) {
        Boolean ok = specialtyDAO.deleteSpecialty(specialty);

        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
