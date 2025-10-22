package notification_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application Running on PORT : 8080");
    }
}
