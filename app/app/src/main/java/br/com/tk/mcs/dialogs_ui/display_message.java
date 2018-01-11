package br.com.tk.mcs.dialogs_ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;

import br.com.tk.mcs.components.font_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.generic.config_display_metrics;

/**
 * Created by wilsonsouza on 28/01/17.
 */
public class display_message extends ProgressDialog
{
	public final static String TAG   = display_message.class.getName ( );
	public final static int    NO_ID = ( - 1 );
	
	public display_message ( Context pWnd,
	                         int message_id
	                       )
	{
		super ( pWnd, config_display_metrics.DialogMessageStyle );
		this.set_theme ( );
		this.setMessage ( message_id );
		this.setCancelable ( false );
		this.setIndeterminate ( true );
		font_impl.set_size ( this,
		                     0,
		                     0
		                   );
	}
	
	public display_message ( Context pWnd,
	                         int caption_id,
	                         int message_id
	                       )
	{
		super ( pWnd,
		        config_display_metrics.DialogMessageStyle
		      );
		
		this.set_theme ( );
		this.setMessage ( message_id );
		this.setCancelable ( false );
		this.setIndeterminate ( true );
		this.set_caption ( caption_id );
		font_impl.set_size ( this,
		                     0,
		                     0
		                   );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public display_message ( Context pWnd,
	                         int nCaption,
	                         String szMsg
	                       )
	{
		super ( pWnd,
		        config_display_metrics.DialogMessageStyle
		      );
		
		this.set_theme ( );
		this.setMessage ( szMsg );
		this.setCancelable ( false );
		this.setIndeterminate ( true );
		this.set_caption ( nCaption );
		font_impl.set_size ( this,
		                     0,
		                     0
		                   );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public <T> display_message set_caption ( T caption )
	{
		if ( caption.getClass ( )
		            .getName ( )
		            .equals ( "java.lang.String" ) )
		{
			if ( caption.toString ( ) != null )
			{
				this.setCustomTitle ( new textview_impl ( getContext ( ) ).set_caption ( caption.toString ( ) )
				                                                          .set_font_size ( textview_impl.CAPTION )
				                                                          .set_color ( textview_impl.DEFCOLOR )
				                                                          .set_border ( true ) );
			}
		}
		else if ( caption.getClass ( )
		                 .getName ( )
		                 .equals ( "java.lang.Integer" ) )
		{
			int id = Integer.parseInt ( caption.toString ( ) );
			
			if ( id != - 1 )
			{
				this.setCustomTitle ( new textview_impl ( getContext ( ),
				                                          id,
				                                          textview_impl.CAPTION,
				                                          textview_impl.DEFCOLOR,
				                                          true
				) );
			}
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@SuppressWarnings( "deprecated" )
	@Override
	public void setMessage ( CharSequence szMsg )
	{
		if ( szMsg != null )
		{
			super.setMessage ( Html.fromHtml ( szMsg.toString ( ) ) );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void setMessage ( int nID )
	{
		if ( nID == - 1 )
		{
			return;
		}
		super.setMessage ( Html.fromHtml ( getContext ( ).getString ( nID ) ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void show ( )
	{
		/**/
		super.show ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private display_message set_theme ( )
	{
		if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP )
		{
			this.getContext ( )
			    .setTheme ( config_display_metrics.DialogStyle );
		}
		return this;
	}
}