package spravaZaznamu;

import java.util.ArrayList;
import java.util.List;
import zaznamy.Task;

public class Sprava implements ISprava{
    List<Task> taskList;

    public Sprava() {
        ObjectNotNull(taskList);
        this.taskList = new ArrayList<>();
    }

    @Override
    public Task addTask(Task task) {
        ObjectNotNull(task);
        taskList.add(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskList;
    }

    @Override
    public Task updateTask(int idOldTask, Task newTask) {
        ObjectNotNull(newTask);
        IdNotNull(idOldTask);
        for (int i = 0; i < taskList.size(); i++) {
            if (idOldTask == taskList.get(i).getId()) {
                taskList.set(i, newTask);
                return newTask;
            }
        }
        return null;
    }

    @Override
    public Task deleteTask(int id) {
        IdNotNull(id);
        for (int i = 0; i < taskList.size(); i++) {
            if (id == taskList.get(i).getId()) {
                Task returnTask = taskList.get(i);
                taskList.remove(i);
                return returnTask;
            }
        }
        return null;
    }
    
    private boolean ObjectNotNull(Object object) {
        if (object == null || object.equals(null)) throw new NullPointerException("Object je null."); 
        else return true;
    }
    
    private boolean IdNotNull(int id) {
        if (id < 1) throw new NullPointerException("Id je null."); 
        else return true;
    }
}
