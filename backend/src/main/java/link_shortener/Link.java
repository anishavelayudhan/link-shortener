package link_shortener;

import jakarta.persistence.*;

// TODO: Apply lombok
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String longUrl;
    String shortUrl;
    Integer visits;

    public Link() {}

    public Link(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.visits = 0;
    }

    public Long getId() {
        return id;
    }

    public Integer getVisits() {
        return visits;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}