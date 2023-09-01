package oa.mingdao.com.service.sys;

import com.google.inject.Singleton;
import oa.mingdao.com.entity.sys.SysUser;
import oa.mingdao.com.providers.store.SysUserStore;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;

@Singleton
public class SysUserService extends HQuery implements SysUserStore {


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public SysUser getById(Long id) {
        return $(id).get(SysUser.class);
    }


}
