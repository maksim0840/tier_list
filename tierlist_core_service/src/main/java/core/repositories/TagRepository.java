package core.repositories;

import core.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByTierId(Long tierId);
    List<Tag> findByObjectId(Long objectId);

}
