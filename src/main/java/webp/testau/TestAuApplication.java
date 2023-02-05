package webp.testau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan //to search for servlets
@SpringBootApplication
public class TestAuApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestAuApplication.class, args);
    }

}
