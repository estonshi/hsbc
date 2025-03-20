package org.eston.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eston.project.entity.vo.BasicResp;
import org.eston.project.entity.vo.QueryVO;
import org.eston.project.entity.vo.TransactionVO;
import org.eston.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shiyingchen
 */
@RestController
@Tag(name = "交易接口", description = "交易接口")
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "创建交易")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BasicResp<String>> create(@RequestBody @Validated TransactionVO vo) {
        String tid = transactionService.create(vo);
        return new ResponseEntity<>(BasicResp.success(tid), HttpStatus.OK);
    }

    @Operation(summary = "更改交易")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<BasicResp<TransactionVO>> modify(@RequestBody @Validated TransactionVO vo) {
        transactionService.modify(vo);
        return new ResponseEntity<>(BasicResp.success(vo), HttpStatus.OK);
    }

    @Operation(summary = "删除交易")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<BasicResp<String>> delete(@RequestParam("tid") String tid) {
        transactionService.delete(tid);
        return new ResponseEntity<>(BasicResp.success(tid), HttpStatus.OK);
    }

    @Operation(summary = "查询全部交易")
    @RequestMapping(value = "/query-all", method = RequestMethod.GET)
    public ResponseEntity<BasicResp<List<TransactionVO>>> queryAll() {
        List<TransactionVO> data = transactionService.queryAll();
        return new ResponseEntity<>(BasicResp.success(data), HttpStatus.OK);
    }

    @Operation(summary = "条件查询交易列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<BasicResp<Page<TransactionVO>>> query(@RequestBody @Validated QueryVO param) {
        Page<TransactionVO> data = transactionService.query(param);
        return new ResponseEntity<>(BasicResp.success(data), HttpStatus.OK);
    }

}
