package org.eston.project.data;

import org.eston.project.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 交易数据类
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t.id from transactions t where t.tid = :tid for update", nativeQuery = true)
    Long getIdByTid(String tid);

    Page<Transaction> findAll(Specification<Transaction> querySpec, Pageable pageable);

}
