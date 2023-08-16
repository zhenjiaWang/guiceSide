package org.guiceside.support.redis.session;

import org.guiceside.web.exception.SessionException;

public class RedisSessionException extends SessionException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RedisSessionException() {
        super();
    }

    public RedisSessionException(String message) {
        super(message);
    }
}