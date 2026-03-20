package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;
//專門負責資料存取的元件,Spring會自動建立並管理該物件
@Repository
public class BookRepositoryImpl implements BookRepository{
	//InMemory版
	private List<Book> books=new CopyOnWriteArrayList<>();
	//初始資料有四本書
	{
		books.add(new Book(1,"小叮噹",12.5,20,true));
		books.add(new Book(2,"老夫子",10.5,30,true));
		books.add(new Book(3,"好小子",9.5,40,true));
		books.add(new Book(4,"新樂園",14.5,50,false));
	}
	@Override
	public List<Book> findAllBooks() {
		return books;
	}

	@Override
	public Optional<Book> getBookById(Integer id) {
		return books.stream().filter(book->book.getId().equals(id)).findFirst();
	}

	@Override
	public boolean addBook(Book book) {
		//建立newId
		OptionalInt optMaxId=books.stream().mapToInt(Book::getId).max();
		int newId=optMaxId.isEmpty()?1:optMaxId.getAsInt()+1;
		//將newId設定給book
		book.setId(newId);
		return books.add(book);
	}

	@Override
	public boolean updateBook(Integer id, Book book) {
		//透過id找到要修改的書
		Optional<Book> optBook=getBookById(id);
		if(optBook.isEmpty()) {
			return false;
		}
		//得到要修改的書
		Book orginalBook=optBook.get();
		//更新欄位資料
		if(book.getAmount()!=null)orginalBook.setAmount(book.getAmount());
		if(book.getName()!=null)orginalBook.setName(book.getName());
		if(book.getPrice()!=null)orginalBook.setPrice(book.getPrice());
		if(book.getPub()!=null)orginalBook.setPub(book.getPub());
		return true;
	}

	@Override
	public boolean deleteBook(Integer id) {
		//透過id找到要刪除的書籍
		Optional<Book> optBook=getBookById(id);
		if(optBook.isEmpty()) {
			return false;
		}
		//移除書籍
		return books.remove(optBook.get());
	}

}
