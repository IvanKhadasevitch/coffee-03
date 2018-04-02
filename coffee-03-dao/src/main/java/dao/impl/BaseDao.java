package dao.impl;

import dao.IDao;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository()
public class BaseDao<T> implements IDao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);

    @PersistenceContext
    @Getter
    private EntityManager em;

    Class<T> clazz;


    /**
     * Saves the entity type <T> in the database if @param entity != null
     *
     * @param entity determine entity with type <T>
     * @return saved entity with not null id
     */
    @Override
    public T add(@NonNull T entity) {
        em.persist(entity);
        if (log.isInfoEnabled()) {
            log.info("Save: " + entity);
        }

        return entity;
    }

    /**
     * update an entity in the database if @param entity != null
     *
     * @param entity determine a new entity to be updated in the database
     * @return updated in the database entity
     */
    @Override
    public T update(@NonNull T entity) {
        em.merge(entity);
        if (log.isInfoEnabled()) {
            log.info("Update: " + entity);
        }
        return entity;
    }

    /**
     * returns an entity with an id from the database
     *
     * @param entityId determine id of entity in database
     * @return entity with type <T> from the database, or null if such an entity was not found
     */
    @Override
    public T get(Serializable entityId) {
        // return null if entity not exist
        T entity = em.find(this.clazz, entityId);
        if (log.isInfoEnabled()) {
            log.info(String.format("Get entity [%s] with id [%s]. Entity: %s",
                    this.clazz, entityId, entity));
        }

        return entity;
    }

    /**
     * removes from the database an entity with an id = entityId
     *
     * @param entityId determine id of entity in database
     */
    @Override
    public void delete(Serializable entityId) {
        T findEntity = this.get(entityId);
        if (log.isInfoEnabled()) {
            log.info("Delete: " + findEntity);
        }
        em.remove(findEntity);
    }

    CriteriaQuery<T> getCriteriaQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> criteria = cb.createQuery(this.clazz);

        Root<T> root = criteria.from(this.clazz);
        criteria.select(root);

        return criteria;
    }
    T getSingleResultWhere(CriteriaQuery<T> criteria) {

        return em.createQuery(criteria).getSingleResult();
    }

    List<T> getListWhere(CriteriaQuery<T> criteria) {

        return em.createQuery(criteria).getResultList();
    }

    /**
     * get all records with type <T> from DB
     *
     * @return a list of all records of type <T> from the database
     *         or empty list if there are no entries
     */
    @Override
    public List<T> getAll() {
        List<T> resultList = em.createQuery(getCriteriaQuery()).getResultList();
        if (log.isInfoEnabled()) {
            log.info("Get all entities: " + resultList);
        }

        return resultList;
    }
}
