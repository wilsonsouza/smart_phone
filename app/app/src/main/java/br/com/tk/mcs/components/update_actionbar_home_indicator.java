/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import br.com.tk.mcs.generic.config_display_metrics;

/**
 * Created by wilsonsouza on 16/02/17.
 */

public class update_actionbar_home_indicator extends imageview_impl
{
	private final int m_base = 0x10;
	
	//--------------------------------------------------------------------------------------------//
	public update_actionbar_home_indicator ( AppCompatActivity pWnd,
	                                         int nResourceID
	                                       )
	{
		super ( pWnd,
		        config_display_metrics.HomeIndicator,
		        nResourceID,
		        false
		      );
		try
		{
			ActionBar pAction = pWnd.getSupportActionBar ( );
			Point point = new Point ( config_display_metrics.HomeIndicator.x - m_base,
			                          this.Params.get_actionbar_height ( ) - m_base
			);
			
			this.resize ( this.get_handle ( ),
			              point
			            );
			pAction.setDisplayOptions ( ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO );
			pAction.setIcon ( this.to_drawable ( ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}
}
