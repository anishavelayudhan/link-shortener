package link_shortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    Purpose: The repository layer is responsible for interacting with the database.
    It handles the actual data operations (e.g., storing data, retrieving data, updating, or deleting).

    Flow: The service calls the repository to fetch or persist data to the database.
    The repository makes the actual database queries and returns the data to the service.
*/

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByLongUrl(String longUrl);

    Optional<Link> findByShortUrl(String shortUrl);
}
