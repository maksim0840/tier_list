package core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tiers")
@Getter
@Setter
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "tierByTag")
    private List<Tag> tagsByTier;

    @OneToMany(mappedBy = "tierByObject")
    private List<TLObject> objectsByTier;

    @OneToMany(mappedBy = "tierByRow")
    private List<TLRow> rowsByTier;

    public Tier(Long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Tier() {}
}
