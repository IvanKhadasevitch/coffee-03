package dao;

import entities.CoffeeType;
import entities.enums.DisabledFlag;

import java.util.List;

public interface ICoffeeTypeDao extends IDao<CoffeeType> {
    /**
     *  get all records with type <CoffeeType> from data base for definite disabledFlag
     *
     * @param disabledFlag determine show or not CoffeeType for user
     * @return a list of all records of type <CoffeeType> from the database
     *         with definite disabledFlag or empty list if there are no entries
     */
    List<CoffeeType> getAllForDisabledFlag(DisabledFlag disabledFlag);
}
