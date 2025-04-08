package yahor.kazlou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.User;

import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Set<User> getAllUsersById(String id);

}
