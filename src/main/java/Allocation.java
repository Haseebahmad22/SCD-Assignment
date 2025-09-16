public class Allocation {
    private int taskId;
    private int percentage;

    public Allocation(int taskId, int percentage) {
        this.taskId = taskId;
        this.percentage = percentage;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "taskId=" + taskId +
                ", percentage=" + percentage +
                '}';
    }
}
