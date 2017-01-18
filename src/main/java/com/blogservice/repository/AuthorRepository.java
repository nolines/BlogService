package com.blogservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.blogservice.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
