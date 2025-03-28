package link_shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/*
    Purpose: The controller acts as the entry point for the API.
    It receives incoming requests (like GET or POST) from the client (browser, mobile app, etc.),
    processes them, and returns the appropriate response.

    Flow: When a user (client) makes a request to the server (for example, a GET request to /users),
    the controller receives this request and routes it to the correct service.
*/

@RestController
@RequestMapping("/api/")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<Link> findAll() {
        return linkService.getAllLinks();
    }

    @GetMapping("/id/{id}")
    public Link findbyId(@PathVariable Long id) {
        return linkService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        Optional<Link> linkOptional = linkService.getRedirectUrl(shortUrl);
        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            URI redirectUri = URI.create(link.getLongUrl());
            return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/")
    public ResponseEntity<Link> create(@RequestBody Link link) {
        try {
            Link createdLink = linkService.create(link);
            return new ResponseEntity<>(createdLink, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Link creation failed", e);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!linkService.remove(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
