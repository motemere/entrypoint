package me.motemere.entrypoint.web;

import me.motemere.entrypoint.entites.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

  private Integer sessionId;
  private Long entryPointTimestamp;
  private Long middleProxyTimestamp;
  private Long endProxyTimestamp;
  private Long finalTimestamp;

  /**
   * Override toString method.
   *
   * @return String representation of this object.
   */
  @Override
  public String toString() {
    return "Message{"
        + ", sessionId=" + sessionId
        + ", entryPointTimestamp=" + entryPointTimestamp
        + ", middleProxyTimestamp=" + middleProxyTimestamp
        + ", endProxyTimestamp=" + endProxyTimestamp
        + ", finalTimestamp=" + finalTimestamp
        + '}';
  }

  /**
   * Make a new message from this request.
   *
   * @return Message object.
   */
  public Message getMessage() {
    return new Message(sessionId, entryPointTimestamp, middleProxyTimestamp, endProxyTimestamp,
        System.currentTimeMillis());
  }
}
