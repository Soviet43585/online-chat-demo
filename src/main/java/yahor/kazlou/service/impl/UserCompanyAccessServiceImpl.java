package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yahor.kazlou.model.UserCompanyAccess;
import yahor.kazlou.repository.UserCompanyAccessRepository;
import yahor.kazlou.service.UserCompanyAccessService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserCompanyAccessServiceImpl implements UserCompanyAccessService {

    private final UserCompanyAccessRepository repository;

    @Override
    public void sendJoinRequestByCompanyId(String currentUserId, String companyId){
        UserCompanyAccess userCompanyAccess = new UserCompanyAccess(currentUserId, companyId, false);
        repository.save(userCompanyAccess);
    }

    @Override
    public Set<UserCompanyAccess> getAllActiveRequestsByCompanyId(String companyId) {
        return repository.getAllByCompanyIdAndIsAvailable(companyId, false);
    }

    @Override
    public UserCompanyAccess getByUserId(String userId) {
        return repository.getReferenceById(userId);
    }

    @Override
    public void deleteByUserId(String userId) {
        repository.deleteById(userId);
    }

    @Override
    public void updateAccessByUserId(UserCompanyAccess userCompanyAccess, boolean isAvailable) {
        userCompanyAccess.setAvailable(isAvailable);
        repository.save(userCompanyAccess);
    }
}
