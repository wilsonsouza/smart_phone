/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.main;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.database.nosql_persistence_controller_lane;
import br.com.tk.mcs.dialogs_ui.question_box;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.manager.ProcessManager;
import br.com.tk.mcs.manager.ProcessTableView;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 18/01/17.
 */

public class TrackManager extends frame_window_impl
{
   public final static String TAG = TrackManager.class.getName( );
   /* main variables */
   private ProcessManager m_manager = null;
   private nosql_persistence_controller_lane m_db = null;
   private ArrayList<Lane> m_lanes = new ArrayList<Lane>( );
   private String m_user_name_logged = null;
   
   //---------------------------------------------------------------------------------------------//
   @Override
   @TargetApi(23)
   public void onCreate( Bundle pSavedState )
   {
      try
      {
         super.onCreate( pSavedState );
         //
         Bundle b = this.getIntent( )
                        .getExtras( );
         this.m_user_name_logged = b.getString( Utils.OPERATOR );
         this.prepare_lanes( );
         this.m_manager = new ProcessManager( this,
                                              this.m_lanes,
                                              this.m_user_name_logged
         ).start_process( );
      }
      catch (Exception e)
      {
         Log.e( TAG,
                e.getMessage( ) );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void onDestroy( )
   {
      try
      {
         super.onDestroy( );
         this.m_manager.finalize_process( );
      }
      catch (Exception e)
      {
         Log.e( TAG,
                e.getMessage( ) );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void onResume( )
   {
      //refresh and recalculate all objects of window
      if (this.m_manager != null)
      {
         if (this.m_manager.get_builder_table_view_handle( ) != null)
         {
            ProcessTableView view = this.m_manager.get_builder_table_view_handle( );
            int height = view.Params.get_display( ).heightPixels - view.Params.get_actionbar_height( );
            int lanes_action_h = this.m_manager.get_builder_lanes_actions_handle( )
                                               .getHeight( );
            int lane_view_h = this.m_manager.get_builder_lanes_view_handle( )
                                            .getHeight( );
            int space_between = config_display_metrics.small_context_size * 2;
            //
            height = height - (lane_view_h + lanes_action_h + space_between);
            view.Params = view.build( view.Params.WRAP_CONTENT,
                                      height );
            view.update_params();
         }
      }
      super.onResume( );
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public boolean onOptionsItemSelected( MenuItem pItem )
   {
      int nId = pItem.getItemId( );
      
      if (nId == android.R.id.home)
      {
         new question_box( this,
                           R.string.manager_exit_title,
                           R.string.manager_exit_response,
                           question_box.DEFAULT
         );
      }
      return true;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessManager get_manager_handle( )
   {
      //
      return this.m_manager;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final nosql_persistence_controller_lane get_db( )
   {
      //
      return m_db;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final ArrayList<Lane> get_lanes( )
   {
      //
      return m_lanes;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public final String get_user_name_logged( )
   {
      //
      return this.m_user_name_logged;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   void prepare_lanes( )
   {
      int i = 0;
      this.m_db = new nosql_persistence_controller_lane( this );
      
      for (final String szName : this.m_db.get_array_of_names( ))
      {
               /* put on message on main thread */
         final String szAddress = this.m_db.get_by_name( szName );
         //Operations pOperation = new Operations("http://" + szAddress + ":8000/");
         final String szUrl = String.format( getString( R.string.ids_url_lane ),
                                             szAddress );
         final Lane pLane = new Lane( i++,
                                      szName,
                                      this.m_user_name_logged,
                                      new Operations( szUrl,
                                                      this.m_user_name_logged )
         );
               /**/
         Log.i( TAG,
                "Lane Url " + szUrl );
         this.m_lanes.add( pLane );
      }
   }
}
