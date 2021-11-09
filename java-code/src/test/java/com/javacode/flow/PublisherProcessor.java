package com.javacode.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 处理器进行数据过滤
 */
public class PublisherProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println(item);
        if (item > 0) {
            this.submit("过滤后的数据: " + item);
        }
        this.subscription.request(1);
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
        System.out.println("处理器处理完成");
    }
}
