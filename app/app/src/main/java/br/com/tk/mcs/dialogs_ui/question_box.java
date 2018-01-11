/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_ui;

import android.content.Context;
import android.view.View;
import android.view.Window;

import br.com.tk.mcs.R;

/**
 * Created by wilsonsouza on 31/01/17.
 */

public class question_box extends dialog_base_impl
{
	public static final String               TAG             = question_box.class.getName ( );
	public static       int                  DEFAULT         = 1;
	public static       int                  CUSTOMER        = 2;
	private             int                  m_mode          = 0;
	private             OnClickListener m_fn_proc_click = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public question_box ( Context pWnd,
	                      int nID,
	                      int nMsg,
	                      int nMode
	                    )
	{
		super ( pWnd,
		        nID,
		        LM_HORIZONTAL
		      );
		try
		{
			this.set_buttons ( nMode );
			this.set_icon ( R.drawable.box_question );
			this.set_message ( nMsg );
			this.create_and_prepare ( );
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public question_box ( Context pWnd,
	                      String szCaption,
	                      String szMessage,
	                      int nMode
	                    )
	{
		super ( pWnd,
		        szCaption,
		        LM_HORIZONTAL
		      );
		try
		{
			this.set_buttons ( nMode );
			this.set_icon ( R.drawable.box_question );
			this.set_message ( szMessage );
			this.create_and_prepare ( );
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public question_box create_and_prepare ( ) throws
	                                   Exception
	{
		this.get_control_by_id ( R.string.manager_button_confirm )
		    .setEnabled ( true );
		//this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		super.create_and_prepare ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private question_box set_buttons ( int nMode )
	{
		switch ( nMode )
		{
			case 1:
				this.set_buttons ( new int[]{ R.string.manager_button_confirm,
				                              R.string.manager_button_cancel
				} );
				break;
			case 2:
				break;
		}
		this.m_mode = nMode;
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v )
	{
		int which = v.getId ( );
		
		if ( this.m_mode == DEFAULT )
		{
			if ( this.m_fn_proc_click != null )
			{
				this.m_fn_proc_click.on_click ( this, v );
			}
			
			switch ( which )
			{
				case R.string.manager_button_confirm:
					this.get_handle ( )
					    .finish ( );
					break;
				case R.string.manager_button_cancel:
					dismiss ( );
					break;
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final question_box set_onclick_listener ( final question_box.OnClickListener fn_proc_click )
	{
		this.m_fn_proc_click = fn_proc_click;
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public interface OnClickListener
	{
		public void on_click ( final question_box dialog,
		                       View view
		                     );
	}
}
