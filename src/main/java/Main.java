import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Read tasks from file
        List<Task> tasks = Task.readTasksFromFile("src/main/resources/tasks.txt");
        // Read resources from file
        List<Resource> resources = Resource.readResourcesFromFile("src/main/resources/resources.txt");
        // Create project
        Project project = new Project(tasks, resources);

        // Print project completion time and duration
        System.out.println("Project Completion Time: " + project.getProjectCompletionTime());
        System.out.println("Project Duration (hours): " + project.getProjectDurationInHours());

        // Print overlapping tasks
        System.out.println("Overlapping Tasks:");
        for (Task t : project.getOverlappingTasks()) {
            System.out.println("Task ID: " + t.getId() + ", Title: " + t.getTitle());
        }

        // Print team for each task
        System.out.println("\nTeams for each task:");
        for (Task t : tasks) {
            List<Resource> team = project.getTeamForTask(t.getId());
            System.out.print("Task ID " + t.getId() + ": ");
            for (Resource r : team) {
                System.out.print(r.getName() + " ");
            }
            System.out.println();
        }

        // Print total effort by resource
        System.out.println("\nTotal Effort by Resource:");
        for (String s : project.getTotalEffortByResource()) {
            System.out.println(s);
        }
    }
}
