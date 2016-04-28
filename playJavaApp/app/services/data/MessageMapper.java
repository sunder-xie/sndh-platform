package services.data;

import model.Message;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface MessageMapper {
   List<Message> all();

   Message getMessageById(Long id);

   int addMessage(Message message);

   int uptMessage(Message message,long id);

   int delMessage(long id);
}
