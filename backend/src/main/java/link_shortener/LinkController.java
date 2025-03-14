package link_shortener;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/urls")
public class LinkController {
    private final LinkRepository linkRepository;

    LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @GetMapping("/{id}")
    Link findbyId(@PathVariable Long id) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isPresent()) {
            return link.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found.");
        }
    }

    @PostMapping
    void create(Link link) {
        linkRepository.create(link);
    }

}
