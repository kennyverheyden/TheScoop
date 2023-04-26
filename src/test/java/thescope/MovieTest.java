package thescope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thescope.exceptions.EntityNotFoundException;
import thescope.models.Movie;
import thescope.services.MovieService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void findMovieByIdTest() {
        Long id = 15L;
        Movie movie = movieService.findMovieById(id);
        assertNotNull(movie);
    }

    @Test
    public void findMoviesByGenreTest() {
        String genre = "Drama";
        List<Movie> movies = movieService.findMoviesByGenre(genre);
        assertEquals("Close", movies.get(0).getTitle());
    }

    @Test
    public void updateMovieTest() {
        Long id = 25L;
        Movie movie = new Movie("The Last kingdom", "Drama", 7.9, 132, true);
        movieService.updateMovie(movie, id);
        assertEquals(movie.getTitle(), movieService.findMovieById(id).getTitle());
    }

    @Test
    public void addMovieTest() {
        Long id = 31L;
        Movie movie = new Movie("The Last kingdom", "Drama", 7.9, 132, true);
        movieService.addMovie(movie);
        assertEquals(movie, movieService.findMovieById(id));
    }

    @Test
    public void deleteMovieTest() {
        Long id = 4L;
        movieService.deleteMovieById(id);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> Integer.parseInt("4"));
        String expectedMessage = "The movie with id";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
