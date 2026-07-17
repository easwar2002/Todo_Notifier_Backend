package todo_app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todo_app.demo.service.EmailService;

    @RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-test-email")
    public String sendTestEmail() {
        emailService.sendEmail(
            "your_email@gmail.com",
            "Test Email",
            "Your email is working successfully "
        );
        return "Email Sent!";
    }
}
    

