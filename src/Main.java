import components.movie.MovieLister;
import ioc.IoCContainer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Initialize IoC container
        IoCContainer container = new IoCContainer("components.movie");

        // Retrieve and use MovieLister
        MovieLister movieLister = container.resolve(MovieLister.class);
        movieLister.listMovies();
    }
}