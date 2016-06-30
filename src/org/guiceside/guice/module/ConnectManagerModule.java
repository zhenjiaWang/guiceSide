package org.guiceside.guice.module;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;
import org.guiceside.guice.strategy.AbstractInterceptorStrategy;
import org.guiceside.support.hsf.ConnectManager;
import org.guiceside.support.hsf.ConnectManagerInterceptor;

import java.lang.reflect.Method;

/**
 * User:zhenjia(zhenjiaWang@gmail.com) Date:2008-8-5 Time:下午04:28:51
 * Email:(zhenjiaWang@gmail.com) QQ:(119582291)
 * <p>
 * 
 * </p>
 */
public class ConnectManagerModule extends AbstractInterceptorStrategy {

	public Matcher<? super Class<?>> getClassMatcher() {

		return Matchers.any();
	}

	public MethodInterceptor getMethodInterceptor() {

		return new ConnectManagerInterceptor();
	}

	public Matcher<? super Method> getMethodMatcher() {

		return Matchers.annotatedWith(ConnectManager.class);
	}

}
