package socket;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private String hostname = "localhost";
    private int port = 5000;
    private Socket clientSocket = null;

    public Client(String hostname, int port){
        this.port = port;
        this.hostname = hostname;
    }

    public void start() throws IOException {
        clientSocket = new Socket(hostname, port);
    }

    private void showStatusMessage(MessageExchanger me){
        System.out.println("RESPONSE STATUS: " + (me.responseStatus ? "ok" : "Operation failed or result set is empty"));
    }

    public void scriptCustom(){
        while(true) {
            try {
                MessageExchanger messResponse = null;
                MessageExchanger messSend = new MessageExchanger();
                System.out.println("Choose function: ");
                System.out.println("1: a*sin(x)");
                System.out.println("2: a+cos(x)");
                System.out.println("3: tan(x)-a");
                System.out.println("4: ax+b");

                Scanner scanner = new Scanner(System.in);
                messSend.funcType = scanner.nextInt();
                System.out.println("Enter a: ");
                messSend.a = scanner.nextInt();
                System.out.println("Enter b: ");
                messSend.b = scanner.nextInt();
                System.out.println("Enter step: ");
                messSend.step = scanner.nextInt();
                System.out.println("Enter start value: ");
                messSend.start = scanner.nextInt();
                System.out.println("Enter finish value: ");
                messSend.finish = scanner.nextInt();

                sendMessageSerializable(messSend);
                messResponse = readMessageSerializable();
                showStatusMessage(messResponse);
                if (messResponse.responseStatus) {
                    messResponse.results.forEach(System.out::println);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageSerializable(MessageExchanger me) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(me);
    }

    public MessageExchanger readMessageSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        MessageExchanger me = (MessageExchanger) in.readObject();

        return me;
    }
}