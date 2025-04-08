package yahor.kazlou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yahor.kazlou.model.User;
import yahor.kazlou.model.UserCompanyAccess;

import java.util.Set;

@Repository
public interface UserCompanyAccessRepository extends JpaRepository<UserCompanyAccess, String> {

    Set<UserCompanyAccess> getAllByCompanyIdAndIsAvailable(String companyId, boolean isAvailable);

}
