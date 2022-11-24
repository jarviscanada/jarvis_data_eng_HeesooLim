package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceTest {
  TwitterService service;

  @Before
  public void initialize() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao dao = new TwitterDao(helper);
    service = new TwitterService(dao);
  }
  @Test
  public void testPostLongTweet() {
    Tweet tweet = TweetUtils.getTweetObject();
    StringBuilder text = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      text.append(tweet.getText());
    }
    tweet.setText(text.toString());
    try {
      service.postTweet(tweet);
    }
    catch (Exception e) {
      Assert.assertTrue(true);
      return;
    }
    Assert.fail();
  }

  @Test
  public void testPostWrongGeoTweet() {
    Tweet tweet = TweetUtils.getTweetObject();
    Coordinates invalidCoords = new Coordinates(new float[]{200f, 200f}, "");
    tweet.setCoordinates(invalidCoords);

    try {
      service.postTweet(tweet);
    }
    catch (Exception e) {
      Assert.assertTrue(true);
      return;
    }
    Assert.fail();
  }

  @Test
  public void testPostValidTweet() {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    Tweet tweet = TweetUtils.getTweetObject();
    Tweet newTweet = service.postTweet(tweet);
    float[] tweetCoords = tweet.getCoordinates().getCoordinates();
    float[] newTweetCoords = newTweet.getCoordinates().getCoordinates();

    Assert.assertEquals(tweet.getText(), percentEscaper.escape(newTweet.getText()));
    Assert.assertTrue(tweet.getText().length() <= 140);
    Assert.assertEquals(tweetCoords[0], newTweetCoords[0], 0.0);
    Assert.assertEquals(tweetCoords[1], newTweetCoords[1], 0.0);
    Assert.assertTrue(tweetCoords[0] <= 180 && tweetCoords[0] >= -180);
    Assert.assertTrue(tweetCoords[1] <= 90 && tweetCoords[1] >= -90);
  }

  @Test
  public void testShowTweet() {
    Tweet tweet = TweetUtils.getTweetObject();
    Tweet newTweet = service.postTweet(tweet);

    Tweet tweetFound = service.showTweet(newTweet.getIdStr(), new String[] {});
    Assert.assertEquals(newTweet, tweetFound);
  }

  @Test
  public void testShowWrongIdTweet() {
    Tweet tweet = TweetUtils.getTweetObject();
    Tweet newTweet = service.postTweet(tweet);
    // add alphabet and special characters to make the ID invalid
    newTweet.setIdStr(TweetUtils.getInvalidId());

    try {
      service.showTweet(newTweet.getIdStr(), new String[] {});
    }
    catch (Exception e) {
      Assert.assertTrue(true);
      return;
    }
    Assert.fail();
  }

  @Test
  public void testDeleteWrongIdTweets() {
//    Tweet tweet = TweetUtils.getTweetObject();
//    Tweet newTweet = service.postTweet(tweet);

    try {
      service.deleteTweets(new String[] {TweetUtils.getInvalidId()});
    }
    catch (Exception e) {
      Assert.assertTrue(true);
      return;
    }
    Assert.fail();
  }

  @Test
  public void testDeleteTweets() {
    List<Tweet> deletedTweets;
    Tweet tweet = TweetUtils.getTweetObject();
    Tweet newTweet = service.postTweet(tweet);
    try {
      deletedTweets = service.deleteTweets(new String[] {newTweet.getIdStr()});
    }
    catch (Exception e) {
      Assert.fail();
      return;
    }

    Assert.assertNotNull(deletedTweets);
    Assert.assertTrue(deletedTweets.size() > 0);
    Assert.assertEquals(newTweet.getIdStr(), deletedTweets.get(0).getIdStr());
  }
}