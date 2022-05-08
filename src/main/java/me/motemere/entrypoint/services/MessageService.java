package me.motemere.entrypoint.services;

import java.io.IOException;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import me.motemere.entrypoint.entites.Message;
import me.motemere.entrypoint.messaging.WebsocketClient;

import org.springframework.stereotype.Component;

@Component
public class MessageService {

  private final WebsocketClient client;

  private int counter = 0;

  private static final Logger LOG = Logger.getLogger(MessageService.class.getName());

  public MessageService(WebsocketClient websocketClient) {
    this.client = websocketClient;
  }

  /**
   * Increments the counter.
   */
  public void increment() {
    counter++;
  }

  /**
   * Returns the current counter.
   *
   * @return the current counter.
   */
  public int getCounter() {
    return counter;
  }

  /**
   * Connects to the websocket server from the client.
   */
  public void connectToWebsocket() throws InterruptedException, ExecutionException {
    client.connect();
  }

  /**
   * Sends a new message to the websocket server.
   */
  public void sendMessage() throws IOException {
    increment();

    Message message = new Message(getCounter(), System.currentTimeMillis(), null,
        null, null);

    LOG.info(String.format("Message: %s", message));

    client.send(message.toJson());
  }

  /**
   * Disconnects from the websocket server.
   */
  public void closeConnection() throws IOException {
    client.close();
  }

  /**
   * Drop counter to 0.
   */
  public void dropCounter() {
    counter = 0;
  }
}
