package dao.impl;

import dao.ICoffeeOrderItemDao;
import entities.CoffeeOrderItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
public class CoffeeOrderItemDao extends BaseDao<CoffeeOrderItem> implements ICoffeeOrderItemDao {
    private static Logger log = Logger.getLogger(CoffeeOrderItemDao.class);

    public CoffeeOrderItemDao() {
        super();
        clazz = CoffeeOrderItem.class;
    }

    /**
     *  get all records with type <CoffeeOrderItem> from data base for definite CoffeeOrder
     *
     * @param orderId determine id of CoffeeOrder in database
     * @return a list of all records of type <CoffeeOrderItem> from the database
     *         for definite CoffeeOrder or empty list if there are no entries
     */
    @Override
    public List<CoffeeOrderItem> getAllForOrderId(Serializable orderId) {
        CriteriaQuery<CoffeeOrderItem> criteria = getCriteriaQuery();
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        Root<CoffeeOrderItem> root = (Root<CoffeeOrderItem>) criteria.getRoots().toArray()[0];

        criteria.where(cb.equal(root.get("order").get("id"), orderId));

        List<CoffeeOrderItem> resultList = getListWhere(criteria);
        if (log.isInfoEnabled()) {
            log.info("For Order with id: [" + orderId + "]. Get all Coffee Order Items: " +
                    resultList);
        }
        return resultList;
    }
}
