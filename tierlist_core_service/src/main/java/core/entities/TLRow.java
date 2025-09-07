package core.entities;

import core.dto.TLRowCreateDTO;
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
    private Integer priorityIndex;

    @OneToMany(mappedBy = "rowByObject")
    private List<TLObject> objectsByRow;

    @ManyToOne
    @JoinColumn(name = "tierId")
    private Tier tierByRow;

    public TLRow(String name, String color, Integer priorityIndex) {
        this.name = name;
        this.color = color;
        this.priorityIndex = priorityIndex;
    }

    public TLRow() {}

    public void deleteObjectById(Long id) {
        objectsByRow.removeIf(obj -> obj.getId().equals(id));
    }

    // Передвинуть элемент и скорректировать индексы всех остальных
    public void insertWithIndexUpdate(int newPos, TLObject movingObject) {
        movingObject.getRowByObject().deleteObjectById(movingObject.getId()); // удаляем объект из прошлой коллекции
        objectsByRow.add(newPos, movingObject); // вставляем объект в новую коллекцию
        for (int i = 0; i < objectsByRow.size(); ++i) {
            objectsByRow.get(i).setPriorityIndex(i); // корректируем индексы
        }
    }

    // Убрать объект из ряда
    public void clearRowFromObject(TLObject clearingObject) {
        objectsByRow.remove(clearingObject); // удаляем объект из коллекции
        clearingObject.setPriorityIndex(null); // очищаем его индексы (теперь он никому не принадлежит)
        for (int i = 0; i < objectsByRow.size(); ++i) {
            objectsByRow.get(i).setPriorityIndex(i); // корректируем индексы в коллекции
        }
    }
}
