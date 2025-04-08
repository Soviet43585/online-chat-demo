package yahor.kazlou.service;

import yahor.kazlou.exception.CompanyNotFoundException;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.User;
import yahor.kazlou.model.UserCompanyAccess;

import java.util.Set;

public interface UserCompanyAccessService {

    void sendJoinRequestByCompanyId(String currentUserId, String companyId) throws CompanyNotFoundException;

    Set<UserCompanyAccess> getAllActiveRequestsByCompanyId(String companyId);

    UserCompanyAccess getByUserId(String userId);

    void deleteByUserId(String userId);

    void updateAccessByUserId(UserCompanyAccess userCompanyAccess, boolean isAvailable);
}
