package yahor.kazlou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import yahor.kazlou.dto.request.UserRegistrationRequest;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.Role;
import yahor.kazlou.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    boolean isExist(String email);
    User findById(String id);

    User getByLogin(String login);

    User getByEmail(String email);

    User register(UserRegistrationRequest request) throws UserAlreadyRegisteredException;

    User getCurrentUser();

    User updateAuthUserRole(Role role);

    User updateAuthUserCompany(Company company);

    User updateUserCompany(User user, Company company);

}
