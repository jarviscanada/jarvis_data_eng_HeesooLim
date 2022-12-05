package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private TwitterHttpHelper helper;

  @Before
  public void initialize() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
  }

  @Test
  public void testHttpPost() throws Exception {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String status = TweetUtils.newTweetText();
    HttpResponse res = helper.httpPost(URI.create(
        "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status)));

    Tweet tweet = TweetUtils.responseToObject(res, Tweet.class);
    Assert.assertEquals(status, tweet.getText());
  }

  @Test
  public void testHttpGet() throws IOException {
    HttpResponse res = helper.httpGet(URI.create("https://api.twitter.com/1.1/statuses/user_timeline.json"));

    String resString = EntityUtils.toString(res.getEntity());
    Assert.assertFalse(resString.contains("error"));
  }
}