package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final int PORT = 1099;
    public static final String REMOTE_WORLD_MAP_REG_NAME = "functions";

    public Server() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            FunctionImpl func = new FunctionImpl();
            registry.rebind(REMOTE_WORLD_MAP_REG_NAME, func);

            System.out.println("SERVER STARTED");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
