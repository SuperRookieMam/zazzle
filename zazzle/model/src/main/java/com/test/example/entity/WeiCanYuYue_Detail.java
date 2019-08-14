package com.test.example.entity;

import com.simple.base.entity.BaseEntity;
import com.simple.codecreate.feature.annotation.IsCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/******
 * 微餐  预约 详细表 
 * @author MK
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "T_WEICAN_YUYUE_DETAIL")
public class WeiCanYuYue_Detail extends BaseEntity{

	/**
	 */
	private static final long serialVersionUID = 168678797898L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	
	/**
	 * 预约ID
	 */
	@Column(name = "weicanYuYue")
	private WeiCanYuYue weicanYuYue;
	
	 
	 /**
	  * 菜品ID 
	  */
	 @Column(name = "caipinID")
	 private String caipinID;
	
	/**
	 * 菜品价格
	 */
	@Column(name = "caipinPrice")
	private double caipinPrice;
	
	/**
	 * 菜品数量 
	 */
	@Column(name = "caipinNumber")
	private String caipinNumber;
	 

	 
}
