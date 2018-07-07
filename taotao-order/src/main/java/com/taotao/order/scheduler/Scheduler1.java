package com.taotao.order.scheduler;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * JOB类
 */
public class Scheduler1 {

    public void execute(){
        System.out.println("任务已执行"+new Date());
    }
}
