package wang.lovem.www.service;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import wang.lovem.www.common.*;

import java.nio.ByteBuffer;

public class DisruptorMain {

    public static void main(String[] args) {
        // 实例化自定义的EventFactory
        MyEventFactory myEventFactory = new MyEventFactory();
        // 指定ringbuffer 的大小
        int ringBufferSize = 1024 * 1024;
        // 实例化 disruptor 对象
        // myEventFactory: 自定义的EventFactory，数据生产者
        // ringBufferSize： 指定ringbuffer 的大小
        // new MyThreadFactory(): 实例化自定义的ThreadFactory ,Executor 已经不推荐使用了
        // ProducerType.MULTI： 多个线程作为生产者
        // new YieldingWaitStrategy() ： 指定调度策略
        Disruptor<DataEvent> disruptor = new Disruptor<DataEvent>(myEventFactory,ringBufferSize,new MyThreadFactory(), ProducerType.MULTI, new YieldingWaitStrategy());
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        // 实例化处理器
        MyIDEventHandler h1 = new MyIDEventHandler();
        MyNameEventHandler h2 = new MyNameEventHandler();
        MyID2EventHandler h3 = new MyID2EventHandler();
        MyName2EventHandler h4 = new MyName2EventHandler();
        MyPrintHandler h5 = new MyPrintHandler();
        // h1,h2同时处理相同数据
        disruptor.handleEventsWith(h1,h2);
        // h1 处理完成后，h3 处理
        disruptor.after(h1).handleEventsWith(h3);
        // h2处理完成后， h4 处理
        disruptor.after(h2).handleEventsWith(h4);
        // 等h3,h4 处理后，h5 处理
        disruptor.after(h3,h4).handleEventsWith(h5);
        //  启动disruptor
        disruptor.start();
        DataEventProducer producer = new DataEventProducer(ringBuffer);

        DataEventTranslator translator = new DataEventTranslator(ringBuffer);

        // 定义 8 个字符长度是为了符合cpu 加载策略
        ByteBuffer bb = ByteBuffer.allocate(8);

        // 生产数据
        for (long l = 0; l<10; l++) {
            //手动提交
            bb.putLong(0, l);
            producer.onData(bb);

            // 隐式提交
            translator.onData(bb);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        disruptor.shutdown();

    }
}
