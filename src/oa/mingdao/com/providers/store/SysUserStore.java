package oa.mingdao.com.providers.store;


import com.google.inject.ImplementedBy;
import oa.mingdao.com.common.StoreException;
import oa.mingdao.com.entity.sys.SysUser;
import oa.mingdao.com.storeImpl.sys.SysUserStoreImp;

@ImplementedBy(SysUserStoreImp.class)
public interface SysUserStore {

    SysUser getById(Long id) throws StoreException
            ;
}
