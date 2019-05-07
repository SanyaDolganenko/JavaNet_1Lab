package oleksandr.dolhanenko.networkjava.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.model.Message;
import oleksandr.dolhanenko.networkjava.utils.ComputerGenerator;
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
    public Message request(Message message) {
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
            processGetRequest(computer, message);
        } else if (request.contains("DELETE")) {
            processDeleteRequest(computer, message);
        } else if (request.contains("ADD")) {
            processAddRequest(message);
        } else if (request.contains("UPDATE")) {
            processUpdateRequest(computer, request, message);
        } else {
            message.setMessage("Unknown query");
        }
        return message;
    }

    private void processGetRequest(Computer computer, Message message) {
        if (computer != null) {
            message.setMessage(computerToJsonSafe(computer));
        }
    }

    private void processDeleteRequest(Computer computer, Message message) {
        if (computer != null) {
            long allCount = computerRepository.count();
            computerRepository.delete(computer);
            if (allCount - computerRepository.count() == 1) {
                message.setMessage("DELETED computer");
            } else {
                message.setMessage("Error deleting computer");
            }
        }
    }

    private void processAddRequest(Message message) {
        Computer computer = ComputerGenerator.generate(1).get(0);
        long count = computerRepository.count();
        computerRepository.save(computer);
        if (computerRepository.count() - count == 1) {
            message.setMessage("ADDED computer: " + computerToJsonSafe(computer));
        } else {
            message.setMessage("Error generating computer");
        }

    }

    private void processUpdateRequest(Computer computer, String request, Message message) {
        if (computer != null) {
            int queryIndexStart = request.indexOf(")") + 1;
            String rawQuery = request.substring(queryIndexStart);
            String[] queries = rawQuery.split(";");
            for (String query : queries) {
                String[] current = query.split("=");
                if (current.length == 2) {
                    String name = current[0].trim().toLowerCase();
                    String value = current[1].trim();
                    switch (name) {
                        case "cpu": {
                            computer.setCpu(value);
                        }
                        break;
                        case "gpu": {
                            computer.setGpu(value);
                        }
                        break;
                        case "ram": {
                            try {
                                int ramSize = Integer.parseInt(value);
                                if (ramSize > 0) {
                                    computer.setRamSize(ramSize);
                                }
                            } catch (NumberFormatException ignored) {

                            }
                        }
                        break;
                    }
                }
            }
            computerRepository.save(computer);
            message.setMessage("UPDATED: " + computerToJsonSafe(computerRepository.findById(computer.getId()).get()));
        }
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
