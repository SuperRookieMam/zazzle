package com.test.example.entity;

import com.simple.base.entity.BaseEntity;
import com.simple.codecreate.feature.annotation.Description;
import com.simple.codecreate.feature.annotation.IsCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 微信账户
 * 
 * Mar 28, 2011
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "T_WEIXIN_ACCOUNT")
public class Account extends BaseEntity {

	/**
	 */
	private static final long serialVersionUID = 5627271591609587914L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
    @Description(label ="编号")
	private Long id;

	/**
	 * 公众号名称
	 */
	@Column(name = "name")
    @Description(label ="公众号名称")
	private String name;

	@Column(name = "services")
    @Description(label ="服务")
	private String services;

	@Column(name = "status")
    @Description(label ="状态")
	private Integer status=0;

	@Column(name = "ispast")
    @Description(label ="ispast")
	private Integer ispast=0;

	@Column(name = "issendemail")
    @Description(label ="发邮件")
	private Integer issendemail=0;

	@Column(name = "words_name")
    @Description(label ="关键字")
	private String words_name;

	@Column(name = "rid")
    @Description(label ="rid")
	private String rid;

	@Column(name = "no")
    @Description(label ="序号")
	private String no;

}
