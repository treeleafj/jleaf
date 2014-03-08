package org.jleaf.db.interceptor;

import javax.persistence.EntityManager;

import org.jleaf.db.utils.EntityManagerUtils;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

/**
 * JPA的EntityManager事务管理
 *
 * @author leaf
 * @date 2014-1-31 下午7:23:19
 */
@SuppressWarnings("serial")
public class EntityTranscationInterceptor implements Interceptor {

//	private static Logger log = Logger.getLogger(EntityTranscationInterceptor.class);

    @Override
    public boolean begin(ActionInvocation ai) {
        EntityManager em = EntityManagerUtils.createEntityManager();
        EntityManagerUtils.setEntityManager(em);

        em.getTransaction().begin();
        return true;
    }

    @Override
    public void end(ActionInvocation ai) {
        EntityManagerUtils.getEntityManager().getTransaction().commit();
    }


}
