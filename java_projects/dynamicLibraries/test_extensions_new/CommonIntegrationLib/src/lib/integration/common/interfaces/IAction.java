package lib.integration.common.interfaces;

public interface IAction
{
  /** name for the action */
  String name();
  
  /** execute the current action */
  int execute();
}
