package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
  @JsonProperty("coordinates")
  private float[] coordinates;
  private String type;

  public Coordinates() {

  }

  public Coordinates(float[] coordinates, String type) {
    this.coordinates = coordinates;
    this.type = type;
  }

  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinates that = (Coordinates) o;
    return Arrays.equals(coordinates, that.coordinates) && Objects.equals(type,
        that.type);
  }

  @Override
  public String toString() {
    return "Coordinates{" +
        "coordinates=" + Arrays.toString(coordinates) +
        ", type='" + type + '\'' +
        '}';
  }
}
