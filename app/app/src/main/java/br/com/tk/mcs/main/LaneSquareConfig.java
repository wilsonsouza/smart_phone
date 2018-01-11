/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Updated by wilson.souza (WR DevInfo)

   Description:
   Fixed: 09-20-2016
   event menu_config_update error StringIndexOutOfBoundsException
   event menu_config_delete error StringIndexOutOfBoundsException
 */
package br.com.tk.mcs.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.adapter.lane_ip_base;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.components.imagebutton;
import br.com.tk.mcs.components.listview_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.database.nosql_persistence_controller_lane;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

public class LaneSquareConfig extends frame_window_impl implements ListView.OnItemClickListener
{
	public static final  String                            TAG          = LaneSquareConfig.class.getName ( );
	private static final int                               m_LIMIT      = 0x1e;
	private static final String                            m_IP         = "IP";
	private              String                            m_current    = "";
	private              MenuItem                          m_update     = null;
	private              MenuItem                          m_delete     = null;
	private              MenuItem                          m_ip_square  = null;
	private              listview_impl                     m_view       = null;
	private              nosql_persistence_controller_lane m_controller = null;
	private              linear_horizontal                 m_foot       = null;
	private              int                               m_selected   = - 1;
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	@TargetApi( Build.VERSION_CODES.JELLY_BEAN_MR1 )
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		
		this.get_toolbar_handle ( )
		    .get_caption_handle ( )
		    .set_caption ( R.string.config_tile );
		
		this.m_controller = new nosql_persistence_controller_lane ( this );
		this.m_view = new listview_impl ( this );
		this.m_view.get_handle ( )
		           .setOnItemClickListener ( this );
		this.m_view.get_handle ( )
		           .setAdapter ( new lane_ip_base ( this,
		                                            this.to_recipe ( m_controller.load_array_of ( ) )
		           ) );
		
		this.m_foot = this.m_view.create_footer ( new int[]{ R.string.config_menu_insert,
		                                                     R.string.config_menu_update,
		                                                     R.string.config_menu_delete,
		                                                     R.string.config_menu_ip_square,
		                                                     R.string.button_cancel
		                                          },
		                                          new int[]{ R.drawable.ic_insert,
		                                                     R.drawable.ic_update,
		                                                     R.drawable.ic_delete,
		                                                     R.drawable.ic_config_ip_of_square,
		                                                     R.drawable.ic_cancel
		                                          }
			,
			                                       false,
			                                       Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
		                                        );
		this.m_foot.Params.gravity = Gravity.CENTER_HORIZONTAL;
		
		this.m_view.set_padding ( config_display_metrics.Padding );
		this.m_view.set_border ( true );
		this.m_view.Params.gravity = Gravity.TOP;
		
		this.set_buttons_action ( )
		    .get_layout_handle ( )
		    .addView ( this.m_view, this.m_view.Params );
		this.get_layout_handle ( )
		    .addView ( new space_impl ( this, 0, config_display_metrics.space_between_controls, false ) );
		this.get_layout_handle ( )
		    .addView ( this.m_foot, this.m_foot.Params );
		
