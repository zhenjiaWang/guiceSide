package oa.mingdao.com.entity;

import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by wangjia on 14-7-29.
 */
@XmlRootElement
@Entity
@Table(name = "SYS_PROVINCE")
public class SysProvince extends IdEntity implements Tracker {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer displayOrder;

    private String note;

    private Date created;

    private String createdBy;

    private Date updated;

    private String updatedBy;

    private String useYn;


    @Column(name = "CREATED", updatable = false)
    public Date getCreated() {
        return created;
    }

    @Column(name = "CREATED_BY", updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    @Column(name = "UPDATED")
    public Date getUpdated() {
        return updated;
    }

    @Column(name = "UPDATED_BY")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Column(name = "USE_YN")
    public String getUseYn() {
        return useYn;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DISPLAY_ORDER")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
