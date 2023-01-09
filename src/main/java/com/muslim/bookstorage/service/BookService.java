package com.muslim.bookstorage.service;

import com.muslim.bookstorage.dao.BookDAO;
import com.muslim.bookstorage.dto.BookDTO;
import com.muslim.bookstorage.dto.CreateBookDTO;
import com.muslim.bookstorage.dto.UpdateBookDTO;
import com.muslim.bookstorage.entity.book.Access;
import com.muslim.bookstorage.entity.book.Book;
import com.muslim.bookstorage.entity.book.Genre;
import com.muslim.bookstorage.exception.BookNotFoundException;
import com.muslim.bookstorage.exception.ForbiddenException;
import com.muslim.bookstorage.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Transactional
    public void createBook(CreateBookDTO createBookDTO) {
        Book book = new Book();
        book.setTitle(createBookDTO.getTitle());
        book.setAuthor(createBookDTO.getAuthor());
        book.setGenre(createBookDTO.getGenre());
        book.setAccess(createBookDTO.getAccess());
        book.setUserName(currentUsername());

        bookDAO.save(book);
    }

    @Transactional(readOnly = true)
    public Set<BookDTO> findBooks() {
        return bookDAO.findByUserName(currentUsername()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Transactional(readOnly = true)
    public Set<BookDTO> findByAuthor(String author) {
        return bookDAO.findByAuthor(author).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Transactional(readOnly = true)
    public Set<BookDTO> findByGenre(Genre genre) {
        return bookDAO.findByGenreAndAccess(genre, Access.PUBLIC).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Transactional
    public void updateBook(int id, UpdateBookDTO updateBookDTO) {
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        checkUserNames(book.getUserName());

        book.setTitle(updateBookDTO.getTitle());
        book.setAuthor(updateBookDTO.getAuthor());
        book.setGenre(updateBookDTO.getGenre());
        book.setAccess(updateBookDTO.getAccess());

        bookDAO.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        checkUserNames(book.getUserName());

        bookDAO.deleteById(id);
    }

    private void checkUserNames(String userName) {
        if(!userName.equalsIgnoreCase(currentUsername())) {
            throw new ForbiddenException("FORBIDDEN");
        }
    }

    private String currentUsername() {
        SecurityUser principal = (SecurityUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUsername();
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setAccess(book.getAccess());

        return bookDTO;
    }
}
