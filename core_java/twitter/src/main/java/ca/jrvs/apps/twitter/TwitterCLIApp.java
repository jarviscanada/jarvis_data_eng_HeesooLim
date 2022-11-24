package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public class TwitterCLIApp {
  private Controller controller;

  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    CrdDao dao = new TwitterDao(helper);
    Service service = new TwitterService(dao);
    Controller controller = new TwitterController(service);
    TwitterCLIApp app = new TwitterCLIApp(controller);
    app.run(args);
  }

  public void run(String[] args) {
    int len = args.length;

    if (len < 2)
      throw new RuntimeException("Please pass valid arguments.\nUSAGE: TwitterApp post|show|delete [options]");
    String action = args[0];

    if (action.equalsIgnoreCase("post") && len == 3) {
      String[] newArgs = {args[1], args[2]};
      prettyPrint(controller.postTweet(newArgs));
    }
    else if (action.equalsIgnoreCase("show") && len <= 3) {
      String[] newArgs = new String[len - 1];
      String[] fields = len == 2 ? new String[]{} : args[2].split(",");
      System.arraycopy(args, 1, newArgs, 0, newArgs.length);
      prettyPrint(controller.showTweet(newArgs), fields);
    }
    else if (action.equalsIgnoreCase("delete") && len == 2) {
      String[] newArgs = {args[1]};
      List<Tweet> tweets = controller.deleteTweet(newArgs);
      tweets.forEach(this::prettyPrint);
    }
    else {
      throw new RuntimeException("Please pass valid arguments.\nUSAGE: TwitterApp post|show|delete [options]");
    }
  }

  public void prettyPrint(Tweet tweet) {
    try {
      System.out.println(TweetUtils.tweetToJson(tweet));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void prettyPrint(Tweet tweet, String[] fields) {
    try {
      System.out.println(TweetUtils.tweetToJson(tweet, fields));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
