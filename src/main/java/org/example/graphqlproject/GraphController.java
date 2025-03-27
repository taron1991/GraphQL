package org.example.graphqlproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final String profileVal;


    @Autowired
    public GraphController(AuthorRepository authorRepository, BookRepository bookRepository, @Value("${spring.profiles.active}") String profileVal) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.profileVal = profileVal;
    }

    @QueryMapping
    public Iterable<Author> authors(){
        return authorRepository.findAll();
    }

    @QueryMapping
    public Author authorById(@Argument Long id){

        return authorRepository.findById(id).orElseThrow();
    }

    @MutationMapping
    Book  addBook(@Argument BookInput book){
        Book book1 = new Book(book.title(),book.publisher(),authorRepository.findById(book.authorId()).orElseThrow());
        bookRepository.save(book1);
        return book1;
    }

    @MutationMapping
    public Boolean deleteBookById(@Argument Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new RuntimeException();
        } else {
            bookRepository.deleteById(id);
        }
        return true;
    }


    @MutationMapping
    public Author updateAuthor(@Argument long id,@Argument AuthorInput author){
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        existingAuthor.setName(author.name());

        List<Book> books = bookRepository.findAllById(author.bookid);

        existingAuthor.setBooks(books);
        authorRepository.save(existingAuthor);
        return existingAuthor;
    }

    @QueryMapping
    public String profile(){
        return "ACTIVE "+profileVal;
    }

    record AuthorInput(String name,List<Long> bookid){}

    record BookInput(String title, String publisher, Long authorId){
    }
}























