package com.example.libraryeventproducer.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryeventproducer.domain.LibraryEvent;
import com.example.libraryeventproducer.domain.LibraryType;
import com.example.libraryeventproducer.service.LibraryEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LibraryEventsController {

	@Autowired
	private LibraryEventsService eventsService;

	@PostMapping("/v1/libraryEvents")
	public ResponseEntity<List<LibraryEvent>> postLibraryEvent(@RequestBody List<LibraryEvent> libraryEventRecords)
			throws JsonProcessingException, InterruptedException, ExecutionException {
		log.debug("********Message sent process Started");
//		eventsService.sendLibraryEvent(libraryEvent);

		eventsService.sendLibraryEventViaProducerRecord(libraryEventRecords);

		log.debug("********Message sent process completed");
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEventRecords);
	}

//	@PutMapping("/v1/libraryEvents")
//	public ResponseEntity<?> updateLibraryEvent(@RequestBody @Valid List<LibraryEvent> libraryEvent) throws JsonProcessingException {
//
//		for(LibraryEvent event : libraryEvent) {
//			ResponseEntity<String> validateResult = validateLibraryEvent(event);
//			if (validateResult != null) {
//				log.debug("********Message validation failed, {}: ", validateResult.getBody());
//				return validateResult;
//			}
//			log.debug("********Message sent process Started");
//			eventsService.sendLibraryEvent(event);
//		}
//		
//		log.debug("********Message sent process completed");
//		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
//
//	}

//	private ResponseEntity<String> validateLibraryEvent(@Valid LibraryEvent libraryEvent) {
//		if (libraryEvent.getLibraryEventId() == null) {
//			return ResponseEntity.badRequest().body("pass the library event id");
//		}
//
//		if (libraryEvent.getEventType() != LibraryType.UPDATE) {
//			return ResponseEntity.badRequest().body("Event type should be UPDATE");
//		}
//
//		return null;
//	}

}
