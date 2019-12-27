package com.tst.automation.opcua.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 系统启动后执行的业务服务类， 1s 后自动运行
 * 自动注入 opc Task 类
 */
@Service
public class OpcUaService implements CommandLineRunner {

    @Autowired
    private OpcUaTask opcUaTask;

    /**
     * 分配一个线程运行 opc 的核心业务，所有的核心任务均在opc project task中实现
     * @param args spring boot 系统参数
     * @throws Exception 异常
     */
    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.schedule(opcUaTask, 1000L, TimeUnit.MILLISECONDS);
    }
}
