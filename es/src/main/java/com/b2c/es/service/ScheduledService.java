package com.b2c.es.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    /**
     * 每分钟的头5秒，每秒钟执行一次
     * 以下执行5次。
     */
    @Scheduled(cron = "0,1,2,3,4,5 * * * * MON-SAT")
    public void scheduledTest01(){

        System.out.println("scheduledTest01===》你好，定时执行这里。。。。");
    }


    /**
     * 1-3秒内，每秒钟执行一次，以下执行3次
     */
    @Scheduled(cron = "0-3 * * * * MON-SAT")
    public void scheduledTest02(){

        System.out.println("scheduledTest02===》生成日报表开始===》");
    }

    /**
     * 每隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * MON-SAT")
    public void scheduledTest03(){

        System.out.println("scheduledTest03----》》生成日报表开始===》");
    }
}
