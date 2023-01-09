package com.muslim.bookstorage.controller;

import com.muslim.bookstorage.dto.BookDTO;
import com.muslim.bookstorage.dto.CreateBookDTO;
import com.muslim.bookstorage.dto.UpdateBookDTO;
import com.muslim.bookstorage.entity.book.Genre;
import com.muslim.bookstorage.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("books")
public class BooksController {
    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void createBook(@Valid @RequestBody CreateBookDTO createBookDTO){
        bookService.createBook(createBookDTO);
    }

    @GetMapping
    public Set<BookDTO> findBooks(){
        return bookService.findBooks();
    }

    @GetMapping("byAuthor/{author}")
    public Set<BookDTO> findByAuthor(@PathVariable String author){
        return bookService.findByAuthor(author);
    }

    @GetMapping("byGenre/{genre}")
    public Set<BookDTO> findByGenre(@PathVariable Genre genre){
        return bookService.findByGenre(genre);
    }

    @PutMapping("{id}")
    public void updateBook(@PathVariable int id, @Valid @RequestBody UpdateBookDTO updateBookDTO){
        bookService.updateBook(id, updateBookDTO);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }
}
