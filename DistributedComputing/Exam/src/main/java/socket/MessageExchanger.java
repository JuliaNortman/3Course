package socket;

import java.io.Serializable;
import java.util.List;

public class MessageExchanger implements Serializable {
    boolean responseStatus;
    int funcType;//1 2 3 4
    int a;
    int b;
    int step;
    int start;
    int finish;
    List<Double> results;

    public MessageExchanger() {}
}
