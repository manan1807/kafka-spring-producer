package com.example.libraryeventproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
	private Integer bookId;
	private String bookName;
	private String bookAuthor;
}
