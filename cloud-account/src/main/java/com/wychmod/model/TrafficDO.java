package com.wychmod.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 二当家小D
 * @since 2025-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("traffic")
public class TrafficDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 每天限制多少条，短链
     */
    private Integer dayLimit;

    /**
     * 当天用了多少条，短链
     */
    private Integer dayUsed;

    /**
     * 总次数，活码才用
     */
    private Integer totalLimit;

    /**
     * 账号
     */
    private Long accountNo;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 产品层级：FIRST青铜、SECOND黄金、THIRD钻石
     */
    private String level;

    /**
     * 过期日期
     */
    private Date expiredDate;

    /**
     * 插件类型
     */
    private String pluginType;

    /**
     * 商品主键
     */
    private Long productId;

    private Date gmtCreate;

    private Date gmtModified;


}
