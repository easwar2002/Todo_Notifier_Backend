package todo_app.demo.service;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo_app.demo.model.Users;
import todo_app.demo.repository.UsersRepository;
import todo_app.demo.service.EmailService;


@Service
public class UsersService {

    @Autowired
    private UsersRepository repo;

    @Autowired
private EmailService emailService;

    // REGISTER LOGIC
    public String register(Users user) {

    if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
        return "All fields are required";
    }

    Users existingUser = repo.findByEmail(user.getEmail());

    if (existingUser != null) {
        return "Email already exists";
    }

    //repo.save(user);
    Users savedUser = repo.save(user);
    System.out.println("Email is being sent to: " + savedUser.getEmail());

      //  SEND EMAIL WITH ID
      try{emailService.sendEmail(
        savedUser.getEmail(),
        "Registration Successful",
        "Hello " + savedUser.getName() +
        "\n\nYour account has been created successfully." +
        "\nUser ID: " + savedUser.getId() +
        "\nEmail: " + savedUser.getEmail() 
    );
}catch(Exception e) {
    System.out.println("Email failed: " + e.getMessage());
}
    
    return "User registered successfully";
}
// LOGIN  (IMPORTANT)
    public Users login(Users user) {

        Users existingUser = repo.findByEmail(user.getEmail());

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return existingUser;
    }
    
    
 // Forgot Password 
public void forgotPassword(String email) {
    Users user = repo.findByEmail(email);

    if (user == null) {
        throw new RuntimeException("User not found");
    }

    String token = UUID.randomUUID().toString();

    user.setResetToken(token);
    repo.save(user);

    String link = "http://localhost:5173/reset-password?token=" + token;

    emailService.sendEmail(
        user.getEmail(),
        "Reset Password",
        "Click here: " + link
    );
}
// Rest Password 

public void resetPassword(String token, String newPassword) {
    Users user = repo.findByResetToken(token);

    if (user == null) {
        throw new RuntimeException("Invalid token");
    }

    user.setPassword(newPassword); // later encode
    user.setResetToken(null);

    repo.save(user);
}
    
}
