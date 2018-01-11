/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description: managed bluetooth device list.
 */
package br.com.tk.mcs.drivers;

/**
 * Created by wilsonsouza on 10/7/16.
 */

public class device_node
{
	private String m_name;
	private String m_address;
	private boolean m_is_paired = false;
	
	//
	public device_node ( String szName,
	                     String szAddress,
	                     boolean bPaired
	                   )
	{
		m_name = szName;
		m_address = szAddress;
		m_is_paired = bPaired;
	}
	
	//
	public String get_name ( )
	{
		return m_name;
	}
	
	//
	public void set_name ( String szName )
	{
		m_name = szName;
	}
	
	//
	public String get_address ( )
	{
		return m_address;
	}
	
	//
	public void set_address ( String szAddress )
	{
		m_address = szAddress;
	}
	
	//
	public boolean get_paired ( )
	{
		return m_is_paired;
	}
	
	//
	public void set_paired ( boolean bPaired )
	{
		m_is_paired = bPaired;
	}
}