package br.com.museuid.banco.controle.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class ModelDAO<T extends Serializable> extends ModelSessionFactory  implements ModelInterface<T> {

    private static final Logger log = LoggerFactory.getLogger(ModelDAO.class);

    public EntityManager getSession(){
        return getEntityManager();
    }

    @Override
    public T add(T model) {
        EntityManager session = getEntityManager();
        EntityTransaction tx = null;
        T obj = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            obj = session.merge(model);
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            log.warn(e.getMessage(), e);
        }finally {
            session.close();
        }

        return obj;
    }

    @Override
    public void update(T model) {
        EntityManager session = getEntityManager();
        EntityTransaction tx = null;
        Integer id = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            session.persist(model);
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            log.warn(e.getMessage(), e);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(T model) {
        EntityManager session = getEntityManager();
        EntityTransaction tx = null;
        boolean status = false;
        try{
            tx = session.getTransaction();
            tx.begin();
            session.remove(session.merge(model));
            tx.commit();
            status = true;
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            log.warn(e.getMessage(), e);
        }finally {
            session.close();
        }

        return status;
    }

    @Override
    public List lists(T model) {
        EntityManager session = getEntityManager();
        EntityTransaction tx = null;
        List lists = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            lists = session.createQuery("FROM "+model.getClass().getSimpleName()).getResultList();
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            log.warn(e.getMessage(), e);
        }finally {
            session.close();
        }

        return lists;
    }

    @Override
    public T get(Class clazz, Integer id) {
        EntityManager session = getEntityManager();
        T obj = null;
        try {
            obj = (T) session.find(clazz, id);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            session.close();
        }

        return obj;
    }

    @Override
    public T findSQL(String sql) {
        EntityManager session = getEntityManager();
        T obj = null;
        try {
            Query query = session.createQuery(sql);
            obj = (T) query.getResultList();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            session.close();
        }

        return obj;
    }
}
