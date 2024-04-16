package nl.DMI.SWS.ATP.Views;

import java.util.concurrent.TimeUnit;
import nl.DMI.SWS.ATP.Util.TimeBase;
public class TestView extends View {
    public TestView() {
        TimeBase test = new TimeBase(100, TimeUnit.MICROSECONDS);
        System.out.println(test);
        test.updateTimeBase(20, TimeUnit.MILLISECONDS);
        System.out.println(test);
    }
}
