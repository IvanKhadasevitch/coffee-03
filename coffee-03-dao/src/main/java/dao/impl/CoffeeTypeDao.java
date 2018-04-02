package dao.impl;

import dao.ICoffeeTypeDao;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
public class CoffeeTypeDao extends BaseDao<CoffeeType> implements ICoffeeTypeDao {
    private static Logger log = Logger.getLogger(CoffeeTypeDao.class);

    public CoffeeTypeDao() {
        super();
        clazz = CoffeeType.class;
    }

    /**
     * returns an entity with an id from the database
     *
     * @param entityId determine id of entity in database
     * @return entity with type <CoffeeType> from the database,
     *         or null if such an entity was not found
     */
    @Override
    public CoffeeType get(Serializable entityId) {
        CoffeeType coffeeType = super.get(entityId);
        if (coffeeType != null) {
            coffeeType.setDisabledFlag('Y' == coffeeType.getDisabled()
                    ? DisabledFlag.Y
                    : DisabledFlag.N);
        }
        return coffeeType;
    }

    /**
     *  get all records with type <CoffeeType> from data base for definite disabledFlag
     *
     * @param disabledFlag determine show or not CoffeeType for user
     * @return a list of all records of type <CoffeeType> from the database
     *         with definite disabledFlag or empty list if there are no entries
     */
    @Override
    public List<CoffeeType> getAllForDisabledFlag(DisabledFlag disabledFlag) {
        CriteriaQuery<CoffeeType> criteria = getCriteriaQuery();
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        Root<CoffeeType> root = (Root<CoffeeType>) criteria.getRoots().toArray()[0];

        Character disabled = DisabledFlag.Y.equals(disabledFlag) ? 'Y' : 'N';
        criteria.where(cb.equal(root.get("disabled"), disabled));

        List<CoffeeType> resultList = getListWhere(criteria);
        resultList.forEach(p ->
                p.setDisabledFlag(Character.valueOf('Y').equals(p.getDisabled())
                        ? DisabledFlag.Y : DisabledFlag.N));
        if (log.isInfoEnabled()) {
            log.info("Get all Coffee types with disabledFlag: [" + disabledFlag + "]. : " +
                    resultList);
        }
        return resultList;
    }

    /**
     * get all records with type <CoffeeType> from DB
     *
     * @return a list of all records of type <CoffeeType> from the database
     * or empty list if there are no entries
     */
    @Override
    public List<CoffeeType> getAll() {
        List<CoffeeType> resultList = super.getAll();
        resultList.forEach(p ->
                p.setDisabledFlag(Character.valueOf('Y').equals(p.getDisabled())
                        ? DisabledFlag.Y : DisabledFlag.N));

        return resultList;
    }
}
