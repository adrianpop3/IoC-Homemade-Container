package components.movie;

import annotations.Autowired;
import annotations.Component;

@Component(customProp="inWeb")
public class InWebMovieFinder implements MovieFinder {
    private final DatabaseAccess databaseAccess;

    @Autowired
    public InWebMovieFinder(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public void findMovies() {
        System.out.println("Finding movies in Web...");
        databaseAccess.accessDatabase();
    }
}
