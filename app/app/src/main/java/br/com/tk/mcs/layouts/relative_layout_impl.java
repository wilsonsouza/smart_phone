package br.com.tk.mcs.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.widget.RelativeLayout;

import br.com.tk.mcs.generic.device_screen;

/**
 * Created by wilsonsouza on 12/8/17.
 */

public class relative_layout_impl extends RelativeLayout
{
	public static final String                      TAG    = relative_layout_impl.class.getName ( );
	public static final int                         WRAP   = RelativeLayout.LayoutParams.WRAP_CONTENT;
	public static final int                         MATCH  = RelativeLayout.LayoutParams.MATCH_PARENT;
	public              RelativeLayout.LayoutParams Params = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public relative_layout_impl ( Context context )
	{
		super ( context );
		this.Params = new RelativeLayout.LayoutParams ( WRAP, WRAP );
		this.setLayoutParams ( this.Params );
		this.update ( WRAP, WRAP );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public relative_layout_impl set_fullscreen ( )
	{
		this.Params = new RelativeLayout.LayoutParams ( MATCH, MATCH );
		this.update ( MATCH, MATCH );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public relative_layout_impl set_padding ( @NonNull final Rect padding )
	{
		Rect r = device_screen.rect_to_dp ( padding );
		this.setPadding ( r.left,
		                  r.top,
		                  r.right,
		                  r.bottom
		                );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public LayoutParams builder ( int width,
	                              int height
	                            )
	{
		this.Params = new LayoutParams ( width, height );
		this.update ( width, height );
		return this.Params;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private relative_layout_impl update ( int x,
	                                      int y
	                                    )
	{
		if ( x == MATCH )
		{
			this.Params.width = Resources.getSystem ( )
			                             .getDisplayMetrics ( ).widthPixels;
		}
		
		if ( y == MATCH )
		{
			linear_layout_impl layout = new linear_layout_impl ( getContext ( ) );
			this.Params.height = Resources.getSystem ( )
			                              .getDisplayMetrics ( ).heightPixels - layout.Params.get_actionbar_height ( );
		}
		return this;
	}
}
