package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class FunctionImpl extends UnicastRemoteObject implements Function {
    protected FunctionImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Double> count(final int func, int a, int b, int step, int start, int finish) {
        List<Double> result = new LinkedList<>();
        for(int x = start; x <= finish; x+= step) {
            switch (func) {
                case 1: {
                    result.add(a*Math.sin(x));
                    break;
                } case 2: {
                    result.add(a+Math.cos(x));
                    break;
                } case 3: {
                    result.add(Math.tan(x)-a);
                    break;
                } case 4: {
                    result.add((double)(a*x+b));
                    break;
                }
            }
        }

        return result;
    }
}
