package com.blogservice;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.blogservice.domain.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogservice.domain.Author;
import com.blogservice.domain.Post;
import com.blogservice.repository.AuthorRepository;
import com.blogservice.repository.PostRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(AuthorRepository authorRepository, PostRepository postRepository) {
        return args -> {

            Author dv = new Author("Cemre", "Çevik", "cemre.by@gmail.com");
            authorRepository.save(dv);

            Post post = new Post("Spring Boot Rocks!");
            post.setSlug("spring-data-rocks");
            post.setTeaser("Post Teaser");
            post.setBody("Post Body");
            post.setPostedOn(new Date());
            post.setAuthor(dv);
            postRepository.save(post);

            Post rest = new Post("REST is what all the cool kids are doing");
            rest.setSlug("rest-is-cool");
            rest.setTeaser("REST Teaser");
            rest.setBody("REST BODY");
            rest.setPostedOn(new Date());
            rest.setAuthor(dv);
            postRepository.save(rest);

        };
    }

}
