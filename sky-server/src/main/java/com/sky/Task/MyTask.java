package com.sky.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: MyTask
 * Package: com.sky.Task
 * Description:
 *定时任务类
 * @Author Kun Hu
 * @Create 6/10/24 13:53
 * @Version 1.0
 */
//
public class MyTask {
    /**
     * 定时任务 每隔5秒触发一次
     */
    /*@Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执行：{}",new Date());
    }*/
}
