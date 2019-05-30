package org.guiceside.persistence.hibernate;

import java.io.Serializable;
import java.util.Set;

public class PersistenceJPA implements Serializable {

    private Set<Class<?>> annotatedClass;

    public Set<Class<?>> getAnnotatedClass() {
        return annotatedClass;
    }

    public void setAnnotatedClass(Set<Class<?>> annotatedClass) {
        this.annotatedClass = annotatedClass;
    }
}
