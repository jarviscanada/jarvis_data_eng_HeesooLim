package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;
  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {this.httpHelper = httpHelper;}



  @Override
  public Tweet create(Tweet entity) {
    String postUri = buildPostUri(entity);
    HttpResponse res = httpHelper.httpPost(URI.create(postUri));
    try {
//      Tweet tweet = HttpUtils.responseToObject(res, Tweet.class);
//      return tweet;
      return parseResponseBody(res, 200);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildPostUri(Tweet entity) {
    StringBuilder uri = new StringBuilder(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + entity.getText());

    if (entity.getCoordinates() != null) {
      float[] coordinates = entity.getCoordinates().getCoordinates();
      uri.append("&long=").append(coordinates[0]).append("&lat=").append(coordinates[1]);
    }
    return uri.toString();
  }

  @Override
  public Tweet findById(String s) {
    String getUri = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s;
    HttpResponse res = httpHelper.httpGet(URI.create(getUri));

    try {
      return parseResponseBody(res, 200);
//      return HttpUtils.responseToObject(res, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Tweet deleteById(String s) {
    String deleteUri = API_BASE_URI + DELETE_PATH + s + ".json";
    HttpResponse res = httpHelper.httpPost(URI.create(deleteUri));

    try {
      return parseResponseBody(res, 200);
//      return HttpUtils.responseToObject(res, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Tweet parseResponseBody(HttpResponse res, Integer expectedStatusCode) throws IOException {
    int statusCode = res.getStatusLine().getStatusCode();
//    String responseString = "";
    String responseString = res.getEntity() == null ? "STATUS - " + statusCode : EntityUtils.toString(res.getEntity());
    if (statusCode != expectedStatusCode) {
      if (res.getEntity() != null)
        throw new RuntimeException("Status code does not match: " + responseString);
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(responseString, Tweet.class);
  }

//  public Tweet parseResponseBody() {
//    
//  }
}
