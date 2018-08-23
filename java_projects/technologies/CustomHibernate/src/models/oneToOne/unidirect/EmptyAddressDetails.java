package models.oneToOne.unidirect;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="empty_addr_details")
public class EmptyAddressDetails
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String descr;

  public EmptyAddressDetails() {}
  public EmptyAddressDetails(String nDescr)
  {
    descr = nDescr;
  }
}
