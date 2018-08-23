package models.oneToOne.bidirect;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="empty_addr_inv_details")
public class EmptyAddressDetailsBi
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String descr;
  
  @OneToOne
  @JoinColumn(name="addr_id")
  public EmptyAddressBi addr;

  public EmptyAddressDetailsBi() {}
  public EmptyAddressDetailsBi(String nDescr)
  {
    descr = nDescr;
  }
}
