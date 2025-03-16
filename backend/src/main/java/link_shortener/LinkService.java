package link_shortener;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Base64;

/*
    Purpose: The service layer holds the business logic. It doesn’t deal with HTTP requests directly,
    but it processes the data and makes decisions based on the request.
    It can also call the repository layer to interact with the database.

    Flow: When the controller makes a request, it delegates the logic to the service layer,
    which processes the data and interacts with the repository to fetch or modify data.
*/

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    public Optional<Link> findById(Long id) {
        return linkRepository.findById(id);
    }

    public Link create(Link link) {
        Optional<Link> existingLink = linkRepository.findByLongUrl(link.getLongUrl());
        if (existingLink.isEmpty()) {
            link.setShortUrl(encodeUrl(link.getLongUrl()));
            return linkRepository.save(link);
        } else {
            return existingLink.get();
        }
    }

    private String encodeUrl(String longUrl) {
        return Base64.getUrlEncoder().encodeToString(longUrl.getBytes());
    }

    public Optional<Link> findShortUrl(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    public Link count(Link link) {
        link.setVisits(link.getVisits() + 1);
        return linkRepository.save(link);
    }

    public boolean remove(Long id) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isPresent()) {
            linkRepository.delete(link.get());
            return true;
        }
        return false;
    }
}
