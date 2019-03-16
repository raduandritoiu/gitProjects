package radua.ui.logic.observers;

public interface IObservable 
{
	void addObserver(IObserver observer);
	void removeObserver(IObserver observer);
	void removeObservers();
}
