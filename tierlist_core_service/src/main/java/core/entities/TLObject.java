package core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tier_objects")
@Getter
@Setter
public class TLObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long pictureId;
    private String description;
    private int priorityIndex;

    @ManyToOne
    @JoinColumn(name = "rowId")
    private TLRow rowByObject;

    @ManyToOne
    @JoinColumn(name = "tierId")
    private Tier tierByObject;

    @ManyToMany
    @JoinTable(name = "tags_by_object",
                joinColumns = @JoinColumn(name = "objectId"),
                inverseJoinColumns = @JoinColumn(name = "tagId"))
    private List<Tag> tagsByObject;


    public TLObject(String name, Long pictureId, String description, int priorityIndex) {
        this.name = name;
        this.pictureId = pictureId;
        this.description = description;
        this.priorityIndex = priorityIndex;
    }

    public TLObject() {}
}
