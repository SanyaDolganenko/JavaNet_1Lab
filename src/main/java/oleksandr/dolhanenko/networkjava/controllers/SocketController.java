package oleksandr.dolhanenko.networkjava.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.model.Message;
import oleksandr.dolhanenko.networkjava.utils.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@Controller
public class SocketController {
    @Autowired
    private ComputerRepository computerRepository;

    @MessageMapping("/request")
    @SendTo("/computers/result")
    public Message greeting(Message message) throws Exception {
        return processRequest(message.getMessage());
    }


    private Message processRequest(String request) {
//        Message resultMessage = new Message();
//        resultMessage.setMessage("Requested: " +
//                HtmlUtils.htmlEscape(message.getMessage()));
        Message message = new Message();
        message.setMessage("Unknown query");
        if (request.contains("GET")) {
            int indexStart = request.indexOf("(");
            int indexEnd = request.indexOf(")");
            if (indexStart >= 0 && indexEnd >= 0 && indexEnd > indexStart) {
                String numberString = request.substring(indexStart + 1, indexEnd);
                try {
                    int number = Integer.parseInt(numberString);
                    Computer computer = computerRepository.findById(number).get();
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(computer);
                    message.setMessage(json);
                } catch (NoSuchElementException e) {
                    message.setMessage("No computer found with this id");
                } catch (NumberFormatException e) {
                    message.setMessage("Invalid number");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

}
