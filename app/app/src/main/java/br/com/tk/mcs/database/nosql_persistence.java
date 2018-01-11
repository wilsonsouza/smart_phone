/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by revolution on 31/01/16.
 */

public class nosql_persistence extends SQLiteOpenHelper
{
	public final static String         TAG                 = nosql_persistence.class.getName ( );
	public static final String         m_database          = "mcs.db";
	public static final int            m_version           = 23;
	public static final String         m_lane              = "lane";
	public static final String         m_conf              = "conf";
	public static final String         m_id                = "_id";
	public static final String         m_name              = "name";
	public static final String         m_ip                = "ip";
	public static final String         m_sen               = "sentido";
	public static final String         m_square_table_name = "square_ip";
	public static final String         m_alias             = "alias";
	protected           SQLiteDatabase m_handle            = null;
	
	//--------------------------------------------------------------------------------------------//
	public nosql_persistence ( Context context )
	{
		super ( context,
		        m_database,
		        null,
		        m_version
		      );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onCreate ( SQLiteDatabase pDb )
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + m_lane;
		
		sql += "( ";
		sql += m_id + " integer primary key autoincrement, ";
		sql += m_ip + " text NOT NULL UNIQUE, ";
		sql += m_name + " text NOT NULL UNIQUE, ";
		sql += m_sen + " text NOT NULL";
		sql += " );";
		
		Log.d ( this.getClass ( )
		            .getName ( ),
		        sql
		      );
		pDb.execSQL ( sql );
		//
		on_create_square_ip_table ( pDb );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onUpgrade ( SQLiteDatabase pDb,
	                        int nOldVersion,
	                        int nNewVersion
	                      )
	{
		pDb.execSQL ( "DROP TABLE IF EXISTS " + m_lane );
		pDb.execSQL ( "DROP TABLE IF EXISTS " + m_square_table_name );
		onCreate ( pDb );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void on_create_square_ip_table ( SQLiteDatabase pDb )
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + nosql_persistence.m_square_table_name;
		
		sql += "( ";
		sql += nosql_persistence.m_id + " integer primary key autoincrement, ";
		sql += nosql_persistence.m_ip + " text not null unique, ";
		sql += nosql_persistence.m_name + " text not null, ";
		sql += nosql_persistence.m_alias + " text not null";
		sql += " );";
		
		Log.d ( this.getClass ( )
		            .getName ( ),
		        sql
		      );
		pDb.execSQL ( sql );
	}
}
