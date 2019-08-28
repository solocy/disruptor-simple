package wang.lovem.www.common;

import com.lmax.disruptor.EventHandler;

public class MyName2EventHandler implements EventHandler<DataEvent> {

    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.setName(String.valueOf(sequence));

        System.out.println("data name is" + event.getName()+ ", thread2 is {}" + Thread.currentThread().getName());
        Thread.sleep(10);
    }
}
