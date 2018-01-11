/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wilsonsouza on 21/02/17.
 */

public class mssql_persistente_manager extends AsyncTask<String, Void, Boolean>
{
	protected Context m_pWnd         = null;
	protected String  m_szDriverName = "net.sourceforge.jtds.jdbc.Driver";
	protected String  m_szUrl        = "jdbc:jtds:sqlserver://";
	protected String m_szIP;
	protected String m_szInstanceName;
	protected String m_szDatabaseName;
	protected Connection       m_conn = null;
	protected DatabaseMetaData m_meta = null;
	protected String m_szUser;
	protected String m_szPassword;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public mssql_persistente_manager ( Context pWnd,
	                                   String szIP,
	                                   String szInstanceName,
	                                   String szDatabaseName
	                                 )
	{
		this.m_pWnd = pWnd;
		this.m_szDatabaseName = szDatabaseName;
		this.m_szInstanceName = szInstanceName;
		this.m_szIP = szIP;
	   /* build database link name */
		this.m_szUrl += this.m_szIP + ";" + this.m_szInstanceName + ";" + this.m_szDatabaseName;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void SetUserAccess ( final String szUser,
	                            final String szPassword
	                          )
	{
		this.m_szUser = szUser;
		this.m_szPassword = szPassword;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected Boolean doInBackground ( String... params )
	{
		Boolean bSuccess = false;
		try
		{
			Class.forName ( this.m_szDriverName );
			this.m_conn = DriverManager.getConnection ( this.m_szUrl,
			                                            this.m_szUser,
			                                            this.m_szPassword
			                                          );
			this.m_meta = this.m_conn.getMetaData ( );
		}
		catch ( ClassNotFoundException | SQLException e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return bSuccess;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected void doPostExecute ( Boolean bResult )
	{
	
	}
}
