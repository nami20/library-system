package net.gradle.com.springbootexpample.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.gradle.com.springbootexpample.model.Author;
import net.gradle.com.springbootexpample.repository.AuthorRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> findAllAuthors() {
		return authorRepository.findAll();
	}
	
	@Override 
	public List<Author> findAuthor(List<String> authors) {
		Specification<Author> specification = new Specification<Author>() {
			@Override
			public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (authors != null && authors.size() > 0) {
					Expression<String> exp = root.get("name");
					predicates.add(exp.in(authors));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		return (List<Author>) authorRepository.findAll(specification, PageRequest.of(1, 20, Sort.by(Sort.Direction.DESC,"name")));
	}

	@Override
	public Author findAuthorById(Long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(String.format("Author not found with ID %d", id)));
	}

	@Override
	public void createAuthor(Author author) {
		authorRepository.save(author);
	}

	@Override
	public void updateAuthor(Author author) {
		authorRepository.save(author);
	}

	@Override
	public void deleteAuthor(Long id) {
		final Author author = authorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(String.format("Author not found with ID %d", id)));

		authorRepository.deleteById(author.getId());
	}

}
