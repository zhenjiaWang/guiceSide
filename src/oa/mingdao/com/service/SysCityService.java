package oa.mingdao.com.service;

import com.google.inject.Singleton;
import oa.mingdao.com.entity.SysCity;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Singleton
public class SysCityService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public SysCity getById(Long id) {
        return $(id).get(SysCity.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<SysCity> getListByProvince(Long provinceId) {
        return $($eq("provinceId.id", provinceId), $eq("useYn", "Y"), $order("displayOrder")).list(SysCity.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<SysCity> getList(List<Selector> selectorList) {
        return $(selectorList).list(SysCity.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public SysCity getName(String name) {
        return $($like("name",name)).get(SysCity.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(SysCity sysCity) {
        $(sysCity).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<SysCity> sysCityList) {
        $(sysCityList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(SysCity sysCity) {
        $(sysCity).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(SysCity.class);
    }
}
