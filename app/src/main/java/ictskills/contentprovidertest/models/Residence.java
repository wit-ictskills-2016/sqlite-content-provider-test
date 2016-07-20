package ictskills.contentprovidertest.models;

import java.util.Date;
import java.util.UUID;


public class Residence
{
  public UUID uuid;
  public Date date;
  public String geolocation;
  public boolean rented;
  public String tenant;
  public double zoom;//zoom level of accompanying map
  public String photo;

  public Residence() { }
}
