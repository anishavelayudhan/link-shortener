package link_shortener;

import java.util.List;
import java.util.Optional;

public interface LinkRepository {

    List<Link> findAll();

    Optional<Link> findById(Long id);

    void create(Link link);

    void update(Link link, Long id);

    void delete(Long id);
}
