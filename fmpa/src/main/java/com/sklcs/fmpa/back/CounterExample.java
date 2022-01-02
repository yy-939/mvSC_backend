package com.sklcs.fmpa.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data // lombok https://blog.csdn.net/qq_37958578/article/details/75099226
public class CounterExample {
    @ApiModelProperty(value="information")
    private String info;
}
