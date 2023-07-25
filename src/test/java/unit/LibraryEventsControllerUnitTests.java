//package unit;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.mockito.ArgumentMatchers.isA;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.example.libraryeventproducer.controller.LibraryEventsController;
//import com.example.libraryeventproducer.domain.Book;
//import com.example.libraryeventproducer.domain.LibraryEvent;
//import com.example.libraryeventproducer.service.LibraryEventsService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
////test slice
//
//@WebMvcTest(controllers = {LibraryEventsController.class})
//public class LibraryEventsControllerUnitTests {
//	
//	@Autowired
//	MockMvc mockMvc;
//	
//	@Autowired
//	ObjectMapper mapper;
//	
//	@MockBean
//	LibraryEventsService eventsService;
//	
//	@Test
//	void postLibraryEvents() throws Exception {
//		
//		var json = mapper.writeValueAsString(new LibraryEvent(null, null, new Book(101, "AC", "AD")));
//		when(eventsService.sendLibraryEvent(isA(LibraryEvent.class)))
//		.thenReturn(null);
//		
//			mockMvc
//			.perform(MockMvcRequestBuilders.post("/v1/libraryEvents")
//					.content(json)
//					.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isCreated());
//		
//		
//	}
//
//}
