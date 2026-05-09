package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Book;

@Repository
public class BookRepositoryJdbcImpl implements BookRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;//自動綁定Spring的JdbcTemplate物件

	@Override
	public List<Book> findAllBooks() {
		String sql="select id,name,price,amount,pub from book";
		//利用BeanPropertyRowMapper(Book.java)會自動將資料表中查詢到的每一筆紀錄注入到Book物件中
		return null;
	}

	@Override
	public Optional<Book> getBookById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean addBook(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBook(Integer id, Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBook(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
