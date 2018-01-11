package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 12/5/17.
 */

public class edittext_base extends linear_horizontal implements TextWatcher
{
	public final static String   TAG      = edittext_base.class.getName ( );
	private             EditText m_handle = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base ( Context context )
	{
		super ( context,
		        null,
		        0
		      );
		this.Params = this.build ( WRAP,
		                           WRAP
		                         );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base builder ( int max_edit_size,
	                               boolean is_border,
	                               int max_width
	                             )
	{
		this.m_handle = new EditText ( this.getContext ( ) );
		
		this.m_handle.setFocusable ( true );
		this.m_handle.setBackgroundColor ( Color.TRANSPARENT );
		this.m_handle.setTextColor ( Color.BLUE );
		this.Params.gravity = Gravity.CENTER;
		
		this.set_length_filter ( max_edit_size )
		    .set_border ( is_border );
		this.set_margins ( config_display_metrics.EditRect );
		
		this.set_data_width ( max_width );
		this.addView ( this.m_handle, this.Params );
		this.invalidate ( true );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base set_length_filter ( int length_filter )
	{
		this.m_handle.setFilters ( new InputFilter[]{ new InputFilter.LengthFilter ( length_filter ) } );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base set_padding ( @NonNull Rect padding )
	{
		Rect r = this.to_rect ( padding );
		this.m_handle.setPadding ( r.left,
		                           r.top,
		                           r.right,
		                           r.bottom
		                         );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public EditText get_handle ( )
	{
		/*
			get EditText object handle
		 */
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base set_input_type ( int input_type )
	{
		this.m_handle.setInputType ( input_type );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_base set_data_width ( int max_data_width )
	{
		if ( max_data_width == NO_ID )
		{
			max_data_width = config_display_metrics.minimum_control_width;
		}
		
		//		Point p = device_screen.point_to_sp ( data_width,
		//		                                      0
		//		                                    );
		//		this.m_handle.setMaxWidth ( p.x );
		//		this.m_handle.setMinWidth ( p.x / 2 );
		//		this.m_handle.setWidth ( p.x );
		//		this.m_handle.setMinHeight ( 0x20 );
		//		this.m_handle.setMinimumHeight ( 0x20 );
		//		this.m_handle.setHeight ( 0x20 );
		
		this.m_handle
			.setMinimumWidth ( this.to_sp ( max_data_width ) );
		this.m_handle
			.setMinimumHeight ( this.to_sp ( config_display_metrics.minimum_control_height ) );
		return this;
		
	}
	
	/**
	 * This method is called to notify you that, within <code>s</code>,
	 * the <code>count</code> characters beginning at <code>start</code>
	 * are about to be replaced by new text with length <code>after</code>.
	 * It is an error to attempt to make changes to <code>s</code> from
	 * this callback.
	 *
	 * @param s
	 * @param start
	 * @param count
	 * @param after
	 */
	@Override
	public void beforeTextChanged ( CharSequence s,
	                                int start,
	                                int count,
	                                int after
	                              )
	{
	
	}
	
	/**
	 * This method is called to notify you that, within <code>s</code>,
	 * the <code>count</code> characters beginning at <code>start</code>
	 * have just replaced old text that had length <code>before</code>.
	 * It is an error to attempt to make changes to <code>s</code> from
	 * this callback.
	 *
	 * @param s
	 * @param start
	 * @param before
	 * @param count
	 */
	@Override
	public void onTextChanged ( CharSequence s,
	                            int start,
	                            int before,
	                            int count
	                          )
	{
	
	}
	
	/**
	 * This method is called to notify you that, somewhere within
	 * <code>s</code>, the text has been changed.
	 * It is legitimate to make further changes to <code>s</code> from
	 * this callback, but be careful not to get yourself into an infinite
	 * loop, because any changes you make will cause this method to be
	 * called again recursively.
	 * (You are not told where the change took place because other
	 * afterTextChanged() methods may already have made other changes
	 * and invalidated the offsets.  But if you need to know here,
	 * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
	 * to mark your place and then look up from here where the span
	 * ended up.
	 *
	 * @param s
	 */
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
}
