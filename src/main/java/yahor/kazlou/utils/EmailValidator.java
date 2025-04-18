package yahor.kazlou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String[] emails = {
                "test@example.com",
                "test.example@domain.ru",
                "gorinich.chiter@gmail.com",
                "test@example",
                "test@example.",
                "test@example..com",
                "testexample.com",
                "тест@пример.рф"
        };

        for (String email : emails) {
            System.out.println(email + " is valid? " + validateEmail(email));
        }
    }

}
