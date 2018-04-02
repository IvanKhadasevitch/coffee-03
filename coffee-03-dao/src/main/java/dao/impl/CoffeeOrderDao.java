package dao.impl;

import dao.ICoffeeOrderDao;
import entities.CoffeeOrder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeOrderDao extends BaseDao<CoffeeOrder> implements ICoffeeOrderDao {
    private static Logger log = Logger.getLogger(CoffeeOrderDao.class);

    public CoffeeOrderDao() {
        super();
        clazz = CoffeeOrder.class;
    }
}
