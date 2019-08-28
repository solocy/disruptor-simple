package wang.lovem.www.service;

import com.lmax.disruptor.RingBuffer;
import wang.lovem.www.common.DataEvent;

import java.nio.ByteBuffer;

/**
 * 数据提供方，生产者
 *
 */
class DataEventProducer {

    private final RingBuffer<DataEvent> ringBuffer;


    DataEventProducer(RingBuffer<DataEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    void onData(ByteBuffer byteBuffer) {
        // 获取ringbuffer 的下一个 sequence
        long sequence = ringBuffer.next();
        try {
            // 获取sequence 里的对象
            DataEvent dataEvent = ringBuffer.get(sequence);
            // 为对象负值
            dataEvent.setId(byteBuffer.getLong(0));
        } finally {
            // 发布sequence
            ringBuffer.publish(sequence);
        }
    }
}
