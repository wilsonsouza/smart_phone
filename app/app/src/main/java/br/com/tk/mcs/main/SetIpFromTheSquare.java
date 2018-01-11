/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
   Last updated: wilsonsouza nov 21 2017 - fixed various failures
 */
package br.com.tk.mcs.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.adapter.square_ip_base;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.components.imagebutton;
import br.com.tk.mcs.components.listview_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.database.nosql_persistence_controller_square_ip;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.tools.Utils;
import br.com.tk.mcs.tools.keep_alive;

public class SetIpFromTheSquare extends frame_window_impl implements ListView.OnItemClickListener
{
	public static final  String                                 TAG           = SetIpFromTheSquare.class.getName ( );
	private static final int                                    ID_BUTTONS    = 0xff00998;
	private              nosql_persistence_controller_square_ip m_db          = null;
	private              display_message                        m_dialog      = null;
	private              boolean                                m_success     = false;
	private              listview_impl                          m_ip          = null;
	private              String                                 m_selected_ip = "";
	private              square_ip_base.recipe                  m_recipe      = null;
	private              linear_horizontal                      m_foot        = null;
	private              int                                    m_selected    = - 1;
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		
		this.m_db = new nosql_persistence_controller_square_ip ( this );
		
		this.get_toolbar_handle ( )
		    .get_caption_handle ( )
		    .get_handle ( )
		    .setText ( R.string.manager_set_ip_square );
		
		this.m_ip = new listview_impl ( this );
		this.prepare_child_controls ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected SetIpFromTheSquare prepare_child_controls ( )
	{
		this.m_foot = this.m_ip.create_footer ( new int[]{ R.string.button_apply,
		                                                   R.string.button_cancel
		                                        },
		                                        new int[]{ R.drawable.ic_ok,
		                                                   R.drawable.ic_cancel
		                                        }, false,
		                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
		                                      );
		this.m_foot.Params.gravity = Gravity.CENTER_HORIZONTAL;
		
		this.m_ip.set_padding ( config_display_metrics.Padding );
		this.m_ip.set_border ( true );
		
		this.get_layout_handle ( )
		    .addView ( this.m_ip, this.m_ip.Params );
		this.get_layout_handle ( )
		    .addView ( new space_impl ( this, 0, config_display_metrics.space_between_controls, false ) );
		this.get_layout_handle ( )
		    .addView ( this.m_foot, this.m_foot.Params );
		
		/* set listview ip plazas */
		this.m_ip.get_handle ( )
		         .setOnItemClickListener ( this );
		
		this.m_ip.get_handle ( )
		         .setAdapter ( new square_ip_base ( this, plazas_to_square_ip_recipe ( ) ) );
		this.update_listview ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	final ArrayList<square_ip_base.recipe> plazas_to_square_ip_recipe ( )
	{
		ArrayList<square_ip_base.recipe> list = new ArrayList<> ( );
		
		for ( company_setup.plazas p : company_setup.get_plazas_list ( ) )
		{
			list.add ( new square_ip_base.recipe ( p ) );
		}
		return list;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public SetIpFromTheSquare update_listview ( )
	{
		this.m_foot.invalidate ( true );
		this.get_layout_handle ( )
		    .invalidate ( true );
		
		int min   = this.get_layout_handle ( ).Params.get_display ( ).heightPixels - this.get_layout_handle ( ).Params.get_actionbar_height ( );
		int space = config_display_metrics.small_context_size * 5;
		
		this.m_ip.Params.height = min - space;
		this.m_ip.update_params ( );
		
		this.m_foot.findViewById ( R.drawable.ic_cancel )
		           .setEnabled ( true );
		this.m_foot.findViewById ( R.drawable.ic_cancel )
		           .setOnClickListener ( this );
		this.m_foot.findViewById ( R.drawable.ic_ok )
		           .setOnClickListener ( this );
		
		this.set_ip_selection ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onDestroy ( )
	{
		//
		super.onDestroy ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View view )
	{
		final int id = view.getId ( );
		
		if ( id == R.drawable.ic_ok )
		{
			m_db.update ( m_recipe.get_ip ( ),
			              m_recipe.get_alias ( ),
			              m_recipe.get_name ( )
			            );
			this.finish ( );
			return;
		}
		
		if ( id == R.drawable.ic_cancel )
		{
			this.finish ( );
		}
		
		if ( id == R.string.manager_button_verify )
		{
			String fmt = String.format ( "Verificando IP %s...",
			                             m_recipe.get_ip ( )
			                           );
			boolean success = false;
			
			m_dialog.show ( );
			m_dialog.setMessage ( fmt );
			Utils.set_keyboard_service_hidden ( this );

         /* try to connect on msc system */
			success = keep_alive.is_online ( m_recipe.get_ip ( ) );
			m_dialog.dismiss ( );
			
			if ( success )
			{
				new message_box ( this,
				                  "Resultado...",
				                  String.format ( "IP %s OK!",
				                                  m_recipe.get_ip ( )
				                                ),
				                  message_box.IDOK
				);
			}
			else
			{
				new message_box ( this,
				                  "Resultado...",
				                  String.format ( "IP %s sem resposta!",
				                                  m_recipe.get_ip ( )
				                                ),
				                  message_box.IDWARNING
				);
			}
		}
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
		imagebutton                ok     = ( imagebutton ) this.m_foot.findViewById ( R.drawable.ic_ok );
		square_ip_base.view_holder holder = ( square_ip_base.view_holder ) view;
		
		m_recipe = ( square_ip_base.recipe ) parent.getItemAtPosition ( position );
		ok.setEnabled ( holder.m_is_selected.get_handle ( )
		                                    .isChecked ( ) );
		
		if ( this.m_selected != - 1 )
		{
			square_ip_base.recipe old_recipe = ( square_ip_base.recipe ) parent.getItemAtPosition ( this.m_selected );
			old_recipe.get_view_holder_handle ( ).m_is_selected.set_checked ( false );
		}
		
		this.m_selected = position;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final listview_impl get_listview_handle ( )
	{
		return this.m_ip;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public SetIpFromTheSquare set_ip_selection ( )
	{
		company_setup.plazas square = this.m_db.select ( );
		ListAdapter data = this.m_ip.get_handle ( )
		                            .getAdapter ( );
		
		for ( int i = 0; i < data.getCount ( ); i++ )
		{
			square_ip_base.recipe base = ( square_ip_base.recipe ) data.getItem ( i );
			
			if ( base.get_ip ( )
			         .equals ( square.get_ip ( ) ) )
			{
				base.set_checked ( true );
				this.m_ip.get_handle ( )
				         .setSelection ( i );
				this.m_selected = i;
				break;
			}
		}
		
		return this;
	}
}
