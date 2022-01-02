package com.sklcs.fmpa.back.controller;

import com.sklcs.fmpa.back.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Swagger
@Api(value="show files", protocols="http")

//json
@RestController
@RequestMapping("/file")

@CrossOrigin(origins = "*",maxAge = 3600*24)
public class FileController {

    @PutMapping("/filedemo")
    @ApiOperation(value = "show files")
    @ResponseBody
    public List<Response> file(@RequestBody List<Command> commands) throws IOException {
        List<Response> result = new ArrayList<>();
        for (Command cmd : commands) {
            String path = "/home/zxy/mvSC/test/" + cmd.getContent();
            System.out.println(path);
            File temp = new File(path);
            if (!temp.exists()) {
                Response response = new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg(), cmd);
                result.add(response);
                break;
            }
            FileInputStream file = new FileInputStream(temp);
            byte[] counterexample = new byte[file.available()];
            int bytesRead = file.read(counterexample, 0, counterexample.length);
                while (bytesRead != -1) {
                    bytesRead = file.read(counterexample, 0, counterexample.length);
                }
                StringBuilder s = new StringBuilder();
                for (byte b : counterexample) {
                    char c = (char) b;
                    s.append(c);
                }
                InputFile cmdresult = new InputFile();
                cmdresult.settitle(cmd.getTitle());
                cmdresult.setcontent(s.toString());
                Response response = new Response(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), cmdresult);
                result.add(response);
                file.close();
            }
        return result;
    }
}
