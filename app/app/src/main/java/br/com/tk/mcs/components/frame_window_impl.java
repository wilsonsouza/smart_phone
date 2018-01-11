/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 27/11/2017.
 */

public class frame_window_impl extends AppCompatActivity implements View.OnClickListener
{
	public final static String            TAG       = frame_window_impl.class.getName ( );
	private             imageview_impl    m_icon    = null;
	private             linear_horizontal m_layout  = null;
	private             toolbar_impl      m_toolbar = null;
	
	//--------------------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onCreate ( Bundle handle )
	{
		super.onCreate ( handle );
		this.m_icon = new imageview_impl ( this,
		                                   imageview_impl.NO_ID
		);
		this.m_layout = new linear_horizontal ( this ).set_fullscreen ();
		this.m_toolbar = new toolbar_impl ( this,
		                                    R.string.app_name
		);
		
		this.m_layout.setOrientation ( LinearLayout.VERTICAL );
		this.request_camera_permisstion ( )
		    .setContentView ( this.m_layout, this.m_layout.Params );
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onDestroy ( )
	{
		super.onDestroy ( );
		//
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	@Override
	public Resources.Theme getTheme ( )
	{
		Resources.Theme theme = super.getTheme ( );
		
		theme.applyStyle ( R.style.ThemeOverlay_AppCompat_ActionBar, true );
		return theme;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	protected frame_window_impl prepare_child_controls ( )
	{
		return this;
		//
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final toolbar_impl get_toolbar_handle ( )
	{
		/**/
		return this.m_toolbar;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_icon_handle ( )
	{
		//
		return this.m_icon;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final linear_horizontal get_layout_handle ( )
	{
		//
		return this.m_layout;
	}
	
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick ( View v )
	{
	
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl show_dialog ( final display_message dialog_handle )
	{
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog_handle.show ( );
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl close_dialog ( final display_message dialog_handle )
	{
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog_handle.dismiss ( );
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl show_message_box ( final String caption,
	                                                  final String message,
	                                                  final int icon_resouece_id
	                                                )
	{
		final Context context = this;
		
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( context,
				                  caption,
				                  message,
				                  icon_resouece_id
				);
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl show_message_box ( final int caption_resource_id,
	                                                  final String message,
	                                                  final int icon_resource_id
	                                                )
	{
		final Context context = this;
		
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( context,
				                  caption_resource_id,
				                  message,
				                  icon_resource_id
				);
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl show_message_box ( final int caption_resource_id,
	                                                  final int message_resource_id,
	                                                  final int icon_resource_id
	                                                )
	{
		final Context context = this;
		
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				new message_box ( context,
				                  caption_resource_id,
				                  message_resource_id,
				                  icon_resource_id
				);
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl set_message_box ( final display_message dialog,
	                                                 final String data
	                                               )
	{
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.setMessage ( data );
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	public final frame_window_impl set_message_box ( final display_message dialog,
	                                                 final int resource_id
	                                               )
	{
		this.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				dialog.setMessage ( resource_id );
			}
		} );
		return this;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------//
	protected frame_window_impl request_camera_permisstion ( )
	{
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
		{
			try
			{
				if ( this.checkSelfPermission ( android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED )
				{
					this.requestPermissions ( new String[]{ android.Manifest.permission.CAMERA },
					                          0
					                        );
				}
			}
			catch ( Exception e )
			{
				Log.e ( TAG, e.getMessage ( ) );
			}
		}
		return this;
	}
}
