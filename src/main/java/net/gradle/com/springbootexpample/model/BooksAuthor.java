package net.gradle.com.springbootexpample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books_authors")
public class BooksAuthor {
	
	@Column
	private Long book_id;
	
	@Column
	private Long author_id;
}
