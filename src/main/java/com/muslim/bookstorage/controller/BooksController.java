package com.muslim.bookstorage.controller;

import com.muslim.bookstorage.aop.LogMethod;
import com.muslim.bookstorage.dto.BookDTO;
import com.muslim.bookstorage.dto.CreateBookDTO;
import com.muslim.bookstorage.dto.UpdateBookDTO;
import com.muslim.bookstorage.entity.book.Genre;
import com.muslim.bookstorage.service.BookService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("books")
public class BooksController {
    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @LogMethod
    @PostMapping
    public void createBook(@Valid @RequestBody CreateBookDTO createBookDTO) {
        bookService.createBook(createBookDTO);
    }

    @LogMethod
    @GetMapping
    public Set<BookDTO> findBooks() {
        return bookService.findBooks();
    }

    @LogMethod
    @GetMapping("byAuthor/{author}")
    public Set<BookDTO> findByAuthor(@PathVariable String author) {
        return bookService.findByAuthor(author);
    }

    @LogMethod
    @GetMapping("byGenre/{genre}")
    public Set<BookDTO> findByGenre(@PathVariable Genre genre) {
        return bookService.findByGenre(genre);
    }

    @LogMethod
    @PutMapping("{id}")
    public void updateBook(@PathVariable int id, @Valid @RequestBody UpdateBookDTO updateBookDTO) {
        bookService.updateBook(id, updateBookDTO);
    }

    @LogMethod
    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    @LogMethod
    @PostMapping("{id}")
    public void uploadBook(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        bookService.uploadBook(id, file);
    }

    @LogMethod
    @GetMapping("download/{id}")
    public HttpEntity<byte[]> download(@PathVariable int id) {
        byte[] bookFile = bookService.getBookFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=book.pdf");
        headers.setContentLength(bookFile.length);

        return new HttpEntity<>(bookFile, headers);
    }
}
