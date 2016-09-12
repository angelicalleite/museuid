package br.com.museuid.banco.controle.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class ModelSessionFactory {

    private final Logger log = LoggerFactory.getLogger(ModelSessionFactory.class);
    private static EntityManagerFactory factory;
    public static ModelSessionFactory INSTANCE = new ModelSessionFactory();

    private ModelSessionFactory(){
        try{
            factory = Persistence.createEntityManagerFactory("museuidPU");
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static ModelSessionFactory getInstance(){
        return INSTANCE;
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

    public void close(){
        factory.close();
    }
}
