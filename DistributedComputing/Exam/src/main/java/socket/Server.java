package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    public String name = "SERVER";
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    public int port;
    public int timeout;

    public Server(int port, int timeout){
        this.port = port;
        this.timeout = timeout;
    }

    public void start() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeout);
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        serve();
    }

    private void serve() throws IOException, ClassNotFoundException {
        while (true) {
            MessageExchanger response = new MessageExchanger();
            MessageExchanger request = readMessageSerializable();
            response.responseStatus = true;

            List<Double> result = new LinkedList<>();
            for (int x = request.start; x <= request.finish; x += request.step) {
                switch (request.funcType) {
                    case 1: {
                        result.add(request.a * Math.sin(x));
                        break;
                    }
                    case 2: {
                        result.add(request.a + Math.cos(x));
                        break;
                    }
                    case 3: {
                        result.add(Math.tan(x) - request.a);
                        break;
                    }
                    case 4: {
                        result.add((double) (request.a * x + request.b));
                        break;
                    }
                }


            }
            response.results = result;

            sendMessageSerializable(response);
        }
    }



    public MessageExchanger readMessageSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        MessageExchanger me = (MessageExchanger)in.readObject();
        //in.close();

        return me;
    }

    public void sendMessageSerializable(MessageExchanger me) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(me);
    }
}