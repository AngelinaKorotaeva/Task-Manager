package taskDAO;

import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import zaznamy.Task;

public class TaskDAO {
    
    public void addTask(Task task) {
        String sql = """
            INSERT INTO tasks(name, description, priority, status, deadline)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getPriority().name());
            ps.setString(4, task.getStatus().name());
            ps.setString(5, task.getDeadline().toString());
            
            ps.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
