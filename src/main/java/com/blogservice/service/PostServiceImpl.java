package com.blogservice.service;

import javax.transaction.Transactional;

import com.blogservice.configuration.RabbitMQConfiguration;
import com.blogservice.domain.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogservice.domain.Post;
import com.blogservice.repository.AuthorRepository;
import com.blogservice.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private AuthorRepository authorRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, AuthorRepository authorRepository){
		this.postRepository = postRepository;
		this.authorRepository = authorRepository;
	}

	@Override
	public Iterable<Post> list() {
		return postRepository.findAll();
	}

	@Override
	public Post read(long id) {
		return postRepository.findOne(id);
	}

	@Override
	@Transactional
	public Post create(Post post) {
		// save the new author
		authorRepository.save(post.getAuthor());
        return postRepository.save(post);
	}

	@Override
	public void delete(long id) {
		postRepository.delete(id);
	}

	@Override
	public Post update(long id,Post update) {
		Post post = postRepository.findOne(id);
		if( update.getTitle() != null ) {
			post.setTitle(update.getTitle());
		}
		return postRepository.save(post);
	}

}
