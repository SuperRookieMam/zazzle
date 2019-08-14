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
 * 微餐  目录表
 * @author yj
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "T_WEICAN_YUYUE")
public class WeiCanYuYue extends BaseEntity{

	/**
	 */
	private static final long serialVersionUID = -5675676867876981L;

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
	  * 预约人微信openid
	  */
	 @Column(name = "openid")
	 private String openid;
	
	/**
	 * 预约人手机
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 预约人数
	 */
	@Column(name = "_num")
	private String _num;
	
 
	
	/**
	 * 性别 1男  0女
	 */
	@Column(name = "_sex")
	private String _sex;
	
	/**
	 * 预定菜品 数量  价格  详情
	 * json[{id,shuliang,jiage},{}]
	 */
	@Column(name="caipinJson",length=6444)
	private String caipinJson;


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
	 * 预约就餐时间
	 */
	@Column(name = "eattime")
	private String eattime;
	
	/**
	 * 预约留言
	 */
	@Column(name = "liuyan")
	private String liuyan;
	
	/**
	 * 预约状态   已使用 1    未使用 0
	 */
	@Column(name = "status")
	private String status="0";

	
	
	//--------------预约支付
	/**
	 * 预约生成的订单号
	 */
	@Column(name = "orderid")
	private String orderid;
	
	/**
	 * 预约 付款成功后的 微信(支付宝) 交易编号
	 */
	@Column(name = "order_trade_no")
	private String order_trade_no;
	
	/**
	 * 预约订单状态  未支付 0  已支付 1
	 */
	@Column(name = "ordre_status")
	private String ordre_status;
	
	/**
	 * 预约订单付款方式   1微信  2 支付宝   3现金
	 */
	@Column(name = "order_pay_way")
	private String order_pay_way;


	/**
	 * 预约订单付款账号  
	 */
	@Column(name = "order_pay_account")
	private String order_pay_account;

	/**
	 * 预约类型  大厅 还是包房
	 */
	@Column(name = "yuyue_type")
	private String yuyue_type;
	
	/**
	 * 预约类型    堂吃 0  预约 1 外卖2
	 */
	@Column(name = "yy_leixing")
	private String yy_leixing;
	
	/**
	 * 预约 地址
	 */
	@Column(name = "yy_address")
	private String yy_address;
	
	/**
	 * 堂吃 桌号
	 */
	@Column(name = "yy_zhuohao")
	private String yy_zhuohao;
	
	/**
	 * 预约支付完成时间 
	 */
	@Column(name = "order_pay_time",updatable = false)
	private LocalDateTime order_pay_time=LocalDateTime.now() ;

}
