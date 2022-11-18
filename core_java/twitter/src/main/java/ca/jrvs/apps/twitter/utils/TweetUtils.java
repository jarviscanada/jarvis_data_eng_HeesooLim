package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TweetUtils {
  public static final String TWEET_JSON_STRING = "{\n"
      + "   \"created_at\":\"Tue Nov 15 13:05:28 +0000 2022\",\n"
      + "   \"id\":1592504054948982786,\n"
      + "   \"id_str\":\"1592504054948982786\",\n"
      + "   \"text\":\"Tweet 2022-11-15 13-05-27\",\n"
      + "   \"entities\":{\n"
      + "      \"hashtags\":[],\n"
      + "      \"user_mentions\":[]\n"
      + "   },\n"
      + "   \"coordinates\":{\n"
      + "            \"type\": \"Point\",\n"
      + "            \"coordinates\": [\n"
      + "                40.741188,\n"
      + "                -73.999825\n"
      + "            ]\n"
      + "        },    \n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  public static String newTweetText() {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    return percentEscaper.escape( "Tweet at:" + new Date().toString());
  }

  public static Tweet getTweetObject() {
    Tweet tweet = new Tweet();
    String text = TweetUtils.newTweetText();

    Coordinates coordinates = new Coordinates();
    float[] coords = {40.74118764f, -73.9998279f};
    coordinates.setCoordinates(coords);
    coordinates.setType("Point");

    tweet.setCoordinates(coordinates);
    tweet.setText(text);
    return tweet;
  }


  public static <T> T responseToObject(HttpResponse response, Class c) throws IOException {
    String resString = EntityUtils.toString(response.getEntity());
    return jsonToObject(resString, c);
  }


  public static <T> T jsonToObject(String json, Class c) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(json, c);
  }
}
