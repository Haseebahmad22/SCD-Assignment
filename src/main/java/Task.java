import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Integer> dependencies;
    public Task() {
        this.dependencies = new ArrayList<>();
    }
    public Task(int id, String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dependencies = new ArrayList<>();
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    // Reads tasks from file and returns a list of Task objects
    public static List<Task> readTasksFromFile(String filePath) {
        List<Task> tasks = new ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd+HHmm");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                LocalDateTime startDate = LocalDateTime.parse(parts[2].trim(), formatter);
                LocalDateTime endDate = LocalDateTime.parse(parts[3].trim(), formatter);
                Task task = new Task(id, title, startDate, endDate);
                // Parse dependencies if present
                for (int i = 4; i < parts.length; i++) {
                    String depStr = parts[i].trim();
                    if (!depStr.isEmpty()) {
                        try {
                            task.getDependencies().add(Integer.parseInt(depStr));
                        } catch (NumberFormatException e) {
                            // Ignore invalid dependency
                        }
                    }
                }
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

}
