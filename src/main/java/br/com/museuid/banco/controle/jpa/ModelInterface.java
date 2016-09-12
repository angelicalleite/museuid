package br.com.museuid.banco.controle.jpa;

import javax.transaction.SystemException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by c1278778 on 01/09/2016.
 */
public interface ModelInterface<T extends Serializable> {
    public T add(T model) ;
    public void update(T model);
    public List<T> lists(T model);
    public T get(Class clazz, Integer id);
    public boolean delete(T model);
    public T findSQL(String sql);
}
