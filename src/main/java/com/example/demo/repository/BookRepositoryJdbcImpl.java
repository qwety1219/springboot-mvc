package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
			Book book=jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class),id);
			return Optional.of(book);
		}catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean addBook(Book book) {
		String sql="insert into book(name,price,amount,pub) values(?,?,?,?)";
		try {
			int rows=jdbcTemplate.update(sql,book.getName(),book.getPrice(),book.getAmount(),book.getPub());
			return rows>0;
		}catch (DuplicateKeyException e) {
			return false;
		}
		
		
	}

	@Override
	public boolean updateBook(Integer id, Book book) {
		String sql="update book set name=?,price=?,amount=?,pub=? where id=?";
		try {
			int rows=jdbcTemplate.update(sql,book.getName(),book.getPrice(),book.getAmount(),book.getPub(),id);
			return rows>0;
		}catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public boolean deleteBook(Integer id) {
		String sql="delete from book where id=?";
		try {
			int rows=jdbcTemplate.update(sql,id);
			return rows>0;
		}catch (DuplicateKeyException e) {
			return false;
		}
	}
	
}
