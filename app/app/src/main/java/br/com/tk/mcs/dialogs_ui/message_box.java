/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_ui;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.font_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.components.update_content_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 27/01/17.
 */

public class message_box extends Dialog implements View.OnClickListener
{
	public static final String          TAG           = message_box.class.getName ( );
	public static final int             LM_VERTICAL   = 0x100;
	public static final int             LM_HORIZONTAL = 0x101;
	public static final int             LM_GRID       = 0x102;
	public static final int             LM_RELATION   = 0x103;
	public static final int             LM_FRAME      = 0x104;
	public static final int             LM_TABLE      = 0x105;
	public static final int             LM_WITHOUT    = 0x200;
	public static       int             IDERROR       = R.drawable.box_error;
	public static       int             IDOK          = R.drawable.box_ok;
	public static       int             IDWARNING     = R.drawable.box_warning;
	public static       int             IDQUESTION    = R.drawable.box_question;
	protected static    Point           m_iconoffset  = config_display_metrics.DialogIcon;
	protected           ViewGroup       View          = null;
	private             int[]           m_buttons     = new int[]{ R.string.button_ok };
	private             linear_vertical m_body        = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     int nID,
	                     int nMsg,
	                     int nIconID
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( nMsg );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( nID );
		this.create ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     int nID,
	                     int nMsg,
	                     int nIconID,
	                     View.OnClickListener pfnClick
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( nMsg );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( nID );
		this.get_control_by_id ( this.m_buttons[ 0 ] )
		    .setOnClickListener ( pfnClick );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     String szCaption,
	                     String szMessage,
	                     int nIconID
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( szMessage );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( szCaption );
		this.create ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     int nID,
	                     String szMessage,
	                     int nIconID
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( szMessage );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( nID );
		this.create ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     int nID,
	                     Exception e,
	                     int nIconID
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( e.getMessage ( ) );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( nID );
		this.create ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public message_box ( Context pWnd,
	                     String szCaption,
	                     Exception e,
	                     int nIconID
	                   )
	{
		super ( pWnd,
		        config_display_metrics.DialogStyle
		      );
		this.set_layout_mode ( LM_HORIZONTAL );
		this.set_icon ( nIconID );
		this.set_message ( e.getMessage ( ) );
		this.set_buttons ( this.m_buttons );
		this.setTitle ( szCaption );
		this.create ( );
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
	private message_box set_layout_mode ( int nLayoutMode )
	{
		this.m_body = new linear_vertical ( getContext ( ) );
		
		switch ( nLayoutMode )
		{
			case LM_FRAME:
				break;
			case LM_GRID:
				break;
			case LM_HORIZONTAL:
				this.View = new linear_horizontal ( this.getContext ( ) );
				this.m_body.addView ( this.View );
				break;
			case LM_RELATION:
				break;
			case LM_TABLE:
				break;
			case LM_VERTICAL:
				this.View = new linear_vertical ( this.getContext ( ) );
				this.m_body.addView ( this.View );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_message ( String szMessage )
	{
		textview_impl caption = new textview_impl ( getContext ( ) );
		
		caption.set_caption ( szMessage )
		       .set_font_size ( textview_impl.DEFAULT )
		       .set_color ( new int[]{ 0,
		                               0
		       } )
		       .set_margins ( config_display_metrics.Padding )
			.Params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
		
		View.addView ( caption, caption.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_message ( int nID )
	{
		textview_impl pMessage = new textview_impl ( getContext ( ),
		                                             nID,
		                                             textview_impl.DEFAULT,
		                                             new int[]{ 0,
		                                                        0
		                                             },
		                                             false
		);
		{
			pMessage.set_margins ( new Rect ( 8,
			                                  8,
			                                  8,
			                                  8
			) );
			pMessage.setGravity ( Gravity.LEFT | Gravity.CENTER_VERTICAL );
		}
		View.addView ( pMessage,
		               pMessage.Params
		             );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void create ( )
	{
		super.create ( );
		font_impl.set_size ( this,
		                     0,
		                     0
		                   );
		update_content_impl.invalidate ( this.m_body,
		                                 true
		                               );
		this.show ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_buttons ( int[] aButtons )
	{
		linear_horizontal p = new linear_horizontal ( getContext ( ) );
		/**/
		for ( int nData : aButtons )
		{
			button_impl b = new button_impl ( getContext ( ),
			                                  nData,
			                                  false,
			                                  button_impl.DEFAULT_RESOURCE,
			                                  //R.drawable.button_selector,
			                                  null,
			                                  null
			);
		   /**/
			b.setEnabled ( true );
			b.setOnClickListener ( this );
			b.get_handle ( )
			 .setPadding ( 8,
			               0,
			               8,
			               0
			             );
			b.set_margins ( new Rect ( 8,
			                           0,
			                           8,
			                           0
			) );
			p.addView ( b );
		}
      /* put horizontal layout on Main view */
		p.Params.gravity = Gravity.CENTER;
		p.set_margins ( new Rect ( 8,
		                           8,
		                           8,
		                           8
		) );
		this.m_body.addView ( p );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.KITKAT )
	protected void set_icon ( int nIconID )
	{
		imageview_impl icon = new imageview_impl ( getContext ( ),
		                                           m_iconoffset,
		                                           nIconID,
		                                           false
		);
		{
			icon.setGravity ( Gravity.CENTER );
			icon.set_margins ( new Rect ( 8,
			                              8,
			                              8,
			                              8
			) );
			View.addView ( icon,
			               0,
			               icon.Params
			             );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v )
	{
		if ( v.getId ( ) == this.m_buttons[ 0 ] )
		{
			this.dismiss ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public View get_control_by_id ( final int nID )
	{
		//
		return this.m_body.findViewById ( nID );
	}
}
