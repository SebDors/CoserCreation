package CoserCreation.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import CoserCreation.models.ClientModel;

@Repository
public interface ClientDAO extends JpaRepository<ClientModel, Integer> {
    List<ClientModel> findByIsActive(boolean isActive);
}
