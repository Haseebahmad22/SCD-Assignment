import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Project {
    private List<Task> tasks;
    private List<Resource> resources;

    public Project(List<Task> tasks) {
        this.tasks = tasks;
        this.resources = new ArrayList<>();
    }

    public Project(List<Task> tasks, List<Resource> resources) {
        this.tasks = tasks;
        this.resources = resources;
    }

    // Returns the latest end time among all tasks
    public LocalDateTime getProjectCompletionTime() {
        LocalDateTime latest = null;
        for (Task t : tasks) {
            if (latest == null || t.getEndDate().isAfter(latest)) {
                latest = t.getEndDate();
            }
        }
        return latest;
    }

    // Returns the duration in hours between earliest start and latest end
    public long getProjectDurationInHours() {
        LocalDateTime earliest = null;
        LocalDateTime latest = null;
        for (Task t : tasks) {
            if (earliest == null || t.getStartDate().isBefore(earliest)) {
                earliest = t.getStartDate();
            }
            if (latest == null || t.getEndDate().isAfter(latest)) {
                latest = t.getEndDate();
            }
        }
        if (earliest != null && latest != null) {
            return Duration.between(earliest, latest).toHours();
        }
        return 0;
    }

    // Highlight overlapping tasks
    public List<Task> getOverlappingTasks() {
        List<Task> overlapping = new ArrayList<>();
        for (Task t : tasks) {
            for (Integer depId : t.getDependencies()) {
                Task dep = findTaskById(depId);
                if (dep != null) {
                    // If this task starts before dependency ends and overlaps
                    if (!t.getStartDate().isAfter(dep.getEndDate()) && t.getStartDate().isBefore(dep.getEndDate())) {
                        overlapping.add(t);
                        break;
                    }
                }
            }
        }
        return overlapping;
    }

    // Find resources that form a team for a specific task
    public List<Resource> getTeamForTask(int taskId) {
        List<Resource> team = new ArrayList<>();
        for (Resource r : resources) {
            for (Allocation alloc : r.getAllocations()) {
                if (alloc.getTaskId() == taskId) {
                    team.add(r);
                    break;
                }
            }
        }
        return team;
    }

    // Find total effort (in hours) required by each resource on the project
    public List<String> getTotalEffortByResource() {
        List<String> result = new ArrayList<>();
        for (Resource r : resources) {
            double totalEffort = 0.0;
            for (Allocation alloc : r.getAllocations()) {
                Task t = findTaskById(alloc.getTaskId());
                if (t != null) {
                    long duration = Duration.between(t.getStartDate(), t.getEndDate()).toHours();
                    totalEffort += duration * (alloc.getPercentage() / 100.0);
                }
            }
            result.add(r.getName() + ": " + totalEffort + " hours");
        }
        return result;
    }

    // Helper method to find a task by its id
    private Task findTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
}
