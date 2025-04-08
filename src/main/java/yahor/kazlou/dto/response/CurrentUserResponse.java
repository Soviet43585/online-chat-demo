package yahor.kazlou.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.Role;

@Data
@AllArgsConstructor
public class CurrentUserResponse {

    private String id;
    private String login;
    private String username;
    private String password;
    private String email;

}
