package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.google.gdata.util.common.base.PercentEscaper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoTest {

  private TwitterDao dao;

  @Before
  public void initialize() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    dao = new TwitterDao(helper);
  }

  @Test
  public void testCreate() {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    Tweet tweet = TweetUtils.getTweetObject();
    Tweet newTweet = dao.create(tweet);
    float[] tweetCoords = tweet.getCoordinates().getCoordinates();
    float[] newTweetCoords = newTweet.getCoordinates().getCoordinates();

    Assert.assertEquals(tweet.getText(), percentEscaper.escape(newTweet.getText()));
    Assert.assertEquals(tweetCoords[0], newTweetCoords[0], 0.0);
    Assert.assertEquals(tweetCoords[1], newTweetCoords[1], 0.0);
  }

  @Test
  public void testFindById() {
    Tweet tweetCreated = dao.create(TweetUtils.getTweetObject());
    Tweet tweetFound = dao.findById(tweetCreated.getIdStr());
    Assert.assertEquals(tweetCreated, tweetFound);
  }

  @Test
  public void testDeleteById() {
    Tweet tweetCreated = dao.create(TweetUtils.getTweetObject());
    Tweet tweetDeleted = dao.deleteById(tweetCreated.getIdStr());
    Assert.assertEquals(tweetCreated, tweetDeleted);
  }
}