package core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tier_rows")
@Getter
@Setter
public class TLRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private int priorityIndex;

    @OneToMany(mappedBy = "rowByObject")
    private List<TLObject> objectsByRow;

    @ManyToOne
    @JoinColumn(name = "tierId")
    private Tier tierByRow;

    public TLRow(String name, String color, int priorityIndex) {
        this.name = name;
        this.color = color;
        this.priorityIndex = priorityIndex;
    }

    public TLRow() {}

    /*
    Передвинуть элемент и скоректировать индексы всех остальных
     */
    public void insertWithIndexUpdate(int pos, TLObject tlObject) {
        if (pos == tlObject.getPriorityIndex()) return;
    }

}
