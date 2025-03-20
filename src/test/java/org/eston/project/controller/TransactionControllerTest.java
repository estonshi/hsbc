package org.eston.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eston.project.entity.vo.QueryVO;
import org.eston.project.entity.vo.TransactionVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String,Object> data = new HashMap<>();
    private static final Map<String,Object> dataCreate = new HashMap<>();

    @BeforeAll
    static void dataPrepare() {
        dataCreate.put("1", new TransactionVO(null, "user123", "user456", "SELL", 100.0, System.currentTimeMillis()));
        dataCreate.put("2", new TransactionVO("t00123", "user123", "user456", "RENT", 100.0, System.currentTimeMillis()));
        dataCreate.put("3", new TransactionVO(null, null, "user456", "RENT", 100.0, System.currentTimeMillis()));
        dataCreate.put("4", new TransactionVO(null, "user123", null, "RENT", 100.0, System.currentTimeMillis()));
        dataCreate.put("5", new TransactionVO(null, "user123", "user456", "OTHER", 100.0, System.currentTimeMillis()));
        dataCreate.put("6", new TransactionVO(null, "user123", "user456", "SELL", -0.123, System.currentTimeMillis()));
        dataCreate.put("7", new TransactionVO(null, "user123", "user456", "SELL", 0.0, null));

        data.put("modify1", new TransactionVO("t123", "user123", "user456", "SELL", 99.0, System.currentTimeMillis()));
        data.put("modify2", new TransactionVO("s123", "user123", "user456", "SELL", 99.0, System.currentTimeMillis()));

        data.put("delete1", "t125");
        data.put("delete2", "t000");

        QueryVO param = new QueryVO();
        param.setFrom("user01");
        param.setTo("user03");
        param.setMinValue(0.0);
        param.setMaxValue(1000.0);
        param.setType("SELL");
        param.setPageNum(0);
        param.setPageSize(10);
        data.put("query1", param);
        QueryVO param2 = new QueryVO();
        param2.setTid(List.of("t123", "t124", "t125"));
        param2.setPageNum(0);
        param2.setPageSize(10);
        data.put("query2", param2);
    }

    @Test
    void create() throws Exception {
        for (int i=1; i<=7; i++) {
            System.out.println("-------- create transaction #" + i + "--------");
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transaction/create")
                    .content(mapper.writeValueAsBytes(dataCreate.get(""+i)))
                    .contentType(MediaType.APPLICATION_JSON);
            MvcResult result;
            if (i == 1) {
                result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
            } else {
                result = mockMvc.perform(builder).andExpect(status().is4xxClientError()).andReturn();
            }
            System.out.println(result.getResponse().getContentAsString());
        }
        System.out.println("-------- create transaction end --------");
    }

    @Test
    void modify() throws Exception {
        System.out.println("-------- modify transaction #1 --------");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transaction/modify")
                .content(mapper.writeValueAsBytes(data.get("modify1")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        System.out.println("-------- modify transaction #2 --------");
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.post("/transaction/modify")
                .content(mapper.writeValueAsBytes(data.get("modify2")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result2 = mockMvc.perform(builder2).andExpect(status().is4xxClientError()).andReturn();
        System.out.println(result2.getResponse().getContentAsString());

        System.out.println("-------- modify transaction end --------");
    }

    @Test
    void delete() throws Exception {
        System.out.println("-------- delete transaction #1 --------");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/transaction/delete")
                .param("tid", (String) data.get("delete1"));
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        System.out.println("-------- delete transaction #2 --------");
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.delete("/transaction/delete")
                .param("tid", (String) data.get("delete2"));
        MvcResult result2 = mockMvc.perform(builder2).andExpect(status().is4xxClientError()).andReturn();
        System.out.println(result2.getResponse().getContentAsString());

        System.out.println("-------- delete transaction end --------");
    }

    @Test
    void queryAll() throws Exception {
        System.out.println("-------- query all transaction --------");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/transaction/query-all")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        System.out.println("-------- query all transaction end --------");
    }

    @Test
    void query() throws Exception {
        System.out.println("-------- query transaction #1 --------");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transaction/query")
                .content(mapper.writeValueAsBytes(data.get("query1")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        System.out.println("-------- query transaction #2 --------");
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.post("/transaction/query")
                .content(mapper.writeValueAsBytes(data.get("query2")))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result2 = mockMvc.perform(builder2).andExpect(status().isOk()).andReturn();
        System.out.println(result2.getResponse().getContentAsString());

        System.out.println("-------- query transaction end --------");

    }
}