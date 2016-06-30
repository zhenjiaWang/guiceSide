package oa.mingdao.com.service;

import com.google.inject.Singleton;
import oa.mingdao.com.entity.SysProvince;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;

import java.util.List;

/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Singleton
public class SysProvinceService extends HQuery {
    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public SysProvince getById(Long id) {
        return $(id).get(SysProvince.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<SysProvince> getList() {
       return  $( $eq("useYn", "Y"), $order("displayOrder")).list(SysProvince.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(SysProvince sysProvince) {
        $(sysProvince).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<SysProvince> sysProvinceList) {
        $(sysProvinceList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(SysProvince sysProvince) {
        $(sysProvince).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(SysProvince.class);
    }

}
