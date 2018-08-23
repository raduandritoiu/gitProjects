package hibernateStyle;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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


public class CreateSessionFactory
{
  public static void test()
  {
    SessionFactory sf = createShortestCompact();
    sf.close();
  }
  
  public static SessionFactory createCustom()
  {
    StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("src/dingy.plm")).build();
    
    MetadataSources mtds = new MetadataSources(ssr);
    mtds.addAnnotatedClass(SimplePerson.class);
    
    mtds.addAnnotatedClass(EmptyAddress.class);
    mtds.addAnnotatedClass(EmptyAddressDetails.class);
    
    mtds.addAnnotatedClass(EmptyAddressBi.class);
    mtds.addAnnotatedClass(EmptyAddressDetailsBi.class);
    
    mtds.addAnnotatedClass(Person.class);
    mtds.addAnnotatedClass(Address.class);
    
    mtds.addAnnotatedClass(SharingPerson_mm.class);
    mtds.addAnnotatedClass(SharedAddress_mm.class);
    
    mtds.addAnnotatedClass(SharingPerson.class);
    mtds.addAnnotatedClass(SharedAddress.class);
    mtds.addAnnotatedClass(Owner.class);
    
    SessionFactory sf = mtds.buildMetadata().buildSessionFactory();
    return sf;
  }
  
  public static SessionFactory createOne()
  {
    final StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
    SessionFactory sf = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
    return sf;
  }
  
  public static SessionFactory createShortestCompact()
  {
    // all in one statement
    return new MetadataSources(new StandardServiceRegistryBuilder().configure().build()).buildMetadata().buildSessionFactory();
  }
  
  
  public static SessionFactory createShortestCompact_1()
  {
    // 4. Standard Service Registry 
    StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
    
    // 9. Session Factory
    SessionFactory sf = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
    
    return sf;
  }
  
  
  public static SessionFactory createShortestCompact_2()
  {
    // 4. Standard Service Registry 
    StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
    
    // 7. Metadata 
    Metadata mtd = new MetadataSources(ssr).buildMetadata();
     
    // 9. Session Factory
    SessionFactory sf = mtd.buildSessionFactory();
    
    return sf;
  }
  
  
  public static SessionFactory createShortestVerbose()
  {
    // 3. Standard Service Registry Builder
    StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
    ssrb.configure(new File("src/dingy.plm")); // OBLIGATORIU
    
    // 4. Standard Service Registry 
    StandardServiceRegistry ssr = ssrb.build();
    
    // 5. Metadata Source
    MetadataSources mtds = new MetadataSources(ssr);
    
    // 7. Metadata 
    Metadata mtd = mtds.buildMetadata();
     
    // 9. Session Factory
    SessionFactory sf = mtd.buildSessionFactory();
    
    return sf;
  }
  
  
  public static SessionFactory createLongestVerbose()
  {
    // 1. Bootstrap Service Registry Builder
    BootstrapServiceRegistryBuilder bsrb = new BootstrapServiceRegistryBuilder();
    // --------- aici pot sa customizez bsrb
    
    // 2. Bootstrap Service Registry
    BootstrapServiceRegistry bsr = bsrb.build();
    
    // 3. Standard Service Registry Builder
    StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder(bsr);
    // sau pot sa folosest 'bsr' default
    // StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
    // --------- aici pot sa customizez ssrb
    ssrb.configure(new File("src/hibernate.cfg.xml")); // OBLIGATORIU - asta trebuie configurata
    // sau pot folosi fisierul standard
    // ssrb.configure(); // OBLIGATORIU - asta trebuie configurata
    
    // 4. Standard Service Registry 
    StandardServiceRegistry ssr = ssrb.build();
    
    // 5. Metadata Source
    MetadataSources mtds = new MetadataSources(ssr);
    // sau pot sa fac by-pass la 'ssr', dar va trebui specificat mai jos la 'mtdb'
    // MetadataSources mtds = new MetadataSources();
    // --------- aici pot sa customizez mtds
    
    // 6. Metadata Builder
    MetadataBuilder mtdb = mtds.getMetadataBuilder();
    // sau pot sa adaug acum 'ssr'
    // MetadataBuilder mtdb = mtds.getMetadataBuilder(ssr);
    // --------- aici pot sa customizez mtdb
    
    // 7. Metadata 
    Metadata mtd = mtdb.build();
    // sau pot sa fac by-pass la 'mtdb'
    // Metadata mtd = mtds.buildMetadata();
    
    // 8. Session Factory Builder
    SessionFactoryBuilder sfb = mtd.getSessionFactoryBuilder();
    // --------- aici pot sa customizez sfb
    
    // 9. Session Factory
    SessionFactory sf = sfb.build();
    // sau pot sa fac by-pass la 'sfb'
    // SessionFactory sf = mtd.buildSessionFactory();
    
    return sf;
  }
}
