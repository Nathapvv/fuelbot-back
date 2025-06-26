package backend.fuelbotbackend.business.location.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationService {
    @Value("${opencage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchLocation(String query) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
            .queryParam("q", query)
            .queryParam("key", apiKey)
            .queryParam("limit", "5")
            .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
