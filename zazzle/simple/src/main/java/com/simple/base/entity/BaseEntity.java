package com.simple.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.codecreate.feature.annotation.Description;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createTime", "modifyTime","createUser","modifyUser"})
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5678900855794670955L;
    @CreatedDate
    @Column(name = "create_time",updatable = false)
    @Description(label = "创建时间")
    private LocalDateTime createTime ;

    @LastModifiedDate
    @Column(name = "modify_time",updatable = false)
    @Description(label = "最后修改时间")
    private LocalDateTime modifyTime ;

    @CreatedBy
    @Column(name = "create_user",updatable = false,insertable = false)
    @Description(label = "创建者")
    private String createUser;

    @LastModifiedBy
    @Column(name = "create_user" ,updatable = false)
    @Description(label = "最后修改者")
    private String modifyUser;
}
