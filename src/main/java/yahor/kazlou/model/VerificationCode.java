package yahor.kazlou.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "verification_code")
public class VerificationCode {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    @Column(name = "isVerified")
    private boolean isVerified;

}
