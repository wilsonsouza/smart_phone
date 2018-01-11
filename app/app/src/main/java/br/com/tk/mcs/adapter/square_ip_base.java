/*

   Sistema de Gestão de Pistas

   (C) 2017 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import br.com.tk.mcs.components.checkbox_impl;
import br.com.tk.mcs.components.listview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.main.SetIpFromTheSquare;

/**
 * Created by wilsonsouza on 24/11/2017.
 */

public class square_ip_base extends BaseAdapter
{
	public final static String            TAG       = square_ip_base.class.getName ( );
	private             Context           m_context = null;
	private             ArrayList<recipe> m_recipe  = null;
	
	//--------------------------------------------------------------------------------------------//
	public square_ip_base ( final Context context,
	                        final ArrayList<recipe> items
	                      )
	{
		m_recipe = items;
		m_context = context;
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public int getCount ( )
	{
		return m_recipe.size ( );
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public Object getItem ( int position )
	{
		return m_recipe.get ( position );
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public long getItemId ( int position )
	{
		return position;
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public View getView ( int position,
	                      View view,
	                      ViewGroup parent
	                    )
	{
		view_holder holder = null;
		
		if ( view == null )
		{
			holder = new view_holder ( parent.getContext ( ) ).builder ( );
			holder.setId ( position );
			holder.m_is_selected.setId ( position );
			view = holder;
			view.setTag ( holder );
		}
		else
		{
			holder = ( view_holder ) view.getTag ( );
		}
		
		recipe data = ( recipe ) getItem ( position );
		
		data.set_view_holder ( holder );
		holder.update ( data );
		
		return view;
	}
	
	//--------------------------------------------------------------------------------------------//
	public static class view_holder extends linear_horizontal implements View.OnClickListener
	{
		public textview_impl m_alias;
		public textview_impl m_name;
		public textview_impl m_ip;
		public checkbox_impl m_is_selected;
		
		//--------------------------------------------------------------------------------------------//
		public view_holder ( final Context context )
		{
			super ( context );
		}
		
		public final view_holder builder ( )
		{
			m_alias = new textview_impl ( getContext ( ) );
			m_name = new textview_impl ( getContext ( ) );
			m_ip = new textview_impl ( getContext ( ) );
			m_is_selected = new checkbox_impl ( getContext ( ), NO_ID ).set_enabled ( false )
			                                                           .set_checked ( false );
			
			addView ( m_alias, m_alias.Params );
			addView ( m_name, m_name.Params );
			addView ( m_ip, m_ip.Params );
			addView ( m_is_selected, m_is_selected.Params );
			
			this.invalidate ( true );
			this.setOnClickListener ( this );
			return this;
		}
		
		//--------------------------------------------------------------------------------------------//
		public final view_holder update ( final recipe data )
		{
			m_alias.get_handle ( )
			       .setText ( data.get_alias ( ) );
			m_alias.Params.width = m_alias.get_pixels_length ( ) + config_display_metrics.small_context_icons_size;
			m_alias.update_params ( );
			
			m_name.get_handle ( )
			      .setText ( data.get_name ( ) );
			m_name.Params.width = m_name.get_pixels_length ( ) + config_display_metrics.small_context_icons_size;
			m_name.update_params ( );
			
			m_ip.get_handle ( )
			    .setText ( data.get_ip ( ) );
			m_ip.Params.width = m_ip.get_pixels_length ( ) + config_display_metrics.small_context_icons_size;
			m_ip.update_params ( );
			
			m_is_selected.set_checked ( data.is_checked ( ) );
			m_is_selected.Params.gravity = Gravity.RIGHT;
			m_is_selected.update_params ( );
			
			return this;
		}
		
		/**
		 * Called when a view has been clicked.
		 *
		 * @param v The view that was clicked.
		 */
		@Override
		public void onClick ( View v )
		{
			SetIpFromTheSquare context = ( SetIpFromTheSquare ) this.getContext ( );
			
			if ( context != null )
			{
				listview_impl vlist    = context.get_listview_handle ( );
				int           position = v.getId ( );
				
				this.m_is_selected.set_checked ( ! this.m_is_selected.get_handle ( )
				                                                     .isChecked ( ) );
				vlist.get_handle ( )
				     .getOnItemClickListener ( )
				     .onItemClick ( vlist.get_handle ( ), v, position, position );
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------//
	public static class recipe extends company_setup.plazas
	{
		public final static String      TAG       = recipe.class.getName ( );
		private             boolean     m_checked = false;
		private             view_holder m_holder  = null;
		
		//--------------------------------------------------------------------------------------------//
		public recipe ( final String name,
		                final String alias,
		                final String ip
		              )
		{
			super ( name,
			        alias,
			        ip
			      );
		}
		
		//--------------------------------------------------------------------------------------------//
		public recipe ( final company_setup.plazas plazas )
		{
			super ( plazas.get_name ( ),
			        plazas.get_alias ( ),
			        plazas.get_ip ( )
			      );
		}
		
		//--------------------------------------------------------------------------------------------//
		public final boolean is_checked ( )
		{
			//
			return this.m_checked;
		}
		
		//--------------------------------------------------------------------------------------------//
		public final recipe set_checked ( final boolean checked )
		{
			this.m_checked = checked;
			return this;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final view_holder get_view_holder_handle ( )
		{
			return this.m_holder;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final recipe set_view_holder ( view_holder holder )
		{
			this.m_holder = holder;
			return this;
		}
	}
}
