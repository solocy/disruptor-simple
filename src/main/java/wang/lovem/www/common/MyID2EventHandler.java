package wang.lovem.www.common;

import com.lmax.disruptor.EventHandler;

public class MyID2EventHandler implements EventHandler<DataEvent> {

    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.setId(sequence);
        System.out.println("data id is" + event.getId() + ", thread2 is {}" + Thread.currentThread().getName());
        Thread.sleep(10);
    }
}
