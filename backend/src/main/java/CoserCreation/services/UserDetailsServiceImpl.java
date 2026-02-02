package CoserCreation.services;

import CoserCreation.DAO.LoginDAO;
import CoserCreation.models.LoginModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginDAO loginDAO;

    public UserDetailsServiceImpl(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginModel user = loginDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Pour l'instant, nous n'avons pas de rôles, donc la liste des autorités est vide.
        return new User(user.getUsername(), user.getPasswordHash(), new ArrayList<>());
    }
}
