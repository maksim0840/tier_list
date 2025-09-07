package core.repositories;

import core.entities.TLRow;
import core.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLRowRepository extends JpaRepository<TLRow, Long> {
    List<TLRow> findByTierId(Long tierId);
}
