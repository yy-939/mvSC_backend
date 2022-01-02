package com.sklcs.fmpa.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Command {
    @ApiModelProperty(value="title")
    private String title;

    @ApiModelProperty(value="content")
    private String content;

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
