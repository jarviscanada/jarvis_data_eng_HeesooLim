package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  @Autowired
  public TwitterController(Service service) {this.service = service;}

  // TwitterApp "post" "tweet_text" "latitude:longitude"
  @Override
  public Tweet postTweet(String[] args) {
    String[] coords = args[1].split(COORD_SEP);
    float longitude = Float.parseFloat(coords[1]);
    float latitude = Float.parseFloat(coords[0]);
    Tweet tweet = new Tweet(args[0], longitude, latitude, "Point");
    return service.postTweet(tweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    String id = args[0];
    if (args.length > 1) {
      return service.showTweet(id, args[1].split(COMMA));
    }
    return service.showTweet(id, new String[]{});
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    return service.deleteTweets(args[0].split(COMMA));
  }
}
