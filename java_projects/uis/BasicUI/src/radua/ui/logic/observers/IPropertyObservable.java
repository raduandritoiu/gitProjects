package radua.ui.logic.observers;

public interface IPropertyObservable 
{
	void addObserver(IPropertyObserver observer);
	void removeObserver(IPropertyObserver observer);
	void removeObservers();
}
