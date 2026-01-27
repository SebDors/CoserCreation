package CoserCreation.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoserCreation.models.creationModel;

@Repository
public interface creationDAO extends JpaRepository<creationModel, Integer> {

}
