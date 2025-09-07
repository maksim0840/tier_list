package core.services;

import core.dto.TLObjectCreateDTO;
import core.entities.TLObject;
import core.entities.TLRow;
import core.entities.Tier;
import core.repositories.TLObjectRepository;
import core.repositories.TLRowRepository;
import core.repositories.TagRepository;
import core.repositories.TierRepository;
import core.repositories.specifications.TLObjectSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TLObjectService {

    private final TLObjectRepository objectRepository;
    private final TierRepository tierRepository;
    private final TLRowRepository rowRepository;
    private final TagRepository tagRepository;

    public TLObjectService(TLObjectRepository objectRepository, TierRepository tierRepository, TLRowRepository rowRepository, TagRepository tagRepository) {
        this.objectRepository = objectRepository;
        this.tierRepository = tierRepository;
        this.rowRepository = rowRepository;
        this.tagRepository = tagRepository;
    }

    public void saveObjectToTier(TLObjectCreateDTO objectCreateDTO, Long tierId) {
        TLObject object = new TLObject(objectCreateDTO);
        Tier tier = tierRepository.findById(tierId).orElseThrow(() ->
                new RuntimeException("TLObjectService.saveObjectToTier: Несуществующий tierId"));
        object.setTierByObject(tier);
        objectRepository.save(object);
    }

    public void moveObjectToRowPos(Long objectId, Long rowId, int newPos) {
        TLObject object = objectRepository.findById(objectId).orElseThrow(() ->
                new RuntimeException("TLObjectService.addObjectToRow: Несуществующий objectId"));
        TLRow row = rowRepository.findById(rowId).orElseThrow(() ->
                new RuntimeException("TLObjectService.addObjectToRow: Несуществующий rowId"));
        row.insertWithIndexUpdate(newPos, object);
    }

    public void clearObjectFromRow(Long objectId) {
        TLObject object = objectRepository.findById(objectId).orElseThrow(() ->
                new RuntimeException("TLObjectService.clearObjectFromRow: Несуществующий objectId"));
        object.getRowByObject().clearRowFromObject(object);
    }

    public TLObject getObjectById(Long id) {
        return objectRepository.findById(id).orElseThrow(() ->
                new RuntimeException("TLObjectService.getObjectById: Несуществующий id"));
    }

    public List<TLObject> getObjectsByTags(List<Long> tagIds) {
        if (!tagIds.stream().allMatch(tagRepository::existsById)) {
            throw new RuntimeException("TLObjectService.getObjectsByTags: Несуществующий tagId");
        }
        return objectRepository.findAll(TLObjectSpecification.allTagsRequired(tagIds));
    }

    public List<TLObject> getObjectsByRow(Long rowId) {
        if (!rowRepository.existsById(rowId)) {
            throw new RuntimeException("TLObjectService.getObjectsByRow: Несуществующий rowId");
        }
        return objectRepository.findByRowId(rowId);
    }

    public List<TLObject> getObjectsByTier(Long tierId) {
        if (!tierRepository.existsById(tierId)) {
            throw new RuntimeException("TLObjectService.getObjectsByTier: Несуществующий tierId");
        }
        return objectRepository.findByTierId(tierId);
    }

    // Получить объекты по другой возможной спецификации / их комбинации + постранично
    public List<TLObject> getObjectsBySpecPaging(Specification<TLObject> spec, PageRequest paging) {
        return objectRepository.findAll(spec, paging).getContent();
    }
}
