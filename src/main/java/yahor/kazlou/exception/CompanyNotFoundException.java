package yahor.kazlou.exception;

import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public class CompanyNotFoundException extends SQLException {

    private ResponseEntity response;

    public CompanyNotFoundException(String reason) {
        super(reason);
    }

}
