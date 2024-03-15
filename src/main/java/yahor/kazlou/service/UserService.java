package yahor.kazlou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import yahor.kazlou.dto.request.UserRegistrationRequest;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    User getByLogin(String login);

    User register(UserRegistrationRequest request) throws UserAlreadyRegisteredException;

}
