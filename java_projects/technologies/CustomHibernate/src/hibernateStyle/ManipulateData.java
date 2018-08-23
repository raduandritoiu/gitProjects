package hibernateStyle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import models.manyToMany.bidirect.SharedAddress_mm;
import models.manyToMany.bidirect.SharingPerson_mm;
import models.manyToMany.threeWay.Owner;
import models.manyToMany.threeWay.SharedAddress;
import models.manyToMany.threeWay.SharingPerson;
import models.oneToMany.bidirect.Address;
import models.oneToMany.bidirect.Person;
import models.oneToOne.bidirect.EmptyAddressBi;
import models.oneToOne.bidirect.EmptyAddressDetailsBi;
import models.oneToOne.unidirect.EmptyAddress;
import models.oneToOne.unidirect.EmptyAddressDetails;
import models.simple.SimplePerson;

public class ManipulateData
{
  public static void test()
  {
    SessionFactory sf = CreateSessionFactory.createCustom();
    
    readPersonAddress(sf);
    
    sf.close();
  }
  
  
  public static void basicManipulate(SessionFactory sf)
  {
    Session session = sf.openSession();
    session.beginTransaction();
    // nu pot incepe alta tranzactie
    // session.beginTransaction();
    
    session.getTransaction().commit();
    // nu pot comite de 2 ori acceasi tranzactie
    // session.getTransaction().commit();
    
    session.beginTransaction();
    session.getTransaction().commit();

    session.beginTransaction();
    session.getTransaction().commit();
    
    session.close();
  }
  
  
// ======== normal =============================================================================
  
  public static void readSimplePerson(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();
    
    SimplePerson pers1 = session.get(SimplePerson.class, 2);
    SimplePerson pers2 = session.byId(SimplePerson.class).load(3);
    SimplePerson pers3 = session.load(SimplePerson.class, 4);
    
    session.close();
  }
  
