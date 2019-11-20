package com.bigct.appint.database.query;

import com.bigct.appint.database.Data;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;

import java.sql.SQLException;
import java.util.List;


public class ReqLogReadAllQuery extends Query
{
	private long mSkip = -1l;
	private long mTake = -1l;


	public ReqLogReadAllQuery()
	{
	}


	public ReqLogReadAllQuery(long skip, long take)
	{
		mSkip = skip;
		mTake = take;
	}


	@Override
	public Data<List<ReqLogModel>> processData() throws SQLException
	{
		Data<List<ReqLogModel>> data = new Data<>();
		data.setDataObject(ReqLogDao.readAll(mSkip, mTake));
		return data;
	}
}
