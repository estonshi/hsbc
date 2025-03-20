package org.eston.project.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * @author shiyingchen
 */
@Data
@Schema(name = "查询条件")
public class QueryVO {

    @Schema(name = "根据交易ID批量查询交易")
    private List<String> tid;

    @Schema(name = "根据交易发起方用户查询交易")
    private String from;

    @Schema(name = "根据交易对手方用户查询交易")
    private String to;

    @Schema(name = "查询交易额大于该值的交易")
    private Double minValue;

    @Schema(name = "查询交易额小于该值的交易")
    private Double maxValue;

    @Schema(name = "根据交易类型过滤")
    @Pattern(regexp = "^(SELL|RENT)$", message = "交易类型错误")
    private String type;

    @Schema(name = "页数")
    @Min(value = 0, message = "不合法的页数")
    private Integer pageNum;

    @Schema(name = "页大小")
    @Min(value = 1, message = "不合法的页大小")
    private Integer pageSize;

    @Override
    public String toString() {
        return "tid=" + (tid!=null?tid.toString():"") + ","
                + "from=" + (from!=null?from:"") + ","
                + "to=" + (to!=null?to:"") + ","
                + "minValue=" + (minValue!=null?minValue:"") + ","
                + "maxValue=" + (maxValue!=null?maxValue:"") + ","
                + "pageNum=" + pageNum + ",pageSize=" + pageSize;
    }

}
