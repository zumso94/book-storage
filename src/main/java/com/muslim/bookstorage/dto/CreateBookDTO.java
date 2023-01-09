package com.muslim.bookstorage.dto;

import com.muslim.bookstorage.entity.book.Access;
import com.muslim.bookstorage.entity.book.Genre;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateBookDTO {
    @NotBlank
    @Size(min=2, max=255)
    private String title;
    @NotBlank
    @Size(min=2, max=128)
    private String author;
    private Genre genre;
    private Access access;
}
