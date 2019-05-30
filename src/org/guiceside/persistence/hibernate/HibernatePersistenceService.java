package org.guiceside.persistence.hibernate;

import com.google.inject.Inject;
import org.guiceside.persistence.PersistenceService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Set;

/**
 * <p>
 * 继承PersistenceService<br/>
 * 实现start()方法初始化SessionFactory
 * </p>
 *
 * @author zhenjia  <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
public class HibernatePersistenceService extends PersistenceService {
    private final SessionFactoryHolder sessionFactoryHolder;

    private final PersistenceJPA persistenceJPA;

    @Inject
    public HibernatePersistenceService(
            SessionFactoryHolder sessionFactoryHolder,
            PersistenceJPA persistenceJPA) {
        this.sessionFactoryHolder = sessionFactoryHolder;
        this.persistenceJPA = persistenceJPA;
    }

    @Override
    public void start() {


        final StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();


        try {
            MetadataSources metadataSources=new MetadataSources(standardServiceRegistry);
            if(persistenceJPA!=null){
                Set<Class<?>> annotatedClass=   persistenceJPA.getAnnotatedClass();
                if(annotatedClass!=null&&!annotatedClass.isEmpty()){
                    for(Class c:annotatedClass){
                        metadataSources.addAnnotatedClass(c);
                    }
                }
            }

            Metadata metadata = metadataSources.getMetadataBuilder()
                    .applyImplicitNamingStrategy( ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
                    .build();


            SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();


            //SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
            sessionFactoryHolder.setSessionFactory(sessionFactory);
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            e.printStackTrace();
        }
//		try {
//			SessionFactory sessionFactory = configuration.buildSessionFactory(registry);
//			sessionFactoryHolder.setSessionFactory(sessionFactory);
//
//		}catch (Exception e){
//			StandardServiceRegistryBuilder.destroy( registry );
//			e.printStackTrace();
//		}
    }

    @Override
    public boolean equals(Object obj) {
        return sessionFactoryHolder
                .equals(((HibernatePersistenceService) obj).sessionFactoryHolder);
    }

    @Override
    public int hashCode() {
        return (sessionFactoryHolder != null ? sessionFactoryHolder.hashCode()
                : 0);
    }
}
