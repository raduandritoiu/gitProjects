package com.bigct.appint.database.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sdragon on 2/5/2016.
 */
@DatabaseTable(tableName="tbl_reqlog")
public class ReqLogModel {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "requrl";
    public static final String COLUMN_TIME = "reqtime";
    public static final String COLUMN_PARAM = "param";

    @DatabaseField(columnName=COLUMN_ID, generatedId=true) private long id;
    @DatabaseField(columnName=COLUMN_URL) private String requrl;
    @DatabaseField(columnName=COLUMN_TIME) private String reqtime;
    @DatabaseField(columnName=COLUMN_PARAM) private String params;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequrl() {
        return requrl;
    }

    public void setRequrl(String requrl) {
        this.requrl = requrl;
    }

    public String getReqtime() {
        return reqtime;
    }

    public void setReqtime(String reqtime) {
        this.reqtime = reqtime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
