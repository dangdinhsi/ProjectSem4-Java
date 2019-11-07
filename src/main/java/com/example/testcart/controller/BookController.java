package com.example.testcart.controller;

import com.example.testcart.entity.Book;
import com.example.testcart.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService bookService;


    //Hiển thị list book để test.
    @GetMapping(value = "/list")
    public String getAllBook(Model model){
        model.addAttribute("list",bookService.listBook());
        return "list";
    }
    @GetMapping(value = "/fakeBook") // seed book để test
    public String fakeBook(Book book){
        book.setName("book 1");
        book.setPrice(1000);
        bookService.addBook(book);
        return "redirect:/book/list";
    }
}
