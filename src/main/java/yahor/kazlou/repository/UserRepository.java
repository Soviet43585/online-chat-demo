package yahor.kazlou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yahor.kazlou.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String email);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

}
