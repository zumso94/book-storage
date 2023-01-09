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
import com.muslim.bookstorage.exception.NotPdfFormatException;
import com.muslim.bookstorage.security.SecurityUser;
import com.muslim.bookstorage.utils.FileUtils;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookDAO bookDAO;
    private final static String PDF_TYPE = "application/pdf";

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

    @Transactional
    public void uploadBook(int id, MultipartFile file) {
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        checkUserNames(book.getUserName());
        checkPdfFormat(file.getContentType());
        book.setBookPath(FileUtils.saveFile(file));

        bookDAO.save(book);
    }

    @SneakyThrows
    @Transactional
    public byte[] getBookFile(int id) {
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        if (book.getBookPath() == null) {
            throw new BookNotFoundException("Book file not found");
        }
        if (!isTheSameUser(book.getUserName()) && !book.isPublic()) {
            throw new ForbiddenException("Forbidden");
        }
        return Files.readAllBytes(Path.of(book.getBookPath()));
    }

    private void checkPdfFormat(String contentType) {
        if (!PDF_TYPE.equals(contentType)) {
            throw new NotPdfFormatException("Wrong format type");
        }
    }

    private void checkUserNames(String userName) {
        if (!isTheSameUser(userName)) {
            throw new ForbiddenException("FORBIDDEN");
        }
    }

    private boolean isTheSameUser(String userName) {
        return userName.equalsIgnoreCase(currentUsername());
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
