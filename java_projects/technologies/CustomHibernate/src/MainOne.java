import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import JPAStyle.CreateaEntityFactory;
import hibernateStyle.CreateSessionFactory;
import hibernateStyle.ManipulateData;
import models.manyToMany.threeWay.SharingPerson;

public class MainOne
{
  public static void main(String[] asd)
  {
//    CreateSessionFactory.test();
//    CreateaEntityFactory.test();
    ManipulateData.test();
    System.out.println("asdsad");
  }
}
