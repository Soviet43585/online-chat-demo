package yahor.kazlou.dto.request;

import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String login;
    private String username;
    private String password;
    private String email;

}
