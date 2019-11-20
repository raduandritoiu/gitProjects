package com.bigct.appint.database.query;

import com.bigct.appint.database.Data;
import com.bigct.appint.database.dao.ReqLogDao;

import java.sql.SQLException;


public class ReqLogDeleteAllQuery extends Query
{
	public ReqLogDeleteAllQuery()
	{
	}


	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = new Data<>();
		data.setDataObject(ReqLogDao.deleteAll());
		return data;
	}
}
