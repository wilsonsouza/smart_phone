/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_layout_impl;
import br.com.tk.mcs.main.Main;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Actions extends linear_horizontal implements View.OnClickListener
{
	public final static String      TAG      = Actions.class.getName ( );
	private             button_impl m_login  = null;
	private             button_impl m_clear  = null;
	private             Main        m_handle = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Actions ( Context context,
	                 linear_layout_impl layout_body
	               )
	{
		super ( context );
		
		this.m_handle = ( Main ) ( ( AppCompatActivity ) context );
		this.Params.gravity = Gravity.CENTER;
		
		this.m_clear = new button_impl ( this.getContext ( ), R.string.button_clear, true );
		this.m_login = new button_impl ( this.getContext ( ), R.string.button_ok, false );
		
		this.m_clear.setOnClickListener ( this );
		this.m_login.setOnClickListener ( this );
		
		this.addView ( this.m_login, this.Params );
		this.addView ( this.alloc_space ( config_display_metrics.space_between_controls, 0 ) );
		this.addView ( this.m_clear, this.Params );
		
		layout_body.addView ( this.alloc_space ( 0, config_display_metrics.space_between_controls ) );
		layout_body.addView ( this, this.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		switch ( v.getId ( ) )
		{
			case R.string.button_ok:
			{
				Main handle = ( Main ) this.getContext ( );
				
				handle.get_manager_handle ( )
				      .get_login ( )
				      .onClick ( v );
				handle.onClick ( v );
				break;
			}
			case R.string.button_clear:
				m_handle.get_manager_handle ( )
				        .get_login ( )
				        .clear_fields ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final button_impl get_login ( )
	{
		//
		return this.m_login;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final button_impl get_clear ( )
	{
		//
		return this.m_clear;
	}
}
