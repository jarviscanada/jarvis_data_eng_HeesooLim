package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet() {
    when(dao.create(any())).thenReturn(new Tweet());
    Tweet tweet = service.postTweet(TweetUtils.getTweetObject());
    assertNull(tweet.getIdStr());
  }

  @Test
  public void showTweet() {
    when(dao.findById(any())).thenReturn(new Tweet());
    Tweet tweet = service.showTweet(TweetUtils.getValidFormatId(), new String[] {});
    assertNull(tweet.getIdStr());
  }

  @Test
  public void deleteTweet() {
    when(dao.deleteById(any())).thenReturn(new Tweet());
    List<Tweet> tweets = service.deleteTweets(new String[] {TweetUtils.getValidFormatId()});
    assertNull(tweets.get(0).getIdStr());
  }

}
