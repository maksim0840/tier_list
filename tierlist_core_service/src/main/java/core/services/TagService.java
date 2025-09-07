package core.services;

import core.dto.TagCreateDTO;
import core.entities.TLObject;
import core.entities.Tag;
import core.entities.Tier;
import core.repositories.TLObjectRepository;
import core.repositories.TagRepository;
import core.repositories.TierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TierRepository tierRepository;
    private final TLObjectRepository objectRepository;

    public TagService(TagRepository tagRepository, TierRepository tierRepository, TLObjectRepository objectRepository) {
        this.tagRepository = tagRepository;
        this.tierRepository = tierRepository;
        this.objectRepository = objectRepository;
    }

    public void saveTagToTier(TagCreateDTO tagCreateDTO, Long tierId) {
        Tag tag = new Tag(tagCreateDTO);
        Tier tier = tierRepository.findById(tierId).orElseThrow(() ->
                new RuntimeException("TagService.saveTagToTier: Несуществующий tierId"));
        tag.setTierByTag(tier);
        tagRepository.save(tag);

    }

    public void addTagToObject(Long tagId, Long objectId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new RuntimeException("TagService.addTagToObject: Несуществующий tagId"));
        TLObject object = objectRepository.findById(objectId).orElseThrow(() ->
                new RuntimeException("TagService.addTagToObject: Несуществующий objectId"));
        object.getTagsByObject().add(tag);
    }

    public void clearTagFromObject(Long tagId, Long objectId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new RuntimeException("TagService.clearTagFromObject: Несуществующий tagId"));
        TLObject object = objectRepository.findById(objectId).orElseThrow(() ->
                new RuntimeException("TagService.clearTagFromObject: Несуществующий objectId"));
        object.clearObjectFromTag(tag);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new RuntimeException("TagService.getTagById: Несуществующий id"));
    }

    public List<Tag> getTagsByObject(Long objectId) {
        if (!objectRepository.existsById(objectId)) {
            throw new RuntimeException("TagService.getTagsByObject: Несуществующий objectId");
        }
        return tagRepository.findByObjectId(objectId);
    }

    public List<Tag> getTagsByTier(Long tierId) {
        if (!tierRepository.existsById(tierId)) {
            throw new RuntimeException("TagService.getTagsByTier: Несуществующий tierId");
        }
        return tagRepository.findByTierId(tierId);
    }
}
