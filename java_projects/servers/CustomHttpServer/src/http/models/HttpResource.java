package http.models;

public class HttpResource
{
  private final String resource;
  
  public HttpResource(String nResource)
  {
    resource = nResource;
  }
  
  public String toBaseResource(HttpResource baseResource)
  {
    if (resource.indexOf(baseResource.resource) == 0)
      return resource.substring(baseResource.resource.length());
    return null;
  }
  
  public boolean accepts(String other)
  {
    return other.indexOf(resource) == 0;
  }
  
  public boolean accepts(HttpResource other)
  {
    return other.resource.indexOf(resource) == 0;
  }
  
  public String toString()
  {
    return resource;
  }
}
