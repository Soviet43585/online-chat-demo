package yahor.kazlou.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationCodeResponse {

    private String email;
    private boolean isVerified;

}
