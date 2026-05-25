package spravaZaznamu;

import java.util.List;
import zaznamy.Task;

public interface ISprava{
    Task addTask(Task task);    //    → добавить задачу в базу
    List<Task> getAllTasks();   //    → достать все задачи из базы
    Task updateTask(int idOldTask, Task newTask); //    → изменить задачу
    Task deleteTask(int id);    //    → удалить задачу
    Task findTask(int id);
}
