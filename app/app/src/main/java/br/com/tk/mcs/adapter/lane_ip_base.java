package br.com.tk.mcs.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import br.com.tk.mcs.components.checkbox_impl;
import br.com.tk.mcs.components.listview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.main.LaneSquareConfig;

/**
 * Created by wilsonsouza on 01/12/2017.
 */

public class lane_ip_base extends BaseAdapter
{
	public final static String                         TAG       = lane_ip_base.class.getName ( );
	private             Context                        m_context = null;
	private             ArrayList<lane_ip_base.recipe> m_recipe  = null;
	
	public lane_ip_base ( Context context,
	                      ArrayList<lane_ip_base.recipe> items
	                    )
	{
		int count = 0;
		
		this.m_context = context;
		this.m_recipe = items;
	}
	
	/**
	 * How many items are in the data set represented by this Adapter.
	 *
	 * @return Count of items.
	 */
	@Override
	public int getCount ( )
	{
		//
		return this.m_recipe.size ( );
	}
	
	/**
	 * Get the data item associated with the specified position in the data set.
	 *
	 * @param position Position of the item whose data we want within the adapter's
	 *                 data set.
	 * @return The data at the specified position.
	 */
	@Override
	public Object getItem ( int position )
	{
		//
		return this.m_recipe.get ( position );
	}
	
	/**
	 * Get the row id associated with the specified position in the list.
	 *
	 * @param position The position of the item within the adapter's data set whose row id we want.
	 * @return The id of the item at the specified position.
	 */
	@Override
	public long getItemId ( int position )
	{
		//
		return position;
	}
	
	/**
	 * Get a View that displays the data at the specified position in the data set. You can either
	 * create a View manually or inflate it from an XML layout file. When the View is inflated, the
	 * parent View (GridView, ListView...) will apply default layout parameters unless you use
	 * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
	 * to specify a root view and to prevent attachment to the root.
	 *
	 * @param position    The position of the item within the adapter's data set of the item whose view
	 *                    we want.
	 * @param convertView The old view to reuse, if possible. Note: You should check that this view
	 *                    is non-null and of an appropriate type before using. If it is not possible to convert
	 *                    this view to display the correct data, this method can create a new view.
	 *                    Heterogeneous lists can specify their number of view types, so that this View is
	 *                    always of the right type (see {@link #getViewTypeCount()} and
	 *                    {@link #getItemViewType(int)}).
	 * @param parent      The parent that this view will eventually be attached to
	 * @return A View corresponding to the data at the specified position.
	 */
	@Override
	public View getView ( int position,
	                      View convertView,
	                      ViewGroup parent
	                    )
	{
		view_holder holder = null;
		
		if ( convertView == null )
		{
			holder = new view_holder ( this.m_context ).builder ( );
			holder.setId ( position );
			convertView = holder;
			convertView.setTag ( holder );
		}
		else
		{
			holder = ( view_holder ) convertView.getTag ( );
		}
		
		lane_ip_base.recipe data = ( lane_ip_base.recipe ) getItem ( position );
		
		data.set_view_holder ( holder );
		holder.update ( data );
		
		return convertView;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static class recipe
	{
		private String m_ip;
		private String m_name;
		private String m_direction;
		private view_holder m_handle = null;
		
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
			//
			return this.m_ip;
		}
		
		public final recipe set_ip ( final String ip )
		{
			this.m_ip = ip;
			return this;
		}
		
		public final view_holder get_view_holder_handle ( )
		{
			//
			return this.m_handle;
		}
		
		public final recipe set_view_holder ( view_holder holder )
		{
			this.m_handle = holder;
			return this;
		}
		
		public final String get_name ( )
		{
			//
			return this.m_name;
		}
		
		public final recipe set_name ( final String name )
		{
			this.m_name = name;
			return this;
		}
		
		public final String get_direction ( )
		{
			//
			return this.m_direction;
		}
		
		public final recipe set_direction ( final String direction )
		{
			this.m_direction = direction;
			return this;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static class view_holder extends linear_horizontal implements View.OnClickListener
	{
		public textview_impl m_ip        = null;
		public textview_impl m_name      = null;
		public textview_impl m_direction = null;
		public checkbox_impl m_checked   = null;
		
		public view_holder ( Context context )
		{
			super ( context );
		}
		
		public final view_holder builder ( )
		{
			final Context context = this.getContext ( );
			
			m_ip = new textview_impl ( context ).set_font_size ( View.NO_ID );
			m_name = new textview_impl ( context ).set_font_size ( View.NO_ID );
			m_direction = new textview_impl ( context ).set_font_size ( View.NO_ID );
			m_checked = new checkbox_impl ( context, View.NO_ID ).set_checked ( false )
			                                                     .set_enabled ( false );
			
			this.Params.width = this.Params.get_display ( ).widthPixels;
			m_name.Params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			m_ip.Params.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
			m_direction.Params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
			m_checked.Params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
			
			this.addView ( m_name, this.m_name.Params );
			this.addView ( m_ip, this.m_ip.Params );
			this.addView ( m_direction, this.m_direction.Params );
			this.addView ( m_checked, this.m_checked.Params );

			this.invalidate ( true );
			this.setOnClickListener ( this );
			return this;
		}
		
		public final textview_impl get_ip_handle ( )
		{
			//
			return this.m_ip;
		}
		
		public final textview_impl get_name_handle ( )
		{
			//
			return this.m_name;
		}
		
		public final textview_impl get_direction_handle ( )
		{
			//
			return this.m_direction;
		}
		
		public final checkbox_impl get_checked_handle ( )
		{
			//
			return this.m_checked;
		}
		
		public final view_holder update ( final lane_ip_base.recipe data )
		{
			final int padding = config_display_metrics.small_context_icons_size;
			
			if ( data != null )
			{
				this.m_ip.set_caption ( data.get_ip ( ) );
				this.m_name.set_caption ( data.get_name ( ) );
				this.m_direction.set_caption ( data.get_direction ( ) );
				
				int w = this.m_ip.get_pixels_length ( ) + padding;
				
				this.m_ip.Params.width = w;
				this.m_ip.update_params ( );
				
				this.m_name.Params.width = w;
				this.m_name.update_params ( );
				
				this.m_direction.Params.width = w;
				this.m_direction.update_params ( );
				
				this.m_checked.Params.width = w;
				this.m_checked.update_params ( );
			}
			
			this.invalidate ( true );
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
			LaneSquareConfig context = ( LaneSquareConfig ) this.getContext ( );
			
			if ( context != null )
			{
				listview_impl vlist    = context.get_listview_handle ( );
				int           position = v.getId ( );
				
				this.m_checked.get_handle ( )
				              .setChecked ( ! this.m_checked.get_handle ( )
				                                            .isChecked ( ) );
				vlist.get_handle ( )
				     .getOnItemClickListener ( )
				     .onItemClick ( vlist.get_handle ( ), v, position, position );
			}
		}
	}
}
