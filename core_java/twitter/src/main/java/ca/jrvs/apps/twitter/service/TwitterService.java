package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

  private CrdDao dao;

  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateIdFormat(id);
    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedTweets = new ArrayList<>();

    for (String id : ids) {
      validateIdFormat(id);
      deletedTweets.add((Tweet) dao.deleteById(id));
    }
    return deletedTweets;
  }

  private void validatePostTweet(Tweet tweet) {
    float latitude = tweet.getCoordinates().getCoordinates()[1];
    float longitude = tweet.getCoordinates().getCoordinates()[0];
    boolean isLatitudeValid = latitude >= -90 && latitude <= 90;
    boolean isLongitudeValid = longitude >= -180 && longitude <= 180;

    if (tweet.getText().length() > 140 || !isLatitudeValid || !isLongitudeValid) {
      throw new IllegalArgumentException();
    }
  }

  private void validateIdFormat(String id) {
    if (!id.matches("[0-9]+")) {
      throw new IllegalArgumentException();
    }
  }
}
