package wang.lovem.www.service;

import com.lmax.disruptor.EventFactory;
import wang.lovem.www.common.DataEvent;

public class MyEventFactory implements EventFactory<DataEvent> {
    public DataEvent newInstance() {
        return new DataEvent();
    }
}
