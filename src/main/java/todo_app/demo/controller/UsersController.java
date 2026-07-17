package todo_app.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todo_app.demo.model.Users;
import todo_app.demo.repository.UsersRepository;
import todo_app.demo.service.UsersService;
import java.util.List;


@CrossOrigin(origins = "https://todo-notifier-frontend.vercel.app")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService service;
    

@GetMapping("/test")
public String test() {
    return "working";
}

 @PostMapping("/register")
    public String register(@RequestBody Users user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public Users login(@RequestBody Users user) {
        return service.login(user);
    } 



}