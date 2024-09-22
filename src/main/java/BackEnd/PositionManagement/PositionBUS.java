package BackEnd.PositionManagement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PositionBUS {

    private ArrayList<Position> positionList = new ArrayList<>();
    private PositionDAO positionDAO = new PositionDAO();

    public PositionBUS() {
        positionList = positionDAO.getAllPosition();
    }

    public ArrayList<Position> getPositionList() {
        return positionList;
    }

    public void readDB() {
        positionList = positionDAO.getAllPosition();
    }

    public String getNextID() {
        String lastID = positionList.get(positionList.size() - 1).getPositionId();
        String characterPart = lastID.substring(0, 2);
        int numberPart = Integer.parseInt(lastID.substring(2));
        numberPart++;
        String nextID = characterPart + String.format("%03d", numberPart);
        return nextID;
    }

    public Position getPositionById(String positionId) {
        for (Position position : positionList) {
            if (position.getPositionId().equals(positionId)) {
                return position;
            }
        }
        return null;
    }

    public Position getPositionByName(String positionName) {
        for (Position position : positionList) {
            if (position.getPositionName().equalsIgnoreCase(positionName)) {
                return position;
            }
        }
        return null;
    }

    public void addPosition(Position position) {
        Boolean ok = positionDAO.addNewPosition(position);

        if (ok) {
            positionList.add(position);
            JOptionPane.showMessageDialog(null, "Thêm mới thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updatePosition(Position position) {
        Boolean ok = positionDAO.updatePosition(position);

        if (ok) {
            for (int i = 0; i < positionList.size(); i++) {
                if (positionList.get(i).getPositionId().equals(position.getPositionId())) {
                    positionList.set(i, position);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deletePosition(Position position) {
        Boolean ok = positionDAO.deletePosition(position);

        if (ok) {
            this.readDB();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
