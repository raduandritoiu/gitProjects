package syncs;

import messages.IMessage;

public interface IBlockingQueue<T extends IMessage> {
	void add(T msg);
	T poll();
	void block(int permits);
}
