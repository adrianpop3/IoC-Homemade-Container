import components.movie.*;
import ioc.IoCContainer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MovieMain {
    public static void main(String[] args) {
        IoCContainer movieContainer = new IoCContainer("components.movie");
        
        MovieLister movieLister = movieContainer.resolve(MovieLister.class);
        movieLister.listMovies();
    }
}