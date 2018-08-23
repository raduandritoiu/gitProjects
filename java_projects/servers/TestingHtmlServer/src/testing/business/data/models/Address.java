package testing.business.data.models;

public class Address
{
  public final String country;
  public final String city;
  public final String street;
  public final int app;
  
  public Address(String nCcountry, String nCity, String nStreet, int nApp)
  {
    country = nCcountry;
    city = nCity;
    street = nStreet;
    app = nApp;
  }
}
