package yahor.kazlou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yahor.kazlou.model.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {

    VerificationCode getVerificationCodeByEmail(String email);

}
