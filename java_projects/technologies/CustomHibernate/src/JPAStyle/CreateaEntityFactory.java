package JPAStyle;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

public class CreateaEntityFactory
{
  public static void test()
  {
    EntityManagerFactory emf = createOne();
//    emf.close();
  }
  
  public static EntityManagerFactory createOne()
  {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
    return emf;
  }
}
