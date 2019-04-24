package oleksandr.dolhanenko.networkjava;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetworkJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkJavaApplication.class, args);

    }

}
