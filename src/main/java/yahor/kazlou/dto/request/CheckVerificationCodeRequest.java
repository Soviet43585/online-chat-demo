package yahor.kazlou.dto.request;

import lombok.Data;

@Data
public class CheckVerificationCodeRequest {

    private String email;
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
