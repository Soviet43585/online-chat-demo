package yahor.kazlou.repository.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yahor.kazlou.dto.request.CreateCompanyRequest;
import yahor.kazlou.model.Company;
import yahor.kazlou.service.CompanyService;
import yahor.kazlou.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/company")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping("/reg-company")
    public Company registerNewCompany(@RequestBody CreateCompanyRequest request) {
        return companyService.register(userService.getCurrentUser(), request);
    }

}
