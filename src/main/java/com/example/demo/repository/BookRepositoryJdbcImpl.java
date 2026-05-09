package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
		//利用BeanPropertyRowMapper(Book.class)會自動將資料表中查詢到的每一筆紀錄注入到Book物件中
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
	}

	@Override
	public Optional<Book> getBookById(Integer id) {
		String sql="select id,name,price,amount,pub from book where id=?";
		//查詢單筆,若沒查到會拋出EmptyResultDataAccessException
		try {
			Book book=jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class));
			return Optional.of(book);
		}catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
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
