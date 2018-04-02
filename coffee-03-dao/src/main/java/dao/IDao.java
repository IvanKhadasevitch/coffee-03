package dao;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

public interface IDao<T> {
    /**
     * Saves the entity type <T> in the database if @param t != null
     *
     * @param t determine entity with type <T>
     * @return saved entity with not null id
     */
    T add(@NonNull  T t);

    /**
     * update an entity t in the database if @param t != null
     *
     * @param t determine a new entity to be updated in the database
     * @return updated in the database entity
     */
    T update(@NonNull T t);

    /**
     * returns an entity with an id from the database
     *
     * @param entityId determine id of entity in database
     * @return entity with type <T> from the database, or null if such an entity was not found
     */
    T get(Serializable entityId);

    /**
     * removes from the database an entity with an id = entityId
     *
     * @param entityId determine id of entity in database
     */
    void delete(Serializable entityId);

    /**
     * get all records with type <T> from DB
     *
     * @return a list of all records of type <T> from the database
     *         or empty list if there are no entries
     */
    List<T> getAll();
}
