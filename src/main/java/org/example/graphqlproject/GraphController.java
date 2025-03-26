package org.example.graphqlproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    @Autowired
    public GraphController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
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

    record BookInput(String title, String publisher, Long authorId){

    }
}
