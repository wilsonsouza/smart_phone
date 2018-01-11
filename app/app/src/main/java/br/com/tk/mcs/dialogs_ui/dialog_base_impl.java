/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.font_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 27/01/17.
 */

public class dialog_base_impl extends Dialog implements View.OnClickListener
{
	public static final int             LM_VERTICAL   = 0x100;
	public static final int             LM_HORIZONTAL = 0x101;
	public static final int             LM_GRID       = 0x102;
	public static final int             LM_RELATION   = 0x103;
	public static final int             LM_FRAME      = 0x104;
	public static final int             LM_TABLE      = 0x105;
	public static final int             LM_WITHOUT    = 0x200;
	protected static    Point           m_icon_offset = config_display_metrics.DialogIcon;
	private             ViewGroup       m_view        = null;
	private             Activity        m_handle      = null;
	private             linear_vertical m_body        = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl ( Context pWnd,
	                          int nID,
	                          int nLayoutMode
	                        )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.m_handle = ( Activity ) pWnd;
		this.m_body = new linear_vertical ( getContext ( ) );
		this.set_layout_mode ( nLayoutMode );
		this.setTitle ( nID );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl ( Context pWnd,
	                          String szData,
	                          int nLayoutMode
	                        )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.m_handle = ( Activity ) pWnd;
		this.m_body = new linear_vertical ( getContext ( ) );
		this.set_layout_mode ( nLayoutMode );
		this.setTitle ( szData );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onCreate ( Bundle pSavedInstanceState )
	{
		super.onCreate ( pSavedInstanceState );
		this.getWindow ( )
		    .setBackgroundDrawableResource ( R.drawable.button_selector );
		this.setCancelable ( false );
		this.setCanceledOnTouchOutside ( false );
		this.setContentView ( this.m_body,
		                      this.m_body.Params
		                    );
		this.m_body.set_margins ( new Rect ( 0,
		                                     8,
		                                     0,
		                                     8
		) );
		this.m_body.set_border ( true );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStart ( )
	{
		//
		super.onStart ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStop ( )
	{
		//
		super.onStop ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private dialog_base_impl set_layout_mode ( int nLayoutMode )
	{
		switch ( nLayoutMode )
		{
			case LM_FRAME:
				break;
			case LM_GRID:
				break;
			case LM_HORIZONTAL:
				this.m_view = new linear_horizontal ( this.getContext ( ) );
				this.m_body.addView ( this.m_view,
				                      0
				                    );
				break;
			case LM_RELATION:
				break;
			case LM_TABLE:
				break;
			case LM_VERTICAL:
				this.m_view = new linear_vertical ( this.getContext ( ) );
				this.m_body.addView ( this.m_view,
				                      0
				                    );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_message ( String szMessage )
	{
		int[] a_colors = new int[]{ 0,
		                            0
		};
		textview_impl caption_handle = new textview_impl ( getContext ( ) )
			.set_caption ( szMessage )
			.set_font_size ( textview_impl.DEFAULT )
			.set_color ( a_colors )
			.set_border ( false );
		
		caption_handle.set_margins ( config_display_metrics.Padding );
		caption_handle.Params.gravity = ( Gravity.LEFT | Gravity.CENTER_VERTICAL );
		
		m_view.addView ( caption_handle, caption_handle.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_message ( int nID )
	{
		textview_impl caption_handle = new textview_impl ( getContext ( ),
		                                                   nID,
		                                                   textview_impl.DEFAULT,
		                                                   new int[]{ 0,
		                                                              0
		                                                   },
		                                                   false
		);
		{
			caption_handle.set_margins ( config_display_metrics.Padding );
			caption_handle.Params.gravity = ( Gravity.LEFT | Gravity.CENTER_VERTICAL );
		}
		m_view.addView ( caption_handle, caption_handle.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl create_and_prepare ( ) throws
	                                               Exception
	{
		font_impl.set_size ( this,
		                     0,
		                     0
		                   );
		
		this.m_body.invalidate ( true );
		this.show ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_buttons ( int[] aButtons )
	{
		linear_horizontal p = new linear_horizontal ( getContext ( ) );
		/**/
		for ( int nData : aButtons )
		{
			button_impl b = new button_impl ( getContext ( ),
			                                  nData,
			                                  false,
			                                  R.drawable.button_selector,
			                                  null,
			                                  null
			);
			/**/
			if ( nData == R.string.button_cancel || nData == R.string.manager_button_cancel )
			{
				b.setEnabled ( true );
			}
			
			b.setOnClickListener ( this );
			b.set_margins ( config_display_metrics.Padding );
			p.addView ( b, b.Params );
		}
		/* put horizontal layout on Main view */
		p.Params.gravity = Gravity.CENTER;
		p.set_margins ( config_display_metrics.Padding );
		this.m_body.addView ( p, 1, p.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.KITKAT )
	protected dialog_base_impl set_icon ( int nIconID )
	{
		imageview_impl icon = new imageview_impl ( getContext ( ),
		                                           m_icon_offset,
		                                           nIconID,
		                                           false
		);
		{
			icon.setGravity ( Gravity.CENTER );
			icon.set_margins ( config_display_metrics.Padding );
			m_view.addView ( icon, 0, icon.Params );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_keyboard ( boolean bEnabled )
	{
		if ( bEnabled )
		{
			this.m_handle.runOnUiThread ( new Runnable ( )
			{
				@Override
				public void run ( )
				{
					m_handle.getWindow ( )
					        .setSoftInputMode ( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
				}
			} );
		}
		else
		{
			this.m_handle.runOnUiThread ( new Runnable ( )
			{
				@Override
				public void run ( )
				{
					m_handle.getWindow ( )
					        .setSoftInputMode ( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
				}
			} );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_keyboard ( boolean bEnabled,
	                                       View view
	                                     )
	{
		this.set_keyboard ( bEnabled );
		
		if ( bEnabled )
		{
			InputMethodManager imm = ( InputMethodManager ) this.m_handle.getSystemService (
				AppCompatActivity.INPUT_METHOD_SERVICE );
			{
				imm.showSoftInput ( view,
				                    0
				                  );
			}
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl show_message_box ( final int nCaption,
	                                           final int nMessage,
	                                           final int nKind
	                                         )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( getContext ( ),
				                  nCaption,
				                  nMessage,
				                  nKind
				);
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl show_message_box ( final String szCaption,
	                                           final String szMessage,
	                                           final int nKind
	                                         )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( getContext ( ),
				                  szCaption,
				                  szMessage,
				                  nKind
				);
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl show_message_box ( final int nCaption,
	                                           final String szMessage,
	                                           final int nKind
	                                         )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( getContext ( ),
				                  nCaption,
				                  szMessage,
				                  nKind
				);
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl close_dialog ( final Dialog dialog )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.dismiss ( );
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl show_dialog ( final Dialog dialog )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.show ( );
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_message_task ( final display_message dialog,
	                                           final String szMsg
	                                         )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.setMessage ( Html.fromHtml ( szMsg ) );
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_message_task ( final display_message dialog,
	                                           final int nMsg
	                                         )
	{
		this.m_handle.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.setMessage ( nMsg );
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public View get_control_by_id ( final int nId )
	{
		//
		return this.m_body.findViewById ( nId );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public dialog_base_impl set_control_by_id ( final int nId,
	                                            final boolean bEnabled
	                                          )
	{
		View p = this.m_body.findViewById ( nId );
		
		if ( p != null )
		{
			p.setEnabled ( bEnabled );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void dismiss ( )
	{
		this.set_keyboard ( false );
		super.dismiss ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_vertical get_body_layout ( )
	{
		//
		return this.m_body;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ViewGroup get_view ( )
	{
		//
		return this.m_view;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_vertical get_body ( )
	{
		//
		return this.m_body;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Activity get_handle ( )
	{
		//
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public interface IRunnable
	{
		void run ( int nWhich );
	}
}
