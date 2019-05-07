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
        Message message = new Message();
        message.setMessage("Unknown query");
        int number = getEnteredNumber(request);
        Computer computer = null;
        if (number == -1) {
            message.setMessage("Invalid number");
        } else {
            computer = getComputerSafe(number);
            if (computer == null) {
                message.setMessage("No computer found with this id");
            }
        }
        if (request.contains("GET")) {
            if (computer != null) {
                message.setMessage(computerToJsonSafe(computer));
            }
        } else if (request.contains("DELETE")) {
            if (computer != null) {
                long allCount = computerRepository.count();
                computerRepository.delete(computer);
                if (allCount - computerRepository.count() == 1) {
                    message.setMessage("Successfully deleted computer");
                }else{
                    message.setMessage("Error deleting computer");
                }
            }
        }
        return message;
    }

    private Computer getComputerSafe(int id) {
        Computer computer = null;
        try {
            computer = computerRepository.findById(id).get();
        } catch (NoSuchElementException e) {

        }
        return computer;
    }

    private String computerToJsonSafe(Computer computer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(computer);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error serializing.";
    }

    private int getEnteredNumber(String request) {
        int indexStart = request.indexOf("(");
        int indexEnd = request.indexOf(")");
        int number = -1;
        if (indexStart >= 0 && indexEnd >= 0 && indexEnd > indexStart) {
            String numberString = request.substring(indexStart + 1, indexEnd);
            try {
                number = Integer.parseInt(numberString);
            } catch (NumberFormatException e) {
                number = -1;
            }
        }
        return number;
    }

}
