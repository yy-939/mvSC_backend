package com.sklcs.fmpa.back;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data // lombok https://blog.csdn.net/qq_37958578/article/details/75099226
public class Response {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object data;

    public Response(){ }

    public Response(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }


    public Response(int code, String msg, Object data){
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

}
