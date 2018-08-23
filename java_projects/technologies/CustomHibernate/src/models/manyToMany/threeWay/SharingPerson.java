package models.manyToMany.threeWay;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="sharing_person")
public class SharingPerson
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @OneToMany(mappedBy = "who", cascade = CascadeType.ALL, orphanRemoval = true)
//  @JoinTable(name = "owners", joinColumns = @JoinColumn(name = "who"))
//@JoinColumn(name="who")
//  @Column(name="who")
//  @JoinColumn(name="who", referencedColumnName="who")
//  @Keyc
  public List<Owner> properties = new ArrayList<>();

  
  public SharingPerson() {}
  public SharingPerson(String nName)
  {
    name = nName;
  }
  
  
  public void addProperty(SharedAddress addr)
  {
    Owner owner = new Owner(this, addr, "adaugat de pers");
    properties.add(owner);
    if (!addr.owners.contains(owner))
      addr.owners.add(owner);
  }
  
  public void removeProperty(SharedAddress addr)
  {
    Owner owner = new Owner(this, addr);
    properties.remove(owner);
    addr.owners.remove(owner);
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if(!(obj instanceof SharingPerson))
      return false;
    return id == ((SharingPerson) obj).id;
  }
}
