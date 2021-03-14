package net.gradle.com.springbootexpample.service;

import java.util.List;

import net.gradle.com.springbootexpample.model.Author;


public interface AuthorService {

	List<Author> findAllAuthors();

	Author findAuthorById(Long id);

	void createAuthor(Author author);

	void updateAuthor(Author author);

	void deleteAuthor(Long id);

	List<Author> findAuthor(List<String> authors);

}
