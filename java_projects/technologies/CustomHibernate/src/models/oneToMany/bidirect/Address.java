package models.oneToMany.bidirect;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="adresa")
public class Address
{
  public static int id_cnt = 0;
  
  
  @Id
  @GeneratedValue(generator="ptIdx")
  @GenericGenerator(name="ptIdx", strategy="increment")
  public int id;
  public String name;
  
  @Column(name="postal_code")
  public int zip_code;
  
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="owner", foreignKey=@ForeignKey(name="owner_key"))
  public Person owner;
  
  @Transient
  public final int gen_id;
  
  public Address() { gen_id = id_cnt++; }
  public Address(String nName)
  {
    this(nName, null);
  }
  
  public Address(String nName, Person pers)
  {
    name = nName;
    setOwner(pers);
    gen_id = id_cnt++;
  }
  
  
  public void setOwner(Person pers)
  {
    if (owner != null)
      owner.realestates.remove(this);
    owner = pers;
    if (owner != null)
      owner.realestates.put(zip_code, this);
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Address))
      return false;
    return id == ((Address) obj).id;
  }
}
