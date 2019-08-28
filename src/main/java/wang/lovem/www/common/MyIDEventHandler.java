package wang.lovem.www.common;

import com.lmax.disruptor.*;

public class MyIDEventHandler implements EventHandler<DataEvent> {

    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.setId(sequence);
        System.out.println("data id is" + event.getId() + ", thread is {}" + Thread.currentThread().getName());
        Thread.sleep(10);
    }
}
