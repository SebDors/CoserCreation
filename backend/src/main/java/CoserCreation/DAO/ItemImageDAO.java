package CoserCreation.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoserCreation.models.ItemImageModel;

@Repository
public interface ItemImageDAO extends JpaRepository<ItemImageModel, Integer> {

}
