/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class edittext_completeview_impl extends linear_horizontal
{
	public final static int                  DEFAULT               = - 1;
	private static      ArrayList<String>    m_QueueOfCompleteView = new ArrayList<> ( );
	private             AutoCompleteTextView m_data                = null;
	private             textview_impl        m_text                = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_completeview_impl ( Context context,
	                                    int nID,
	                                    int nSize,
	                                    boolean bEnabled,
	                                    int nFontSize,
	                                    int nWidth,
	                                    boolean bBorder
	                                  )
	{
		super ( context );
		this.m_data = new AutoCompleteTextView ( this.getContext ( ) );
		this.m_text = new textview_impl ( this.getContext ( ),
		                                  nID,
		                                  textview_impl.DEFAULT,
		                                  textview_impl.TRACOLOR,
		                                  false
		);
		this.m_text.setGravity ( Gravity.LEFT );
		this.m_data.setFilters ( new InputFilter[]{ new InputFilter.LengthFilter ( nSize ) } );
		this.set_padding ( config_display_metrics.Padding )
		    .set_font_size ( nFontSize )
		    .set_enabled ( bEnabled );
		this.set_data_width ( nWidth )
		    .set_margins ( config_display_metrics.Padding );
		
		if ( nID != DEFAULT )
		{
			this.addView ( this.m_text,
			               this.m_text.Params
			             );
		}
		this.addView ( this.m_data,
		               this.Params
		             );
		this.set_border ( bBorder );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_completeview_impl ( Context context,
	                                    String szLabel,
	                                    int nSize,
	                                    boolean bEnabled,
	                                    int nFontSize,
	                                    int nWidth,
	                                    boolean bBorder
	                                  )
	{
		super ( context );
		this.m_data = new AutoCompleteTextView ( this.getContext ( ) );
		this.m_text = new textview_impl ( this.getContext ( ) ).set_caption ( szLabel )
		                                                       .set_font_size ( textview_impl.DEFAULT )
		                                                       .set_color ( textview_impl.TRACOLOR );
		this.m_text.setGravity ( Gravity.LEFT );
		this.m_data.setFilters ( new InputFilter[]{ new InputFilter.LengthFilter ( nSize ) } );
		this.set_padding ( config_display_metrics.Padding )
		    .set_font_size ( nFontSize )
		    .set_enabled ( bEnabled );
		this.set_data_width ( nWidth )
		    .set_margins ( config_display_metrics.Padding );
		
		if ( ! szLabel.isEmpty ( ) )
		{
			this.addView ( this.m_text,
			               this.m_text.Params
			             );
		}
		
		this.addView ( this.m_data,
		               this.Params
		             );
		this.set_border ( bBorder );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_completeview_impl set_border ( boolean bDraw )
	{
		if ( bDraw )
		{
			borderwidget_impl.builder ( this.m_data );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_data ( )
	{
		return this.m_data.getText ( )
		                  .toString ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_completeview_impl set_margins ( Rect margins )
	{
		super.set_margins ( margins );
		this.m_text.set_margins ( margins );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_completeview_impl set_padding ( Rect padding )
	{
		this.m_data.setPadding ( padding.left,
		                         padding.top,
		                         padding.right,
		                         padding.bottom
		                       );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private edittext_completeview_impl set_font_size ( int nFontSize )
	{
		switch ( nFontSize )
		{
			case DEFAULT:
				font_impl.set_size ( this.m_data,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				font_impl.set_size ( this.m_text.get_handle ( ),
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_completeview_impl set_enabled ( boolean bEnabled )
	{
		this.setEnabled ( bEnabled );
		this.m_data.setEnabled ( bEnabled );
		this.m_text.get_handle ( )
		           .setEnabled ( bEnabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private edittext_completeview_impl set_data_width ( int nWidth )
	{
		switch ( nWidth )
		{
			case - 1:
				nWidth = to_sp ( config_display_metrics.minimum_control_width );
				break;
			default:
				nWidth = to_sp ( nWidth );
				break;
		}
		this.m_data.setWidth ( nWidth );
		this.m_data.setHeight ( this.to_sp ( config_display_metrics.minimum_control_height ) );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void add_queue_of_complete_view ( final String szValue )
	{
	   /* warranty all tag and plate don't duplicate */
		if ( m_QueueOfCompleteView.indexOf ( szValue.toUpperCase ( )
		                                            .trim ( ) ) == - 1 )
		{
			m_QueueOfCompleteView.add ( szValue.toUpperCase ( )
			                                   .trim ( ) );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ArrayList<String> get_queue_of_complete_view ( )
	{
		//
		return m_QueueOfCompleteView;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_adapter ( )
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String> ( getContext ( ),
		                                                          android.R.layout.select_dialog_item,
		                                                          m_QueueOfCompleteView
		);
		{
			this.m_data.setThreshold ( 1 );
			this.m_data.setAdapter ( adapter );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void remove_adapter ( )
	{
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final textview_impl get_text_handle ( )
	{
		//
		return this.m_text;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final AutoCompleteTextView get_handle ( )
	{
		//
		return m_data;
	}
}
