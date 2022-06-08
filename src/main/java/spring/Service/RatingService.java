package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.Application;
import spring.Entity.BookRating;
import spring.Entity.Model.Rating;
import spring.Repository.RatingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;
    public void save(Rating rating){
        ratingRepository.save(rating);
    }
    public List<BookRating> bookRating(){
        Pageable pageable = PageRequest.of(0, 16);
        List<BookRating> bookRatings = ratingRepository.getAllBookRating(pageable);
        bookRatings.stream().filter(result->result.getRating()>4).collect(Collectors.toList());
        return bookRatings;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void deleteRating(){
        ratingRepository.deleteRatingByRating();
    }
}
