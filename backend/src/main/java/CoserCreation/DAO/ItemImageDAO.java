package CoserCreation.DAO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoserCreation.models.ItemImageModel;

@Repository
public interface ItemImageDAO extends JpaRepository<ItemImageModel, Integer> {
    List<ItemImageModel> findByItemId(int itemId);
}
