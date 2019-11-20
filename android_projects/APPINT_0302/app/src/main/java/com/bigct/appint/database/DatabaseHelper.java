package com.bigct.appint.database;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private static final String DATABASE_NAME = "database.db";
	private static final String DATABASE_PATH = "/data/data/"+MobixApplication.getContext().getPackageName()+"/";
	private static final int DATABASE_VERSION = 1;
	private static final String PREFS_KEY_DATABASE_VERSION = "database_version";

	// singleton
	private static DatabaseHelper instance;
	public static synchronized DatabaseHelper getInstance()
	{
		if(instance==null) instance = new DatabaseHelper();
		return instance;
	}


	private DatabaseHelper()
	{
		super(MobixApplication.getContext(), DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
		if(!databaseExists() || DATABASE_VERSION>getVersion())
		{
			synchronized(this)
			{
				boolean success = copyPrepopulatedDatabase();
				if(success)
				{
					setVersion(DATABASE_VERSION);
				}
			}
		}
	}


	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
	}


	@Override
	public void close()
	{
		super.close();
	}


	public synchronized void clearDatabase()
	{
		try
		{
			TableUtils.clearTable(getConnectionSource(), ReqLogDao.class);
		}
		catch(android.database.SQLException e)
		{
			e.printStackTrace();
		}
		catch(java.sql.SQLException e)
		{
			e.printStackTrace();
		}
	}

	Dao<ReqLogModel, Long> mReqLogDao = null;
	public synchronized Dao<ReqLogModel, Long> getReqLogDao() throws java.sql.SQLException
	{
		if(mReqLogDao==null)
		{
			mReqLogDao = getDao(ReqLogModel.class);
		}
		return mReqLogDao;
	}

	private boolean databaseExists()
	{
		File file = new File(DATABASE_PATH + DATABASE_NAME);
		boolean exists = file.exists();
		return exists;
	}


	private boolean copyPrepopulatedDatabase()
	{
		// copy database from assets
		try
		{
			// create directories
			File dir = new File(DATABASE_PATH);
			dir.mkdirs();

			// output file name
			String outputFileName = DATABASE_PATH + DATABASE_NAME;

			// create streams
			InputStream inputStream = MobixApplication.getContext().getAssets().open(DATABASE_NAME);
			OutputStream outputStream = new FileOutputStream(outputFileName);

			// write input to output
			byte[] buffer = new byte[1024];
			int length;
			while((length = inputStream.read(buffer))>0)
			{
				outputStream.write(buffer, 0, length);
			}

			// close streams
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}


	private int getVersion()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MobixApplication.getContext());
		return sharedPreferences.getInt(PREFS_KEY_DATABASE_VERSION, 0);
	}


	private void setVersion(int version)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MobixApplication.getContext());
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PREFS_KEY_DATABASE_VERSION, version);
		editor.commit();
	}
}
