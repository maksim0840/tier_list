package core.services;

import core.dto.TLRowCreateDTO;
import core.entities.TLRow;
import core.entities.Tier;
import core.repositories.TLObjectRepository;
import core.repositories.TLRowRepository;
import core.repositories.TierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TLRowService {

    private final TLRowRepository rowRepository;
    private final TierRepository tierRepository;

    public TLRowService(TLRowRepository rowRepository, TierRepository tierRepository) {
        this.rowRepository = rowRepository;
        this.tierRepository = tierRepository;
    }

    public void saveRowToTier(TLRowCreateDTO rowCreateDTO, Long tierId) {
        Tier tier = tierRepository.findById(tierId).orElseThrow(() ->
                new RuntimeException("TLRowService.saveRowToTier: Несуществующий tierId"));
        Integer newPriorityIndex = tier.getMaxRowsPriorityIndex();
        newPriorityIndex = (newPriorityIndex == null) ? (0) :  (newPriorityIndex + 1);
        TLRow row = new TLRow(rowCreateDTO.name(), rowCreateDTO.color(), newPriorityIndex);
        row.setTierByRow(tier);
        rowRepository.save(row);
    }

    public TLRow getRowById(Long id) {
        return rowRepository.findById(id).orElseThrow(() ->
                new RuntimeException("TLRowService.getRowById: Несуществующий id"));
    }

    public List<TLRow> getRowsByTier(Long tierId) {
        if (!tierRepository.existsById(tierId)) {
            throw new RuntimeException("TLRowService.getRowsByTier: Несуществующий tierId");
        }
        return rowRepository.findByTierId(tierId);
    }
}
