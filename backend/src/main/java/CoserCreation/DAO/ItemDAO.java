package CoserCreation.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoserCreation.models.ItemModel;

@Repository
public interface ItemDAO extends JpaRepository<ItemModel, Integer> {

}
