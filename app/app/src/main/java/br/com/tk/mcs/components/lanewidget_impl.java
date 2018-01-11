/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description: vcs revision 925
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import java.util.Locale;

import br.com.tk.mcs.R;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.State.BarreraState;
import br.com.tk.mcs.lane.State.LaneState;
import br.com.tk.mcs.lane.State.TrafficLightState;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 21/12/16.
 */

public class lanewidget_impl extends linear_vertical
{
	public final static String           TAG         = lanewidget_impl.class.getName ( );
	protected           Point            m_imagesize = config_display_metrics.LaneIcon;
	protected           Rect             m_margins   = new Rect ( 8,
	                                                              8,
	                                                              8,
	                                                              8
	);
	protected           long             m_time      = System.currentTimeMillis ( );
	private             radiobutton_impl Caption     = null;
	private             imageview_impl   Semaphore   = null;
	private             imageview_impl   Vehicle     = null;
	private             imageview_impl   Traffic     = null;
	private             imageview_impl   Barrer      = null;
	private             Lane             LaneHandle  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public lanewidget_impl ( Context pWnd )
	{
		super ( pWnd );
		/* set Main layout parameters */
		//this.m_params.width = 110;
		this.set_margins ( new Rect ( 8,
		                              8,
		                              8,
		                              8
		) );
		
		//this.set_padding(this.m_imagemargins);
		/* set direction of layout to vertical */
		//this.setGravity(Gravity.CENTER_VERTICAL|Gravity.TOP);
		/* create_and_prepare components of lane box */
		this.Vehicle = new imageview_impl ( this.getContext ( ),
		                                    this.m_imagesize,
		                                    R.drawable.area_blank,
		                                    false
		).set_margins ( m_margins );
		this.Vehicle.set_color ( Color.WHITE );
		this.Vehicle.setGravity ( Gravity.CENTER );
		this.Barrer = new imageview_impl ( this.getContext ( ),
		                                   this.m_imagesize,
		                                   R.drawable.ln_barrer_blank_hd,
		                                   false
		).set_margins ( m_margins );
		this.Barrer.set_color ( Color.WHITE );
		this.Barrer.setGravity ( Gravity.CENTER );
		this.Semaphore = new imageview_impl ( this.getContext ( ),
		                                      this.m_imagesize,
		                                      R.drawable.traffic_blank,
		                                      false
		).set_margins ( m_margins );
		
		this.Semaphore.set_color ( Color.WHITE );
		this.Semaphore.setGravity ( Gravity.CENTER );
		this.Traffic = new imageview_impl ( this.getContext ( ),
		                                    this.m_imagesize,
		                                    R.drawable.light_blank,
		                                    false
		).set_margins ( m_margins );
		
		this.Traffic.set_color ( Color.WHITE );
		this.Traffic.setGravity ( Gravity.CENTER );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void create ( final Lane pLane,
	                     final int nID,
	                     View.OnClickListener pClick
	                   )
	{
      /* put caption */
		linear_horizontal pCaption = new linear_horizontal ( this.getContext ( ) );
		{
			this.Caption = new radiobutton_impl ( this.getContext ( ),
			                                      pLane.get_name ( ),
			                                      true,
			                                      false,
			                                      radiobutton_impl.CAPTION
			);
			this.Caption.setId ( nID );
			this.Caption.set_padding ( new Rect ( 1,
			                                      1,
			                                      1,
			                                      1
			) );
			this.Caption.Params.gravity = Gravity.TOP;
			this.Caption.setBackgroundColor ( Color.GRAY );
			pCaption.set_border ( Color.GRAY );
			pCaption.addView ( this.Caption,
			                   this.Caption.Params
			                 );
		}
      /* put semaphore */
		linear_horizontal pHeader = new linear_horizontal ( this.getContext ( ) );
		{
			this.Semaphore.get_handle ( )
			              .setId ( nID );
			pHeader.addView ( this.Semaphore,
			                  this.Semaphore.Params
			                );

         /* put vehicle */
			//this.Vehicle.Params.gravity = Gravity.RIGHT | Gravity.TOP;
			this.Vehicle.get_handle ( )
			            .setId ( nID );
			pHeader.addView ( this.Vehicle,
			                  this.Vehicle.Params
			                );
		}
      /* put traffic */
		linear_horizontal pFoot = new linear_horizontal ( this.getContext ( ) );
		{
			//this.Traffic.Params.gravity = Gravity.LEFT | Gravity.TOP;
			this.Traffic.get_handle ( )
			            .setId ( nID );
			pFoot.addView ( this.Traffic,
			                this.Traffic.Params
			              );

         /* put barrier */
			//this.Barrer.Params.gravity = Gravity.RIGHT | Gravity.TOP;
			this.Barrer.get_handle ( )
			           .setId ( nID );
			pFoot.addView ( this.Barrer,
			                this.Barrer.Params
			              );
		}

      /* set lane */
		this.LaneHandle = pLane;
		this.setId ( pLane.get_id ( ) );

      /* put all images in contaner*/
		this.addView ( pCaption,
		               pCaption.Params
		             );
		this.addView ( pHeader,
		               pHeader.Params
		             );
		this.addView ( pFoot,
		               pFoot.Params
		             );

      /* disable all images */
		this.Semaphore.setEnabled ( false );
		this.Vehicle.setEnabled ( false );
		this.Barrer.setEnabled ( false );
		this.Traffic.setEnabled ( false );

      /* set listener */
		this.Caption.setOnClickListener ( pClick );
		this.Vehicle.get_handle ( )
		            .setOnClickListener ( pClick );
		this.Barrer.get_handle ( )
		           .setOnClickListener ( pClick );
		this.Semaphore.get_handle ( )
		              .setOnClickListener ( pClick );
		this.Traffic.get_handle ( )
		            .setOnClickListener ( pClick );

      /* recalc */
		this.invalidate ( true );
		this.Caption.Params.width = this.getWidth ( );

      /* draw border */
		this.set_border ( true );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void enable_semaphore ( LaneState state,
	                               boolean bOperator,
	                               final int nSemaphoreState
	                             )
	{
		final int nID = this.Semaphore.getId ( );
		
		this.Semaphore.setEnabled ( state == LaneState.Opened && bOperator );
		
		switch ( nSemaphoreState )
		{
			case - 1:
				if ( nID != R.drawable.traffic_black )
				{
					this.Semaphore.set_image ( R.drawable.traffic_black );
				}
				return;
			case 1:
				if ( nID != R.drawable.traffic_green )
				{
					this.Semaphore.set_image ( R.drawable.traffic_green );
				}
				return;
			case 2:
				if ( nID != R.drawable.traffic_red )
				{
					this.Semaphore.set_image ( R.drawable.traffic_red );
				}
				return;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_barrer ( BarreraState bs )
	{
		if ( bs == BarreraState.SensorON )
		{
			this.Barrer.set_image ( R.drawable.ln_barrer_up_hd );
		}
		else if ( bs == BarreraState.SensorOFF )
		{
			this.Barrer.set_image ( R.drawable.ln_barrer_down_hd );
		}
		else if ( bs == BarreraState.SensorUnknown )
		{
			this.Barrer.set_image ( R.drawable.ln_barrer_blank_hd );
		}
		else
		{
			this.Barrer.set_image ( R.drawable.ln_barrer_blank_hd );
		}
		
		this.LaneHandle.set_barrera_state ( bs );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_light_entry ( TrafficLightState ts )
	{
		if ( ts == TrafficLightState.LightGreen )
		{
			this.Traffic.set_image ( R.drawable.light_green );
		}
		else if ( ts == TrafficLightState.LightRed )
		{
			this.Traffic.set_image ( R.drawable.light_red );
		}
		else if ( ts == TrafficLightState.LightOff )
		{
			this.Traffic.set_image ( R.drawable.light_black );
		}
		else
		{
			this.Traffic.set_image ( R.drawable.light_blank );
		}
		
		this.LaneHandle.set_marquise_state ( ts );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_light_exit ( TrafficLightState ts )
	{
		if ( ts == TrafficLightState.LightGreen )
		{
			this.Traffic.set_image ( R.drawable.light_green );
		}
		else if ( ts == TrafficLightState.LightRed )
		{
			this.Traffic.set_image ( R.drawable.light_red );
		}
		else if ( ts == TrafficLightState.LightOff )
		{
			this.Traffic.set_image ( R.drawable.light_black );
		}
		else
		{
			this.Traffic.set_image ( R.drawable.light_blank );
		}
		
		this.LaneHandle.set_passage_state ( ts );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_vehicle ( LaneState state,
	                          int nVehicles,
	                          int nStopped,
	                          boolean bOperator
	                        )
	{
      /* verify if have vehicle stopped on the lane */
		boolean _state = ( state != this.get_lane_handle ( )
		                                .get_state ( ) );
		boolean _vehicles = ( nVehicles != this.get_lane_handle ( )
		                                       .get_total_vehicles ( ) );
		boolean bSuccess = ( _state || _vehicles || nStopped != this.LaneHandle.get_vehicle_stopped ( ) );
		
		if ( company_setup.is_debug )
		{
			String fmt = String.format ( Locale.FRANCE,
			                             "Name %s, State %s, Vehicles %d, Stopped %d BVehicle %s",
			                             this.LaneHandle.get_name ( ),
			                             state.toString ( ),
			                             nVehicles,
			                             nStopped,
			                             Boolean.toString ( this.Vehicle.isEnabled ( ) )
			                           );
			String fmt1 = String.format ( Locale.FRANCE,
			                              "Name %s, State %s, Vehicles %d, Stopped %d Success %s",
			                              this.LaneHandle.get_name ( ),
			                              this.LaneHandle.get_state ( )
			                                             .toString ( ),
			                              this.LaneHandle.get_total_vehicles ( ),
			                              this.LaneHandle.get_vehicle_stopped ( ),
			                              Boolean.toString ( bSuccess )
			                            );
			Log.e ( TAG,
			        fmt
			      );
			Log.e ( TAG,
			        fmt1
			      );
		}
		//
		if ( bSuccess )
		{
         /* disable or enable vehicle image */
			boolean bEnabled = ( state == LaneState.Opened && bOperator && nStopped == 1 );
			this.Vehicle.setEnabled ( bEnabled );
			//
			switch ( nVehicles )
			{
				case 1:
					this.Vehicle.set_image ( R.drawable.ln_vehicle1_hd );
					break;
				case 2:
					this.Vehicle.set_image ( R.drawable.ln_vehicle2_hd );
					break;
				case 3:
					this.Vehicle.set_image ( R.drawable.ln_vehicle3_hd );
					break;
				case 4:
					this.Vehicle.set_image ( R.drawable.ln_vehicle4_hd );
					break;
				default:
					this.Vehicle.set_image ( R.drawable.area_blank );
					break;
			}

         /* has vehicle stopped on the lane */
			if ( nStopped > 0 )
			{
				Long value = ( System.currentTimeMillis ( ) - this.m_time ) / 1000;
				int  nClr  = Color.WHITE;
				
				if ( value > 3 )
				{
					nClr = Color.RED;
					this.m_time = System.currentTimeMillis ( );
				}
				
				this.Vehicle.get_handle ( )
				            .setBackgroundColor ( nClr );
			}
			
			this.LaneHandle.set_total_vehicles ( nVehicles );
			this.LaneHandle.set_vehicle_stopped ( nStopped );
			this.Vehicle.get_handle ( )
			            .setBackgroundColor ( Color.WHITE );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_semaphore_handle ( )
	{
	   //
		return this.Semaphore;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_vehicle_handle ( )
	{
	   //
		return this.Vehicle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_traffic_handle ( )
	{
	   //
		return this.Traffic;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_barrer_handle ( )
	{
	   //
		return this.Barrer;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl get_caption_handle ( )
	{
	   //
		return this.Caption;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Lane get_lane_handle ( )
	{
	   //
		return this.LaneHandle;
	}
   //-----------------------------------------------------------------------------------------------------------------//
   public void call_window()
   {
   
   }
}
