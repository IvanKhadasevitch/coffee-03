package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CoffeeOrderItem")

@Data
@NoArgsConstructor
public class CoffeeOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    // id 		int not null auto_increment, -- pk
    private Integer id;

    //type_id 	int not null, -- сорт кофе
    @ManyToOne
    @Access(AccessType.PROPERTY)
    private CoffeeType type;

    //order_id 	int not null, -- к какому заказу принадлежит
    @ManyToOne
    @Access(AccessType.PROPERTY)
    private CoffeeOrder order;

    //quantity 	int, -- сколько чашек
    @Access(AccessType.PROPERTY)
    private Integer quantity;

    public CoffeeOrderItem(Integer quantity) {
        this.quantity = quantity;
    }
}
