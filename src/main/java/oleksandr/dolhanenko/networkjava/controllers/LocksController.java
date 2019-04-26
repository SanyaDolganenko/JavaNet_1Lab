package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
//@RestController
@RequestMapping("/locks")
public class LocksController {
    String topic = "CC50E3566FDD";
    private MqttClient sampleClient;
    private int qos = 2;

    public LocksController() {
        String broker = "tcp://m16.cloudmqtt.com:11031";
        String clientId = "JavaSample";
        String username = "server";
        String password = "12345";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Waiting for your command:");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

    @GetMapping("/open")
    public ResponseEntity<String> openLock() {
        MqttMessage message = null;
        message = new MqttMessage("open".getBytes());
        try {
            message.setQos(qos);
            sampleClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("", HttpStatus.OK);

    }

    @GetMapping("/close")
    public ResponseEntity<String> closeLock() {
        MqttMessage message = null;
        message = new MqttMessage("close".getBytes());
        try {
            message.setQos(qos);
            sampleClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("", HttpStatus.OK);

    }
}
