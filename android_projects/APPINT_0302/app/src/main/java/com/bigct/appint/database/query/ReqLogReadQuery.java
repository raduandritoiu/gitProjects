package com.bigct.appint.database.query;

import com.bigct.appint.database.Data;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;

import java.sql.SQLException;


public class ReqLogReadQuery extends Query
{
	private long mId;


	public ReqLogReadQuery(long id)
	{
		mId = id;
	}


	@Override
	public Data<ReqLogModel> processData() throws SQLException
	{
		Data<ReqLogModel> data = new Data<>();
		data.setDataObject(ReqLogDao.read(mId));
		return data;
	}
}
