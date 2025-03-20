package org.eston.project.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.eston.project.conf.exception.BadRequestException;
import org.eston.project.data.TransactionRepository;
import org.eston.project.entity.Transaction;
import org.eston.project.entity.vo.QueryVO;
import org.eston.project.entity.vo.TransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author shiyingchen
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @CacheEvict(value = {"allTransaction", "queryTransaction"})
    public String create(TransactionVO vo) {
        Transaction t = Transaction.of(vo);
        if (null != t.getTid()) {
            throw new BadRequestException("不能指定交易ID");
        }
        t.setTid(UUID.randomUUID().toString());
        repository.save(t);
        return t.getTid();
    }

    @CacheEvict(value = {"allTransaction", "queryTransaction"})
    @Transactional(rollbackOn = Exception.class)
    public void modify(TransactionVO vo) {
        Transaction t = Transaction.of(vo);
        Long id = repository.getIdByTid(t.getTid());
        if(id == null) {
            throw new BadRequestException("无此交易");
        }
        t.setId(id);
        repository.save(t);
    }

    @CacheEvict(value = {"allTransaction", "queryTransaction"})
    @Transactional(rollbackOn = Exception.class)
    public void delete(String tid) {
        Long id = repository.getIdByTid(tid);
        if(id == null) {
            throw new BadRequestException("无此交易");
        }
        repository.deleteById(id);
    }

    @Cacheable(value = "allTransaction")
    public List<TransactionVO> queryAll() {
        return repository.findAll().stream().map(Transaction::toVo).toList();
    }

    @Cacheable(cacheNames = "queryTransaction", key = "#param.toString()")
    public Page<TransactionVO> query(QueryVO param) {
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
        Specification<Transaction> spec = new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(@NonNull Root<Transaction> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
                List<Predicate> pl = new ArrayList<>();
                if (param.getTid() != null) {
                    CriteriaBuilder.In<String> in = criteriaBuilder.in(root.get("tid"));
                    param.getTid().forEach(in::value);
                    pl.add(in);
                }
                if (param.getFrom() != null) {
                    pl.add(criteriaBuilder.equal(root.get("from"), param.getFrom()));
                }
                if (param.getTo() != null) {
                    pl.add(criteriaBuilder.equal(root.get("to"), param.getTo()));
                }if (param.getType() != null) {
                    pl.add(criteriaBuilder.equal(root.get("type"), param.getType()));
                }
                if (param.getMinValue() != null) {
                    pl.add(criteriaBuilder.greaterThanOrEqualTo(root.get("value"), param.getMinValue()));
                }
                if (param.getMaxValue() != null) {
                    pl.add(criteriaBuilder.lessThanOrEqualTo(root.get("value"), param.getMaxValue()));
                }
                return criteriaBuilder.and(pl.toArray(new Predicate[0]));
            }
        };
        Page<Transaction> re = repository.findAll(spec, PageRequest.of(param.getPageNum(),param.getPageSize(),Sort.by(Sort.Direction.DESC,"time")));
        return re.map(Transaction::toVo);
    }

}
