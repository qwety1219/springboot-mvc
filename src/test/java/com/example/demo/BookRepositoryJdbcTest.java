package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@SpringBootTest
public class BookRepositoryJdbcTest {
	@Autowired
	@Qualifier("bookRepositoryJdbcImpl")//指定實現類
	private BookRepository bookRepository;
	@Test
	void queryTest() {
		List<Book> books=bookRepository.findAllBooks();
		books.forEach(System.out::println);
	}
}
