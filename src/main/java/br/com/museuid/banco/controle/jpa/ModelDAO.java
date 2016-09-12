package br.com.museuid.banco.controle.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class ModelDAO<T extends Serializable> implements ModelInterface<T> {

    private static final Logger log = LoggerFactory.getLogger(ModelDAO.class);
    private static ModelSessionFactory factory;

    public ModelDAO(){
        factory = ModelSessionFactory.getInstance();
    }

    public ModelDAO(ModelSessionFactory factory){
        factory = ModelSessionFactory.getInstance();
    }

    public EntityManager getSession(){
        factory = ModelSessionFactory.getInstance();
        return factory.getEntityManager();
    }

    @Override
    public T add(T model) {
        EntityManager session = factory.getEntityManager();
        EntityTransaction tx = null;
        T id = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            id = session.merge(model);
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return id;
    }

    @Override
    public void update(T model) {
        EntityManager session = factory.getEntityManager();
        EntityTransaction tx = null;
        Integer id = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            session.persist(model);
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public List lists(T model) {
        EntityManager session = factory.getEntityManager();
        EntityTransaction tx = null;
        List lists = null;
        try{
            tx = session.getTransaction();
            tx.begin();
            lists = session.createQuery("FROM "+model.getClass().getSimpleName()).getResultList();
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return lists;
    }

    @Override
    public T get(Class clazz, Integer id) {
        EntityManager session = factory.getEntityManager();
        T obj = null;
        try {
            obj = (T) session.find(clazz, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return obj;
    }

    @Override
    public T findSQL(String sql) {
        EntityManager session = factory.getEntityManager();
        T obj = null;
        try {
            Query query = session.createQuery(sql);
            obj = (T) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return obj;
    }

    @Override
    public boolean delete(T model) {
        EntityManager session = factory.getEntityManager();
        EntityTransaction tx = null;
        boolean status = false;
        try{
            tx = session.getTransaction();
            tx.begin();
            session.remove(model);
            tx.commit();
            status = true;
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return status;
    }
}
