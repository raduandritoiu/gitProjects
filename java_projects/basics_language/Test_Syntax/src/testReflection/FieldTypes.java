package testReflection;

import java.util.List;

public class FieldTypes<T, V extends Base>
{
  public int i;
  public int[] iArr;
  public String str;
  public String[] strArr;
  public T genT;
  public V genV;
  public T[] gen_T_arr;
  public V[] gen_V_arr;
  public List lst_simple;
  public List<Integer> lst_int;
  public List<V> lst_param;
  public List<?> lst_wild;
  
  
  public FieldTypes()
  {
  }
}