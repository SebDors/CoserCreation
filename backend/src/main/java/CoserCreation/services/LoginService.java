package CoserCreation.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import CoserCreation.DAO.LoginDAO;
import CoserCreation.models.LoginModel;

@Service
public class LoginService {

    private final LoginDAO loginDAO;
    private final PasswordEncoder passwordEncoder;

    public LoginService(LoginDAO loginDAO, PasswordEncoder passwordEncoder) {
        this.loginDAO = loginDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        Optional<LoginModel> userOptional = loginDAO.findByUsername(username);
        if (userOptional.isPresent()) {
            LoginModel user = userOptional.get();
            return passwordEncoder.matches(password, user.getPasswordHash());
        }

        return false;
    }
}
