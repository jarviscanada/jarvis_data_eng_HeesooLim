package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  /**
   * Dependencies are specified as private member variables
   */
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    httpClient = new DefaultHttpClient();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    HttpPost req = new HttpPost(uri);
    HttpResponse res = null;

    req.setHeader("Accept", "application/json");
    req.setHeader("Content-type", "application/json");
    try {
      consumer.sign(req);
      HttpClient httpClient = HttpClientBuilder.create().build();
      res = httpClient.execute(req);

    } catch (OAuthMessageSignerException | OAuthExpectationFailedException |
             OAuthCommunicationException | IOException e) {
      throw new RuntimeException(e);
    }
    return res;
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    HttpGet req = new HttpGet(uri);
    HttpResponse res = null;
    try {
      consumer.sign(req);
      HttpClient httpClient = HttpClientBuilder.create().build();
      res = httpClient.execute(req);
    } catch (OAuthMessageSignerException | OAuthExpectationFailedException | IOException |
             OAuthCommunicationException e) {
      throw new RuntimeException(e);
    }
    return res;
  }
}
