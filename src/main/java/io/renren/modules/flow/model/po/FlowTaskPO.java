package io.renren.modules.flow.model.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import io.renren.common.utils.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 任务
 * </p>
 *
 * @author jgl
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("flow_task")
@ApiModel(value="FlowTaskPO对象", description="任务")
public class FlowTaskPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告主id")
    @TableField("advertiser_id")
    private Long advertiserId;

    @ApiModelProperty(value = "任务类型id")
    @TableField("task_type_id")
    private Long taskTypeId;

    @ApiModelProperty(value = "任务id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "单价（点击一次的价格）")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "总数(总点击次数)")
    @TableField("total")
    private Long total;

    @ApiModelProperty(value = "剩余点击次数")
    @TableField("stock")
    private Long stock;

    @ApiModelProperty(value = "已点击次数")
    @TableField("sale_count")
    private Long saleCount;

    @ApiModelProperty(value = "（每个用户）限购数量")
    @TableField("limit_count")
    private Integer limitCount;

    @ApiModelProperty(value = "上架（0下架， 1上架）")
    @TableField("upper_shelf")
    private Integer upperShelf;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "是否删除 0否 1是")
    @TableField("isDel")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("updated_time")
    private Date updatedTime;

    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private Date endTime;


}
