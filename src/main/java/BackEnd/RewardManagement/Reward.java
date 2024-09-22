package BackEnd.RewardManagement;

public class Reward {

    private String rewardId, rewardName;
    private int reward;
    private boolean deleteStatus;

    public Reward(String rewardId, String rewardName, int reward) {
        this.rewardId = rewardId;
        this.rewardName = rewardName;
        this.reward = reward;
        this.deleteStatus = false;
    }

    public Reward() {
        this.rewardId = "RE001";
        this.rewardName = "Không có thưởng";
        this.reward = 0;
        this.deleteStatus = false;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
