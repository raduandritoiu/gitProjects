package com.bigct.appint.database;


public interface DatabaseCallListener
{
	public void onDatabaseCallRespond(DatabaseCallTask task, Data<?> data);
	public void onDatabaseCallFail(DatabaseCallTask task, Exception exception);
}
