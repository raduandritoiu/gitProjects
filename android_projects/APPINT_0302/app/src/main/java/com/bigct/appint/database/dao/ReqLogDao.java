package com.bigct.appint.database.dao;

import com.bigct.appint.database.DatabaseHelper;
import com.bigct.appint.database.model.ReqLogModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sdragon on 2/5/2016.
 */
public class ReqLogDao extends BaseDao{
    private static Dao<ReqLogModel, Long> getDao() throws SQLException
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getReqLogDao();
    }

    public static int refresh(ReqLogModel reqLog) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        return dao.refresh(reqLog);
    }

    public static int insert(ReqLogModel reqLog) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        return dao.create(reqLog);
    }


    public static ReqLogModel read(long id) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        return dao.queryForId(id);
    }

    public static List<ReqLogModel> readAll(long skip, long take) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        List<ReqLogModel> list;
        if(skip==-1l && take==-1l)
        {
            QueryBuilder<ReqLogModel, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy("id", true);
            list = dao.query(queryBuilder.prepare());
        }
        else
        {
            QueryBuilder<ReqLogModel, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy("id", true);
            queryBuilder.offset(skip).limit(take);
            list = dao.query(queryBuilder.prepare());
        }
        return list;
    }


    public static int update(ReqLogModel reqLog) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        return dao.update(reqLog);
    }


    public static int delete(long id) throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        return dao.deleteById(id);
    }


    public static int deleteAll() throws SQLException
    {
        Dao<ReqLogModel, Long> dao = getDao();
        DeleteBuilder<ReqLogModel, Long> deleteBuilder = dao.deleteBuilder();
        return dao.delete(deleteBuilder.prepare());
    }
}
