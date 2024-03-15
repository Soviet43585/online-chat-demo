package yahor.kazlou.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;
import yahor.kazlou.dto.request.CheckVerificationCodeRequest;
import yahor.kazlou.dto.request.SendMailRequest;
import yahor.kazlou.dto.request.UserRegistrationRequest;
import yahor.kazlou.exception.AppError;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.VerificationCode;
import yahor.kazlou.repository.VerificationCodeRepository;
import yahor.kazlou.service.EmailService;
import yahor.kazlou.service.UserService;
import yahor.kazlou.service.VerificationCodeService;
import yahor.kazlou.utils.EmailValidator;

import java.util.Arrays;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final VerificationCodeService codeService;
    private final EmailService emailService;
    private final VerificationCodeRepository codeRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationRequest request) {
        if(codeRepository.getVerificationCodeByEmail(request.getEmail()).isVerified()){
            try {
                return ResponseEntity.ok(userService.register(request));
            } catch (UserAlreadyRegisteredException e) {
                return new  ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), e.getMessage()), HttpStatus.CONFLICT);
            }
        } else {
            return new  ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), "Your mail not verified!"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/send_code")
    public ResponseEntity<?> sendCode(@RequestBody String email) {

        if(EmailValidator.validateEmail(email)) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Email isn't valid"), HttpStatus.BAD_REQUEST);
        }
        SendMailRequest request = new SendMailRequest();
        request.setTo(email.trim());
        request.setSubject("Verification code for poperdoleno.pl");
        request.setText("Here is your verification code - " + codeService.generateCode(email) + ". Enter this at poperdoleno.pl for finish your registration");
        try {
            emailService.sendEmail(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MailSendException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка отправки кода на почту"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/check_code")
    public ResponseEntity<?> checkCode(@RequestBody CheckVerificationCodeRequest request) {
        VerificationCode code = codeRepository.getVerificationCodeByEmail(request.getEmail());
        if(request.getCode().equals(code.getCode())) {
            code.setVerified(true);
            codeRepository.save(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
