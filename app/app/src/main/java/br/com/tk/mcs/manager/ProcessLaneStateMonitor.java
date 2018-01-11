/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.annotation.TargetApi;

import br.com.tk.mcs.components.lanewidget_impl;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.lane.State.LaneState;
import br.com.tk.mcs.main.TrackManager;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class ProcessLaneStateMonitor extends ProcessLaneState
{
   public final static String TAG = ProcessLaneStateMonitor.class.getName( );
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessLaneStateMonitor( final String szUser,
                                   final lanewidget_impl pView,
                                   final TrackManager pWnd
   )
   {
      super( szUser,
             pWnd
      );
      super.m_pView = pView;
      super.m_pLane = super.m_pView.get_lane_handle( );
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void run( )
   {
      while (m_bWhile)
      {
         try
         {
            m_pLong = m_pView.get_lane_handle( )
                             .get_operations( )
                             .get_long_status( );
            set_state( false );
            
            this.m_pView.postDelayed( new Runnable( )
                                      {
                                         @Override
                                         public void run( )
                                         {
                                            verify_sensors( );
                                            update_state_of_operator( );
                                         }
                                      },
                                      0x64
            );
            /* suspend thread by 100ms and 100ns */
            Thread.sleep( 0xFF );
         }
         catch (Exception e)
         {
            //Log.e(this.getClass().getName(), e.getMessage());
            e.printStackTrace( );
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   @TargetApi( 16 )
   protected void verify_sensors( )
   {
      try
      {
         if (company_setup.is_debug)
         {
            this.m_pView.call_window();
         }
         
         /* verify if have vehicle stopped on the lane */
         this.m_pView.set_vehicle( this.m_state,
                                   this.m_nTotalVehicles,
                                   this.m_nStoppedVehicle,
                                   this.m_bOperator
         );
         this.m_pView.enable_semaphore( this.m_state,
                                        this.m_bOperator,
                                        this.m_nSemaphoreOfMarquise
         );
         
         if (this.m_LightEntry != this.m_pLane.get_marquise_state( ))
         {
            this.m_pView.set_light_entry( this.m_LightEntry );
         }
         
         if (this.m_LightExit != this.m_pLane.get_passage_state( ))
         {
            this.m_pView.set_light_exit( this.m_LightExit );
         }
         
         if (this.m_BarreraState != this.m_pLane.get_barrera_state( ))
         {
            this.m_pView.set_barrer( this.m_BarreraState );
         }
      }
      catch (Exception e)
      {
         //Log.e(this.getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   protected void update_state_of_operator( )
   {
      final LaneState nState = this.m_pView.get_lane_handle( )
                                           .get_state( );
      
      if (this.m_state != nState || this.m_bOperator)
      {
         this.m_pView.get_caption_handle( )
                     .get_handle( )
                     .setBackgroundColor( LaneState.get_color( this.m_state ) );
         this.m_pView.get_lane_handle( )
                     .set_state( this.m_state );
      }
   }
}
