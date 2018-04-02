package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CoffeeOrder")

@Setter @Getter
@NoArgsConstructor
public class CoffeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    //id     int not null auto_increment, -- pk
    private Integer id;

    //order_date 		datetime not null, -- время заказа
    @CreationTimestamp
    @Column(name = "order_date", insertable = true,  updatable = false)
    @Access(AccessType.PROPERTY)
    private Timestamp orderDate;

    //`name`			varchar(100), -- имя заказчика
    @Column(name = "`name`", length = 100)
    @Access(AccessType.PROPERTY)
    private String customerName;

    //delivery_address 	varchar(200) not null, -- куда доставлять
    @Column(name = "delivery_address", nullable = false, length = 200)
    @Access(AccessType.PROPERTY)
    private String deliveryAddress;

    //cost 				double, -- сколко стоит
    @Access(AccessType.PROPERTY)
    private Double cost;

    //don't save in DB as field of Table CoffeeOrder
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "order")
    @BatchSize(size = 7)     //specify the size for batch loading the entries of a lazy collection.
    @Access(AccessType.FIELD)
    private List<CoffeeOrderItem> coffeeOrderItemList = new ArrayList<>();

    public CoffeeOrder(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void addCoffeeOrderItem(CoffeeOrderItem coffeeOrderItem) {
        coffeeOrderItemList.add( coffeeOrderItem );
        coffeeOrderItem.setOrder( this );
    }

    public void removeCoffeeOrderItem(CoffeeOrderItem coffeeOrderItem) {
        coffeeOrderItemList.remove( coffeeOrderItem );
        coffeeOrderItem.setOrder( null );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoffeeOrder)) return false;

        CoffeeOrder that = (CoffeeOrder) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getOrderDate() != null ? !getOrderDate().equals(that.getOrderDate()) : that.getOrderDate() != null)
            return false;
        if (getCustomerName() != null ? !getCustomerName().equals(that.getCustomerName()) : that.getCustomerName() != null)
            return false;
        if (!getDeliveryAddress().equals(that.getDeliveryAddress())) return false;
        return getCost() != null ? getCost().equals(that.getCost()) : that.getCost() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + getDeliveryAddress().hashCode();
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", cost=" + cost +
                '}';
    }
}
