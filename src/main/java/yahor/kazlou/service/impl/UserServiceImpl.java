package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yahor.kazlou.dto.request.UserRegistrationRequest;
import yahor.kazlou.exception.UserAlreadyRegisteredException;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.Role;
import yahor.kazlou.model.User;
import yahor.kazlou.repository.UserRepository;
import yahor.kazlou.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean isExist(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByLogin(login);
        if (Objects.isNull(u)) {
            u = getByEmail(login);
            if(Objects.isNull(u)) {
                throw new UsernameNotFoundException(String.format("User %s is not found", login));
            }
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
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
        System.out.println(user.toString());
        return user;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get();
    }

    @Override
    public User updateAuthUserRole(Role role) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get();
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateAuthUserCompany(Company company) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get();
        user.setCompany(company);
        return userRepository.save(user);
    }

    @Override
    public User updateUserCompany(User user, Company company) {
        user.setCompany(company);
        userRepository.save(user);
        return user;
    }


}
