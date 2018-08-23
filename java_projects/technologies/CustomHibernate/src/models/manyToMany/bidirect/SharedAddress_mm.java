package models.manyToMany.bidirect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="shared_address")
public class SharedAddress_mm
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @ManyToMany(mappedBy="realestates")
  public List<SharingPerson_mm> owners = new ArrayList<>();

  
  public SharedAddress_mm() {}
  public SharedAddress_mm(String nName)
  {
    name = nName;
  }
  
  
  public void addOwner(SharingPerson_mm owner)
  {
    owners.add(owner);
    if (!owner.realestates.contains(this))
      owner.realestates.add(this);
  }
  
  public void removeOwner(SharingPerson_mm owner)
  {
    owners.remove(owner);
    owner.realestates.remove(this);
  }
}
