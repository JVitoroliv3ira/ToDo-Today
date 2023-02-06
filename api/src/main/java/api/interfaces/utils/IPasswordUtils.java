package api.interfaces.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface IPasswordUtils {
    static PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    static String encrypt(String value) {
        return getEncoder().encode(value);
    }

    static Boolean matches(String rawValue, String encodedValue) {
        return getEncoder().matches(rawValue, encodedValue);
    }
}
