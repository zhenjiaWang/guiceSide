package org.guiceside.support.redis.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created by zhenjiawang on 2016/12/23.
 */
public class RedisSession implements HttpSession, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String key;

    private long creationTime;

    private long lastAccessedTime;

    private int maxInactiveInterval;

    private boolean newSession;

    private boolean available;

    private RedisSession() {

    }

    public RedisSession(String id, int maxInactiveInterval) {
        this.id = id;
        this.maxInactiveInterval = maxInactiveInterval;
        this.key = this.id + RedisSessionUtils.Suffix;
        this.available = false;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void invalidate() {
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override
    public void setAttribute(String attr, Object attrValue) {
    }


    @Override
    public String getAttribute(String attr) {
        return null;
    }


    @Override
    public void removeAttribute(String attr) {
    }


    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }


    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }


    @Override
    public boolean isNew() {
        return newSession;
    }

    public void setNewSession(boolean newSession) {
        this.newSession = newSession;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void putValue(String s, Object o) {

    }

    @Override
    public void removeValue(String s) {

    }
}
