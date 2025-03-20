package org.eston.project.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasicResp<T> implements Serializable {

    private T data;
    private String message;
    private Integer code;

    public static <T> BasicResp<T> success(T data) {
        BasicResp<T> resp = new BasicResp<>();
        resp.code = 100;
        resp.data = data;
        resp.message = "success";
        return resp;
    }

    public static <T> BasicResp<T> fail(String msg) {
        BasicResp<T> resp = new BasicResp<>();
        resp.code = 0;
        resp.data = null;
        resp.message = msg;
        return resp;
    }

}
