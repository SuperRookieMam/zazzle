package com.test.example.entity;

import com.simple.base.entity.BaseEntity;
import com.simple.codecreate.feature.annotation.IsCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/******
 * 微餐  菜品表
 * @author yj
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "T_WEICAN_CAIPIN")
public class WeiCanCaiPin extends BaseEntity{

	/**
	 */
	private static final long serialVersionUID = -5269000637180895183L;

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
	 *  大图
	 */
	@Column(name = "bigpic")
	private String bigpic;

	
	/**
	 *  列表图片
	 */
	@Column(name = "listpic")
	private String listpic;
	
	/**
	 * 菜品介绍
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 菜品价格
	 */
	@Column(name = "price")
	private Double price=0.0;


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
	
	/**
	 * 菜品添加时间
	 */
	@Column(name = "createtime",updatable = false)
	private LocalDateTime createtime=LocalDateTime.now() ;


	
	/**
	 * 菜品状态 上架  1  下架 0 
	 */
	@Column(name = "status")
	private String status="1";

 
}
