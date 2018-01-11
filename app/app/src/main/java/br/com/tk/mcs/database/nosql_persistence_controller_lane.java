/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by revolution on 31/01/16.
 */

public class nosql_persistence_controller_lane extends nosql_persistence
{
	public static final String TAG      = nosql_persistence_controller_lane.class.getName ( );
	public static final int    ID_IP    = 0;
	public static final int    ID_NAME  = 1;
	public static final int    ID_ARROW = 2;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public nosql_persistence_controller_lane ( Context context )
	{
		super ( context );
		/**/
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean insert ( String name,
	                        String ip,
	                        String sentido
	                      )
	{
		m_handle = this.getWritableDatabase ( );
		ContentValues values = new ContentValues ( );
		values.put ( this.m_ip,
		             ip.trim ( )
		           );
		values.put ( this.m_name,
		             name.trim ( )
		           );
		values.put ( this.m_sen,
		             sentido.trim ( )
		           );
		
		long res = - 1;
		
		try
		{
			res = m_handle.insert ( this.m_lane,
			                        null,
			                        values
			                      );
		}
		catch ( SQLiteException e )
		{
			e.printStackTrace ( );
		}
		
		m_handle.close ( );
		return ( res != - 1 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean delete ( String ip,
	                        String name
	                      )
	{
		String where = this.m_ip + " = " + "\"" + ip.trim ( ) + "\"" + " and ";
		
		where += this.m_name + " = " + "\"" + name.trim ( ) + "\"";
		m_handle = this.getReadableDatabase ( );
		
		int rows = 0;
		
		try
		{
			rows = m_handle.delete ( this.m_lane,
			                         where,
			                         null
			                       );
		}
		catch ( SQLiteException e )
		{
			e.printStackTrace ( );
		}
		
		m_handle.close ( );
		return ( rows > 0 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean update ( String oldIP,
	                        String ip,
	                        String name
	                      )
	{
		m_handle = this.getWritableDatabase ( );
		String        where  = this.m_ip + " = " + "\"" + oldIP.trim ( ) + "\"";
		ContentValues values = new ContentValues ( );
		values.put ( this.m_ip,
		             ip.trim ( )
		           );
		values.put ( this.m_name,
		             name.trim ( )
		           );
		
		int rows = 0;
		
		try
		{
			rows = m_handle.update ( this.m_lane,
			                         values,
			                         where,
			                         null
			                       );
		}
		catch ( SQLiteException e )
		{
			e.printStackTrace ( );
		}
		
		m_handle.close ( );
		return ( rows > 0 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ArrayList<nosql_persistence_controller_lane.recipe> load_array_of ( )
	{
		Cursor cursor;
		String[] fields = { this.m_id,
		                    this.m_name,
		                    this.m_ip,
		                    this.m_sen
		};
		String where = this.m_name + " = " + "\"" + m_name.trim ( ) + "\" order by name";
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		
		final ArrayList<nosql_persistence_controller_lane.recipe> base = new ArrayList<> ( );
		
		if ( cursor.getCount ( ) > 0 )
		{
			
			cursor.moveToFirst ( );
			
			do
			{
				String ip        = cursor.getString ( cursor.getColumnIndex ( this.m_ip ) );
				String name      = cursor.getString ( cursor.getColumnIndex ( this.m_name ) );
				String direction = cursor.getString ( cursor.getColumnIndex ( this.m_sen ) );
				
				base.add ( new nosql_persistence_controller_lane.recipe ( ip,
				                                                          name,
				                                                          direction
				) );
			} while ( cursor.moveToNext ( ) );
		}
		
		m_handle.close ( );
		return base;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public ArrayAdapter<String> load_array_of ( final Context context )
	{
		Cursor cursor;
		String[] fields = { this.m_id,
		                    this.m_name,
		                    this.m_ip
		};
		String where = this.m_name + " = " + "\"" + m_name.trim ( ) + "\" order by name";
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		ArrayAdapter<String> adp = new ArrayAdapter<String> ( context,
		                                                      android.R.layout.simple_list_item_checked
		);
		
		if ( cursor.getCount ( ) > 0 )
		{
			
			cursor.moveToFirst ( );
			
			do
			{
				String str = cursor.getString ( cursor.getColumnIndex ( this.m_ip ) );
				
				str += " (" + cursor.getString ( cursor.getColumnIndex ( this.m_name ) ) + ")";
				adp.add ( str );
			} while ( cursor.moveToNext ( ) );
		}
		
		m_handle.close ( );
		return adp;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public ArrayList<String> get_array_of_address ( )
	{
		Cursor   cursor;
		String[] fields = { this.m_ip };
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		ArrayList<String> arr = new ArrayList<String> ( );
		
		if ( cursor.getCount ( ) > 0 )
		{
			
			cursor.moveToFirst ( );
			
			do
			{
				arr.add ( cursor.getString ( cursor.getColumnIndex ( this.m_ip ) ) );
			} while ( cursor.moveToNext ( ) );
		}
		
		m_handle.close ( );
		return arr;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public ArrayList<String> get_array_of_names ( )
	{
		Cursor   cursor;
		String   where  = this.m_name + " = " + "\"" + m_name.trim ( ) + "\" order by name";
		String[] fields = { this.m_name };
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		ArrayList<String> arr = new ArrayList<String> ( );
		
		if ( cursor.getCount ( ) > 0 )
		{
			
			cursor.moveToFirst ( );
			
			do
			{
				arr.add ( cursor.getString ( cursor.getColumnIndex ( this.m_name ) ) );
			} while ( cursor.moveToNext ( ) );
		}
		
		m_handle.close ( );
		return arr;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_by_name ( String name )
	{
		Cursor   cursor;
		String[] fields = { this.m_ip };
		String   where  = this.m_name + " = " + "\"" + name.trim ( ) + "\" order by name";
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		if ( cursor.getCount ( ) > 0 )
		{
			cursor.moveToFirst ( );
		}
		m_handle.close ( );
		return cursor.getString ( cursor.getColumnIndex ( this.m_ip ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_by_ip ( String ip )
	{
		Cursor   cursor;
		String   where  = this.m_ip + " = " + "\"" + ip.trim ( ) + "\"";
		String[] fields = { this.m_name };
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		if ( cursor.getCount ( ) > 0 )
		{
			cursor.moveToFirst ( );
		}
		m_handle.close ( );
		return cursor.getString ( cursor.getColumnIndex ( this.m_name ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final String[] get_detals_by_ip ( final String in_ip )
	{
		Cursor cursor;
		String[] fields = { this.m_id,
		                    this.m_name,
		                    this.m_ip,
		                    this.m_sen
		};
		String where = this.m_ip + " = " + "\"" + in_ip.trim ( ) + "\"";
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          where,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		if ( cursor.getCount ( ) > 0 )
		{
			cursor.moveToFirst ( );
		}
		m_handle.close ( );
		return new String[]{
			cursor.getString ( cursor.getColumnIndex ( this.m_ip ) ),
			cursor.getString ( cursor.getColumnIndex ( this.m_name ) ),
			cursor.getString ( cursor.getColumnIndex ( this.m_sen ) )
		};
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int count ( )
	{
		int      i      = 0;
		String[] fields = { this.m_name };
		Cursor   cursor = null;
		
		m_handle = this.getReadableDatabase ( );
		cursor = m_handle.query ( this.m_lane,
		                          fields,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		if ( cursor.getCount ( ) > 0 )
		{
			cursor.moveToFirst ( );
			do
			{
				i++;
			} while ( cursor.moveToNext ( ) );
		}
		m_handle.close ( );
		return i;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static class recipe
	{
		private String m_ip;
		private String m_name;
		private String m_direction;
		
		public recipe ( final String ip,
		                final String name,
		                final String direction
		              )
		{
			this.m_direction = direction;
			this.m_ip = ip;
			this.m_name = name;
		}
		
		public final String get_ip ( )
		{
			return this.m_ip;
		}
		
		public final recipe set_ip ( final String ip )
		{
			this.m_ip = ip;
			return this;
		}
		
		public final String get_name ( )
		{
			return this.m_name;
		}
		
		public final recipe set_name ( final String name )
		{
			this.m_name = name;
			return this;
		}
		
		public final String get_direction ( )
		{
			return this.m_direction;
		}
		
		public final recipe set_direction ( final String direction )
		{
			this.m_direction = direction;
			return this;
		}
	}
}