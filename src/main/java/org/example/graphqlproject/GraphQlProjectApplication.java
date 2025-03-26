package org.example.graphqlproject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class GraphQlProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphQlProjectApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {

        return (args -> {
            Author igor = new Author("Igor");
            Author vasya = new Author("Vasya");

            Book book = new Book("War of Ings", "Lyon", igor);
            Book book2 = new Book("Severe Jungs", "San", vasya);
            Book book1 = new Book("Clone of Ger", "Lyon2", igor);

            igor.setBooks(List.of(book1,book2));
            vasya.setBooks(List.of(book));


            authorRepository.save(igor);
            authorRepository.save(vasya);

        });
    }

}


