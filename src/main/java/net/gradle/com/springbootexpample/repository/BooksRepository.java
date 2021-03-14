package net.gradle.com.springbootexpample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.gradle.com.springbootexpample.model.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>, JpaSpecificationExecutor<Books>{

}