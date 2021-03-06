package com.lanfangyi.nettyim.pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lanfangyi@haodf.com
 * @version 1.0
 * @since 2019/9/2 5:36 PM
 */
@Slf4j
public class FutureTaskPool {

    /**
     * LinkedBlockingQueue（）或者（int i）:
     * 大小不固定的BlockingQueue，若其构造时指定大小，生成的BlockingQueue有大小限制，此处不指定大小，
     * 其大小有Integer.MAX_VALUE来决定。其所含的对象是FIFO顺序排序的。
     */
    private static BlockingQueue<Runnable> bq = new LinkedBlockingQueue<>();


    /**
     * ThreadPoolExecutor:创建自定义线程池，池中保存的线程数为3，允许最大的线程数为6
     */
    private static ThreadPoolExecutor tpe = new ThreadPoolExecutor(3, 6, 50, TimeUnit.MILLISECONDS, bq);

    public static ListeningExecutorService getExecutorService() {
        return MoreExecutors.listeningDecorator(FutureTaskPool.getExecutorService());
    }

    /**
     * 获取线程队列的大小
     */
    public static BlockingQueue<Runnable> getBlockingQueue() {
        return bq;
    }

    public static boolean shutDown() {
        log.info("销毁线程池 start");
        if (tpe != null && !tpe.isShutdown()) {
            tpe.shutdown();
            log.info("销毁线程池 success");
        }
        log.info("销毁线程池 end");
        return true;
    }
}
