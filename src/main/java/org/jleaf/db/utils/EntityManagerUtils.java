package org.jleaf.db.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author leaf
 * @date 2014-1-26 下午4:19:32
 */
public class EntityManagerUtils {

    private static EntityManagerFactory factory;

    private static ThreadLocal<EntityManager> entityManagerTL = new ThreadLocal<EntityManager>();

    public static EntityManager getEntityManager() {
        return entityManagerTL.get();
    }

    public static EntityManager createEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("jpa");
        }
        return factory.createEntityManager();
    }

    public static void setEntityManager(EntityManager entityManager) {
        entityManagerTL.set(entityManager);
    }

    public static void removeEntityManager() {
        entityManagerTL.remove();
    }

}
