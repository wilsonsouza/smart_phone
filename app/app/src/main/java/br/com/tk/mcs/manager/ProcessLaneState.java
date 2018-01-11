/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import br.com.tk.mcs.adapter.lane_manager_base;
import br.com.tk.mcs.components.lanewidget_impl;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.State.BarreraState;
import br.com.tk.mcs.lane.State.LaneState;
import br.com.tk.mcs.lane.State.TrafficLightState;
import br.com.tk.mcs.main.TrackManager;
import br.com.tk.mcs.remote.response.GetLongStatusResponse;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class ProcessLaneState implements Runnable
{
   public final static String TAG = ProcessLaneState.class.getName( );
   protected final int AVI_LANE = 2;
   protected LaneState m_state = LaneState.NoSync;
   protected String m_CurrentVechicleImage = null;
   protected int m_Current = 0;
   protected int m_Selected = -1;
   protected int m_Next = 0;
   protected boolean m_bChangeOperator = false;
   protected Lane m_pLane = null;
   protected int m_nTotalVehicles = 0;
   protected int m_nStoppedVehicle = 0;
   protected BarreraState m_BarreraState = BarreraState.SensorUnknown;
   protected TrafficLightState m_LightEntry = TrafficLightState.LightUnkown;
   protected TrafficLightState m_LightExit = TrafficLightState.LightUnkown;
   protected String m_szOperator = "";
   protected boolean m_bOperator = false;
   protected lanewidget_impl m_pView = null;
   protected boolean m_bWhile = true;
   protected GetLongStatusResponse m_pLong = null;
   protected long m_start = System.currentTimeMillis( );
   protected ProcessManager m_manager = null;
   protected TrackManager m_pWnd = null;
   protected int m_nLaneMode = 2;
   protected int m_nSemaphoreOfMarquise = 0;
   protected boolean m_isdebug = company_setup.is_debug;
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessLaneState( String operator,
                            TrackManager context
   )
   {
      this.m_szOperator = operator;
      this.m_bOperator = this.m_szOperator.equals( operator );
      //this.setName(this.getClass().getName());
      //this.setPriority(Thread.NORM_PRIORITY);
      this.m_pWnd = context;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public void set_buildermanager_pointer( final ProcessManager manager )
   {
      //
      this.m_manager = manager;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void run( )
   {
      final ProcessLanesView lanes_view = this.m_manager.get_builder_lanes_view_handle( );
      final int nLanesMax = lanes_view.get_contaneir_handle( )
                                      .getChildCount( );
      
      while (this.m_bWhile && nLanesMax > 0)
      {
         try
         {
            m_pView = (lanewidget_impl) lanes_view.get_contaneir_handle( )
                                                  .findViewById( m_Current );
            m_pLane = m_pView.get_lane_handle( );
            m_pLong = m_pView.get_lane_handle( )
                             .get_operations( )
                             .get_long_status( );
            //
            if (m_Current != m_Next)
            {
               set_state( true );
               /* run others method by Main thread */
               this.m_pWnd.runOnUiThread( new Runnable( )
               {
                  @Override
                  public void run( )
                  {
                     verify_sensors( );
                     update_state_of_operator( );
                     m_Current = m_Next;
                  }
               } );
               //
               this.m_pView.postDelayed( new Runnable( )
                                         {
                                            @Override
                                            public void run( )
                                            {
                                               update_table_view( );
                                            }
                                         },
                                         0x400
               );
            }
            else
            {
               set_state( false );
               /* run others method by Main thread */
               this.m_pWnd.runOnUiThread( new Runnable( )
               {
                  @Override
                  public void run( )
                  {
                     verify_sensors( );
                     update_state_of_operator( );
                     m_Current = m_Next;
                  }
               } );
               //
               this.m_pView.postDelayed( new Runnable( )
                                         {
                                            @Override
                                            public void run( )
                                            {
                                               update_table_view( );
                                            }
                                         },
                                         0x400
               );
            }
            /* wait 255ms only if Current is different of Next position */
            if (m_Current == m_Next)
            {
               Thread.sleep( 0xFF );
            }
            
         }
         catch (Exception e)
         {
            //Log.e(this.getClass().getName(), e.getMessage());
            e.printStackTrace( );
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public void start( )
   {
      /*
      if (!this.isAlive())
      {
         m_bWhile = true;
         super.start();
      }
      */
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public void interrupt( )
   {
      //
      m_bWhile = false;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   private LaneState get_lane_state( final GetLongStatusResponse pLong,
                                     final boolean bChanges
   )
   {
      if (pLong != null)
      {
         this.m_state = LaneState.from_value( pLong.get_lane_state( ) );
      }
      else
      {
         if (bChanges)
         {
            this.m_state = LaneState.Starting;
         }
         else
         {
            this.m_state = LaneState.NoSync;
         }
      }
      return this.m_state;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   private BarreraState get_barrier_state( GetLongStatusResponse pLong,
                                           boolean bChanges
   )
   {
      if (pLong != null)
      {
         return BarreraState.from_value( pLong.get_device( )
                                              .getBarrierExit( ) );
      }
      else
      {
         if (bChanges)
         {
            return BarreraState.SensorUnknown;
         }
         else
         {
            if (this.m_state == LaneState.NoSync)
            {
               return BarreraState.SensorUnknown;
            }
            else
            {
               return BarreraState.SensorOFF;
            }
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   TrafficLightState get_traffic_light_state( GetLongStatusResponse pLong,
                                              boolean bChanges,
                                              boolean bOnOff
   )
   {
      if (pLong != null)
      {
         final int nLightStateEntry = pLong.get_device( )
                                           .getLightStateEntry( );
         final int nLightStateExit = pLong.get_device( )
                                          .getLightStateExit( );
         return TrafficLightState.from_value( bOnOff ?
                                                 nLightStateEntry :
                                                 nLightStateExit );
      }
      else
      {
         if (bChanges)
         {
            return TrafficLightState.LightUnkown;
         }
         else
         {
            if (this.m_state == LaneState.NoSync)
            {
               return TrafficLightState.LightUnkown;
            }
            else
            {
               return TrafficLightState.LightOff;
            }
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   protected void verify_sensors( )
   {
      try
      {
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
         
         if (this.m_state != LaneState.Starting && this.m_Current == this.m_Next)
         {
            final ProcessLanesView lv = this.m_manager.get_builder_lanes_view_handle( );
            
            if (lv.get_warning_message( ) != null)
            {
               lv.get_warning_message( )
                 .dismiss( );
               lv.set_warning_message( null );
               //					this.m_manager.get_builder_table_view_handle ( )
               //					              .clear ( );
            }
         }
               /**/
         if (this.m_state == LaneState.Opened && this.m_pLong != null)
         {
            this.m_CurrentVechicleImage = "";
         }
      }
      catch (Exception e)
      {
         //Log.e(this.getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   private String format_date( final String szFmt )
   {
      try
      {
         final String year = String.copyValueOf( szFmt.toCharArray( ),
                                                 0,
                                                 4 );
         final String month = String.copyValueOf( szFmt.toCharArray( ),
                                                  4,
                                                  2 );
         final String day = String.copyValueOf( szFmt.toCharArray( ),
                                                6,
                                                2 );
         final String hour = String.copyValueOf( szFmt.toCharArray( ),
                                                 8,
                                                 2 );
         final String minute = String.copyValueOf( szFmt.toCharArray( ),
                                                   10,
                                                   2 );
         final String seconds = String.copyValueOf( szFmt.toCharArray( ),
                                                    12,
                                                    2 );
         
         return (day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + seconds);
      }
      catch (Exception e)
      {
         e.printStackTrace( );
      }
      return szFmt;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   protected void update_table_view( )
   {
      try
      {
         if (this.m_Current == this.m_pLane.get_id( ))
         {
            if (!m_isdebug && m_pLong != null)
            {
               final Vector<GetLongStatusResponse.DetailArray> pQueue = this.m_pLong.get_detail_array( );
               final ArrayList<lane_manager_base.recipe> lines = new ArrayList<>( );
               //final Vector<List<String>>                      pItems = new Vector<List<String>> ( );
               //final Vector<tablewidget_impl.detailsarray>     pData  = new Vector<> ( );
               //
               if (pQueue != null)
               {
                  for (GetLongStatusResponse.DetailArray p : pQueue)
                  {
                     final String lane_name = m_pLane.get_name( ) + "/" + this.m_pLong.get_lane_name( );
                     final lane_manager_base.recipe data = new lane_manager_base.recipe( this.format_date( p.Moment ),
                                                                                         lane_name,
                                                                                         p.PanNumber,
                                                                                         p.TransactionId,
                                                                                         p.PaymentType,
                                                                                         p.PaymentMeans,
                                                                                         p.VehicleClass,
                                                                                         this.m_pLane,
                                                                                         p );
                     
                     lines.add( data );
                     /*
                     List<String> itens = new ArrayList<String> ( );
							{
								itens.add ( this.format_date ( p.Moment ) );
								itens.add ( m_pLane.get_name ( ) + "/" + this.m_pLong.get_lane_name ( ) );
								itens.add ( p.PanNumber );
								itens.add ( p.TransactionId );
								itens.add ( p.PaymentMeans );
								pItems.add ( itens );
							}/*
                     /* create_and_prepare a copy of current data like backup
							tablewidget_impl.detailsarray back = new tablewidget_impl.detailsarray ( );
							{
								back.set_moment ( this.format_date ( p.Moment ) );
								back.set_alias_lane_name ( this.m_pLane.get_name ( ) );
								back.set_original_lane_name ( this.m_pLong.get_lane_name ( ) );
								back.set_pan_number ( p.PanNumber );
								back.set_payment_means ( p.PaymentMeans.trim ( ) );
								back.set_transaction_id ( p.TransactionId );
								back.set_vehicle_class ( p.VehicleClass );
								pData.add ( back );
							}*/
                  }
                  /* internal backup
                  final ProcessTableView tv = this.m_manager.get_builder_table_view_handle ( );
						
						//tv.set_data_backup ( pData );
                  /* put on table view */
                  //tv.update_rows ( pItems );
                  this.m_manager.get_builder_table_view_handle( )
                                .set_array_list_of( lines );
               }
               return;
            }
            /* only debug mode */
            if (m_isdebug)
            {
               final SimpleDateFormat fmt = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss",
                                                                  Locale.FRANCE );
               final ArrayList<lane_manager_base.recipe> lines = new ArrayList<>( );
               //final Vector<List<String>>                  pItems = new Vector<List<String>> ( );
               //final Vector<tablewidget_impl.detailsarray> pBack  = new Vector<> ( );
               
               for (int i = 0; i < 0xA; i++)
               {
                  final String date_time = fmt.format( new Date( ) );
                  final String lane_name = this.m_pLane.get_name( ) + "P";
                  final String tag_name = String.format( Locale.FRANCE,
                                                         "%010d",
                                                         i );
                  final String trans_id = "TRANSACTION-" + Integer.toString( i );
                  final String pay_means = "CA";
                  final String pay_kind = "NONE";
                  final String vechicle = "01";
                  final lane_manager_base.recipe data = new lane_manager_base.recipe( date_time,
                                                                                      lane_name,
                                                                                      tag_name,
                                                                                      trans_id,
                                                                                      pay_kind,
                                                                                      pay_means,
                                                                                      vechicle,
                                                                                      this.m_pLane,
                                                                                      null );
                  
                  lines.add( data );
                  //						List<String> itens = new ArrayList<String> ( );
                  //						{
                  //							itens.add ( fmt.format ( new Date ( ) ) );
                  //							itens.add ( this.m_pLane.get_name ( ) + "P" );
                  //							itens.add ( String.format ( Locale.FRANCE,
                  //							                            "%010d",
                  //							                            i
                  //							                          ) );
                  //							itens.add ( "TRANSACTION-" + Integer.toString ( i ) );
                  //							itens.add ( "CA" );
                  //							pItems.add ( itens );
                  //						}
                  /****
                   tablewidget_impl.detailsarray back = new tablewidget_impl.detailsarray ( );
                   {
                   back.set_moment ( fmt.format ( new Date ( ) ) );
                   back.set_alias_lane_name ( this.m_pLane.get_name ( ) );
                   back.set_vehicle_class ( Integer.toString ( i ) );
                   back.set_transaction_id ( "TRANSACTION-" + Integer.toString ( i ) );
                   back.set_payment_means ( "NO" );
                   back.set_payment_type ( "TG" );
                   back.set_pan_number ( "IUO0098" );
                   pBack.add ( back );
                   }*/
               }
               /****
                final ProcessTableView tv = this.m_manager.get_builder_table_view_handle ( );
                
                tv.set_data_backup ( pBack );
                tv.update_rows ( pItems );
                /* turn off debug mode */
               this.m_manager.get_builder_table_view_handle( )
                             .set_array_list_of( lines );
               company_setup.is_debug = false;
            }
         }
      }
      catch (final Exception e)
      {
         //Log.e(getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   protected void update_state_of_operator( )
   {
      try
      {
         if (this.m_Current == this.m_pLane.get_id( ))
         {
            final boolean bSearch = (m_state != LaneState.NoSync && m_state != LaneState.Starting);
            final boolean bOpened = (m_state == LaneState.Closed);
            final boolean bClosed = (m_state == LaneState.Opened && m_bOperator);
            final boolean bResponsible = (m_state == LaneState.Opened && !m_bOperator);
            final boolean bMoney = (m_state != LaneState.NoSync && m_state != LaneState.Starting && m_bOperator);
            final boolean bTag = (m_state != LaneState.NoSync && m_state != LaneState.Starting && m_bOperator);
            final boolean bSimulation = (bClosed && m_nTotalVehicles > 0 && m_pView.get_caption_handle( )
                                                                                   .is_checked( ));
            final boolean bAllowOpened = (m_pView.get_caption_handle( )
                                                 .is_checked( ) && m_nLaneMode == AVI_LANE);
            final ProcessLaneActions actions = m_manager.get_builder_lanes_actions_handle( );
            
				/*
					for deploy
				 */
            if (m_isdebug)
            {
               actions.Search.setEnabled( m_isdebug );
               actions.OpenLane.setEnabled( m_isdebug );
               actions.CloseLane.setEnabled( m_isdebug );
               actions.ChangeOperator.setEnabled( m_isdebug && bResponsible );
               actions.PaymentMoney.setEnabled( m_isdebug );
               actions.PaymentTag.setEnabled( m_isdebug );
               actions.Violation.setEnabled( m_isdebug );
               actions.Free.setEnabled( m_isdebug );
               actions.Simulation.setEnabled( m_isdebug );
               
               m_pView.get_caption_handle( )
                      .get_handle( )
                      .setBackgroundColor( LaneState.get_color( m_state ) );
               m_pView.get_lane_handle( )
                      .set_state( m_state );
               m_pView.get_semaphore_handle( )
                      .setEnabled( m_isdebug );
               m_pView.get_barrer_handle( )
                      .setEnabled( m_isdebug );
               m_pView.get_traffic_handle( )
                      .setEnabled( m_isdebug );
            }
            else
            {
               actions.Search.setEnabled( bSearch );
               actions.OpenLane.setEnabled( bOpened && bAllowOpened );
               actions.CloseLane.setEnabled( bClosed && bAllowOpened );
               actions.ChangeOperator.setEnabled( bResponsible );
               actions.PaymentMoney.setEnabled( bMoney );
               actions.PaymentTag.setEnabled( bTag );
               actions.Violation.setEnabled( bTag );
               actions.Free.setEnabled( bTag );
               actions.Simulation.setEnabled( bSimulation );
               
               m_pView.get_caption_handle( )
                      .get_handle( )
                      .setBackgroundColor( LaneState.get_color( m_state ) );
               m_pView.get_lane_handle( )
                      .set_state( m_state );
               m_pView.get_semaphore_handle( )
                      .setEnabled( bClosed && bAllowOpened );
               m_pView.get_barrer_handle( )
                      .setEnabled( bClosed && bAllowOpened );
               m_pView.get_traffic_handle( )
                      .setEnabled( bClosed && bAllowOpened );
            }
         }
      }
      catch (final Exception e)
      {
         //Log.e(this.getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final String get_current_vechicle_image( )
   {
      return this.m_CurrentVechicleImage;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneState set_current_vechicle_image( final String vechicle_image )
   {
      this.m_CurrentVechicleImage = vechicle_image;
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final int get_next( )
   {
      return this.m_Next;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneState set_next( final int next )
   {
      this.m_Next = next;
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final Lane get_lane( )
   {
      return this.m_pLane;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final LaneState get_state( )
   {
      return this.m_state;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   protected ProcessLaneState set_state( final boolean bChanges )
   {
      try
      {
         this.m_BarreraState = get_barrier_state( this.m_pLong,
                                                  bChanges
         );
         this.m_state = get_lane_state( this.m_pLong,
                                        bChanges
         );
         this.m_LightEntry = get_traffic_light_state( this.m_pLong,
                                                      bChanges,
                                                      true
         );
         this.m_LightExit = get_traffic_light_state( this.m_pLong,
                                                     bChanges,
                                                     false
         );
         
         if (this.m_pLong != null)
         {
            this.m_bChangeOperator = this.m_szOperator.equals( this.m_pLong.get_operator_code( ) );
            this.m_nTotalVehicles = this.m_pLong.get_device( )
                                                .getTotalVehicles( );
            this.m_nStoppedVehicle = this.m_pLong.get_device( )
                                                 .getVehicleStopped( );
            this.m_nLaneMode = this.m_pLong.get_lane_mode( );
            
            final String szValue = this.m_pLong.get_device( )
                                               .getLightStateCanopy( );
            {
               if (szValue.trim( )
                          .isEmpty( ))
               {
                  this.m_nSemaphoreOfMarquise = -1;
               }
               else
               {
                  this.m_nSemaphoreOfMarquise = Integer.parseInt( szValue );
               }
            }
         }
         else
         {
            this.m_bChangeOperator = false;
            this.m_nStoppedVehicle = 0;
            this.m_nTotalVehicles = 0;
            this.m_nLaneMode = 0;
            this.m_nSemaphoreOfMarquise = -1;
         }
      }
      catch (Exception e)
      {
         //Log.e(this.getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final boolean get_change_operator( )
   {
      return this.m_bChangeOperator;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneState set_change_operator( final boolean change_operator )
   {
      this.m_bChangeOperator = change_operator;
      return this;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   
}
//-----------------------------------------------------------------------------------------------------------------//
//060115SAU201703311005227722
//060102PAU20170331101228