package radua.ui.logic.basics;

public interface IWritableSize extends IReadableSize
{
	void width(double width);
	void height(double height);
	
	IWritableSize resizeTo(IReadableSize size);
	IWritableSize resizeTo(double width, double height);
	
	IWritableSize scale(double scale);
}
