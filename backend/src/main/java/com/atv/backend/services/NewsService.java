package com.atv.backend.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    public List<NewsResponse> getNews() {
        List<NewsResponse> mockedNews = new ArrayList<>();

        mockedNews.add(new NewsResponse(
                "Breaking: SpaceX Successfully Launches Falcon 9 Rocket",
                "SpaceX has successfully launched its Falcon 9 rocket, marking another milestone in space exploration.",
                "https://example.com/news1",
                "2023-05-28T12:34:56Z",
                "In a historic achievement, SpaceX's Falcon 9 rocket soared into the sky, carrying a payload of satellites. The launch took place at Cape Canaveral, Florida, and signifies a major advancement in reusable rocket technology."
        ));

        mockedNews.add(new NewsResponse(
                "Tech Giant Unveils Next-Generation Smartphone with AI Features",
                "The leading tech company has revealed its latest smartphone model, packed with cutting-edge AI capabilities.",
                "https://example.com/news2",
                "2023-05-27T10:20:30Z",
                "The newest smartphone from the tech giant combines sleek design with powerful AI functionalities. The device uses advanced machine learning algorithms to enhance user experience and deliver unprecedented performance. With features like facial recognition and voice commands, this smartphone sets a new benchmark in the industry."
        ));

        mockedNews.add(new NewsResponse(
                "Scientists Discover New Species in Amazon Rainforest",
                "A team of researchers has identified a previously unknown species in the heart of the Amazon rainforest.",
                "https://example.com/news3",
                "2023-05-26T08:12:14Z",
                "During an expedition deep into the Amazon rainforest, scientists stumbled upon a remarkable discovery—a new species of frog. The vibrant-colored amphibian, named Dendrobates amazonicus, showcases unique characteristics and behaviors that have never been documented before. This finding highlights the importance of preserving the biodiversity of the Amazon."
        ));

        mockedNews.add(new NewsResponse(
                "World Leaders Gather for Global Climate Summit",
                "Representatives from nations around the world convene to address urgent climate change issues.",
                "https://example.com/news4",
                "2023-05-25T06:08:10Z",
                "In an effort to combat the escalating climate crisis, world leaders have come together for a global climate summit. The summit aims to foster international collaboration, establish ambitious emission reduction targets, and promote sustainable practices across various sectors. The urgency of the situation calls for immediate action to safeguard the planet for future generations."
        ));

        return mockedNews;
    }


    public static class NewsResponse {
        private final String title;
        private final String description;
        private final String url;
        private final String publishedAt;
        private final String content;

        public NewsResponse(String title, String description, String url, String publishedAt, String content) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.publishedAt = publishedAt;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getContent() {
            return content;
        }
    }
}
