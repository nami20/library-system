package net.gradle.com.springbootexpample.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Setter
public class BooksFilter {
	
	private List<String> booknames;

	private List<String> authornames;
	
	private List<Long> ids;

}
