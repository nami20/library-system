package net.gradle.com.springbootexpample.service;

import java.util.List;


import net.gradle.com.springbootexpample.model.Books;
import net.gradle.com.springbootexpample.model.BooksFilter;

public interface BooksService {

	void createBook(Books book);

	List<Books> findAllBooks();

	List<Books> findBooks(BooksFilter filter);

}
