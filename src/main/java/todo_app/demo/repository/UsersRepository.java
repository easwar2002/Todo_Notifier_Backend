package todo_app.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo_app.demo.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    
    Users findByEmail(String email);
    
    Users findByEmailAndPassword(String email, String password);
    
    Users findByResetToken(String token);
}