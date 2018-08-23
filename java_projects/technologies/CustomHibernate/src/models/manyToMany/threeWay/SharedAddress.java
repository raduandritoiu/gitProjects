package models.manyToMany.threeWay;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="shared_address")
public class SharedAddress
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @OneToMany(mappedBy = "what", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Owner> owners = new ArrayList<>();

  
  public SharedAddress() {}
  public SharedAddress(String nName)
  {
    name = nName;
  }
  
  
  public void addOwner(SharingPerson pers)
  {
    Owner owner = new Owner(pers, this, "adaugat de adresa");
    owners.add(owner);
    if (!pers.properties.contains(owner))
      pers.properties.add(owner);
  }
  
  public void removeOwner(SharingPerson pers)
  {
    Owner owner = new Owner(pers, this);
    owners.remove(owner);
    pers.properties.remove(owner);
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if(!(obj instanceof SharedAddress))
      return false;
    return id == ((SharedAddress) obj).id;
  }
}
