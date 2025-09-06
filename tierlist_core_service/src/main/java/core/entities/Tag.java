package core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tier_tags")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @ManyToOne
    @JoinColumn(name = "tierId")
    private Tier tierByTag;

    @ManyToMany(mappedBy = "tagsByObject")
    private List<TLObject> objectsByTag;

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Tag() {}
}
