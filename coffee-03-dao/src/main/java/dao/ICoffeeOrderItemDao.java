package dao;

import entities.CoffeeOrderItem;

import java.io.Serializable;
import java.util.List;

public interface ICoffeeOrderItemDao extends IDao<CoffeeOrderItem> {
    /**
     *  get all records with type <CoffeeOrderItem> from data base for definite CoffeeOrder
     *
     * @param orderId determine id of CoffeeOrder in database
     * @return a list of all records of type <CoffeeOrderItem> from the database
     *         for definite CoffeeOrder or empty list if there are no entries
     */
    List<CoffeeOrderItem> getAllForOrderId(Serializable orderId);
}
