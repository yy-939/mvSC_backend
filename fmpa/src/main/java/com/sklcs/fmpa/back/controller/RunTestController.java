package com.sklcs.fmpa.back.controller;

import com.sklcs.fmpa.back.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Swagger
@Api(value="Run python prog", protocols="http")

//json
@RestController
@RequestMapping("/runtest")

@CrossOrigin(origins = "*",maxAge = 3600*24)
public class RunTestController {

        // 在此controller下模拟的测试example文件

        private String makedir() {
            // 新建文件夹
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());
            int n = 3;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < n; i++) {
                str.append((char) (Math.random() * 26 + 'a'));
            }
            File dir = new File("/home/zxy/mvSC_backend/example/" + date + str);
            if (!dir.exists()) {//如果文件夹不存在
                dir.mkdir();//创建文件夹
            }
            String path = "/home/zxy/mvSC_backend/example/" + date + str + "/";
            return path;
        }

        private FileInputStream savehtml(String dir) throws IOException {
            String path = "/home/zxy/mvSC/main/temp_files/output/counterExample.html";
            File htmlfile = new File(path);
            if (!htmlfile.exists()) {
                System.out.println("file does not exist");
            }
            String temp = "/home/zxy/mvSC/main/temp_files/output/temp.html";
            FileCopyUtils.copy(htmlfile, new File(temp));
            String htmlname = htmlfile.getName();
            String txtname = htmlname.substring(0, htmlname.lastIndexOf(".") + 1);
            txtname += "txt";
            File tempfile = new File(temp);
            tempfile.renameTo(new File(dir + txtname));
            FileInputStream file = new FileInputStream(dir + "/counterExample.txt");
            return file;
        }



        @PostMapping("/cmd")
        @ApiOperation(value = "run python program")
        @ResponseBody
        public Response runtest(@RequestBody List<Command> commands) throws IOException {
            String line, dirpath, command;
            dirpath = makedir();
            command = "python3 /home/zxy/mvSC/main/automata/run.py -s /home/zxy/mvSC/test/"+commands.get(0).getContent()+" -m /home/zxy/mvSC/test/"+commands.get(1).getContent()+" -p /home/zxy/mvSC/test/"+commands.get(2).getContent()+" -c l";
            System.out.println(command);
            try {
                Process proc = Runtime.getRuntime().exec(command);

                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                line = in.readLine();
                while (line != null) {
                    if (line.contains("TRUE")) {
                        in.close();
                        return new Response(RespCode.TRUE.getCode(), RespCode.TRUE.getMsg());
                    } else if (line.contains("FALSE")) {
                        try {
                            FileInputStream file = savehtml(dirpath);
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
                            CounterExample result = new CounterExample();
                            result.setInfo(s.toString());
                            file.close();
                            return new Response(RespCode.FALSE.getCode(), RespCode.FALSE.getMsg(), result);
                        } catch (FileNotFoundException e) {
                            System.out.println("file does not exist");
                            return new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg());
                        }
                    }
                    line = in.readLine();
                }
                in.close();
                proc.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg());
            }
            return new Response(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg());
        }
    }

