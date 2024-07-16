package ma.xproce.schoolnewsboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication ( exclude = { SecurityAutoConfiguration.class })

public class SchoolNewsBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolNewsBoardApplication.class, args);
    }

}
