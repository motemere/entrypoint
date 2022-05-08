package me.motemere.entrypoint.messaging;

import java.util.logging.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MessageHandler extends TextWebSocketHandler {

  private static final Logger LOG = Logger.getLogger(MessageHandler.class.getName());

  /**
   * Method to add a new WebSocketSession to the list of connected clients.
   *
   * @param session the WebSocketSession to add.
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);

    LOG.info("Socket connected: " + session);
  }

  /**
   * Method to remove a WebSocketSession from the list of connected clients.
   *
   * @param session the WebSocketSession to remove.
   * @param status  the status of the connection.
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    LOG.info(String.format("Socket closed: [%s], [%s]", status.getReason(), status.getCode()));

    super.afterConnectionClosed(session, status);
  }

  /**
   * Method to handle the incoming message from the client.
   *
   * @param session the WebSocketSession of the client.
   * @param message the message from the client.
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    super.handleTextMessage(session, message);

    LOG.info("Received: " + message.getPayload());
  }

}
