/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import br.com.tk.mcs.generic.company_setup;

/**
 * Created by wilsonsouza on 11/17/16.
 */

public class nosql_persistence_controller_square_ip extends nosql_persistence
{
	public static final String TAG = nosql_persistence_controller_square_ip.class.getName ( );
	
	//-----------------------------------------------------------------------------------------------------------------//
	public nosql_persistence_controller_square_ip ( Context context )
	{
		super ( context );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean update ( String szValue,
	                        final String square_name,
	                        final String square_alias
	                      )
	{
		String  aFields[] = new String[]{ nosql_persistence.m_ip };
		Cursor  pCursor   = null;
		boolean bSuccess  = false;
		long    lResult   = - 1;
		//
		m_handle = this.getWritableDatabase ( );
		on_create_square_ip_table ( m_handle );
		pCursor = m_handle.query ( nosql_persistence.m_square_table_name,
		                           aFields,
		                           null,
		                           null,
		                           null,
		                           null,
		                           null,
		                           null
		                         );
		//
		if ( pCursor.getCount ( ) > 0 )
		{
			String szIP = new String ( );
			//
			pCursor.moveToFirst ( );
			szIP = pCursor.getString ( pCursor.getColumnIndex ( nosql_persistence.m_ip ) );
			
			if ( ! szIP.equals ( szValue.trim ( ) ) )
			{
				ContentValues pValues = new ContentValues ( );
				//
				try
				{
					pValues.put ( nosql_persistence.m_ip,
					              szValue.trim ( )
					            );
					if ( ! square_alias.isEmpty ( ) )
					{
						pValues.put ( nosql_persistence.m_alias,
						              square_alias
						            );
					}
					
					if ( ! square_name.isEmpty ( ) )
					{
						pValues.put ( nosql_persistence.m_name,
						              square_name
						            );
					}
					
					lResult = m_handle.update ( nosql_persistence.m_square_table_name,
					                            pValues,
					                            null,
					                            null
					                          );
					bSuccess = ( lResult != - 1 );
				}
				catch ( SQLException e )
				{
					e.printStackTrace ( );
				}
			}
		}
		else
		{
			ContentValues pValues = new ContentValues ( );
			//
			try
			{
				pValues.put ( nosql_persistence.m_ip,
				              szValue.trim ( )
				            );
				if ( ! square_alias.isEmpty ( ) )
				{
					pValues.put ( nosql_persistence.m_alias,
					              square_alias
					            );
				}
				
				if ( ! square_name.isEmpty ( ) )
				{
					pValues.put ( nosql_persistence.m_name,
					              square_name
					            );
				}
				
				lResult = m_handle.insert ( nosql_persistence.m_square_table_name,
				                            null,
				                            pValues
				                          );
				bSuccess = ( lResult != - 1 );
			}
			catch ( SQLException e )
			{
				e.printStackTrace ( );
			}
		}
		//
		m_handle.close ( );
		return bSuccess;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public company_setup.plazas select ( )
	{
		String szIP         = new String ( );
		String aFields[]    = new String[]{ nosql_persistence.m_ip,
		                                    nosql_persistence.m_alias,
		                                    nosql_persistence.m_name
		};
		Cursor cursor       = null;
		String square_name  = null;
		String square_alias = null;
		//
		m_handle = this.getReadableDatabase ( );
		on_create_square_ip_table ( m_handle );
		cursor = m_handle.query ( nosql_persistence.m_square_table_name,
		                          aFields,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null,
		                          null
		                        );
		//
		if ( cursor.getCount ( ) > 0 )
		{
			cursor.moveToFirst ( );
			szIP = cursor.getString ( cursor.getColumnIndex ( nosql_persistence.m_ip ) );
			square_alias = cursor.getString ( cursor.getColumnIndex ( nosql_persistence.m_alias ) );
			square_name = cursor.getString ( cursor.getColumnIndex ( nosql_persistence.m_name ) );
			//
			if ( ! Character.isDigit ( szIP.charAt ( 0 ) ) )
			{
				szIP = "";
			}
		}
		//
		m_handle.close ( );
		return new company_setup.plazas ( square_alias,
		                                  square_name,
		                                  szIP
		);
	}
}
