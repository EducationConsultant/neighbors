package com.education.consultant.educon.config;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = QuestionRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(QuestionRepository questionRepository) {
        return args -> questionRepository.save(
                new Question(1, "naslov", "user",
                        "opis","kat", "ans1", "ans2", "ans3",
                        "ans4", "nivo", "tacan", "odg"));
    }
}
