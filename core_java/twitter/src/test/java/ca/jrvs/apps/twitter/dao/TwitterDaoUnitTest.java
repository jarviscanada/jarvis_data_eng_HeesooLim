package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  @Test
  public void postTweet() throws IOException {
    performTweetTest();
  }

  @Test
  public void findTweet() throws IOException {
    performTweetTest();
  }

  @Test
  public void deleteTweet() throws IOException {
    performTweetTest();
  }

  /**
   * Perform test for posting, finding, deleting a tweet
   */
  @Test
  public void performTweetTest() throws IOException {
    // get the StackTrace and define the type of test
    int testType = 0;
    String stackTrace = Arrays.toString(Thread.currentThread().getStackTrace());
    if (stackTrace.contains("postTweet"))
      testType = 1;
    else if (stackTrace.contains("deleteTweet"))
      testType = 2;
    else if (stackTrace.contains("findTweet"))
      testType = 3;

    if (testType > 3 || testType < 1)
      throw new RuntimeException("Invalid test type. (Range is 1-3)");

    if (testType == 1 || testType == 2)
      when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    else
      when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));

    try {
      if (testType == 1)
        dao.create(TweetUtils.getTweetObject());
      else if (testType == 2)
        dao.deleteById(isNotNull()); // Exception should be thrown here
      else
        dao.findById(isNotNull()); // Exception should be thrown here
      fail(); // otherwise fail
    }
    catch (RuntimeException e) {
      Assert.assertTrue(true);
    }

    if (testType == 1 || testType == 2)
      when (mockHelper.httpPost(isNotNull())).thenReturn(null);
    else
      when (mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = TweetUtils.jsonToObject(TweetUtils.TWEET_JSON_STRING, Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = null;
    if (testType == 1)
      tweet = spyDao.create(TweetUtils.getTweetObject());
    else if (testType == 2)
      tweet = spyDao.deleteById(isNotNull());
    else
      tweet = spyDao.findById(isNotNull());

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}
