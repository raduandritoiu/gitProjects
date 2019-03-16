package radua.ui.logic.basics;

public class MSize implements IWritableSize 
{
	private double _width;
	private double _height;
	
	public MSize() { this((double) 0, (double) 0); }
	public MSize(double width, double height) {
		_width = width; _height = height;
	}

	public void width(double width) { _width = width; }
	public void height(double height)  { _height = height; }
	
	public double width() { return _width; }
	public double height() { return _height; }
	
	public MSize resizeTo(IReadableSize size) { _width = size.width(); _height = size.height(); return this; }
	public MSize resizeTo(double width, double height) { _width = width; _height = height; return this; }
	
	public MSize scale(double scale) { _width *= scale; _height *= scale; return this; }
	
	public IReadableSize clone() { return new MSize(_width, _height); }
	
	public String toString() {
		return "MSize["+_width+","+_height+"]";
	}
}
