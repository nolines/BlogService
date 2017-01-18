package com.blogservice.controller;

import com.blogservice.domain.Message;
import com.blogservice.exception.PostNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.blogservice.domain.Post;
import com.blogservice.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Post> list() {
        return postService.list();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Post create(@RequestBody Post post) {

        System.out.println("Sending new creation message ... ");
        Post returned = postService.create(post);

        rabbitTemplate.convertAndSend("Message" + new Message(returned.getId(), returned.getPostedOn()));

        return returned;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post read(@PathVariable(value = "id") long id) throws PostNotFoundException {

        Post post = postService.read(id);

        if(post == null){
            throw new PostNotFoundException("Post with id: " + id + " not found");
        }

        System.out.println("Sending new message ... ");
        rabbitTemplate.convertAndSend("Message" + post);
        return post;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Post update(@PathVariable(value = "id") long id, @RequestBody Post post) {
        return postService.update(id, post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") int id) {
        postService.delete(id);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public void handlePostNotFound(PostNotFoundException exception, HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}
