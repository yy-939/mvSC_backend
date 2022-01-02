package com.sklcs.fmpa.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class Message implements Serializable {

    @ApiModelProperty(value="information")
    private String info;

    public String getInfo(){
        return info;
    }

    public String setInfo(String info){
        this.info = info;
        return "Content of this message has been modified: " + info;
    }



}
