package core.repositories;

import core.entities.TLObject;
import core.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLObjectRepository extends JpaRepository<TLObject, Long>, JpaSpecificationExecutor<TLObject> {
    List<TLObject> findByTierId(Long tierId);
    List<TLObject> findByRowId(Long rowId);
}
