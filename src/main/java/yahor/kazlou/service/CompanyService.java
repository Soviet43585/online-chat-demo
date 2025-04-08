package yahor.kazlou.service;

import yahor.kazlou.dto.request.CreateCompanyRequest;
import yahor.kazlou.model.Company;
import yahor.kazlou.model.User;

import java.util.Set;

public interface CompanyService {

    Company register(User user, CreateCompanyRequest request);

    Company getById(String id);

}
