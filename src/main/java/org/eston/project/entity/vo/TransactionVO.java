package org.eston.project.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * 交易数据实体VO
 * @author shiyingchen
 */
@Data
@Schema(name = "交易数据实体")
public class TransactionVO implements Serializable {

    @Schema(name = "交易ID", example = "t123456")
    private String tid;

    @Schema(name = "交易发起方ID", example = "user001")
    @NotBlank(message = "交易发起方不能为空")
    private String from;

    @Schema(name = "交易对手方ID", example = "user002")
    @NotBlank(message = "交易对手方不能为空")
    private String to;

    @Schema(name = "交易类型", description = "SELL/RENT")
    @Pattern(regexp = "^(SELL|RENT)$", message = "交易类型错误")
    private String type;

    @Schema(name = "交易额", example = "1.23")
    @Min(value = 0, message = "交易额不能为负")
    private Double value;

    @Schema(name = "交易时间，unix毫秒时间戳", example = "1742449608257")
    @NotNull
    private Long time;

    public TransactionVO(String tid, String from, String to, String type, Double value, Long time) {
        this.tid = tid;
        this.from = from;
        this.to = to;
        this.type = type;
        this.value = value;
        this.time = time;
    }

}
