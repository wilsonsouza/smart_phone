/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.activitys_operations.FrameReleaseVehicle;
import br.com.tk.mcs.components.horizontal_scrollview_impl;
import br.com.tk.mcs.components.lanewidget_impl;
import br.com.tk.mcs.dialogs_operations.DialogChangeGateState;
import br.com.tk.mcs.dialogs_operations.DialogChangeMarquiseState;
import br.com.tk.mcs.dialogs_operations.DialogReleaseVehicle;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dispatch.vehicle_parameters;
import br.com.tk.mcs.drivers.bluetooth_printer_controller;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.main.TrackManager;
import br.com.tk.mcs.tools.detect_device_type;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class ProcessLanesView extends horizontal_scrollview_impl implements View.OnClickListener
{
   public final String TAG = ProcessLanesView.class.getName( );
   public Vector<Lane> m_items = new Vector<>( );
   private int m_current_lane = -1;
   private display_message m_warning_message = null;
   private ProcessManager m_manager = null;
   private TrackManager m_wnd = null;
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessLanesView( Activity context )
   {
      super( context,
             false,
             Color.BLACK,
             new Rect( 0,
                       4,
                       0,
                       4
             ),
             null
      );
      this.m_wnd = (TrackManager) context;
      this.Params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
      this.Params = this.build( MATCH,
                                WRAP );
      this.m_wnd.get_layout_handle( )
                .addView( this,
                          this.Params );
      this.set_border( true );
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessLanesView create( ArrayList<Lane> pLanes )
   {
      int nID = 0;
      /* process all lanes */
      for (Lane p : pLanes)
      {
         lanewidget_impl pView = new lanewidget_impl( this.getContext( ) );
         {
            pView.create( p,
                          nID,
                          this );
            pView.setId( nID );
            this.get_contaneir_handle( )
                .addView( pView );
            this.m_items.add( p );
            nID++;
         }
      }
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final void set_manager_pointer( final ProcessManager pManager )
   {
      //
      this.m_manager = pManager;
   }
   
   //-----------------------------------------------------------------------------------------------------------//
   @Override
   public void onClick( View pView )
   {
      try
      {
         String bitmap = m_manager.get_verify_lane_state_handle( )
                                  .get_current_vechicle_image( );
         lanewidget_impl lane_widget = (lanewidget_impl) this.get_contaneir_handle( )
                                                             .getChildAt( pView.getId( ) );
         Lane pLane = lane_widget.get_lane_handle( );
         String message = String.format( getContext( ).getString( R.string.ids_syncronize ),
                                         pLane.get_name( ) );
         
         if(company_setup.is_debug)
         {
            pView = lane_widget.get_vehicle_handle();
         }
         
         if (pView == lane_widget.get_caption_handle( )
                                 .get_handle( ))
         {
            if (m_current_lane != pView.getId( ))
            {
               if (m_current_lane != -1)
               {
                  final lanewidget_impl p = (lanewidget_impl) get_contaneir_handle( ).findViewById( m_current_lane );
                  
                  if (p.get_caption_handle( )
                       .is_checked( ))
                  {
                     p.get_caption_handle( )
                      .set_checked( false );
                  }
                  
                  m_current_lane = pView.getId( );
               }
               else
               {
                  m_current_lane = pView.getId( );
               }
               /* enable select lane */
               lane_widget.get_caption_handle( )
                          .set_checked( true );
               m_manager.get_verify_lane_state_handle( )
                        .set_next( lane_widget.get_caption_handle( )
                                              .getId( ) );
					/* display message */
               m_warning_message = new display_message( getContext( ),
                                                        R.string.ids_wait,
                                                        message );
               m_warning_message.show( );
            }
         }
         else if (pView == lane_widget.get_vehicle_handle( ))
         {
            if (detect_device_type.is_smart_phone( ))
            {
               Intent vehicle = new Intent( this.m_wnd,
                                            FrameReleaseVehicle.class );
               bluetooth_printer_controller printer_handle = this.m_manager.get_printer_manager_handle( );
               Bundle b = new Bundle(  );
               vehicle_parameters param = new vehicle_parameters( pLane, bitmap );
               
               b.putParcelable( FrameReleaseVehicle.IDS_PARAMETERS, param );
               vehicle.putExtras( b);
               this.m_wnd.startActivity( vehicle );
            }
         }
         else if (pView == lane_widget.get_barrer_handle( ))
         {
            new DialogChangeGateState( this.getContext( ),
                                       pLane
            );
         }
         else if (pView == lane_widget.get_semaphore_handle( ))
         {
            new DialogChangeMarquiseState( this.getContext( ),
                                           pLane
            );
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         Log.e( TAG,
                e.getMessage( ) );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final display_message get_warning_message( )
   {
      //
      return this.m_warning_message;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLanesView set_warning_message( final display_message dm )
   {
      //
      this.m_warning_message = dm;
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final Vector<Lane> get_lane_items( )
   {
      //
      return this.m_items;
   }
   
}
