package testTemplate.bla;

public interface I_Link<T> extends T
{
	void set(T inner);
	T get();
}
