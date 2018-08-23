package models.simple;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="simple_person")
public class SimplePerson
{
  @Id
  @GeneratedValue(generator="maiJos")
  @GenericGenerator(name="maiJos", strategy="increment")
  public int id;
  public String name;
  public int age;
  
  @Transient
  public String music;
  @Transient
  public int height;
  @Transient
  public int weight;
  
  public SimplePerson() { }
  public SimplePerson(String nName, int nAge) 
  {
    name = nName;
    age = nAge;
  }
}
