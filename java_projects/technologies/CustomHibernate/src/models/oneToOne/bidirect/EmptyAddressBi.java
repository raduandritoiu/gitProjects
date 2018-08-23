package models.oneToOne.bidirect;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="empty_address_inv")
public class EmptyAddressBi
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  public int id;
  public String name;

  @OneToOne(mappedBy="addr", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
  public EmptyAddressDetailsBi detalii;

  
  public EmptyAddressBi() {}
  public EmptyAddressBi(String nName, EmptyAddressDetailsBi nDetails)
  {
    name = nName;
    setDetalii(nDetails);
  }
  
  public void setDetalii(EmptyAddressDetailsBi nDetalii)
  {
    if (detalii != null)
      detalii.addr = null;
    detalii = nDetalii;
    if (detalii != null)
      detalii.addr = this;
  }
}
