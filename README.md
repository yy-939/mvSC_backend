# mvSC Backend

## Project Structure
```Python
|- fmpa

|    |---- src/main/java/com/sklcs/fmpa/back/config

|    |    |    |    |    |    |    |    |----config  
|    |    |    |    |    |    |    |    |    |----JacksonConfig //  json handler  
|    |    |    |    |    |    |    |    |    |----SwaggerConfig //  configuration for swagger (a testing tool)  
|    |    |    |    |    |    |    |    |    |----CorsConfig 

|    |    |    |    |    |    |    |    |----controller  
|    |    |    |    |    |    |    |    |    |----MessageController // simply for testing   
|    |    |    |    |    |    |    |    |    |----RunController // main controller for mvSC Backend,receive user input files content and execute python program  
|    |    |    |    |    |    |    |    |    |----RunLocalController // testing RunController locally
|    |    |    |    |    |    |    |    |    |----RunTestController // main controller for mvSC Backend, testing example files
|    |    |    |    |    |    |    |    |    |----FileController // main controller for mvSC Backend, example files demo


|    |    |    |    |    |    |    |    |----service  
|    |    |    |    |    |    |    |    |    |----MessageService // simply for testing   

|    |    |    |    |    |    |    |    |----CounterExample
|    |    |    |    |    |    |    |    |----Command  
|    |    |    |    |    |    |    |    |----InputFile
|    |    |    |    |    |    |    |    |----Message  
|    |    |    |    |    |    |    |    |----RespCode  
|    |    |    |    |    |    |    |    |----Response
```  

## System Operation 
* MessageController
    * /receive: 接受前端input {msg}，返回{msg} + 当前时间
    * /test: 返回一串json map
    ***
    * /listAll: 列出所有的message
    * /add：根据input message 新增一个message到message box
    * /delete：根据input list of message id，删除指定的message
    * /update：根据input map of message， 更新指定的message
***
* RunController  
    接受前端的input list of commands，execute mvSC python program, 返回response code：如果verify true, 返回成功 response；如果verify false，返回失败response及html反例文件  
***
* RunLocalController   
    RunController 的local version
***
* RunTestController   
    接受前端的input list of commands，用指定的example test execute mvSC python program, 返回response code：如果verify true, 返回成功 response；如果verify false，返回失败response及html反例文件  
***
* FileController
    接受前端的input list of input file names，找到指定的example input file并将file content返回

## Instruction
* Running locally:  
    Run FmpaApplication which is under package back, log in the website http://localhost:8081/swagger-ui.html#
* Running on server:
    cd to mvSC_backend, execute command: java -jar [.jar files]

