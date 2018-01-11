/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 12/01/17.
 */

public class tablewidget_impl extends linear_vertical
{
	public final static    int                  TEXTVIEW        = 0x0010;
	public final static    int                  RADIOBOX        = 0x0020;
	public final static    int                  CHECKBOX        = 0x0040;
	public final static    int                  BITMAP          = 0x0080;
	public final static    int                  BUTTON          = 0x0100;
	public final static    int                  BITMAPBUTTON    = 0x0200;
	public final static    int                  EDITTEXT        = 0x0400;
	public final static    int                  DEFAULT         = - 1;
	protected final static int                  DEFAULT_SIZE    = 0x00be;
	protected              tableheader          m_items_header  = null;
	protected              tablelist            m_lines_items   = null;
	protected              int                  m_selected      = - 1;
	protected              View.OnClickListener m_fn_proc_click = null;
	protected              int                  m_id            = 0;
	protected              Activity             m_handle        = ( Activity ) getContext ( );
	protected              Vector<detailsarray> m_backup_data   = null;
	
	;
	
	//-----------------------------------------------------------------------------------------------------------------//
	/**/
	public tablewidget_impl ( Context pWnd )
	{
		super ( pWnd );
		this.Params.height = 0x100;
		this.set_margins ( new Rect ( 8,
		                              0,
		                              8,
		                              8
		) );
		this.m_items_header = new tableheader ( this.getContext ( ),
		                                        this
		);
		this.m_lines_items = new tablelist ( this.getContext ( ),
		                                     this
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void create ( View.OnClickListener pClick )
	{
		this.m_fn_proc_click = pClick;
		/* create_and_prepare dimension */
		Point point = new Point ( this.m_lines_items.get_contaneir ( ).Params.width - 24,
		                          WRAP
		);
		this.m_lines_items.get_contaneir ( ).Params.set_dimension ( point );
		this.set_border ( true );
		this.invalidate ( true );
		this.m_lines_items.Params.height = this.m_lines_items.get_contaneir ( ).Params.height - 0x10;
		this.m_lines_items.get_handle ( )
		                  .setLayoutParams ( this.m_lines_items.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public tablewidget_impl create_header ( Vector<fielddata> pHeader )
	{
		this.m_items_header.create ( pHeader );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Vector<fielddata> get_header_items ( )
	{
		//
		return this.m_items_header.m_items;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Vector<fielddata> get_row_item ( int nRow )
	{
		//
		return this.m_lines_items.m_lines.get ( nRow ).m_data;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public synchronized void update_rows ( final Vector<List<String>> pQueue )
	{
		m_handle.runOnUiThread ( new TaskUpdateRows ( pQueue ) );
	}
	//-----------------------------------------------------------------------------------------------------------------//
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int get_id ( )
	{
		//
		return this.m_id;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public tablewidget_impl update ( )
	{
		this.setMinimumHeight ( this.Params.height - this.m_items_header.Params.height );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_scrollview_area ( )
	{
		this.m_lines_items.Params.height = this.Params.height + 8;
		this.m_lines_items.setLayoutParams ( this.m_lines_items.Params );
		this.invalidate ( this.m_lines_items,
		                  true
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void clear ( )
	{
		this.m_lines_items.m_lines.clear ( );
		this.m_lines_items.get_contaneir ( )
		                  .removeAllViews ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_data_backup ( final Vector<detailsarray> pData )
	{
		this.m_backup_data = pData;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public detailsarray get_data_backup ( final String szTransactionId )
	{
		for ( int i = 0; i < this.m_backup_data.size ( ); i++ )
		{
			detailsarray p = this.m_backup_data.get ( i );
			
			if ( p.get_transaction_id ( )
			      .trim ( )
			      .equals ( szTransactionId.trim ( ) ) )
			{
				return p;
			}
		}
		
		return null;
	}
	
	/***/
	public static class detailsarray
	{
		private String m_transaction_id     = "";
		private String m_moment             = "";
		private String m_payment_type       = "";
		private String m_payment_means      = "";
		private String m_pan_number         = "";
		private String m_vehicle_class      = "";
		private String m_alias_lane_name    = "";
		private String m_original_lane_name = "";
		
		public detailsarray ( )
		{}
		
		public final String get_transaction_id ( )
		{
			return this.m_transaction_id;
		}
		
		public final detailsarray set_transaction_id ( final String transaction_id )
		{
			this.m_transaction_id = transaction_id;
			return this;
		}
		
		public final String get_moment ( )
		{
			return this.m_moment;
		}
		
		public final detailsarray set_moment ( final String moment )
		{
			this.m_moment = moment;
			return this;
		}
		
		public final String get_payment_type ( )
		{
			return this.m_payment_type;
		}
		
		public final detailsarray set_payment_type ( final String payment_type )
		{
			this.m_payment_type = payment_type;
			return this;
		}
		
		public final String get_payment_means ( )
		{
			return this.m_payment_means;
		}
		
		public final detailsarray set_payment_means ( final String payment_means )
		{
			this.m_payment_means = payment_means;
			return this;
		}
		
		public final String get_pan_number ( )
		{
			return this.m_pan_number;
		}
		
		public final detailsarray set_pan_number ( final String pan_number )
		{
			this.m_pan_number = pan_number;
			return this;
		}
		
		public final String get_vehicle_class ( )
		{
			return this.m_vehicle_class;
		}
		
		public final detailsarray set_vehicle_class ( final String vehicle_class )
		{
			this.m_vehicle_class = vehicle_class;
			return this;
		}
		
		public final String get_alias_lane_name ( )
		{
			return this.m_alias_lane_name;
		}
		
		public final detailsarray set_alias_lane_name ( final String alias_lane_name )
		{
			this.m_alias_lane_name = alias_lane_name;
			return this;
		}
		
		public final String get_original_lane_name ( )
		{
			return this.m_original_lane_name;
		}
		
		public final detailsarray set_original_lane_name ( final String original_lane_name )
		{
			this.m_original_lane_name = original_lane_name;
			return this;
		}
	}
	
	/*
	* class resposible by create_and_prepare header caption of table
	* */
	public static class tableheader extends linear_horizontal
	{
		public final static String            TAG     = tableheader.class.getName ( );
		private             Vector<fielddata> m_items = new Vector<> ( );
		
		//-----------------------------------------------------------------------------------------------------------------//
		public tableheader ( Context pWnd,
		                     tablewidget_impl pOwner
		                   )
		{
			super ( pWnd,
			        null,
			        config_display_metrics.CaptionStyle
			      );
			this.Params = this.build ( MATCH,
			                           WRAP
			                         );
			this.set_margins ( new Rect ( 1,
			                              0,
			                              2,
			                              8
			) );
			this.set_border ( true );
			this.setBackgroundResource ( R.drawable.window_caption_bar );
			pOwner.addView ( this,
			                 this.Params
			               );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final void create ( final Vector<fielddata> pQueue )
		{
			try
			{
				for ( fielddata pField : pQueue )
				{
					textview_impl p = new textview_impl ( getContext ( ) ).set_caption ( pField.get_name ( ) )
					                                                      .set_font_size ( textview_impl.CAPTION )
					                                                      .set_color ( textview_impl.DEFCOLOR )
					                                                      .set_border ( true );
					{
						p.set_padding ( new Rect ( 2, 2, 0, 2 ) )
						 .get_handle ( )
						 .setGravity ( Gravity.LEFT );
						p.get_handle ( )
						 .setSingleLine ( );
						p.setTag ( pField.get_name ( ) );
						p.Params.gravity = Gravity.LEFT;
						pField.set_item ( p );
						/* put in back internal stack*/
						m_items.add ( pField );
						p.Params.width = to_dp ( pField.get_width ( ) );
						p.setLayoutParams ( p.Params );
						addView ( p );
					}
				}
				this.invalidate ( true );
			}
			catch ( Exception e )
			{
				Log.e ( TAG, e.getMessage ( ) );
			}
		}
	}
	
	/*
	* define width and height on fields
	* */
	public class fielddata
	{
		private int       m_width;
		private int       m_height;
		private String    m_name;
		private ViewGroup m_item;
		private int       m_id;
		private int       m_kind;
		
		public fielddata ( )
		{
			this.m_width = DEFAULT_SIZE;
			this.m_height = WRAP;
			this.m_name = "";
			this.m_item = null;
			this.m_id = - 1;
			this.m_kind = TEXTVIEW;
		}
		
		public fielddata ( String szName,
		                   textview_impl pItem
		                 )
		{
			this.m_name = szName;
			this.m_item = pItem;
			this.m_width = DEFAULT_SIZE;
		}
		
		public fielddata ( int nNameID,
		                   int nWidth
		                 )
		{
			this.m_item = null;
			this.m_width = nWidth == DEFAULT ?
			               DEFAULT_SIZE :
			               nWidth;
			this.m_name = getContext ( ).getString ( nNameID );
		}
		
		public fielddata ( int nNameID,
		                   int nWidth,
		                   int nID,
		                   int nKind
		                 )
		{
			this.m_item = null;
			this.m_width = ( nWidth == DEFAULT ?
			                 DEFAULT_SIZE :
			                 nWidth );
			this.m_name = getContext ( ).getString ( nNameID );
			this.m_id = nID;
			this.m_kind = ( nKind == DEFAULT ?
			                TEXTVIEW :
			                nKind );
		}
		
		public fielddata ( String szName,
		                   int nWidth,
		                   int nID,
		                   int nKind
		                 )
		{
			this.m_item = null;
			this.m_width = ( nWidth == DEFAULT ?
			                 DEFAULT_SIZE :
			                 nWidth );
			this.m_name = szName;
			this.m_id = nID;
			this.m_kind = ( nKind == DEFAULT ?
			                TEXTVIEW :
			                nKind );
		}
		
		public final int get_width ( )
		{
			return this.m_width;
		}
		
		public final int get_height ( )
		{
			return this.m_height;
		}
		
		public final String get_name ( )
		{
			return this.m_name;
		}
		
		public final ViewGroup get_item ( )
		{
			return this.m_item;
		}
		
		public final fielddata set_item ( final ViewGroup items )
		{
			this.m_item = items;
			return this;
		}
		
		public final int get_id ( )
		{
			return this.m_id;
		}
		
		public final fielddata set_id ( final int id )
		{
			this.m_id = id;
			return this;
		}
		
		public final int get_kind ( )
		{
			return this.m_kind;
		}
	}
	
	/*
	* class responsible by display items of table
	* */
	public class tableitem extends linear_horizontal
	{
		protected Vector<fielddata> m_data = new Vector<> ( );
		protected int               nH     = config_display_metrics.get_attribute_size_horizontal ( this.getContext ( ),
		                                                                                            android.R.attr.actionBarSize
		                                                                                          );
		
		//-----------------------------------------------------------------------------------------------------------------//
		public tableitem ( Context pWnd )
		{
			super ( pWnd );
			this.Params = this.build ( m_items_header.Params.width - 0x20,
			                           nH - 0xA
			                         );
			this.set_margins ( new Rect ( 0,
			                              2,
			                              0,
			                              2
			) );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public tableitem ( Context pWnd,
		                   Vector<fielddata> pItems
		                 )
		{
			super ( pWnd );
			this.Params = this.build ( m_items_header.Params.width - 0x20,
			                           nH - 0xA
			                         );
			this.create ( pItems );
			this.set_margins ( new Rect ( 0,
			                              2,
			                              0,
			                              2
			) );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final void create ( final Vector<fielddata> pItems )
		{
			try
			{
				Point area = new Point ( config_display_metrics.ButtonImageIcon.x - 8,
				                         config_display_metrics.ButtonImageIcon.y - 8
				);
				this.setLayoutParams ( this.Params );
				this.m_data.clear ( );
				
				for ( fielddata f : pItems )
				{
					Context context = this.getContext ( );
					
					switch ( f.get_kind ( ) )
					{
						case TEXTVIEW:
						{
							textview_impl p = new textview_impl ( context );
							
							p.set_caption ( f.get_name ( ) )
							 .set_font_size ( textview_impl.DEFAULT )
							 .set_color ( textview_impl.TRACOLOR );
							
							p.Params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
							p.set_padding ( new Rect ( 2,
							                           2,
							                           0,
							                           2
							) );
							p.get_handle ( )
							 .setSingleLine ( true );
							p.setTag ( f.get_name ( ) );
							f.set_item ( p );
							
							p.Params.width = to_dp ( f.get_width ( ) );
							p.Params.height = to_dp ( area.y );
							p.setLayoutParams ( p.Params );
							p.get_handle ( )
							 .setGravity ( p.Params.gravity );
							
							this.addView ( f.get_item ( ) );
							this.m_data.add ( f );
							break;
						}
						case BITMAP:
						{
							imageview_impl p = new imageview_impl ( context,
							                                        area,
							                                        R.drawable.ic_tbl_photo_image,
							                                        false
							);
							
							p.Params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
							p.set_padding ( new Rect ( 2,
							                           2,
							                           0,
							                           2
							) );
							p.setTag ( f.get_name ( ) );
							f.set_item ( p );
							
							p.Params.width = to_dp ( f.get_width ( ) );
							p.setLayoutParams ( p.Params );
							this.addView ( f.get_item ( ) );
							this.m_data.add ( f );
							break;
						}
					}
				}
				
				this.invalidate ( true );
				pItems.clear ( );
			}
			catch ( Exception e )
			{
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final boolean locate ( final Object value )
		{
			for ( fielddata f : this.m_data )
			{
				if ( f.get_name ( )
				      .indexOf ( ( String ) value ) != 0 )
				{
					return true;
				}
			}
			return false;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final Vector<fielddata> get_items ( )
		{
			return this.m_data;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	/* class to display data on screen */
	class tablelist extends scrollview_impl implements View.OnClickListener
	{
		private Vector<tableitem> m_lines = new Vector<> ( );
		
		//-----------------------------------------------------------------------------------------------------------------//
		public tablelist ( Context pWnd,
		                   tablewidget_impl pOwner
		                 )
		{
			super ( pWnd,
			        false,
			        Color.WHITE,
			        null,
			        null
			      );
			this.get_contaneir ( )
			    .set_padding ( new Rect ( 8,
			                              0,
			                              0,
			                              8
			    ) );
			this.Params = this.build ( WRAP,
			                           WRAP
			                         );
			pOwner.addView ( this,
			                 this.Params
			               );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final void create ( final Vector<tableitem> pLines )
		{
			try
			{
				m_id = pLines.size ( ) - 1;
				this.m_lines.clear ( );
				this.get_contaneir ( )
				    .removeAllViews ( );
				
				for ( tableitem pRow : pLines )
				{
					pRow.setId ( m_id );
					pRow.set_border ( true );
					pRow.setClickable ( true );
					pRow.setOnClickListener ( this );
					
					this.get_contaneir ( )
					    .addView ( pRow,
					               0,
					               pRow.Params
					             );
					this.m_lines.add ( pRow );
					m_id--;
				}
				
				this.invalidate ( true );
			}
			catch ( Exception e )
			{
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final boolean locate ( final String value )
		{
			for ( tableitem item : this.m_lines )
			{
				if ( item.locate ( value ) )
				{
					return true;
				}
			}
			return false;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void onClick ( View v )
		{
			int nKey = v.getId ( );
			
			if ( m_selected != nKey )
			{
				( ( tableitem ) get_contaneir ( ).getChildAt ( nKey ) ).set_border ( Color.BLUE );
				
				if ( m_selected != - 1 )
				{
					( ( tableitem ) get_contaneir ( ).getChildAt ( m_selected ) ).set_border ( Color.WHITE );
				}
				
				m_selected = nKey;
			}
			else
			{
				m_selected = nKey;
			}
			
			if ( m_fn_proc_click != null )
			{
				m_fn_proc_click.onClick ( v );
				this.postDelayed ( new Runnable ( )
				                   {
					                   @Override
					                   public void run ( )
					                   {
						                   ( ( tableitem ) get_contaneir ( ).getChildAt ( m_selected ) ).set_border ( Color.WHITE );
					                   }
				                   },
				                   0x400
				                 );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	//
	//
	//
	//
	// Task to process items on table
	//-----------------------------------------------------------------------------------------------------------------//
	class TaskUpdateRows implements Runnable
	{
		private final Vector<List<String>> m_queue;
		
		//-----------------------------------------------------------------------------------------------------------------//
		public TaskUpdateRows ( final Vector<List<String>> pQueue )
		{
			this.m_queue = pQueue;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void run ( )
		{
			try
			{
				final Vector<fielddata> pRow   = new Vector<> ( );
				final Vector<tableitem> pItems = new Vector<> ( );
				//
				for ( List<String> items : this.m_queue )
				{
					int i = 0;
					
					for ( String str : items )
					{
						fielddata f = ( fielddata ) m_items_header.m_items.get ( i++ );
						pRow.add ( new fielddata ( str,
						                           f.get_width ( ),
						                           f.get_id ( ),
						                           f.get_kind ( )
						) );
					}
					
					pItems.add ( new tableitem ( getContext ( ),
					                             pRow
					) );
				}
				
				if ( pItems.size ( ) > 0 )
				{
					m_lines_items.create ( pItems );
				}
			}
			catch ( Exception e )
			{
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
