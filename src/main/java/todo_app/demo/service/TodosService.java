package todo_app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import todo_app.demo.model.Todos;
import todo_app.demo.model.Users;
import todo_app.demo.repository.TodosRepository;
import todo_app.demo.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodosService {

    @Autowired
    private TodosRepository repo;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    //  GET TODO BY ID
    public Todos getTodoById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    //  GET TODOS BY USER
    public List<Todos> getTodosByUser(Long userId) {
        return repo.findByUser_Id(userId);
    }

    //  CREATE TODO + EMAIL
    public Todos createTodo(Todos todo, Long userId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUser(user);

        Todos savedTodo = repo.save(todo);

        //  EMAIL
        try {
            emailService.sendEmail(
                user.getEmail(),
                "Task Added",
                "Hello " + user.getName() +
                "\n\nNew Task Added:\n" + savedTodo.getMessage()
                );
} catch (Exception e) {
    System.out.println("Email failed");
}
        

        return savedTodo;
    }

    //  GET ALL TODOS
    public List<Todos> getAllTodos() {
        return repo.findAll();
    }

    //  DELETE TODO + EMAIL
    public String deleteTodo(Long id) {

        Todos todo = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        String message = todo.getMessage();
       // String email = todo.getUser().getEmail();
       String email = null; 

        if (todo.getUser() != null) {
            email = todo.getUser().getEmail();
        }

        repo.deleteById(id);

       // EMAIL
        if (email != null) {
            try {
                emailService.sendEmail(
                        email,
                        "Task Deleted",
                        "Task Deleted:\n" + message
                );
            } catch (Exception e) {
                System.out.println("Email failed");
            }
        }

        return "Deleted successfully";
    }

    //  UPDATE TODO + EMAIL
    public Todos updateTodo(Long id, Todos todo) {

        Todos existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        existing.setMessage(todo.getMessage());
        existing.setCompleted(todo.isCompleted());

        Todos updated = repo.save(existing);

        //  EMAIL
        if (updated.getUser() != null) {
            try {emailService.sendEmail(
                    updated.getUser().getEmail(),
                    "Task Updated",
                    "Hello " + updated.getUser().getName() +
                    "\n\nTask Updated:\n" + updated.getMessage() +
                    "\nCompleted: " + updated.isCompleted()
            );
            } catch (Exception e) {
        System.out.println("Email failed");
        e.printStackTrace();
    }
}

        return updated;
    }

    //  TOGGLE COMPLETE + EMAIL
    public Todos toggleComplete(Long id) {

        Todos todo = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // toggle status
        todo.setCompleted(!todo.isCompleted());

        Todos updatedTodo = repo.save(todo);

        //  EMAIL ONLY WHEN COMPLETED
        if (updatedTodo.isCompleted() && updatedTodo.getUser() != null) {
        try {
            emailService.sendEmail(
                    updatedTodo.getUser().getEmail(),
                    "Task Completed",
                    "Hello " + updatedTodo.getUser().getName() +
                    "\n\nYou completed this task:\n" + updatedTodo.getMessage()
            );
             } catch (Exception e) {
        System.out.println("Email failed");
    }
}

        return updatedTodo;
    }

// reset Password 
@Scheduled(fixedRate = 60000)
public void checkReminders() {
    List<Todos> todos = repo.findAll();

    for (Todos t : todos) {
        if (t.getReminderTime() != null &&
    t.getReminderTime().isBefore(LocalDateTime.now()) &&
    !t.isCompleted()) {

            System.out.println("Reminder: " + t.getMessage());

           

            t.setReminderTime(null);
            repo.save(t);
        }
    }
}

}