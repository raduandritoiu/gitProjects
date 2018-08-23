import java.util.Date;

import nativ.models.NativeClassA;
import support.models.MyCallback;
import support.models.ValClass;
import utils.Utils;

public class Main
{
  public static void main (String args[])
  {
    
    Date dt1 = new Date();
    long lg = dt1.getTime();
    Date dt2 = new Date(lg);
    
    
    float fl;
    NativeClassA nat = new NativeClassA(3, 3, 3, "first test", new ValClass(2, 2, 4));
    NativeClassA.loadLib();
    
    //fu(nat);
    // -------------
    
    MyCallback callback = new MyCallback(100);
    fl = nat.registerCallback(callback);
    Utils.PrintTh("registerCallback() = " + fl);
    fl = nat.hitCallback(1);
    Utils.PrintTh("hitCallback(1) = " + fl);
    fl = nat.hitCallback(2);
    Utils.PrintTh("hitCallback(2) = " + fl);
    fl = nat.hitCallback(3);
    Utils.PrintTh("hitCallback(3) = " + fl);

    callback = null;
    System.gc();

    fl = nat.hitCallback(4);
    Utils.PrintTh("hitCallback(4) = " + fl);
    fl = nat.hitCallback(5);
    Utils.PrintTh("hitCallback(5) = " + fl);
    fl = nat.hitCallback(6);
    Utils.PrintTh("hitCallback(6) = " + fl);
    
    fl = nat.clearCallback();
    Utils.PrintTh("clearCallback() = " + fl);
    System.gc();
    
    fl = nat.hitCallback(7);
    Utils.PrintTh("hitCallback(7) = " + fl);
    fl = nat.hitCallback(8);
    Utils.PrintTh("hitCallback(8) = " + fl);
    fl = nat.hitCallback(9);
    Utils.PrintTh("hitCallback(9) = " + fl);
    
    
    // -------------
    fl = nat.val();
    Utils.PrintTh("val(3, 3, 3) = " + fl);
    
    fl = nat.innerVal();
    Utils.PrintTh("innerVal(2, 2, 4) = " + fl);
    
    fl = nat.totalVal();
    Utils.PrintTh("totalVal(3, 3, 3) (2, 2, 4) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 0);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 0) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 1);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 1) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 2);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 2) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 3);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 3) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 4);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 4) = " + fl);
    
    fl = nat.nativeComputeFunc(5, 5, 8, 5);
    Utils.PrintTh("nativeExecuteFunc(5, 5, 8, 5) = " + fl);
    
    Utils.PrintTh("");
    
    
    // -------------
    NativeClassA nat1 = new NativeClassA();
    fl = nat1.setFromC(7, 7, 7, 0);
    Utils.PrintTh("setFromC(7, 7, 7, 0) = " + fl);
    fl = nat1.val();
    Utils.PrintTh("val(7, 7, 7) = " + fl);
    fl = nat1.innerVal();
    Utils.PrintTh("innerVal(0, 0, 0) = " + fl);
    fl = nat1.totalVal();
    Utils.PrintTh("totalVal(7, 7, 7) (0, 0, 0) = " + fl);
    Utils.PrintTh("");
    
    nat1 = new NativeClassA();
    fl = nat1.setFromC(7, 7, 7, 1);
    Utils.PrintTh("setFromC(7, 7, 7, 1) = " + fl);
    fl = nat1.val();
    Utils.PrintTh("val(0, 0, 0) = " + fl);
    fl = nat1.innerVal();
    Utils.PrintTh("innerVal(7, 7, 7) = " + fl);
    fl = nat1.totalVal();
    Utils.PrintTh("totalVal(0, 0, 0) (7, 7, 7) = " + fl);
    Utils.PrintTh("");
    
    nat1 = new NativeClassA();
    fl = nat1.setFromC(7, 7, 7, 2);
    Utils.PrintTh("setFromC(7, 7, 7, 2) = " + fl);
    fl = nat1.val();
    Utils.PrintTh("val(0, 0, 0) = " + fl);
    fl = nat1.innerVal();
    Utils.PrintTh("innerVal(7, 7, 7) = " + fl);
    fl = nat1.totalVal();
    Utils.PrintTh("totalVal(0, 0, 0) (7, 7, 7) = " + fl);
    Utils.PrintTh("");
    
    nat1 = new NativeClassA();
    fl = nat1.setFromC(11, 11, 11, 3);
    Utils.PrintTh("setFromC(7, 7, 7, 3) = " + fl);
    fl = nat1.val();
    Utils.PrintTh("val(0, 0, 0) = " + fl);
    fl = nat1.innerVal();
    Utils.PrintTh("innerVal(7, 7, 7) = " + fl);
    fl = nat1.totalVal();
    Utils.PrintTh("totalVal(0, 0, 0) (7, 7, 7) = " + fl);
    Utils.PrintTh("");
    
    nat1 = new NativeClassA();
    fl = nat1.setFromC(7, 7, 7, 5);
    Utils.PrintTh("setFromC(7, 7, 7, 5) = " + fl);
    fl = nat1.val();
    Utils.PrintTh("val(0, 0, 0) = " + fl);
    fl = nat1.innerVal();
    Utils.PrintTh("innerVal(7, 7, 7) = " + fl);
    fl = nat1.totalVal();
    Utils.PrintTh("totalVal(0, 0, 0) (7, 7, 7) = " + fl);
    Utils.PrintTh("");
    
    
    // -------------
    fl = nat.rememberVals(5, 5, 5);
    Utils.PrintTh("rememberVals(5, 5, 4) = " + fl);
    
    fl = nat.getVals(6);
    Utils.PrintTh("getVals(6) = " + fl);
    
    Utils.PrintTh("");
  }
  
  
  private static void fu(NativeClassA nat)
  {
    MyCallback call = new MyCallback(4);
    System.gc();
    
  }
}
