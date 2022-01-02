package com.sklcs.fmpa.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class InputFile {
    @ApiModelProperty(value="title")
    private String title;

    @ApiModelProperty(value="content")
    private String content;


    public void settitle(String title) {
        this.title = title;
    }

    public void setcontent(String toString) {
        this.content = toString;
    }
}
