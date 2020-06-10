package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Function extends Remote {
    public List<Double> count(int func, int a, int b, int step, int start, int finish) throws RemoteException;
}
