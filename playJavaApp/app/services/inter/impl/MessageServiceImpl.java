package services.inter.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Message;
import play.libs.Json;
import services.data.MessageMapper;
import services.inter.MessageService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MessageServiceImpl implements MessageService{
    @Inject
    private MessageMapper messageMapper;

    @Override
    public ArrayNode all() {
        ArrayNode arr =Json.newArray();
        List<Message> list =  messageMapper.all();
        for(Object m : list){
            JsonNode json = Json.toJson(m);
            arr.add(json);
        }
       return arr;
    }

    @Override
    public JsonNode getMessageById(Long id) {
        return Json.toJson(messageMapper.getMessageById(id));
    }

    @Override
    public int addMessage(JsonNode message) {
       return messageMapper.addMessage(Json.fromJson(message,Message.class));
    }

    @Override
    public int uptMessage(JsonNode message, long id) {

       return messageMapper.uptMessage(Json.fromJson(message,Message.class),id);
    }

    @Override
    public int delMessage(long id) {
        return messageMapper.delMessage(id);
    }
}
