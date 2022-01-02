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
@RequestMapping("/run")
@CrossOrigin(origins = "*",maxAge = 3600*24)
public class RunController {
    // 在此controller下模拟的是用户上传文件

    private String runHelper1Helper(List<Command> lst, String command){
        // validity
        if (!lst.get(0).getTitle().contains(".sol")){
            command = "invalid, first file should be a .sol file";
        }
        if (!lst.get(1).getTitle().contains(".txt")){
            command = "invalid, second file should be a scenario configuration file (.txt)";
        }
        if (!lst.get(2).getTitle().contains(".txt")){
            command = "invalid, third file should be a property file (.txt)";
        }
        return command;
    }

    private String makedir(){
        // 新建文件夹
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());
        int n = 3;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < n; i++) {
            str.append((char) (Math.random() * 26 + 'a'));
        }
        File dir=new File("/home/zxy/mvSC_backend/user/"+date+str);
        if(!dir.exists()){//如果文件夹不存在
            dir.mkdir();//创建文件夹
        }
        String path = "/home/zxy/mvSC_backend/user/"+date+str+"/";
        return path;
    }

    private String runHelper1(List<Command> lst, String path){
        // 还没有考虑workflow
        int len = lst.size();
        String command;
        switch (len){
            case 3:
                command = "python3 /home/zxy/mvSC/main/automata/run.py -s " + path+lst.get(0).getTitle() + " -m " + path+lst.get(1).getTitle() + " -p " + path+lst.get(2).getTitle() + " -c l";
                command = runHelper1Helper(lst, command);
                break;
            case 4:
                command = "python3 /home/zxy/mvSC/main/automata/run.py -s " + path+lst.get(0).getTitle() + " -m " + path+lst.get(1).getTitle() + " -p " + path+lst.get(2).getTitle() + "-c l " + lst.get(3).getContent();
                command = runHelper1Helper(lst, command);
                break;
            case 5:
                command = "python3 /home/zxy/mvSC/main/automata/run.py -s " + path+lst.get(0).getTitle() + " -m " + path+lst.get(1).getTitle() + " -p " + path+lst.get(2).getTitle() + " -c l " + lst.get(3).getContent() + " " + lst.get(4).getContent();
                command = runHelper1Helper(lst, command);
                break;
            default:
                command = "invalid number of commands provided";
                break;
        }
        return command;
    }

     private FileInputStream savehtml(String dir) throws IOException {
         String path = "/home/zxy/mvSC/main/temp_files/output/counterExample.html"; // counterexample的名字
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
         FileInputStream file = new FileInputStream(dir+"/counterExample.txt");
         return file;
     }

     private void filehandler(List<Command> lst, String dirpath){
         int i;
         int filenum=3; // Need to be replaced with other integers
         for (i=0; i<filenum; i++) {
             Command cmd = lst.get(i);
             String content = cmd.getContent().replace("\\n", "\n");
             File file = new File(dirpath+cmd.getTitle());
             try {
                 if (!file.exists()) {
                     file.createNewFile();
                 }
                 PrintStream ps = new PrintStream(new FileOutputStream(file));
                 ps.println(content);
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
         }
     }


    @PostMapping("/cmd")
    @ApiOperation(value = "run python program")
    @ResponseBody
    public Response run(@RequestBody List<Command> lst) throws IOException {
        String line, command, dirpath;
        dirpath = makedir();
        command = runHelper1(lst, dirpath);
        if (command.contains("invalid")){
            Message msg = new Message();
            msg.setInfo(command);
            return new Response(RespCode.INVALID.getCode(), RespCode.INVALID.getMsg(), msg);
        }
        filehandler(lst, dirpath);

        try {
            Process proc = Runtime.getRuntime().exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            line = in.readLine();
            while (line != null) {
                if (line.contains("TRUE")){
                    in.close();
                    return new Response(RespCode.TRUE.getCode(), RespCode.TRUE.getMsg());
                    }
                else if (line.contains("FALSE")){
                    try {
                            FileInputStream file = savehtml(dirpath);
                            byte[] counterexample = new byte[file.available()];
                            int bytesRead = file.read(counterexample, 0, counterexample.length);
                            while(bytesRead != -1){
                                bytesRead = file.read(counterexample, 0, counterexample.length);
                            }
                            StringBuilder s = new StringBuilder();
                            for (byte b:counterexample){
                                char c = (char) b;
                                s.append(c);
                            }
                            CounterExample result = new CounterExample();
                            result.setInfo(s.toString());
                            file.close();
                            return new Response(RespCode.FALSE.getCode(), RespCode.FALSE.getMsg(), result);
                            }
                        catch (FileNotFoundException e){
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


