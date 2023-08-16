package oa.mingdao.com.entity;


import org.guiceside.persistence.entity.IdEntityPK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by zhenjiaWang on 16/7/13.
 */
@Embeddable
public class EntrustO2BuyPK extends IdEntityPK implements Serializable {
    private Long id;

    private Long userId;

    public EntrustO2BuyPK(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public EntrustO2BuyPK() {

    }

    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntrustO2BuyPK other = (EntrustO2BuyPK) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (id != other.id)
            return false;
        return true;
    }
}
