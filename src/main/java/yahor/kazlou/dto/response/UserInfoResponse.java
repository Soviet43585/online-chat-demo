package yahor.kazlou.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import yahor.kazlou.model.Role;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private String id;
    private String login;
    private String username;
    private String email;
    private String role;

}
