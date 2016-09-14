package br.com.museuid.banco.controle.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ModelSessionFactory {

    private final Logger log = LoggerFactory.getLogger(ModelSessionFactory.class);
    private final String PERSISTENCE_UNIT_NAME = "museuidPU";
    private EntityManagerFactory factory;

    public ModelSessionFactory(){
        try{
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

    public void close(){
        factory.close();
    }
}
