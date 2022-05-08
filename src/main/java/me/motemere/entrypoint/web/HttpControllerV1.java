package me.motemere.entrypoint.web;

import me.motemere.entrypoint.entites.Message;
import me.motemere.entrypoint.services.MessageService;
import me.motemere.entrypoint.repositories.MessageRepository;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HttpControllerV1 {

  private final MessageRepository messageRepository;
  private final MessageService messageService;
  private Long startTimeStamp;
  private final Long periodInMillis = 3000L;
  private static final Logger LOG = Logger.getLogger(HttpControllerV1.class.getName());

  public HttpControllerV1(MessageRepository repository, MessageService service) {
    this.messageRepository = repository;
    this.messageService = service;
  }

  /**
   * Ping request.
   *
   * @return ping response.
   */
  @GetMapping("/ping/")
  public String ping() {
    LOG.info("Called ping()");

    return "pong";
  }

  /**
   * Start request.
   *
   * @return start response.
   */
  @GetMapping("/start/")
  public String start() throws IOException, ExecutionException, InterruptedException {
    LOG.info("Called start(); Interaction started");

    startTimeStamp = System.currentTimeMillis();

    messageService.connectToWebsocket();
    messageService.sendMessage();

    return "Interaction started";
  }

  /**
   * Stop request.
   *
   * @return start response.
   */
  @GetMapping("/stop/")
  public String stop() throws IOException {
    LOG.info("Called stop(); Interaction stopped");

    messageService.closeConnection();

    return "Interaction manually stopped. Processed " + messageService.getCounter() + " messages";
  }

  /**
   * Store message request.
   *
   * @param request message request.
   * @return store message response.
   */
  @PostMapping(path = "/store/", consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public String store(@RequestBody Request request) throws IOException {
    LOG.info(String.format("Called store() with request: %s", request.toString()));

    Message message = request.getMessage();
    LOG.info(String.format("Message: %s", message.toString()));

    messageRepository.save(message);

    if (System.currentTimeMillis() - startTimeStamp < periodInMillis) {
      messageService.sendMessage();
    } else {
      messageService.closeConnection();
      messageService.dropCounter();
    }

    return "Message stored";
  }
}
