package com.javacode.flow;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowTest {
    /**
     * 发布者 -> 订阅者
     */
    @Test
    public void test() throws InterruptedException {
        // 定义发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        // 定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 保存订阅关系，用来给发布者相应
                this.subscription = subscription;
                // 请求一个数据
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 处理接收到的数据
                System.out.println(item);
                // 再获取一个数据，可以调节发布频率
                subscription.request(1);
                // 如果已完成要求，不再接收数据
                // subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                // 出现异常后不再处理数据
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 处理完成
                System.out.println("处理完成");
            }
        };

        // 发布者与订阅者建立关系
        publisher.subscribe(subscriber);

        // 生产数据并发布
        publisher.submit(123);
        publisher.submit(456);
        publisher.submit(789);

        // 关闭发布者
        publisher.close();

        Thread.currentThread().join(1000);
    }

    /**
     * 发布者 -> 处理器 -> 订阅者
     */
    @Test
    public void processorTest() throws InterruptedException {
        // 创建发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        // 创建处理器
        PublisherProcessor processor = new PublisherProcessor();
        // 发布者与处理器建立关系
        publisher.subscribe(processor);

        // 订阅者，消费信息
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 保存订阅关系，用来给发布者相应
                this.subscription = subscription;
                // 请求一个数据
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                // 处理接收到的数据
                System.out.println(item);
                // 再获取一个数据，可以调节发布频率
                subscription.request(1);
                // 如果已完成要求，不再接收数据
                // subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                // 出现异常后不再处理数据
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 处理完成
                System.out.println("订阅者处理完成");
            }
        };

        // 处理器与订阅者建立关系
        processor.subscribe(subscriber);

        publisher.submit(-2);
        publisher.submit(0);
        publisher.submit(-1);
        publisher.submit(1);

        publisher.close();
        Thread.currentThread().join(1000);
    }
}
