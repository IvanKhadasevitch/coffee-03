package services.impl;

import dao.ICoffeeTypeDao;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.ICoffeeTypeService;

import java.util.List;

@Service
@Transactional(transactionManager = "txManager")
public class CoffeeTypeService extends BaseService<CoffeeType> implements ICoffeeTypeService {
    private static Logger log = Logger.getLogger(CoffeeTypeService.class);

    private ICoffeeTypeDao coffeeTypeDao;

    @Autowired
    public CoffeeTypeService(ICoffeeTypeDao coffeeTypeDao) {
        super();
        this.coffeeTypeDao = coffeeTypeDao;
    }

    /**
     * get all records of CoffeeTypes from DB where CoffeeType.disabled = disabledFlag
     *
     * @param disabledFlag determines whether ("N") or not ("Y") to show on the UI given CoffeeType
     * @return a list of all records of CoffeeTypes from the database
     *         where CoffeeType.disabled = disabledFlag or
     *         empty list if there are no entries
     */
    @Override
    public List<CoffeeType> getAllForDisabledFlag(DisabledFlag disabledFlag) {
        return coffeeTypeDao.getAllForDisabledFlag(disabledFlag);
    }
}
