package com.example.testcart.service;

import com.example.testcart.entity.Book;
import com.example.testcart.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    public List<Book> listBook(){
        return bookRepository.findAll();
    }
    public Book findBookById(long id){
        return bookRepository.findById(id).orElse(null);
    }
}
