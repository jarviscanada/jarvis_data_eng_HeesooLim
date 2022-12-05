package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TwitterControllerTest {
  TwitterController controller;
  @Before
  public void initialize() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao dao = new TwitterDao(helper);
    TwitterService service = new TwitterService(dao);
    controller = new TwitterController(service);
  }
  @Test
  public void testPostTweet() {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
//    Tweet tweet = TweetUtils.getTweetObject();
//    float[] coords = tweet.getCoordinates().getCoordinates();

    String text = TweetUtils.newTweetText();
    float[] coords = TweetUtils.getCoordinateValue();
    String coordString = coords[1] + ":" + coords[0];

    Tweet postedTweet = controller.postTweet(new String[] {text, coordString});

    Assert.assertEquals(text, postedTweet.getText());
    Assert.assertEquals(new Coordinates(coords, "Point"), postedTweet.getCoordinates());
  }

  @Test
  public void testShowTweet() {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
//    Tweet tweet = TweetUtils.getTweetObject();
//    float[] coords = tweet.getCoordinates().getCoordinates();
//    String coordString = coords[1] + ":" + coords[0];

    String text = TweetUtils.newTweetText();
    float[] coords = TweetUtils.getCoordinateValue();
    String coordString = coords[1] + ":" + coords[0];

    Tweet postedTweet = controller.postTweet(new String[] {text, coordString});
    Tweet tweetFound = controller.showTweet(new String[] {postedTweet.getIdStr(), "id,text"});

    Assert.assertEquals(postedTweet.getText(), tweetFound.getText());
    Assert.assertEquals(postedTweet.getIdStr(), tweetFound.getIdStr());
  }

  @Test
  public void testDeleteTweet() {
    List<Tweet> deletedTweets;
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    Tweet tweet = TweetUtils.getTweetObject();
    String newTweetText = tweet.getText() + "new";
    float[] coords = tweet.getCoordinates().getCoordinates();
    String coordString = coords[1] + ":" + coords[0];

    Tweet tweetToDelete1 = controller.postTweet(new String[] {tweet.getText(), coordString});
    Tweet tweetToDelete2 = controller.postTweet(new String[] {newTweetText, coordString});

    try {
      deletedTweets = controller.deleteTweet(new String[] {tweetToDelete1.getIdStr() + "," + tweetToDelete2.getIdStr()});
    }
    catch (Exception e) {
      Assert.fail();
      return;
    }

    Assert.assertNotNull(deletedTweets);
    Assert.assertEquals(deletedTweets.size(), 2);
    Assert.assertEquals(tweetToDelete1.getIdStr(), deletedTweets.get(0).getIdStr());
    Assert.assertEquals(tweetToDelete2.getIdStr(), deletedTweets.get(1).getIdStr());
  }
}