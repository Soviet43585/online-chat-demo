package yahor.kazlou.repository.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;
import yahor.kazlou.dto.request.*;
import yahor.kazlou.dto.response.CurrentUserResponse;
import yahor.kazlou.dto.response.JoinListResponse;
import yahor.kazlou.dto.response.UserInfoResponse;
import yahor.kazlou.exception.AppError;
import yahor.kazlou.exception.CompanyNotFoundException;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.User;
import yahor.kazlou.model.UserCompanyAccess;
import yahor.kazlou.model.VerificationCode;
import yahor.kazlou.repository.VerificationCodeRepository;
import yahor.kazlou.service.*;
import yahor.kazlou.utils.EmailValidator;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final VerificationCodeService codeService;
    private final EmailService emailService;
    private final VerificationCodeRepository codeRepository;
    private final UserCompanyAccessService userAccessService;
    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationRequest request) {
        if(codeRepository.getVerificationCodeByEmail(request.getEmail()).isVerified()){
            try {
                User user = userService.register(request);
                return ResponseEntity.ok(new UserInfoResponse(user.getId(), user.getLogin(), user.getUsername(), user.getEmail(), user.getRole()));
            } catch (UserAlreadyRegisteredException e) {
                return new  ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), e.getMessage()), HttpStatus.CONFLICT);
            }
        } else {
            return new  ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), "Your mail not verified!"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/request-code")
    public ResponseEntity<?> sendCode(@RequestBody SendCodeToEmailRequest email) {
        if(!EmailValidator.validateEmail(email.getEmail())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Email isn't valid"), HttpStatus.BAD_REQUEST);
        }
        if(!userService.isExist(email.getEmail())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "This email already registered"), HttpStatus.BAD_REQUEST);
        }
        SendMailRequest request = new SendMailRequest();
        request.setTo(email.getEmail().trim());
        request.setSubject("Verification code for poperdoleno.pl");
        request.setText("Here is your verification code - " + codeService.generateCode(email.getEmail()) + ". Enter this at poperdoleno.pl for finish your registration");
        try {
            emailService.sendEmail(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MailSendException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка отправки кода на почту"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/check-code")
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

    @PostMapping("/user/join-request")
    public ResponseEntity<?> sendJoinCompanyRequest(@RequestBody JoinCompanyRequest request) throws CompanyNotFoundException {
        if(companyService.getById(request.getCompanyId()) != null) {
            userAccessService.sendJoinRequestByCompanyId(userService.getCurrentUser().getId(), request.getCompanyId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new CompanyNotFoundException("Company with that id not found");
        }
    }

    @GetMapping("/leader/show-join-requests")
    public JoinListResponse getAllJoinRequests() {
        Set<UserCompanyAccess> requests = userAccessService.getAllActiveRequestsByCompanyId(userService.getCurrentUser().getCompany().getId());
        Set<UserInfoResponse> users = new HashSet<>();
        for(UserCompanyAccess var : requests) {
            User user = (userService.findById(var.getUserId()));
            users.add(new UserInfoResponse(user.getId(), user.getLogin(), user.getUsername(), user.getEmail(), user.getRole()));
        }
        return new JoinListResponse(users);
    }

    @PostMapping("/leader/add-user/{userId}")
    public ResponseEntity<?> addUserToCompany(@PathVariable String userId) {
        User currentUser = userService.getCurrentUser();
        try {
            UserCompanyAccess userCompanyAccess = userAccessService.getByUserId(userId);
            if (userCompanyAccess.getCompanyId().equals(currentUser.getCompany().getId())) {
                if (!userCompanyAccess.isAvailable()) {
                    userService.updateUserCompany(userService.findById(userId), currentUser.getCompany());
                    userAccessService.updateAccessByUserId(userCompanyAccess, true);
                    userAccessService.deleteByUserId(userId);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    userAccessService.deleteByUserId(userId);
                    return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User already in a company."), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "This record is not for your company."), HttpStatus.FORBIDDEN);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Access record not found."), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/get-current-user")
    public CurrentUserResponse getCurrentUser() {
        User user = userService.getCurrentUser();
        return new CurrentUserResponse(user.getId(), user.getLogin(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
