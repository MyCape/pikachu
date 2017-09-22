package com.pp.pikachu.models;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class Airports extends RealmObject {

  @PrimaryKey @SerializedName("icao") private String icao;

  @SerializedName("iata") private String iata;

  @SerializedName("name") private String name;

  @SerializedName("city") private String city;

  @SerializedName("country") private String country;

  @SerializedName("elevation") private String elevation;

  @SerializedName("lat") private String latitude;

  @SerializedName("lon") private String longitude;

  @SerializedName("tz") private String tz;

  public Airports() {
    // Intended to be empty.
  }

  public String getIcao() {
    return icao;
  }

  public String getIata() {
    return iata;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getElevation() {
    return elevation;
  }

  public String getLongitude() {
    return longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getTz() {
    return tz;
  }

  public void setIcao(String icao) {
    this.icao = icao;
  }

  public void setIata(String iata) {
    this.iata = iata;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setElevation(String elevation) {
    this.elevation = elevation;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public void setTz(String tz) {
    this.tz = tz;
  }
}
