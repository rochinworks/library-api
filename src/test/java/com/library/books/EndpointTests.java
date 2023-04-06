package com.library.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.library.books.db.MemoryLibrary;
import com.library.books.models.BooksEntity;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemoryLibrary mockBooksDao;

    @Test
    public void healthCheckShouldReturnHealthy() throws Exception {
        assertThat(this.mockMvc
                .perform(get("/health"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Spring boot up and running"))));
    }

    @Test
    public void checkCreateBookHappyPath() throws Exception {
        BooksEntity book = new BooksEntity();
        book.setAuthor("some author");
        book.setTitle("some title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String bookJson = ow.writeValueAsString(book);

        assertThat(this.mockMvc
                .perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                ));
    }

    @Test
    public void checkGetBookHappyPath() throws Exception {
        BooksEntity book = new BooksEntity();
        book.setAuthor("some author");
        book.setTitle("some title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String bookJson = ow.writeValueAsString(book);

        when(mockBooksDao.createBook(book)).thenReturn("123");
        when(mockBooksDao.getBook("123")).thenReturn(book);

        MvcResult result = this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andReturn();

        assertThat(this.mockMvc.perform(get("/books/{id}", result))
                .andExpectAll(
                        status().isOk()
                ));
    }

    @Test
    public void checkDeleteBookHappyPath() throws Exception {
        BooksEntity book = new BooksEntity();
        book.setAuthor("some author");
        book.setTitle("some title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String bookJson = ow.writeValueAsString(book);

        when(mockBooksDao.createBook(book)).thenReturn("123");

        MvcResult result = this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andReturn();

        assertThat(this.mockMvc.perform(delete("/books/{id}", result))
                .andExpectAll(
                        status().isOk()
                ));
    }

    @Test
    public void checkUpdateBookHappyPath() throws Exception {
        BooksEntity book = new BooksEntity();
        book.setAuthor("some author");
        book.setTitle("some title");

        BooksEntity bookUpdated = new BooksEntity();
        book.setAuthor("some new author");
        book.setTitle("some new title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String bookJson = ow.writeValueAsString(book);
        String updatedBookJson = ow.writeValueAsString(bookUpdated);

        when(mockBooksDao.createBook(book)).thenReturn("123");
        when(mockBooksDao.getBook("123")).thenReturn(book);
        when(mockBooksDao.updateBook("123", bookUpdated)).thenReturn("234");

        // create book
        MvcResult result = this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andReturn();

        // assert update returns a different id
        MvcResult updateResult = this.mockMvc.perform(put("/books/{id}", result)
                        .content(updatedBookJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // check we can get the updated result
        assertThat(this.mockMvc.perform(get("/books/{id}", updateResult))
                .andExpectAll(
                        status().isOk()
                ));
    }
    @Test
    public void checkGetAllBooksHappyPath() throws Exception {
        BooksEntity book = new BooksEntity();
        book.setAuthor("some author");
        book.setTitle("some title");
        BooksEntity bookTwo = new BooksEntity();
        book.setAuthor("some other author");
        book.setTitle("some other title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String bookJson = ow.writeValueAsString(book);
        String bookTwoJson = ow.writeValueAsString(bookTwo);

        when(mockBooksDao.createBook(book)).thenReturn("123");
        when(mockBooksDao.getBook("123")).thenReturn(book);
        when(mockBooksDao.createBook(bookTwo)).thenReturn("234");
        when(mockBooksDao.getBook("234")).thenReturn(bookTwo);

        this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andReturn();

        this.mockMvc.perform(post("/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookTwoJson))
                .andReturn();

        assertThat(this.mockMvc.perform(get("/books"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                ));
    }
}
