package com.example.libraryeventproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEvent {

	private String libraryEventId;
	private LibraryType eventType;
	private Book book;
}
