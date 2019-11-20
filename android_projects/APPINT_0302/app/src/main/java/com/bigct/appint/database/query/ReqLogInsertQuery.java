package com.bigct.appint.database.query;

import com.bigct.appint.database.Data;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;

import java.sql.SQLException;


public class ReqLogInsertQuery extends Query
{
	private ReqLogModel mReqLog;
	
	
	public ReqLogInsertQuery(ReqLogModel reqLog)
	{
		mReqLog = reqLog;
	}
	
	
	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = new Data<>();
		data.setDataObject(ReqLogDao.insert(mReqLog));
		return data;
	}
}
