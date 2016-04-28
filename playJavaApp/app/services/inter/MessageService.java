package services.inter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.ImplementedBy;
import model.Message;
import services.inter.impl.MessageServiceImpl;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
@ImplementedBy(MessageServiceImpl.class)
public interface MessageService {
    ArrayNode all();

    JsonNode getMessageById(Long id);

    int addMessage(JsonNode message);

    int uptMessage(JsonNode message,long id);

    int delMessage(long id);
}
