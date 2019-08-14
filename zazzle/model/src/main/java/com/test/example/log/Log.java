package com.test.example.log;

import com.simple.base.entity.BaseEntity;
import com.simple.codecreate.feature.annotation.Description;
import com.simple.codecreate.feature.annotation.IsCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 日志对象
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IsCreate
@Table(name = "t_log")
public class Log extends BaseEntity {

	private static final long serialVersionUID = -5398795297842978376L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	@Description(label ="编号")
	private Long id;

	/** 用户名 */
	@Column(name = "username")
	@Description(label ="用户名")
	private String username;

	/** 模块 */
	@Column(name = "module")
	@Description(label ="模块")
	private String module;

	/** 参数值 */
	@Column(name = "params")
	@Description(label ="参数值")
	private String params;

	@Column(name = "remark")
	@Description(label ="备注")
	private String remark;

	/** 是否执行成功 */
	@Column(name = "flag")
	@Description(label ="是否执行成功")
	private Boolean flag;


}
