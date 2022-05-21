package me.motemere.entrypoint.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "message")
public class Message extends me.motemere.testproject.dto.Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "session_id")
  private Integer sessionId;

  @Column(name = "ms_1_timestamp")
  private Long entryPointTimestamp;

  @Column(name = "ms_2_timestamp")
  private Long middleProxyTimestamp;

  @Column(name = "ms_3_timestamp")
  private Long endProxyTimestamp;

  @Column(name = "end_timestamp")
  private Long finalTimestamp;

  public Message() {
  }

  public Message(Integer idSession, Long ms1Timestamp, Long ms2Timestamp, Long ms3Timestamp,
      Long endTimestamp) {
    this.sessionId = idSession;
    this.entryPointTimestamp = ms1Timestamp;
    this.middleProxyTimestamp = ms2Timestamp;
    this.endProxyTimestamp = ms3Timestamp;
    this.finalTimestamp = endTimestamp;
  }

}
