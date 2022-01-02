package com.sklcs.fmpa.back;

/*
ENUM reference page: https://www.cnblogs.com/lixinjun8080/p/10910835.html
 */

public enum RespCode {
        SUCCESS(666, "操作成功"),
        FAIL(-1, "操作失败"),
        WARN(100, "Empty message box"),
        // For verify
        INVALID(40001, "Command invalid"),
        TRUE(60001, "Verify completed. The Contract Satisfies The Property!"),
        FALSE(40004, "Verify completed. The Contract Dose Not Satisfy The Property, Counter Example Generating!");


    private int code;
    private String msg;

    RespCode(){}

    RespCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
