package BackEnd.CriticismManagement;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CriticismBUS {
    private ArrayList<Criticism> listcriticism = new ArrayList<>();
    private CriticismDAO criticismDAO = new CriticismDAO();

    public CriticismBUS() {
        listcriticism = criticismDAO.getAllCriticism();
    }

    public void readDB() {
        listcriticism = criticismDAO.getAllCriticism();
    }

    public String getNextID() {
        String lastID = listcriticism.get(listcriticism.size() - 1).getCriticismId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    // public Criticism getCriticism(String criticismId){
    // for (Criticism cr : listcriticism ) {
    // if (cr.getCriticismId().equals(criticismId)) {
    // return cr;
    // }
    // }
    // return null;
    // }

    public ArrayList<Criticism> getlistcriticism() {
        return this.listcriticism;
    }

    public Criticism getCriticismById(String criticismId) {
        for (Criticism cr : listcriticism) {
            if (cr.getCriticismId().equals(criticismId)) {
                return cr;
            }
        }
        return null;
    }

//    public ArrayList<Criticism> search(String type, String value) {
//        ArrayList<Criticism> result = new ArrayList<>();
//
//        listcriticism.forEach((id) -> {
//            if (type.equals("Tất cả")) {
//                if (String.valueOf(id.getCriticismId()).toLowerCase().contains(value.toLowerCase())
//                        || String.valueOf(id.getJudgement()).toLowerCase().contains(value.toLowerCase())) {
//                    result.add(id);
//                }
//            } else {
//                switch (type) {
//
//                    case "Mã kỷ luật":
//                        if (String.valueOf(id.getCriticismId()).toLowerCase().contains(value.toLowerCase())) {
//                            result.add(id);
//                        }
//                        break;
//                    case "Tiền phạt":
//                        if (String.valueOf(id.getJudgement()).toLowerCase().contains(value.toLowerCase())) {
//                            result.add(id);
//                        }
//                        break;
//                }
//            }
//
//        });
//        // Ngay lap, tong tien
//        for (int i = result.size() - 1; i >= 0; i--) {
//            Criticism id = result.get(i);
//        }
//
//        return result;
//    }

    public void addCriticism(Criticism cr) {
        Boolean ok = criticismDAO.addNewCriticism(cr);

        if (ok) {
            listcriticism.add(cr);
        }
    }
     public void addCriticismExcel(Criticism cr) {
        Boolean ok = criticismDAO.addNewCriticism(cr);

        if (ok) {
           listcriticism.add(cr);
        }
    }

    public void updateCriticism(Criticism cr) {
        Boolean ok = criticismDAO.updateCriticism(cr);

        if (ok) {
            for (int i = 0; i < listcriticism.size(); i++) {
                if (listcriticism.get(i).getCriticismId().equals(cr.getCriticismId())) {
                    listcriticism.set(i, cr);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // public void deleteCriticism(Criticism cr) {
    // Boolean ok = criticismDAO.deleteCriticism(cr);
    //
    // if (ok) {
    // for (int i = 0; i < listcriticism.size(); i++) {
    // if (listcriticism.get(i).getCriticismId().equals(cr)) {
    // listcriticism.remove(i);
    // break;
    // }
    // }
    // }
    // }
    public boolean delete(Criticism criticism) {

        // Cố gắng xóa đánh giá từ DAO.
        boolean ok = criticismDAO.deleteCriticism(criticism);

        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }

        return ok;
    }

    public Criticism getCriticismByName(String criticismName) {
        for (Criticism criticism : listcriticism) {
            if (criticism.getCriticismName().equals(criticismName)) {
                return criticism;
            }
        }
        return null;
    }

}
