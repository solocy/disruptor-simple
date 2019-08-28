package wang.lovem.www.common;

import com.lmax.disruptor.*;

public class MyPrintHandler implements EventHandler<DataEvent> {

    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.toString());
    }
}
