package rmi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Function func = null;
    public static final String RMI_WORLD_MAP_ADDR = "functions";

    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        func = (Function) Naming.lookup(RMI_WORLD_MAP_ADDR);
        System.out.println("CLIENT STARTED ");
    }

    public void start() throws RemoteException {
        while(true) {
            System.out.println("Choose function: ");
            System.out.println("1: a*sin(x)");
            System.out.println("2: a+cos(x)");
            System.out.println("3: tan(x)-a");
            System.out.println("4: ax+b");

            Scanner scanner = new Scanner(System.in);
            int funcType = scanner.nextInt();
            System.out.println("Enter a: ");
            int a = scanner.nextInt();
            System.out.println("Enter b: ");
            int b = scanner.nextInt();
            System.out.println("Enter step: ");
            int step = scanner.nextInt();
            System.out.println("Enter start value: ");
            int start = scanner.nextInt();
            System.out.println("Enter finish value: ");
            int finish = scanner.nextInt();
            List<Double> res = func.count(funcType, a, b, step, start, finish);
            System.out.println("Result: ");
            for (double result : res) {
                System.out.println(result);
            }
        }
    }
}