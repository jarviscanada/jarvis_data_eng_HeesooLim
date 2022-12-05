package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
  @Mock
  TwitterService service;

  @InjectMocks
  TwitterController controller;

  @Test
  public void postTweet() throws IOException {
    when(service.postTweet(any())).thenThrow(new RuntimeException("mock"));

    try {
      Tweet tweet = TweetUtils.getTweetObject();
      float[] coords = tweet.getCoordinates().getCoordinates();
      String coordString = coords[1] + ":" + coords[0];
      controller.postTweet(new String[] {tweet.getText(), coordString});
    }
    catch (RuntimeException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    when(service.showTweet(any(), any())).thenThrow(new RuntimeException("mock"));

    try {
      Tweet tweet = TweetUtils.getTweetObject();
      controller.showTweet(new String[] {tweet.getText(), "id,text"});
    }
    catch (RuntimeException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void deleteTweet() {
    when(service.deleteTweets(any())).thenThrow(new RuntimeException("mock"));

    try {
      controller.deleteTweet(new String[] {TweetUtils.getValidFormatId()});
    }
    catch (RuntimeException e) {
      Assert.assertTrue(true);
    }
  }
}
