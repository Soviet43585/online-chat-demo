package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yahor.kazlou.dto.request.CreateCompanyRequest;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.Role;
import yahor.kazlou.model.User;
import yahor.kazlou.repository.CompanyRepository;
import yahor.kazlou.service.CompanyService;
import yahor.kazlou.service.UserService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final UserService userService;

    @Override
    public Company register(User user, CreateCompanyRequest request) {

        Company company = new Company();
        company.setName(request.getCompanyName());
        company.setDescription(request.getDescription());
        repository.save(company);
        userService.updateAuthUserRole(Role.ROLE_LEADER);
        userService.updateAuthUserCompany(company);
        return company;

    }

    @Override
    public Company getById(String id) {
        return repository.getReferenceById(id);
    }
}
