package components.movie;

import annotations.Autowired;
import annotations.Component;
import annotations.Preferred;

@Component //(customProp="inMem")
@Preferred
public class InMemoryMovieFinder implements MovieFinder {
    private final DatabaseAccess databaseAccess;

    @Autowired
    public InMemoryMovieFinder(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public void findMovies() {
        System.out.println("Finding movies in memory...");
        databaseAccess.accessDatabase();
    }
}
