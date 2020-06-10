package rmi;

public class Main {
    public static void main(String[] args) {
        new Thread(Server::new).start();

        new Thread(() -> {
            try {
                Thread.sleep(10000); //wait until server started
                new Client().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
