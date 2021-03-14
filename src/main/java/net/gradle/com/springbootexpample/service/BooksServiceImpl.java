package net.gradle.com.springbootexpample.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.gradle.com.springbootexpample.model.Author;
import net.gradle.com.springbootexpample.model.Books;
import net.gradle.com.springbootexpample.model.BooksFilter;
import net.gradle.com.springbootexpample.repository.BooksRepository;

@Service
public class BooksServiceImpl implements BooksService{
	
	@Autowired
	private BooksRepository repository;

	@Override
	public void createBook(Books book) {
		repository.save(book);
	}

	@Override
	public List<Books> findAllBooks() {
		return repository.findAll();
	}

	@Override
	public List<Books> findBooks(BooksFilter filter) {
		Specification<Books> specification = new Specification<Books>() {
			@Override
			public Predicate toPredicate(Root<Books> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (filter.getBooknames() != null && filter.getBooknames().size() > 0) {
					Expression<String> exp = root.get("name");
					predicates.add(exp.in(filter.getBooknames()));
				}
				if (filter.getIds() != null && filter.getIds().size() > 0) {
					Expression<String> exp = root.get("id");
					predicates.add(exp.in(filter.getIds()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		return (List<Books>) repository.findAll(specification, PageRequest.of(1, 20, Sort.by(Sort.Direction.DESC,"name")));
	}

}
