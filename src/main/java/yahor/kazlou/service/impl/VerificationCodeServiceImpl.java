package yahor.kazlou.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yahor.kazlou.model.VerificationCode;
import yahor.kazlou.repository.VerificationCodeRepository;
import yahor.kazlou.service.VerificationCodeService;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository codeRepository;
    private static final Random random = new Random();

    @Override
    public String generateCode(String email) {
        VerificationCode code = codeRepository.getVerificationCodeByEmail(email);
        if(code != null) {
            code.setCode(RandomStringUtils.random(6, 0, 0, true, false, null, random));
            code.setVerified(false);
            codeRepository.save(code);
            return code.getCode();
        } else {
            code = new VerificationCode();
            code.setEmail(email);
            code.setCode(RandomStringUtils.random(6, 0, 0, true, false, null, random));
            code.setVerified(false);
            codeRepository.save(code);
            return code.getCode();
        }
    }

    public boolean checkCode(String email, String userCode) {
        VerificationCode code = codeRepository.getVerificationCodeByEmail(email);
        return code != null && code.getCode().equals(userCode);
    }
}
