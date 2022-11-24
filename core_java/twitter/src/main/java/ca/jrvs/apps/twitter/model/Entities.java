package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities {
  private HashTag[] hashtags;

  public HashTag[] getHashtags() {
    return hashtags;
  }

  public void setHashtags(HashTag[] hashtags) {
    this.hashtags = hashtags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entities entities = (Entities) o;
    return Arrays.equals(hashtags, entities.hashtags);
  }

  @Override
  public String toString() {
    return "Entities{" +
        "hashtags=" + Arrays.toString(hashtags) +
        '}';
  }
}
