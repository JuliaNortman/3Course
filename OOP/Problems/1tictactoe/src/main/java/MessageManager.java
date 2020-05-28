import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class MessageManager {

  private Logger logger = LogManager.getLogManager().getLogger(MessageManager.class.getName());
  private MessageConsumer consumer;
  private MessageProducer producer;
  private Session session;
  private Connection connection;
  private GameLogic gameLogic;

  MessageManager(boolean isServer, GameLogic gameLogic) throws JMSException {
    this.gameLogic = gameLogic;

    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
    connection = connectionFactory.createConnection();
    connection.start();

    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    Destination fromServer = session.createQueue("fromServer");
    Destination fromClient = session.createQueue("fromClient");
    if (isServer) {
      producer = session.createProducer(fromServer);
      consumer = session.createConsumer(fromClient);
      startServer();
    } else {
      producer = session.createProducer(fromClient);
      consumer = session.createConsumer(fromServer);
      startClient();
    }
  }

  public void end() throws JMSException {
    connection.close();
  }

  public void startServer() throws JMSException {
    while (consumer.receiveNoWait() != null) ;
    TextMessage message = session.createTextMessage("Hello !!! I m server. Lets play");
    producer.send(message);

    Message message2 = consumer.receive();
    if (message2 instanceof TextMessage) {
      logger.info("Received message '" + ((TextMessage) message2).getText() + "'");
    }
  }

  public void startClient() throws JMSException {
    while (true) {
      Message message = consumer.receive();
      if (message instanceof TextMessage
          && ((TextMessage) message).getText().equals("Hello !!! I m server. Lets play")) {
        logger.info("Received message '" + ((TextMessage) message).getText() + "'");
        break;
      }
    }
    TextMessage message2 = session.createTextMessage("Hello !!! I m client. Lets play");
    producer.send(message2);
  }

  public void sendPoint(int x, int y) {
    try {
      // producer.send(session.createObjectMessage(new Point(x,y)));
      producer.send(session.createObjectMessage(x));
      producer.send(session.createObjectMessage(y));
    } catch (JMSException e) {
      logger.severe(e.getMessage());
    }
  }

  public void getPoint() {
    Thread thread =
        new Thread(
            () -> {
              try {

                Message message1 = consumer.receive();
                Message message2 = consumer.receive();
                if (message1 != null && message2 != null) {
                  int x = ((int) ((ObjectMessage) message1).getObject());
                  int y = ((int) ((ObjectMessage) message2).getObject());
                  gameLogic.anotherPlayerStep(new Point(x, y));
                } else {
                  gameLogic.anotherPlayerStep(new Point(-1, -1));
                }

              } catch (JMSException e) {
                logger.severe(e.getMessage());
              }
            });

    thread.start();
  }
}
