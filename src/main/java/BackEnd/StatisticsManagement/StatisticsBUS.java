package BackEnd.StatisticsManagement;

import java.util.ArrayList;

public class StatisticsBUS {
    private StatisticsDAO statisticsDAO = new StatisticsDAO();
    private ArrayList<Object> listGender = new ArrayList<>();
    private ArrayList<Object> listTop5HighestSalaryEmployees = new ArrayList<>();
    private ArrayList<Object> listDegree = new ArrayList<>();

    public StatisticsBUS() {
        listGender = statisticsDAO.getEmployeeGender();
        listTop5HighestSalaryEmployees = statisticsDAO.getTop5HighestSalaryEmployees();
        listDegree = statisticsDAO.getEmployeeDegree();
    }

    public void readDB() {
        listGender = statisticsDAO.getEmployeeGender();
        listTop5HighestSalaryEmployees = statisticsDAO.getTop5HighestSalaryEmployees();
        listDegree = statisticsDAO.getEmployeeDegree();
    }

    public ArrayList<Object> getEmployeeGender() {
        return listGender;
    }

    public ArrayList<Object> getTop5HighestSalaryEmployees() {
        return listTop5HighestSalaryEmployees.size() > 0 ? listTop5HighestSalaryEmployees : null;
    }

    public ArrayList<Object> getEmployeeDegree() {
        return listDegree;
    }
}
