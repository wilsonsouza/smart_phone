/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by wilsonsouza on 01/03/17.
 */

public class company_setup
{
	public static final String  TAG         = company_setup.class.getName ( );
	public static       boolean is_arteris  = false;
	public static       boolean is_cro      = true;
	public static       boolean is_tamoios  = false;
	public static       boolean is_tecsidel = false;
	public static       boolean is_via_rio  = false;
	public static       boolean is_040      = false;
	/* internal */
	public static       boolean is_debug    = true;
	public static       boolean is_scanner  = true;
	
	//--------------------------------------------------------------------------------------------//
	public final static Vector<plazas> get_plazas_list ( )
	{
		Vector<plazas> data = new Vector<plazas> ( );
		
		if ( company_setup.is_tamoios )
		{
			data.add ( new plazas ( "Praça Jambeiro",
			                        "P1 Tamoios",
			                        "172.16.62.62"
			) );
			data.add ( new plazas ( "Praça Paraibuna",
			                        "P2 Tamoios",
			                        "172.16.102.62"
			) );
		}
		else if ( company_setup.is_via_rio )
		{
			int ip = 0xbe;
			
			for ( int i = 1; i < 4; i++ )
			{
				data.add ( new plazas ( String.format ( Locale.FRANCE,
				                                        "Praça %d",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "P%d VIARIO",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "192.168.42.%d",
				                                        ip
				                                      )
				) );
				ip += 0xA;
			}
		}
		else if ( company_setup.is_040 )
		{
			int ip = 0x90;
			
			for ( int i = 1; i < 8; i++ )
			{
				data.add ( new plazas ( String.format ( Locale.FRANCE,
				                                        "Praça %d",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "P%d 040",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "10.50.%d.134",
				                                        ip
				                                      )
				) );
				ip += 0x10;
			}
			//
			ip = 0;
			//
			for ( int i = 8; i < 12; i++ )
			{
				data.add ( new plazas ( String.format ( Locale.FRANCE,
				                                        "Praça %d",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "P%d 040",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "10.51.%d.134",
				                                        ip
				                                      )
				) );
				ip += 0x10;
			}
		}
		else if ( company_setup.is_cro )
		{
			for ( int i = 1; i < 0xA; i++ )
			{
				data.add ( new plazas ( String.format ( Locale.FRANCE,
				                                        "Praça %d",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "P%d Rota do Oeste",
				                                        i
				                                      ),
				                        String.format ( Locale.FRANCE,
				                                        "10.200.%d.20",
				                                        10 * i
				                                      )
				) );
			}
		}
		else if ( company_setup.is_tecsidel )
		{
			data.add ( new plazas ( "Praça 1",
			                        "Tecsidel",
			                        "192.168.1.8:8181"
			) );
		}
		return data;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final static plazas find_plazas ( final String value )
	{
		List<plazas> plazas_list = company_setup.get_plazas_list ( );
		
		for ( plazas p : plazas_list )
		{
			if ( p.get_alias ( )
			      .toUpperCase ( )
			      .contains ( value.toUpperCase ( ) ) || p.get_name ()
			                                              .toUpperCase ( )
			                                              .contains ( value.toUpperCase ( ) ) )
			{
				return p;
			}
		}
		return null;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final mssql_database_configuration get_sql_database_access ( )
	{
		if ( is_tecsidel )
		{
			return new mssql_database_configuration ( "USRTOLLHOST",
			                                          "192.168.1.8"
			);
		}
		return new mssql_database_configuration ( );
	}
	
	//--------------------------------------------------------------------------------------------//
	public static class plazas
	{
		private String m_name;
		private String m_alias;
		private String m_ip;
		
		public plazas ( ) {}
		
		public plazas ( final String name,
		                final String alias,
		                final String ip
		              )
		{
			this.m_name = name;
			this.m_alias = alias;
			this.m_ip = ip;
		}
		
		public String get_name ( )
		{
			return this.m_name;
		}
		
		public String get_alias ( )
		{
			return this.m_alias;
		}
		
		public String get_ip ( )
		{
			return this.m_ip;
		}
	}
	
	//--------------------------------------------------------------------------------------------//
	public static class mssql_database_configuration /*sql_server_access*/
	{
		private String m_user         = "USRTOLLHOST";
		private String m_password     = "USRTOLLHOST";
		private String m_catalog_name = "";
		private String m_ip           = "";
		private String m_company_name = "";
		
		//------------------------------------------------------------------------------------------//
		public mssql_database_configuration ( ) {}
		
		//------------------------------------------------------------------------------------------//
		public mssql_database_configuration ( final String catalog_name,
		                                      final String ip
		                                    )
		{
			this.m_catalog_name = catalog_name;
			this.m_ip = ip;
		}
		
		//------------------------------------------------------------------------------------------//
		public final String get_user ( )
		{
			return this.m_user;
		}
		
		//------------------------------------------------------------------------------------------//
		public final String get_password ( )
		{
			return this.m_password;
		}
		
		//------------------------------------------------------------------------------------------//
		public final String get_catalog_name ( )
		{
			return this.m_catalog_name;
		}
		
		//------------------------------------------------------------------------------------------//
		public final void set_catalog_name ( final String catalog_name )
		{
			this.m_catalog_name = catalog_name;
		}
		
		//------------------------------------------------------------------------------------------//
		public final String get_ip ( )
		{
			return this.m_ip;
		}
		
		//------------------------------------------------------------------------------------------//
		public final void set_ip ( final String ip )
		{
			this.m_ip = ip;
		}
		
		//------------------------------------------------------------------------------------------//
		public final String get_company_name ( )
		{
			return this.m_company_name;
		}
	}
}
