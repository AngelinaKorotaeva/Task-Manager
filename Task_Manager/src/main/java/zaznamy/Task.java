package zaznamy;

import enumClass.Priority_task;
import enumClass.Status_task;
import java.time.LocalDate;

public class Task {
    private int id;
    private String name;
    private String description;
    private Priority_task priority;
    private Status_task status;
    private LocalDate deadline;

    public Task(int id, String name, String description, Priority_task priority, Status_task status, LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority_task getPriority() {
        return priority;
    }

    public void setPriority(Priority_task priority) {
        this.priority = priority;
    }

    public Status_task getStatus() {
        return status;
    }

    public void setStatus(Status_task status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + priority + ", " + status + ", " + deadline;
    }
    
}


