package lt.itakademija;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"lt"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(DataPreLoader loader) {
        return (args) -> {
            loader.createAdmin();
            loader.createCounties();
            loader.createDistricts();
            loader.loadParties();
            loader.createNonPartyCandidates();
            loader.createRepresentatives();
            loader.votesSingle();
            loader.votesMulti();
        };
    }
}

