package me.motemere.entrypoint.messaging;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class WebsocketClient {

  private static final Logger LOG = Logger.getLogger(WebsocketClient.class.getName());

  private final String uri;

  private WebSocketSession session;

  @Autowired
  public WebsocketClient(@Value("${websocket.uri}") String uriString) {
    this.uri = uriString;
  }

  /**
   * Connects to the websocket server.
   */
  public void connect() throws ExecutionException, InterruptedException {
    StandardWebSocketClient client = new StandardWebSocketClient();
    ListenableFuture<WebSocketSession> future = client.doHandshake(new MessageHandler(), uri);
    session = future.get();

    LOG.info("Connected to " + uri);
  }

  /**
   * Sends a message to the websocket server.
   *
   * @param message the message to send.
   */
  public void send(String message) throws IOException {
    session.sendMessage(new TextMessage(message));
  }

  /**
   * Disconnects from the websocket server.
   */
  public void close() throws IOException {
    session.close();
  }

}
