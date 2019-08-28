package wang.lovem.www.service;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import wang.lovem.www.common.DataEvent;

import java.nio.ByteBuffer;

/**
 * 数据提供方，生产者
 *
 */
public class DataEventTranslator {

    private final RingBuffer<DataEvent> ringBuffer;

    public DataEventTranslator(RingBuffer<DataEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    // 隐式的将数据提交到ringbuffer
    private static final EventTranslatorOneArg<DataEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<DataEvent, ByteBuffer>() {
                public void translateTo(DataEvent event, long sequence, ByteBuffer bb) {
                    event.setId(bb.getLong(0));
                }
            };

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
