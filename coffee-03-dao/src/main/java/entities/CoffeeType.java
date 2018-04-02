package entities;

import entities.enums.DisabledFlag;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CoffeeType")

@Getter
public class CoffeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    // id 		int not null auto_increment, -- pk
    private Integer id;

    //type_name 	varchar(200) not null unique, -- название
    @Column(name = "type_name", unique = true, nullable = false, length = 200)
    @Access(AccessType.PROPERTY)
    private String typeName;

    //price 		double not null, -- цена
    @Access(AccessType.PROPERTY)
    private Double price;

    //disabled 		char(1), -- если disabled = 'Y', то не показывать данный сорт в списке доступных сортов
    @Column(nullable = false)
    @Access(AccessType.FIELD)
    private Character disabled;

    //don't save in DB as field of Table CoffeeOrder
    @Transient
    private DisabledFlag disabledFlag;

    //don't save in DB as field of Table CoffeeOrder
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "type")
    @BatchSize(size = 7)     //specify the size for batch loading the entries of a lazy collection.
    @Access(AccessType.FIELD)
    private List<CoffeeOrderItem> coffeeOrderItemList = new ArrayList<>();

    public CoffeeType() {
        this.disabledFlag = DisabledFlag.N;
        this.disabled = 'N';
    }

    public CoffeeType(String typeName, Double price, DisabledFlag disabledFlag) {
        this.typeName = typeName;
        this.price = price;
        this.disabledFlag = disabledFlag;
        if (DisabledFlag.Y.equals(this.disabledFlag)) {
            this.disabled = 'Y';
        } else {
            this.disabled = 'N';
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDisabledFlag(DisabledFlag disabledFlag) {
        this.disabledFlag = disabledFlag;
        if (DisabledFlag.Y.equals(this.disabledFlag)) {
            this.disabled = 'Y';
        } else {
            this.disabled = 'N';
        }
    }

    public void setCoffeeOrderItemList(List<CoffeeOrderItem> coffeeOrderItemList) {
        this.coffeeOrderItemList = coffeeOrderItemList;
    }

    public void addCoffeeOrderItem(CoffeeOrderItem coffeeOrderItem) {
        coffeeOrderItemList.add( coffeeOrderItem );
        coffeeOrderItem.setType( this );
    }

    public void removeCoffeeOrderItem(CoffeeOrderItem coffeeOrderItem) {
        coffeeOrderItemList.remove( coffeeOrderItem );
        coffeeOrderItem.setType( null );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoffeeType)) return false;

        CoffeeType that = (CoffeeType) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (!getTypeName().equals(that.getTypeName())) return false;
        if (!getPrice().equals(that.getPrice())) return false;
        return getDisabled() == that.getDisabled();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getTypeName().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getDisabled().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CoffeeType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", price=" + price +
                ", disabled=" + disabled +
                '}';
    }
}
