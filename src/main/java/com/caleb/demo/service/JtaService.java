package com.caleb.demo.service;

import com.caleb.demo.entity.LogInfo;
import com.caleb.demo.mapper.mapper1.LogInfoMapper1;
import com.caleb.demo.util.EntityGeneraterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class JtaService {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static volatile boolean IS_OK = true;

    @Autowired
    private ILogInfoService logInfoService1Impl;

    @Autowired
    private LogInfoMapper1 mapper1;

    @Autowired
    private ILogInfoService logInfoService2Impl;

    @Transactional(readOnly = false)
    public void testJTA() {
        List<LogInfo> logInfos = EntityGeneraterUtil.generate(5000);
        logInfoService1Impl.insertBatchByOn(logInfos);
    }

    @Transactional(readOnly = false)
    public void testJTABatch() {
        List<LogInfo> logInfos = EntityGeneraterUtil.generate(10000);
        logInfoService1Impl.insertBatchByOn(logInfos.subList(0,5000));
        logInfoService2Impl.insertBatchByOn(logInfos.subList(5000,10000));
//        int i =1 /0;
    }

    public void testMutliThread(){
        //子线程等待主线程通知
        List<LogInfo> logInfos = EntityGeneraterUtil.generate(10);
        TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        CountDownLatch mainMonitor = new CountDownLatch(1);
        int threadCount = 5;
        CountDownLatch childMonitor = new CountDownLatch(threadCount);
        //子线程运行结果
        List<Boolean> childResponse = new ArrayList<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "：开始执行");
                    mapper1.insertBatchByOn(logInfos);
                     if (finalI == 4) {
                     throw new Exception("出现异常");
                     }
                    childResponse.add(Boolean.TRUE);
                    childMonitor.countDown();
                    System.out.println(Thread.currentThread().getName() + "：准备就绪,等待其他线程结果,判断是否事务提交");
                    mainMonitor.await();
                    if (IS_OK) {
                        System.out.println(Thread.currentThread().getName() + "：事务提交");
                        platformTransactionManager.commit(transaction);
                    } else {
                        System.out.println(Thread.currentThread().getName() + "：事务回滚");
                        platformTransactionManager.rollback(transaction);
                    }
                } catch (Exception e) {
                    childResponse.add(Boolean.FALSE);
                    childMonitor.countDown();
                    System.out.println(Thread.currentThread().getName() + "：出现异常,开始事务回滚");
                    platformTransactionManager.rollback(transaction);
                }
            });
        }
        //主线程等待所有子线程执行response
        try {
            childMonitor.await();
            for (Boolean resp : childResponse) {
                if (!resp) {
                    //如果有一个子线程执行失败了，则改变mainResult，让所有子线程回滚
                    System.out.println(Thread.currentThread().getName()+":有线程执行失败，标志位设置为false");
                    IS_OK = false;
                    break;
                }
            }
            //主线程获取结果成功，让子线程开始根据主线程的结果执行（提交或回滚）
            mainMonitor.countDown();
            //为了让主线程阻塞，让子线程执行。
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
