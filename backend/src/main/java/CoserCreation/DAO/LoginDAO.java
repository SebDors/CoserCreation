package CoserCreation.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import CoserCreation.models.LoginModel;

public interface LoginDAO extends JpaRepository<LoginModel, Integer> {
    Optional<LoginModel> findByUsername(String username);
}
