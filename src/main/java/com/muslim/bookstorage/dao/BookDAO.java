package com.muslim.bookstorage.dao;

import com.muslim.bookstorage.entity.book.Access;
import com.muslim.bookstorage.entity.book.Book;
import com.muslim.bookstorage.entity.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface BookDAO extends JpaRepository<Book, Integer> {
    Set<Book> findByUserName(String userName);
    Set<Book> findByGenreAndAccess(Genre genre, Access access);
    @Query(
            value = "Select * From Books WHERE lower(author) LIKE lower(concat('%',:author, '%')) and access='PUBLIC'",
            nativeQuery = true
    )
    Set<Book> findByAuthor(@Param("author") String author);
}
