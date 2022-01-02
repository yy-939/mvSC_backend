package com.sklcs.fmpa.back.controller;


import com.sklcs.fmpa.back.Message;
import com.sklcs.fmpa.back.RespCode;
import com.sklcs.fmpa.back.Response;
import com.sklcs.fmpa.back.service.MessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

// Swagger
@Api(value="Message", protocols="http")

//json
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /*
    =================================for testing===============
     */
    @PutMapping("/receive/{msg}") // {}和parameter对应，如果多个{}&{}
    @ApiOperation(value="receive")
    public String test(@PathVariable String msg){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        return msg + date;
    }

    @PutMapping("/receive2")
    @ApiOperation(value="receive2")
    public Response test2(@RequestBody Message msg){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        msg.setInfo(msg.getInfo()+date);
        return new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), msg);
    }

    @GetMapping("/test")
    @ApiOperation(value="test resp")
    public Response resp(){
        Map<String, Object> map = new HashMap<>();
        Message msg = new Message();
        msg.setInfo("i am message");
        map.put("用户信息", msg);
        map.put("在干啥呢", "working");
        map.put("备注说明", "hello world");
        map.put("一串数字", 123456789);
        return new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), map);
    }

    /*
    =================================Messages===============
     */

    @GetMapping("/listAll")
    @ApiOperation(value="Look up all messages")
    public Map<Long, Message> list(){
        return messageService.list();
    }


    @PostMapping("/add")
    @ApiOperation(value = "Add new message")
    @ResponseBody
    public Response add(@RequestBody Message msg){
        return messageService.addMsg(msg);
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete message")
    public Response delete(@RequestBody List<String> lst){
        return messageService.deleteMsg(lst);
    }


    @PutMapping("/update")
    @ApiOperation(value = "update message")
    public Response update(@RequestBody Map<String, Message> map){
        return messageService.update(map);
    }



}
