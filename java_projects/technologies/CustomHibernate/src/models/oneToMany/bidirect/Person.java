package models.oneToMany.bidirect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="person")
public class Person
{
  @Id
  @GeneratedValue(generator="maiJos")
  @GenericGenerator(name="maiJos",strategy="increment")
  public int id;
  public String name;
  
//  @OneToMany(mappedBy="owner", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
//  public List<Address> realestates = new ArrayList<>();
  
  @OneToMany(mappedBy="owner", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
  @MapKeyColumn(name="postal_code")
  public Map<Integer, Address> realestates = new HashMap();
  
  
  public Person() {}
  public Person(String nName) 
  {
    name= nName;
  }
  
  
  public void addRealestate(Address addr)
  {
    if (addr == null)
      return;
    if (addr.owner != null)
      addr.owner.removeRealestate(addr);
    realestates.put(addr.zip_code, addr);
    addr.owner = this;
  }
  
  public void removeRealestate(Address addr)
  {
    Address r = realestates.remove(addr.zip_code);
    if (r != null)
      addr.owner = null;
  }
  
  public void removeRealestate(int idx)
  {
    Address addr = realestates.remove(idx);
    if (addr != null)
      addr.owner = null;
  }
}
