package services;

import java.io.Serializable;
import java.util.List;

public interface IService <T> {
    T add(T t);

    T update(T t);

    T get(Serializable id);

    void delete(Serializable id);

    List<T> getAll();
}
