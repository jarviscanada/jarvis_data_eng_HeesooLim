package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.utils.TweetUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
  @JsonProperty("created_at")
  private String createdAt;
  private long id;
  @JsonProperty("id_str")
  private String idStr;
  private String text;
  private Entities entities;
  @JsonProperty("coordinates")
  private Coordinates coordinates;
  @JsonProperty("retweet_count")
  private int retweetCount;
  @JsonProperty("favorite_count")
  private int favoriteCount;
  private boolean favorited;
  private boolean retweeted;

  public Tweet () {

  }

  public Tweet (String text, float longitude, float latitude, String coordsType) {
    this.text = text;
    this.coordinates = new Coordinates(new float[] {longitude, latitude}, coordsType);
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public int getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public boolean isRetweeted() {
    return retweeted;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tweet tweet = (Tweet) o;
    PercentEscaper percentEscaper = new PercentEscaper("", false);

    return id == tweet.id && retweetCount == tweet.retweetCount
        && favoriteCount == tweet.favoriteCount && favorited == tweet.favorited
        && retweeted == tweet.retweeted && Objects.equals(createdAt, tweet.createdAt)
        && Objects.equals(idStr, tweet.idStr)
        && Objects.equals(percentEscaper.escape(text), percentEscaper.escape(tweet.getText()))
        && Objects.equals(entities, tweet.entities) && Objects.equals(coordinates,
        tweet.coordinates);
  }

  @Override
  public String toString() {
    return "Tweet{" +
        "createdAt='" + createdAt + '\'' +
        ", id=" + id +
        ", idStr='" + idStr + '\'' +
        ", text='" + text + '\'' +
        ", entities=" + entities +
        ", coordinates=" + coordinates +
        ", retweetCount=" + retweetCount +
        ", favoriteCount=" + favoriteCount +
        ", favorited=" + favorited +
        ", retweeted=" + retweeted +
        '}';
  }
}
