/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */

package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.radiobutton_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.manager.ProcessManager;

/**
 * Created by wilsonsouza on 01/08/2017.
 */

public class DialogPrinterManager extends dialog_base_impl
{
	public final static String           TAG                = DialogPrinterManager.class.getName ( );
	protected           ProcessManager   m_builder          = null;
	protected           radiobutton_impl m_printer_enabled  = null;
	protected           radiobutton_impl m_printer_disabled = null;
	protected           textview_impl    m_printer_status   = null;
	protected           int              m_icon_id          = 0;
	
	//-------------------------------------------------------------------------------------------//
	public DialogPrinterManager ( final Context context,
	                              final ProcessManager builder_handle,
	                              final int resource_icon_id
	                            )
	{
		super ( context,
		        "Impressora",
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_icon_id = resource_icon_id;
			this.m_builder = builder_handle;
			this.create_and_prepare ( );
		}
		catch ( Exception e )
		{
			Log.e ( e.getClass ( )
			         .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	@Override
	public dialog_base_impl create_and_prepare ( ) throws
	                                   Exception
	{
		boolean bIsOnline = m_builder.get_printer_manager_handle ( )
		                             .get_device_adapter ( )
		                             .isEnabled ( );
		this.m_printer_enabled = new radiobutton_impl ( this.getContext ( ),
		                                                R.string.enabled_discover_printer,
		                                                true,
		                                                ! bIsOnline,
		                                                radiobutton_impl.DEFAULT
		);
		this.m_printer_disabled = new radiobutton_impl ( this.getContext ( ),
		                                                 R.string.disable_discover_printer,
		                                                 true,
		                                                 bIsOnline,
		                                                 radiobutton_impl.DEFAULT
		);
		this.m_printer_status = new textview_impl ( this.getContext ( ),
		                                            this.m_icon_id,
		                                            textview_impl.DEFAULT,
		                                            textview_impl.DEFCOLOR,
		                                            false
		);
		
		this.m_printer_disabled.setOnClickListener ( this );
		this.m_printer_enabled.setOnClickListener ( this );
		linear_vertical body = new linear_vertical ( this.getContext ( ) );
		{
			body.addView ( this.m_printer_enabled );
			body.addView ( this.m_printer_disabled );
			body.setGravity ( Gravity.START );
			body.setPadding ( 16,
			                  16,
			                  16,
			                  16
			                );
		}
		linear_vertical top = new linear_vertical ( this.getContext ( ) );
		{
			int cond = R.string.printer_online;
			//
			switch ( this.m_icon_id )
			{
				case R.drawable.printer_ok:
					cond = R.string.printer_online;
					break;
				case R.drawable.printer_nopaper:
					cond = R.string.printer_nopaper;
					break;
				case R.drawable.print_superhot:
					cond = R.string.printer_superhot;
					break;
				case R.drawable.printer_error:
					cond = R.string.printer_offline;
					break;
				case R.drawable.printer_without_batery:
					cond = R.string.printer_batery_weak;
					break;
			}
			//
			this.m_printer_status.get_handle ( )
			                     .setText ( cond );
			this.m_printer_status.set_color ( textview_impl.TRACOLOR );
			top.setGravity ( Gravity.CENTER );
			top.setPadding ( 16,
			                 16,
			                 16,
			                 16
			               );
			top.addView ( this.m_printer_status );
			top.addView ( body );
		}
		//
		this.get_view ( )
		    .addView ( top );
		this.set_icon ( this.m_icon_id );
		this.set_buttons ( new int[]{ R.string.button_ok,
		                              R.string.button_cancel
		} );
		this.set_control_by_id ( R.string.button_ok,
		                         true
		                       );
		return super.create_and_prepare ( );
	}
	
	//-------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View view )
	{
		switch ( view.getId ( ) )
		{
			case R.string.button_ok:
				this.UpdatePrinterStatus ( );
				this.dismiss ( );
				break;
			case R.string.button_cancel:
				this.dismiss ( );
				break;
			default:
			{
				boolean bEnabled  = view == this.m_printer_enabled.get_handle ( ) && this.m_printer_enabled.is_checked ( );
				boolean bDisabled = view == this.m_printer_disabled.get_handle ( ) && this.m_printer_disabled.is_checked ( );
				
				this.m_printer_enabled.set_checked ( bEnabled );
				this.m_printer_disabled.set_checked ( bDisabled );
				break;
			}
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	protected void UpdatePrinterStatus ( )
	{
		if ( this.m_printer_enabled.is_checked ( ) )
		{
			this.m_builder.get_printer_manager_handle ( )
			              .set_stop_and_wait ( false );
		}
		else if ( this.m_printer_disabled.is_checked ( ) )
		{
			this.m_builder.get_printer_manager_handle ( )
			              .set_stop_and_wait ( true );
		}
	}
	//-------------------------------------------------------------------------------------------//
}
