package yahor.kazlou.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_company_access_queue")
public class UserCompanyAccess {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "is_available")
    private boolean isAvailable = false;

}
