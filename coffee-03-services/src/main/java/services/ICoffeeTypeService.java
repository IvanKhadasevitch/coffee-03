package services;

import entities.CoffeeType;
import entities.enums.DisabledFlag;

import java.util.List;

public interface ICoffeeTypeService extends IService<CoffeeType> {
    /**
     * get all records of CoffeeTypes from DB where CoffeeType.disabled = disabledFlag
     *
     * @param disabledFlag determines whether ("N") or not ("Y") to show on the UI given CoffeeType
     * @return a list of all records of CoffeeTypes from the database
     *         where CoffeeType.disabled = disabledFlag or
     *         empty list if there are no entries
     */
    List<CoffeeType> getAllForDisabledFlag(DisabledFlag disabledFlag);
}
