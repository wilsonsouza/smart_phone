/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description: managed bluetooth device node.
 */
package br.com.tk.mcs.drivers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilsonsouza on 10/6/16.
 */

public class device_adapter extends BaseAdapter
{
	private java.util.List<device_node> m_node_list;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public device_adapter ( )
	{
		m_node_list = new ArrayList<device_node> ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public int getCount ( )
	{
		return m_node_list.size ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public Object getItem ( int position )
	{
		return m_node_list.get ( position );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public long getItemId ( int position )
	{
		return position;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public View getView ( int position,
	                      View convertView,
	                      ViewGroup parent
	                    )
	{
		return convertView;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void add ( String szName,
	                  String szAddress,
	                  boolean bPaired
	                )
	{
		m_node_list.add ( new device_node ( szName,
		                                    szAddress,
		                                    bPaired
		) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void clear ( )
	{
		m_node_list.clear ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public device_node find ( String szAddress )
	{
		for ( device_node i : m_node_list )
		{
			if ( szAddress.equals ( i.get_address ( ) ) )
			{
				return i;
			}
		}
		return null;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public device_node search ( String szName )
	{
		for ( device_node i : m_node_list )
		{
			if ( szName.equals ( i.get_name ( ) ) )
			{
				return i;
			}
		}
		return null;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public List<device_node> getItems ( )
	{
		return m_node_list;
	}
}
