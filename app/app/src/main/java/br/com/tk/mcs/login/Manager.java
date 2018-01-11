/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.toolbar_impl;
import br.com.tk.mcs.components.update_actionbar_home_indicator;
import br.com.tk.mcs.database.nosql_persistence_controller_lane;
import br.com.tk.mcs.main.Main;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Manager implements View.OnClickListener
{
	public final static String                            TAG                = Manager.class.getName ( );
	private             Main                              m_handle           = null;
	private             Layout                            m_main_layout      = null;
	private             Body                              m_main_body_layout = null;
	private             Wallpaper                         m_main_wallpaper   = null;
	private             Login                             m_main_login       = null;
	private             Actions                           m_main_actions     = null;
	private             nosql_persistence_controller_lane m_nosql_controller = null;
	private             update_actionbar_home_indicator   m_home_icon        = null;
	private             toolbar_impl                      m_toolbar          = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Manager ( Context context )
	{
		super ( );
		/**/
		this.m_handle = ( Main ) ( ( AppCompatActivity ) context );
		/**/
		this.m_nosql_controller = new nosql_persistence_controller_lane ( this.m_handle );
		this.m_main_layout = new Layout ( this.m_handle );
		this.m_main_body_layout = new Body ( this.m_handle,
		                                     this.m_main_layout
		);
		this.m_main_wallpaper = new Wallpaper ( this.m_handle,
		                                        this.m_main_body_layout
		);
		this.m_main_login = new Login ( this.m_handle,
		                                this.m_main_body_layout
		);
		this.m_main_actions = new Actions ( this.m_handle,
		                                    this.m_main_body_layout
		);
		   /* put logo */
		this.m_home_icon = new update_actionbar_home_indicator ( this.m_handle,
		                                                         R.drawable.ic_company_web
		);
		
		int[] icons_id = new int[]{ R.drawable.cctv_camera_icon };
		{
			
			this.m_toolbar = new toolbar_impl ( this.m_handle,
			                                    R.string.app_name
			);
			//this.m_toolbar.set_icon_list ( icons_id,
			//                               this
			//                             );
		}
		   /* recalcule all components on screen */
		this.m_main_layout.invalidate ( true );
		this.m_main_body_layout.set_border ( true );
		this.m_main_body_layout.setBackgroundColor ( Color.WHITE );
		this.m_main_body_layout.invalidate ( true );
		   /**/
		this.m_main_body_layout.set_padding ( new Rect ( 0x10,
		                                                 0x10,
		                                                 0x10,
		                                                 0x10
		) );
		this.m_main_body_layout.invalidate ( true );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		int nId = v.getId ( );
		
		switch ( nId )
		{
			case R.drawable.cctv_camera_icon:
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Layout get_layout ( )
	{
		return this.m_main_layout;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Actions get_actions ( )
	{
		return this.m_main_actions;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Login get_login ( )
	{
		return this.m_main_login;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Wallpaper get_wallpaper ( )
	{
		return this.m_main_wallpaper;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final nosql_persistence_controller_lane get_controller_lane_handle ( )
	{
		return this.m_nosql_controller;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Body get_body_layout ( )
	{
		return this.m_main_body_layout;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final toolbar_impl get_toolbar ( )
	{
		return this.m_toolbar;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final update_actionbar_home_indicator get_home_icon ( )
	{
		return this.m_home_icon;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Main get_main_handle ( )
	{
		return this.m_handle;
	}
}
