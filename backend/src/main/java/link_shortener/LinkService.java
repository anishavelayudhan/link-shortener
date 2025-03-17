package link_shortener;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.net.URI;
import java.net.URISyntaxException;

/*
    Purpose: The service layer holds the business logic. It doesn’t deal with HTTP requests directly,
    but it processes the data and makes decisions based on the request.
    It can also call the repository layer to interact with the database.

    Flow: When the controller makes a request, it delegates the logic to the service layer,
    which processes the data and interacts with the repository to fetch or modify data.
*/

@Service
public class LinkService {
    private static final int MAX_LENGTH = 7;
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
        try {
            new URI(link.getLongUrl());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        
        Optional<Link> linkOptional = linkRepository.findByLongUrl(link.getLongUrl());
        if (linkOptional.isEmpty()) {
            link.setShortUrl(generateShortUrl(link.getLongUrl()));
            return linkRepository.save(link);
        } else {
            return linkOptional.get();
        }
    }

    private String generateShortUrl(String longUrl) {
        String shortUrl;
        do {
            shortUrl = hashToShortUrl(longUrl);
        } while (linkRepository.findByShortUrl(shortUrl).isPresent());
        return shortUrl;
    }

    private String hashToShortUrl(String longUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(longUrl.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < MAX_LENGTH ; i++) {
                hexString.append(String.format("%02x", hashBytes[i]));
            }

            return hexString.substring(0, MAX_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash for short URL", e);
        }
    }

    public Optional<Link> getRedirectUrl(String shortUrl) {
        Optional<Link> linkOptional = linkRepository.findByShortUrl(shortUrl);
        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            incrementVisits(link);
            return Optional.of(link);
        }
        return linkOptional;
    }

    private void incrementVisits(Link link) {
        link.setVisits(link.getVisits() + 1);
        linkRepository.save(link);
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
