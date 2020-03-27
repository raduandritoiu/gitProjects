package radua.ui.logic.observers;

public interface IPropertyObserver 
{
	void notify(IPropertyObservable observable, ObservableProperty event, Object value);
}
