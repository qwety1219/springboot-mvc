package com.example.demo;

import java.util.List;
import java.util.Optional;

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
//	@Test
	void queryTest() {
		List<Book> books=bookRepository.findAllBooks();
		books.forEach(System.out::println);
	}
//	@Test
	void getTest() {
		Optional<Book> optBook=bookRepository.getBookById(3);
		if(optBook.isPresent()) {
			Book book=optBook.get();
			System.out.println(book);
		}else {
			System.out.println("查無資料");
		}
	}
//	@Test
	void addTest() {
		Book book=new Book(0,"Java",50.5,120,true);
		boolean check=bookRepository.addBook(book);
		System.out.println(check?"新增成功":"新增失敗");
	}
//	@Test
	void updateTest() {
		Optional<Book> optBook=bookRepository.getBookById(3);
		if(optBook.isPresent()) {
			Book book=optBook.get();
			//修改必要資料
			book.setName("老夫子");
			book.setAmount(33);
			book.setPrice(10.15);
			book.setPub(true);
			//進行修改
			boolean check=bookRepository.updateBook(book.getId(), book);
			System.out.println(check?"修改成功":"修改失敗");
		}else {
			System.out.println("查無此書");
		}
	}
	@Test
	void deleteTest() {
		Optional<Book> optBook=bookRepository.getBookById(3);
		if(optBook.isPresent()) {
			boolean delete=bookRepository.deleteBook(3);
			System.out.println(delete?"刪除成功":"刪除失敗");
		}else {
			System.out.println("查無此書");
		}
	}
}
