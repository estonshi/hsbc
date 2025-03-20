package org.eston.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.eston.project.entity.vo.TransactionVO;

/**
 * 交易数据实体
 * @author shiyingchen
 */
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tid")
    private String tid;

    @Column(name = "from_user")
    private String from;

    @Column(name = "to_user")
    private String to;

    @Column(name = "type")
    private String type;

    @Column(name = "value_curr")
    private Double value;

    @Column(name = "create_time")
    private Long time;

    public static Transaction of(TransactionVO vo) {
        Transaction transaction = new Transaction();
        transaction.setTid(vo.getTid());
        transaction.setFrom(vo.getFrom());
        transaction.setTo(vo.getTo());
        transaction.setType(vo.getType());
        transaction.setValue(vo.getValue());
        transaction.setTime(vo.getTime());
        return transaction;
    }

    public TransactionVO toVo() {
        return new TransactionVO(this.getTid(), this.getFrom(), this.getTo(), this.getType(), this.getValue(), this.getTime());
    }

}
