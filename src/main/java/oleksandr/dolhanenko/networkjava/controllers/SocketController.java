package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class SocketController {

    @MessageMapping("/request")
    @SendTo("/computers/result")
    public Message greeting(Message message) throws Exception {
        Message resultMessage = new Message();
        resultMessage.setMessage("Requested: " +
                HtmlUtils.htmlEscape(message.getMessage()));
        return resultMessage;
    }

}
