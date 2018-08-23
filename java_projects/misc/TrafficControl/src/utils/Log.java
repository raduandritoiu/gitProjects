package utils;

import messages.IMessage;


public class Log {
	public static void Print(String str) {
		System.out.println(str);
	}
	
	public static void Print(IMessage msg) {
		// get time
		System.out.println("00:00" + ", " + msg.senderName() + " : " + msg.message());
	}
	
	public static void Print(IMessage msg, String receiverName) {
		// get time
		System.out.println("00:00" + ", " + msg.senderName() + " -> " + receiverName + ", " + msg.message());
	}
}
