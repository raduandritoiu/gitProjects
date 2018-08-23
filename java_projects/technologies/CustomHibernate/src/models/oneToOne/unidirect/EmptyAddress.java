package models.oneToOne.unidirect;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="empty_address")
public class EmptyAddress
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @OneToOne
  @JoinColumn(name="details")
  public EmptyAddressDetails detalii;

  
  public EmptyAddress() {}
  public EmptyAddress(String nName, EmptyAddressDetails nDetails)
  {
    name = nName;
    detalii = nDetails;
  }
}
