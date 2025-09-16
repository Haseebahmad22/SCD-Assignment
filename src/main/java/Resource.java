import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Resource {
    private String name;
    private List<Allocation> allocations;

    public Resource(String name, List<Allocation> allocations) {
        this.name = name;
        this.allocations = allocations;
    }

    // Function to read resources and allocations from file
    public static List<Resource> readResourcesFromFile(String filePath) {
        List<Resource> resources = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0].trim();
                List<Allocation> allocations = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    String allocStr = parts[i].trim();
                    if (!allocStr.isEmpty()) {
                        String[] allocParts = allocStr.split(":");
                        if (allocParts.length == 2) {
                            int taskId = Integer.parseInt(allocParts[0]);
                            int percentage = Integer.parseInt(allocParts[1]);
                            allocations.add(new Allocation(taskId, percentage));
                        }
                    }
                }
                resources.add(new Resource(name, allocations));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    public String getName() {
        return name;
    }
    public List<Allocation> getAllocations() {
        return allocations;
    }
}
