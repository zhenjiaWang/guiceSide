package oa.mingdao.com.providers.biz;


import com.google.inject.ImplementedBy;
import oa.mingdao.com.bizImpl.sys.SysUserBizImp;
import oa.mingdao.com.common.BizException;

@ImplementedBy(SysUserBizImp.class)
public interface SysUserBiz {
    String userInfo(Long id) throws BizException;
}
