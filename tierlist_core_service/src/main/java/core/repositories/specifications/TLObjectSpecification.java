package core.repositories.specifications;

import core.entities.TLObject;
import core.entities.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TLObjectSpecification {

    // Получить объекты, у которых имеются все теги из списка
    public static Specification<TLObject> allTagsRequired(List<Long> tagIds) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return criteriaBuilder.conjunction(); // без фильтра
            }
            Join<TLObject, Tag> tag = root.join("tagsByObject", JoinType.INNER);
            // WHERE tag.id IN (:tagIds)
            Predicate where = tag.get("id").in(tagIds);

            // GROUP BY объект и требуем COUNT(DISTINCT tag) == размер списка
            criteriaQuery.groupBy(root.get("id"));
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.countDistinct(tag.get("id")), (long) tagIds.size()));
            return where;
        };
    }
}
