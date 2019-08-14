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
 * 门店信息
 * 
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_SHOP")
@IsCreate
public class Shop extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3453451L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
    @Description(label ="编号")
	private Long id;


	@Column(name = "name")
    @Description(label ="店铺名称")
	private String name;

	/**
	 * Account
	 */
	@Column(name = "account")
	private Account account;

	@Column(name = "shopType")
	private Integer  shopType=0;//门店类型

	@Column(name = "tel")
	private String tel;

	@Column(name = "addr")
	private String addr;

	@Column(name = "cover_url")
	private String cover_url;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;

	@Column(name = "content")
	private String content;

	/**
	 * 小票机 用户编号
	 */
	@Column(name = "xp_partner")
	private String xp_partner;

	/**
	 * 小票机 打印机终端号
	 */
	@Column(name = "xp_machine_code")
	private String xp_machine_code;

	/**
	 * 小票机 apiKey   API密钥
	 */
	@Column(name = "xp_apiKey")
	private String xp_apiKey;

	/**
	 * 小票机 mKey 打印机密钥
	 */
	@Column(name = "xp_mKey")
	private String xp_mKey;

	/**
	 * 堂吃  是否打印小票
	 *  0 不出票     1 下单就出票   2 支付成功才出票
	 */
	@Column(name = "tc_chupiao")
	private String tc_chupiao;

	/**
	 * 预约  是否打印小票
	 *  0 不出票     1 下单就出票   2 支付成功才出票
	 */
	@Column(name = "yy_chupiao")
	private String yy_chupiao;

	/**
	 * 外卖  是否打印小票
	 *  0 不出票     1 下单就出票   2 支付成功才出票
	 */
	@Column(name = "wm_chupiao")
	private String wm_chupiao;

}
