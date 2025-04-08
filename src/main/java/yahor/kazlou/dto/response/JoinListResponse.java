package yahor.kazlou.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import yahor.kazlou.model.User;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class JoinListResponse {

    private Set<UserInfoResponse> users;

}
