package com.test.example.entity;

import com.simple.base.entity.BaseEntity;
import com.simple.codecreate.feature.annotation.IsCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/******
 * 微餐  目录表
 * @author mk
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "T_WEICAN_MENU")
public class WeiCanMenu extends BaseEntity{

	/**
	 */
	private static final long serialVersionUID = 67587681L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 是否显示
	 */
	@Column(name = "isshow")
	private String isshow;

	/**
	 * 当前目录包含的菜品JSON 信息 
	 */
	@Column(name = "caipin_json")
	private String caipin_json;
	
	
	/**
	 * 父级ID    没有就为0 
	 */
	@Column(name = "pid")
	private String pid;

	/**
	 * Account
	 */
	@Column(name = "account")
	private Account account;

	/**
	 * SHOP 店铺
	 */
	@Column(name = "shop")
	private Shop shop;

}
