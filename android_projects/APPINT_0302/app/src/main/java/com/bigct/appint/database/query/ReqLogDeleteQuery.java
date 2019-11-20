package com.bigct.appint.database.query;

import com.bigct.appint.database.Data;
import com.bigct.appint.database.dao.ReqLogDao;

import java.sql.SQLException;


public class ReqLogDeleteQuery extends Query
{
	private long mId;


	public ReqLogDeleteQuery(long id)
	{
		mId = id;
	}


	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = new Data<>();
		data.setDataObject(ReqLogDao.delete(mId));
		return data;
	}
}
