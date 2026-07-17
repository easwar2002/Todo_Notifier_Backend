package todo_app.demo.model;

import jakarta.persistence.*;
import todo_app.demo.model.Users;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "todos")  
public class Todos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")   
    private String message;

    @CreationTimestamp
@Column(name = "created_at", nullable = false, updatable = false)
private LocalDateTime createdAt;

    
    @ManyToOne
    @JoinColumn(name = "user_id")   
    @JsonIgnore 
    private Users user;

    @Column(name = "reminder_time")
    private LocalDateTime reminderTime;

    // getters & setters
     public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "completed")
    private boolean completed = false; //  NEW FIELD

    public boolean isCompleted() {
    return completed;
}

public void setCompleted(boolean completed) {
    this.completed = completed;
}

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public LocalDateTime getReminderTime() {
    return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
    this.reminderTime = reminderTime;
    }
}