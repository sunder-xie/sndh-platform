package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;
import services.inter.MessageService;
import utils.RedisUtil;

import javax.inject.Inject;

public class HomeController extends Controller {

    public Result index() {
        return ok(messageService.all());
    }

    public Result showNessage(long id){
        JsonNode json = messageService.getMessageById(id);
        if(json != null){
            return ok(json);
        }else{
            ObjectNode res = JsonNodeFactory.instance.objectNode();
            return notFound(Json.toJson(res.put("message","Customer Not Found")));
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result addNessage(){
        JsonNode jsonNode = request().body().asJson();
        int flag = messageService.addMessage(jsonNode);
        if(flag > 0){
            return ok("success");
        }
        return ok("fail");
    }

    public Result showRedis(){
       return ok(RedisUtil.get("user.id"));
    }

    @Inject
    private MessageService messageService;

}
