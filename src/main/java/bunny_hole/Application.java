package bunny_hole;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Application {

	private final static String QUEUE_NAME = "test";
	private final static String EXCHANGE_NAME = "notification";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("eddie");
		factory.setPassword("Leo0826519");
		factory.setVirtualHost("test");
		Connection conn = factory.newConnection();
		Channel ch = conn.createChannel();
		
		ch.exchangeDeclare(EXCHANGE_NAME, "fanout");
//		String queueName = ch.queueDeclare().getQueue();
		ch.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
//		ch.queueDeclare(QUEUE_NAME, false, false, false, null);
		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(message);
		};
		ch.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
	}

}
