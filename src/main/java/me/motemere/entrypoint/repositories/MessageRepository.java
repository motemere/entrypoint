package me.motemere.entrypoint.repositories;

import me.motemere.entrypoint.entites.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {

}
