package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
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
    return "Tweet at:" + new Date().toString() + getRandomString("abcdefghijklmnopqrstuvwxyz1234567890", 10);
  }

  public static Tweet getTweetObject() {
    return new Tweet(TweetUtils.newTweetText(),
        40.74118764f, -73.9998279f, "Point");
  }

  public static float[] getCoordinateValue() {
    Random random = new Random();
    boolean isNegative = random.nextFloat() < 0.5;
    float longitude = (random.nextFloat() * 179);
    float latitude = random.nextFloat() * 79;
    if (isNegative) {
      longitude *= -1;
      latitude *= -1;
    }
    return new float[] {longitude, latitude};
  }


  public static <T> T responseToObject(HttpResponse response, Class c) throws IOException {
    String resString = EntityUtils.toString(response.getEntity());
    return jsonToObject(resString, c);
  }


  public static <T> T jsonToObject(String json, Class c) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(json, c);
  }

  public static String getInvalidId() {
    String pool = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "-=+!@#$%^&*";
    return getRandomString(pool, 17);
  }

  public static String getValidFormatId() {
    String pool = "1234567890";
    return getRandomString(pool, 17);
  }

  public static String getRandomString(String pool, int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    int len = pool.length();
    for (int i = 0; i < length; i++) {
      sb.append(pool.charAt(random.nextInt(len)));
    }
    return sb.toString();
  }

  public static String tweetToJson(Tweet tweet) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_DEFAULT);
    mapper.setSerializationInclusion(Include.NON_NULL);
    // pretty json
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tweet);
  }

  public static String tweetToJson(Tweet tweet, String[] fields) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode fullJson = mapper.valueToTree(tweet);
    ObjectNode emptyJson = mapper.createObjectNode();

    if (fields.length > 0) {
      for (String field : fields) {
        emptyJson.put(field, fullJson.get(field));
      }
      try {
        // pretty json
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emptyJson);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fullJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
