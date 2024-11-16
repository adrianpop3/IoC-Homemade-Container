package components.movie;

import annotations.Autowired;
import annotations.Component;
import annotations.Preferred;

@Component
@Preferred
public class MovieListerImpl implements MovieLister {
    private final MovieFinder movieFinder;

    @Autowired //(implType="inMem")
    public MovieListerImpl(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    @Override
    public void listMovies() {
        System.out.println("\nListing movies...");
        movieFinder.findMovies();
    }
}
