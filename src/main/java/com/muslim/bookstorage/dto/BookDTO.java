package com.muslim.bookstorage.dto;

import com.muslim.bookstorage.entity.book.Access;
import com.muslim.bookstorage.entity.book.Genre;
import lombok.Data;

@Data
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private Genre genre;
    private Access access;
}