		this.update_listview ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onResume ( )
	{
		super.onResume ( );
		this.update_listview ( );
		
		if ( m_delete != null && m_update != null )
		{
			setEnable ( false );
		}
		
		if ( m_view != null )
		{
			ArrayList<lane_ip_base.recipe> recipe_queue = this.to_recipe ( this.m_controller.load_array_of ( ) );
			lane_ip_base                   base         = new lane_ip_base ( this, recipe_queue );
			
			this.m_view.get_handle ( )
			           .setAdapter ( base );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public boolean onCreateOptionsMenu ( Menu menu )
	{
		/*
		MenuInflater inflater = getMenuInflater ( );
		inflater.inflate ( R.menu.menu_config,
		                   menu
		                 );
		m_delete = menu.findItem ( R.id.menu_config_delete );
		m_update = menu.findItem ( R.id.menu_config_update );
		m_ip_square = menu.findItem ( R.id.menu_config_ip_square );
		
		setEnable ( false );
		*/
		return super.onCreateOptionsMenu ( menu );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public LaneSquareConfig update_listview ( )
	{
		this.m_foot.invalidate ( true );
		this.get_layout_handle ( )
		    .invalidate ( true );
		
		int min   = this.get_layout_handle ( ).Params.get_display ( ).heightPixels - this.get_layout_handle ( ).Params.get_actionbar_height ( );
		int space = config_display_metrics.small_context_size * 5;
		
		this.m_view.Params.height = min - space;
		this.m_view.update_params ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean execute_buttons_actions ( View view )
	{
		Intent wnd = null;
		
		switch ( view.getId ( ) )
		{
			case R.drawable.ic_insert:
			{
				if ( m_controller.count ( ) < m_LIMIT )
				{
					wnd = new Intent ( LaneSquareConfig.this, TrackRegister.class );
					
					startActivity ( wnd );
					setEnable ( false );
				}
				else
				{
					new message_box ( this,
					                  R.string.ids_warning,
					                  R.string.config_msg_error,
					                  message_box.IDWARNING
					);
				}
				return true;
			}
			case R.drawable.ic_update:
			{
				wnd = new Intent ( LaneSquareConfig.this, TrackRegister.class );
				wnd.putExtra ( m_IP, m_current.trim ( ) );
				startActivity ( wnd );
				setEnable ( false );
				return true;
			}
			case R.drawable.ic_delete:
			{
				m_controller.delete ( m_current.trim ( ), m_controller.get_by_ip ( m_current.trim ( ) ) );
				this.m_view.get_handle ( )
				           .setAdapter ( new lane_ip_base ( this,
				                                            this.to_recipe ( m_controller.load_array_of ( ) )
				           ) );
				setEnable ( false );
				this.m_selected = - 1;
				return true;
			}
			case R.drawable.ic_config_ip_of_square:
			{
				wnd = new Intent ( LaneSquareConfig.this, SetIpFromTheSquare.class );
				startActivity ( wnd );
				return true;
			}
			case R.drawable.ic_cancel:
			{
				this.finish ();
			}
		}
		
		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final listview_impl get_listview_handle ( )
	{
		return this.m_view;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void setEnable ( boolean status )
	{
		/*m_delete.setEnabled ( status );
		m_update.setEnabled ( status );*/
		imagebutton delete = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_delete );
		imagebutton update = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_update );
		
		delete.setEnabled ( status );
		update.setEnabled ( status );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ArrayList<lane_ip_base.recipe> to_recipe ( final ArrayList<nosql_persistence_controller_lane.recipe> data )
	{
		final ArrayList<lane_ip_base.recipe> out = new ArrayList<> ( );
		
		for ( nosql_persistence_controller_lane.recipe p : data )
		{
			out.add ( new lane_ip_base.recipe ( p.get_ip ( ),
			                                    p.get_name ( ),
			                                    p.get_direction ( )
			) );
		}
		return out;
	}
	
	/**
	 * Callback method to be invoked when an item in this AdapterView has
	 * been clicked.
	 * <p>
	 * Implementers can call getItemAtPosition(position) if they need
	 * to access the data associated with the selected item.
	 *
	 * @param parent   The AdapterView where the click happened.
	 * @param view     The view within the AdapterView that was clicked (this
	 *                 will be a view provided by the adapter)
	 * @param position The position of the view in the adapter.
	 * @param id       The row id of the item that was clicked.
	 */
	@Override
	public void onItemClick ( AdapterView<?> parent,
	                          View view,
	                          int position,
	                          long id
	                        )
	{
		lane_ip_base.recipe data = ( lane_ip_base.recipe ) parent.getItemAtPosition ( position );
		
		imagebutton              delete  = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_delete );
		imagebutton              change  = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_update );
		lane_ip_base.view_holder checked = ( lane_ip_base.view_holder ) view;
		
		delete.setEnabled ( checked.m_checked.get_handle ( )
		                                     .isChecked ( ) );
		change.setEnabled ( checked.m_checked.get_handle ( )
		                                     .isChecked ( ) );
		this.m_current = data.get_ip ( );
		
		if ( this.m_selected != - 1 )
		{
			lane_ip_base.recipe old = ( lane_ip_base.recipe ) parent.getItemAtPosition ( this.m_selected );
			
			old.get_view_holder_handle ( )
			   .get_checked_handle ( )
			   .set_checked ( false );
		}
			/*	data.get_view_holder_handle ( )
				    .get_checked_handle ( )
				    .set_checked ( ! ischecked );
			}*/
		
		this.m_selected = position;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private LaneSquareConfig set_buttons_action ( )
	{
		imagebutton insert = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_insert );
		imagebutton update = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_update );
		imagebutton delete = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_delete );
		imagebutton square = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_config_ip_of_square );
		imagebutton cancel = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_cancel );
		View.OnClickListener click = new View.OnClickListener ( )
		{
			@Override
			public void onClick ( View v )
			{
				execute_buttons_actions ( v );
			}
		};
		
		insert.setEnabled ( true );
		square.setEnabled ( true );
		cancel.setEnabled ( true );
		
		insert.setOnClickListener ( click );
		update.setOnClickListener ( click );
		delete.setOnClickListener ( click );
		square.setOnClickListener ( click );
		cancel.setOnClickListener ( click );
		
		return this;
	}
}

