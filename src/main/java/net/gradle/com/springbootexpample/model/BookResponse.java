package net.gradle.com.springbootexpample.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Setter
public class BookResponse {
	private Long id;

	private String isbn;

	private String name;

	private String rackno;

	private String description;
	
	private String author;

}
