package oa.mingdao.com.bizImpl.sys;

import com.google.inject.Inject;
import net.sf.json.JSONObject;
import oa.mingdao.com.common.BizException;
import oa.mingdao.com.common.StoreException;
import oa.mingdao.com.entity.sys.SysUser;
import oa.mingdao.com.providers.biz.SysUserBiz;
import oa.mingdao.com.providers.store.SysUserStore;
import org.guiceside.commons.JsonUtils;
import org.guiceside.support.hsf.BaseBiz;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class SysUserBizImp extends BaseBiz implements SysUserBiz {
    @Inject
    private SysUserStore sysUserStore;

    @Override
    public String userInfo(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            if (sysUserStore != null) {
                SysUser sysUser = sysUserStore.getById(id);
                if (sysUser != null) {
                    JSONObject userObj = JsonUtils.formIdEntity(sysUser, 0);
                    resultObj.put("userInfo", userObj);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }
}
