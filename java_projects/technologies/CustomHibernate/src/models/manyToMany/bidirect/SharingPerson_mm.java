package models.manyToMany.bidirect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="sharing_person")
public class SharingPerson_mm
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "owners", joinColumns = @JoinColumn(name = "who"), inverseJoinColumns = @JoinColumn(name = "what"))
  public List<SharedAddress_mm> realestates = new ArrayList<>();

  
  public SharingPerson_mm() {}
  public SharingPerson_mm(String nName)
  {
    name = nName;
  }
  
  public void addRealestate(SharedAddress_mm addr)
  {
    realestates.add(addr);
    if (!addr.owners.contains(this))
      addr.owners.add(this);
  }
  
  public void removeRealestates(SharedAddress_mm addr)
  {
    realestates.remove(addr);
    addr.owners.remove(this);
  }
}
