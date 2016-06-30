package org.guiceside.support.hsf;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.guiceside.persistence.WorkManager;
import org.guiceside.persistence.hibernate.SessionFactoryHolder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import java.lang.reflect.Method;

/**
 * User:zhenjiawang(zhenjiaWang@gmail.com) Date:2008-7-22 Time:下午05:19:22
 * Email:(zhenjiaWang@gmail.com) QQ:(119582291)
 * <p>
 * 针对@Transactional 处理简单事务
 * </p>
 */
public class ConnectManagerInterceptor implements MethodInterceptor {

	private static final  Logger log=Logger.getLogger(ConnectManagerInterceptor.class);


	@ConnectManager
	private static class Internal {
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {


		ConnectManager connectManager = readConnectManagerMetadata(methodInvocation);
		Object result=null;
		if(connectManager!=null){
			beginWork();
			result = methodInvocation.proceed();
			endWork();
		}
		return result;
	}

	private ConnectManager readConnectManagerMetadata(
			MethodInvocation methodInvocation) {
		ConnectManager connectManager;
		Method method = methodInvocation.getMethod();
		Class<?> targetClass = methodInvocation.getThis().getClass()
				.getSuperclass();
		if (method.isAnnotationPresent(ConnectManager.class)) {
			connectManager = method.getAnnotation(ConnectManager.class);
		} else if (targetClass.isAnnotationPresent(ConnectManager.class)) {
			connectManager = targetClass.getAnnotation(ConnectManager.class);
		} else {
			connectManager = Internal.class.getAnnotation(ConnectManager.class);
		}
		return connectManager;
	}


	public void beginWork() {
		if (ManagedSessionContext.hasBind(SessionFactoryHolder
				.getCurrentSessionFactory()))
			return;
		Session session = SessionFactoryHolder.getCurrentSessionFactory()
				.openSession();

		ManagedSessionContext.bind(session);
	}

	public void endWork() {
		SessionFactory sessionFactory = SessionFactoryHolder
				.getCurrentSessionFactory();
		if (!ManagedSessionContext.hasBind(sessionFactory))
			return;
		try {
			Session session = sessionFactory.getCurrentSession();

			session.close();
		} finally {
			ManagedSessionContext.unbind(sessionFactory);
		}
	}

}
