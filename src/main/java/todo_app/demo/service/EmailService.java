package todo_app.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            System.out.println("Sending email in background...");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("easwarappdev@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}