package yahor.kazlou.service;

import yahor.kazlou.dto.request.SendMailRequest;

public interface EmailService {

    void sendEmail(SendMailRequest request);

}
