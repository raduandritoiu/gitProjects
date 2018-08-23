package models.manyToMany.threeWay;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="owners")
public class Owner
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String percentage;

  @ManyToOne
  @JoinColumn(name="who", foreignKey=@ForeignKey(name="who"))
  public SharingPerson who;
  
  @ManyToOne
  @JoinColumn(name="what", foreignKey=@ForeignKey(name="what"))
  public SharedAddress what;
  
  
  public Owner() {}
  public Owner(String nPercentage) 
  {
    this(null, null, nPercentage);
  }
  public Owner(SharingPerson pers, SharedAddress addr) 
  {
    this(pers, addr, null);
  }
  
  public Owner(SharingPerson pers, SharedAddress addr, String nPercentage) 
  {
    who = pers;
    what = addr;
    percentage = nPercentage;
  }
  
  
  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Owner))
      return false;
    Owner other = (Owner) obj;
    return who.equals(other.who) && what.equals(other.what);
  }
}
