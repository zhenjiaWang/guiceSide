package oa.mingdao.com.storeImpl.sys;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import oa.mingdao.com.common.StoreException;
import oa.mingdao.com.entity.sys.SysUser;
import oa.mingdao.com.providers.store.SysUserStore;
import oa.mingdao.com.service.sys.SysUserService;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

@Singleton
public class SysUserStoreImp implements SysUserStore {

    @Inject
    private SysUserService sysUserService;

    @Override
    @ConnectManager
    public SysUser getById(Long id) throws StoreException {
        try {
            return sysUserService.getById(id);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
