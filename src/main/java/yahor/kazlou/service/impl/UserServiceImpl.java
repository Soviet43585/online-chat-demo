package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yahor.kazlou.dto.request.UserRegistrationRequest;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.User;
import yahor.kazlou.repository.UserRepository;
import yahor.kazlou.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();



    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(u.getRole())));
    }

    @Override
    public User register(UserRegistrationRequest request) throws UserAlreadyRegisteredException {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyRegisteredException("User with this email already registered");
        } else if(userRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyRegisteredException("User with this login already registered");
        }
        User user = new User();
        user.setLogin(request.getLogin());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
        return user;
    }
}
