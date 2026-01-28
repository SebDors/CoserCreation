package CoserCreation.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoserCreation.models.ColorModel;

@Repository
public interface ColorDAO extends JpaRepository<ColorModel, Integer> {

}