  public static void removeSimplePerson(SessionFactory sf)
  {
    Session session = sf.openSession();
    SimplePerson pers = session.get(SimplePerson.class, 2);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    session.remove(pers);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  public static void addSimplePerson(SessionFactory sf)
  {
    Session session = sf.openSession();
    SimplePerson pers = new SimplePerson("simple_pers_7", 43);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    session.save(pers);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  public static void changeSimplePerson(SessionFactory sf)
  {
    Session session = sf.openSession();
    SimplePerson pers = session.get(SimplePerson.class, 4);
    pers.name = "changed_name_5";
    
    // ca sa modific ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    session.save(pers);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
//======== One-to-one unidirectional =============================================================================

  public static void readEmptyAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();
    
    EmptyAddress addr = session.get(EmptyAddress.class, 2);
    
    session.close();
  }
  
  public static void removeEmptyAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddress addr1 = session.get(EmptyAddress.class, 4);
    EmptyAddress addr2 = session.get(EmptyAddress.class, 3);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    session.remove(addr1);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    // nu pot sterge detalii daca mai sunt addr care pointeaza catre ele
//    session.beginTransaction();
//    session.remove(addr2.detalii);
//    // sau delete() - oricare dintre cele doua
//    session.getTransaction().commit();
    
    // dar pot sterge detaliile de la addr1
    session.beginTransaction();
    session.remove(addr1.detalii);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    session.close();
  }
  
  public static void addEmptyAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddressDetails detail = new EmptyAddressDetails("o casa frumoasa adaugata");
    EmptyAddress addr = new EmptyAddress("empty_addr_6", detail);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    // trebuie sa le salvez in ordinea aceasta
    session.save(detail);
    session.save(addr);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  
//======== One-to-one bidirectional =============================================================================
  
  public static void readEmptyAddressBi(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();
    
    EmptyAddressBi addr = session.get(EmptyAddressBi.class, 2);
    
    session.close();
  }
  
  public static void removeEmptyAddressBi(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddressBi addr = session.get(EmptyAddressBi.class, 4);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    // este de ajuns sa sterg parintele
    session.remove(addr);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    session.close();
  }
  
  public static void addEmptyAddressBi(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddressDetailsBi detail = null;//new EmptyAddressDetailsBi("cam miroase");
    EmptyAddressBi addr = new EmptyAddressBi("empty_addr_8", detail);
    
    session.beginTransaction();
    // este de ajuns sa salvez parintele
    session.save(addr);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  public static void changeEmptyAddressBi(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddressBi addr = session.get(EmptyAddressBi.class, 9);
    EmptyAddressDetailsBi det = new EmptyAddressDetailsBi("new one");
    
    addr.setDetalii(det);
    
    session.beginTransaction();
    // este de ajuns sa salvez parintele
    session.save(addr);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  
  //======== One-to-Many bidirectional =============================================================================
  
  public static void readPersonAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();
    
    Person pers1 = session.get(Person.class, 3);
    Person pers2 = session.get(Person.class, 5);
    Person pers3 = session.get(Person.class, 6);
    
//    int len = pers1.realestates.size();
    
    session.close();
  }
  
  public static void removePersonAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
//    Address addr = session.get(Address.class, 2);
    Person pers = session.get(Person.class, 2);
    int len = pers.realestates.size();
    Address addr = pers.realestates.get(len-1);
    
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    // este de ajuns sa sterg parintele
    session.remove(pers);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    session.close();
  }
  
  public static void addPersonAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    Person pers = new Person("added_pers_7");
    Address addr1 = new Address("parca e o mlastina", pers);
    Address addr2 = new Address("penthouse", pers);
    
    session.beginTransaction();
    // este de ajuns sa salvez parintele
    session.save(pers);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  public static void changePersonAddress(SessionFactory sf)
  {
    Session session = sf.openSession();
    
    Person new_pers = session.get(Person.class, 6);
    Address addr = session.get(Address.class, 20);
    Person last_pers = addr.owner;
    
    // make sure we load the person's realestate
    int len = new_pers.realestates.size();
    len = last_pers.realestates.size();
    
    // move the realestate
    addr.owner = new_pers;
//    new_pers.realestates.add(addr);
//    last_pers.realestates.remove(addr);
    
    
    
    session.beginTransaction();
    // este de ajuns sa salvez parintele

//    session.save(last_pers);
    
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
    
    System.out.println("asdasd");
  }
  
  //======== Many-to-Many bidirectional =============================================================================
  
  public static void readOwnersBidirect(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();
    
    SharingPerson_mm pers = session.get(SharingPerson_mm.class, 1);
    int len = pers.realestates.size();
    
    SharedAddress_mm addr = session.get(SharedAddress_mm.class, 2);
    len = addr.owners.size();
    
    session.close();
  }
  
  public static void removeOwnersBidirect(SessionFactory sf)
  {
    Session session = sf.openSession();
    SharingPerson_mm pers = session.get(SharingPerson_mm.class, 2);
    int rs = pers.realestates.size();
    SharedAddress_mm addr = session.get(SharedAddress_mm.class, 3);
    int os = addr.owners.size();
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    // este de ajuns sa sterg parintele
//    session.remove(pers);
//    pers.removeProperty(addr);
    addr.removeOwner(pers);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    session.close();
  }
  
  public static void addOwnersBidirect(SessionFactory sf)
  {
    Session session = sf.openSession();
    EmptyAddressDetailsBi detail = new EmptyAddressDetailsBi("o casa frumoasa adaugata");
    EmptyAddressBi addr = new EmptyAddressBi("empty_addr_6", detail);
    
    session.beginTransaction();
    // este de ajuns sa salvez parintele
    session.save(addr);
    // sau persist() - oricare dintre cele doua
    session.getTransaction().commit();
    session.close();
  }
  
  
  //======== Many-to-Many three way =============================================================================
  
  public static void readOwnersThreeWay(SessionFactory sf)
  {
    Session session = sf.openSession();
    // nu este nevoie de tranzactie
    // session.beginTransaction();

    Owner owner = session.get(Owner.class, 17);
    
    SharingPerson pers = session.get(SharingPerson.class, 2);
    int len = pers.properties.size();
    
    SharedAddress addr = session.get(SharedAddress.class, 4);
    len = addr.owners.size();
    
    session.close();
  }
  
  public static void removeOwnersThreeWay(SessionFactory sf)
  {
    Session session = sf.openSession();
    SharingPerson pers = session.get(SharingPerson.class, 1);
    SharedAddress addr = session.get(SharedAddress.class, 4);
    
    // ca sa sterg ceva trebuie sa deschid tranzactia si trebuie sa o comit implicit
    session.beginTransaction();
    // este de ajuns sa sterg parintele
//    session.remove(pers);
//    pers.removeProperty(addr);
    addr.removeOwner(pers);
    // sau delete() - oricare dintre cele doua
    session.getTransaction().commit();
    
    session.close();
  }
}
