package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import services.inter.MessageService;
import javax.inject.Inject;

public class HomeController extends Controller {

    public Result index() {
      return ok(messageService.all());
    }

    public Result showNessage(long id){
       return ok(messageService.getMessageById(id));
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

    @Inject
    private MessageService messageService;

}
