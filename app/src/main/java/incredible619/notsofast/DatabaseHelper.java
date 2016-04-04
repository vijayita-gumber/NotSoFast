package incredible619.notsofast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Garima on 23-Oct-15.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "PCSMAMIDSEM.db";
    public static final String TABLE_NAME = "VALUESDATA";
    public static final String COL_1 = "XAXIS";
    public static final String COL_2 = "YAXIS";
    public static final String COL_3 = "ZAXIS";
    public static final String COL_4 = "LATITUDE";
    public static final String COL_5 = "LONGITUDE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (XAXIS TEXT , YAXIS TEXT , ZAXIS TEXT , LATITUDE TEXT , LONGITUDE TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(double xaxis,double yaxis,double zaxis,double latitude,double longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,xaxis);
        contentValues.put(COL_2,yaxis);
        contentValues.put(COL_3,zaxis);
        contentValues.put(COL_4, latitude);
        contentValues.put(COL_5, longitude);
        if(latitude>0 && longitude >0)
        {
            long result = db.insert(TABLE_NAME,null ,contentValues);
            if(result == -1)
                return false;
            else
                return true;
        }
        else
            return false ;

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return res ;
    }

    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME );
    }
}
