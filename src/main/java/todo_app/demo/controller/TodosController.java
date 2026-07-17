package todo_app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todo_app.demo.model.Todos;
import todo_app.demo.service.TodosService;

import java.util.List;

@CrossOrigin(origins = "https://todo-notifier-frontend.vercel.app")
@RestController
@RequestMapping("/todos")
public class TodosController {

    @Autowired
    private TodosService service;

    // GET ALL
    @GetMapping
    public List<Todos> getAllTodos() {
        return service.getAllTodos();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Todos getTodoById(@PathVariable Long id) {
        return service.getTodoById(id);
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public List<Todos> getTodosByUser(@PathVariable Long userId) {
        return service.getTodosByUser(userId);
    }

    // TOGGLE COMPLETE (FIXED)
    @PutMapping("/complete/{id}")
    public Todos toggleComplete(@PathVariable Long id) {
        return service.toggleComplete(id);
    }

    // CREATE
   @PostMapping("/user/{userId}")
   public Todos createTodo(@RequestBody Todos todo, @PathVariable Long userId) {
   return service.createTodo(todo, userId); 
}

    // UPDATE
    @PutMapping("/{id}")
    public Todos updateTodo(@PathVariable Long id, @RequestBody Todos todo) {
        return service.updateTodo(id, todo);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id) {
        return service.deleteTodo(id);
    }
    
}