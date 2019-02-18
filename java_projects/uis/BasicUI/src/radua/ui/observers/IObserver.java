package radua.ui.observers;

public interface IObserver 
{
	void notify(IObservable observable, ObservableEvent event, Object value);
}
