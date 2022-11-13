package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TwitterApiTest {
  private static String CONSUMER_KEY = System.getenv("consumerKey");
  private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static String ACCESS_TOKEN = System.getenv("accessToken");
  private static String TOKEN_SECRET = System.getenv("tokenSecret");

  public static void main(String[] args) throws Exception {
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

    String status = "It's a cold day!";
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String text = "{\"text\": \"Testing from java\"}";
    HttpPost req = new HttpPost("https://api.twitter.com/2/tweets");

    consumer.sign(req);

    StringEntity entity = new StringEntity(text);
    req.setEntity(entity);
    req.setHeader("Accept", "application/json");
    req.setHeader("Content-type", "application/json");

    Arrays.stream(req.getAllHeaders()).forEach(r -> {
      System.out.println("Header: "+r);
    });

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpResponse res = httpClient.execute(req);
    System.out.println(EntityUtils.toString(res.getEntity()));
  }
}
