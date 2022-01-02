package com.sklcs.fmpa.back.service;

import com.sklcs.fmpa.back.Message;
import com.sklcs.fmpa.back.RespCode;
import com.sklcs.fmpa.back.Response;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;



@Service
public class MessageService implements Serializable {
    Map<Long, Message> allMsg = new HashMap<>();
    int msgNum = 0;

    public Map<Long, Message> list() {
        return allMsg;
    }

    public Response addMsg(Message msg) {
        msgNum++;
        allMsg.put((long) msgNum, msg);
        return new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), msg);
    }

    public Response deleteMsg(List<String> lst) {
        if (allMsg.isEmpty() || lst.isEmpty()){
            return new Response(RespCode.WARN.getCode(), RespCode.WARN.getMsg(), lst);
        }

        int temp = lst.size();
        Map<Long, Message> map = new HashMap<>();
        for (Long index : allMsg.keySet()){
            map.put(index, allMsg.get(index));
        }

        for (String lstIndex : lst) {
            for (Long index : map.keySet()) {
                String id = index.toString();
                if (id.equals(lstIndex)) {
                    allMsg.remove(index);
                    temp -= 1;
                }
            }
        }
        if(temp != 0){
            return new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg(), lst);
        }
        else{
            return new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), lst);
        }
    }

    public Response update(Map<String, Message> map) {
        if (allMsg.isEmpty() || map.isEmpty()){
            return new Response(RespCode.WARN.getCode(), RespCode.WARN.getMsg(), map);
        }

        int temp = map.size();
        for (String mapIndex : map.keySet()) {
            for (Long index : allMsg.keySet()) {
                String id = index.toString();
                if (id.equals(mapIndex)) {
                    temp -= 1;
                    String newinfo = map.get(mapIndex).getInfo();
                    allMsg.get(index).setInfo(newinfo);
                }
            }
        }

        if(temp != 0){
            return new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg(), map);
        }
        else{
            return new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), map);
        }
    }

    public String edit(Long id, String content){
        for (Long index : allMsg.keySet()) {
            if (index.equals(id)){
                return allMsg.get(index).setInfo(content);
            }
        }
        return "Cannot find this message";
    }
}
