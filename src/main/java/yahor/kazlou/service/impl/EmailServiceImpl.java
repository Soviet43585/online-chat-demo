package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import yahor.kazlou.dto.request.SendMailRequest;
import yahor.kazlou.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender sender;

    @Override
    public void sendEmail(SendMailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getText());
        try {
            sender.send(message);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
