package services.impl;

import dao.IDao;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.IService;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(transactionManager = "txManager")
@NoArgsConstructor
public class BaseService<T> implements IService<T> {
    private static Logger log = Logger.getLogger(BaseService.class);

    @Autowired
    private IDao<T> baseDao;

    @Autowired
    public BaseService(IDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    public T add(T t) {
        return baseDao.add(t);
    }

    public T update(T t) {
        return baseDao.update(t);
    }

    public T get(Serializable id) {
        return baseDao.get(id);
    }

    public void delete(Serializable id) {
        baseDao.delete(id);
    }

    public List<T> getAll() {
        return baseDao.getAll();
    }
}
