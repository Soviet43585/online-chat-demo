package yahor.kazlou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yahor.kazlou.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

}
