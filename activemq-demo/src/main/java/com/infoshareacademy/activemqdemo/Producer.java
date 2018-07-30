package com.infoshareacademy.activemqdemo;

import java.io.IOException;
import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection(); // utwórz połączenie z connectionFactory i rozpocznij metodą start()
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("ISA.JJDD4.MSG.QUEUE"); // pobierz z sesji destination ISA.JJDD4.MSG.QUEUE

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String text = scanner.nextLine();

            Message mymsg = session.createTextMessage(text);
            producer.send(mymsg);

            if (text.equals("exit")) {
                break;
            }
        }

        session.close();
        connection.close();
    }
}
