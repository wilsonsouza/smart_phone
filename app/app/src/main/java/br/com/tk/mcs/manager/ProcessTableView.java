/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.activitys_operations.FrameTableViewItem;
import br.com.tk.mcs.adapter.lane_manager_base;
import br.com.tk.mcs.components.listview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.dispatch.tableview_item;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.main.TrackManager;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class ProcessTableView extends listview_impl implements ListView.OnItemClickListener
{
   private ProcessManager m_manager = null;
   private ArrayList<lane_manager_base.recipe> m_items_base = new ArrayList<>( );
   private int[] m_header_list = new int[]{R.string.tbl_datetime,
                                           R.string.tbl_via,
                                           R.string.tbl_tag,
                                           R.string.tbl_down,
                                           R.string.tbl_photo
   };
   private TrackManager m_wnd = null;
   private linear_horizontal m_header = null;
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessTableView( final Activity context )
   {
      super( context );
      
      this.m_wnd = (TrackManager) context;
      this.m_header = this.set_header( this.m_header_list,
                                       false );
      
      this.addView( this.m_header,
                    0,
                    this.m_header.Params );
      this.addView( this.alloc_space( 0,
                                      config_display_metrics.space_between_controls ),
                    1 );
      
      this.get_handle( )
          .setAdapter( new lane_manager_base( this.getContext( ),
                                              this.get_array_list_of( ) ) );
      this.m_wnd.get_layout_handle( )
                .addView( this,
                          this.Params );
      
      int width = (this.Params.get_display( ).widthPixels / this.m_header_list.length);
      
      for (int i = 0; i < this.m_header.getChildCount( ); i++)
      {
         textview_impl t = (textview_impl) this.m_header.getChildAt( i );
         
         if ((int) t.get_handle( )
                    .getId( ) == R.string.tbl_down)
         {
            t.Params.width = width + config_display_metrics.space_between_controls * 2;
            continue;
         }
         
         t.Params.width = width + config_display_metrics.space_between_controls;
         t.set_color( textview_impl.DEFCOLOR );
         t.update_params( );
      }
      
      this.m_header.setBackgroundColor( Color.BLUE );
      this.m_header.set_border( true );
      this.set_border( true );
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessTableView set_manager_pointer( final ProcessManager manager )
   {
      //
      this.m_manager = manager;
      return this;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ArrayList<lane_manager_base.recipe> get_array_list_of( )
   {
      //
      return this.m_items_base;
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessTableView set_array_list_of( ArrayList<lane_manager_base.recipe> queue )
   {
      this.m_items_base = queue;
      this.get_handle( )
          .setAdapter( new lane_manager_base( this.getContext( ),
                                              this.m_items_base ) );
      return this;
   }
   
   /**
    * Callback method to be invoked when an item in this AdapterView has
    * been clicked.
    * <p>
    * Implementers can call getItemAtPosition(position) if they need
    * to access the data associated with the selected item.
    *
    * @param parent   The AdapterView where the click happened.
    * @param view     The view within the AdapterView that was clicked (this
    *                 will be a view provided by the adapter)
    * @param position The position of the view in the adapter.
    * @param id       The row id of the item that was clicked.
    */
   @Override
   @TargetApi(16)
   public void onItemClick( AdapterView<?> parent,
                            View view,
                            int position,
                            long id
   )
   {
      Intent frame = new Intent( this.m_wnd,
                                 FrameTableViewItem.class );
      final lane_manager_base.recipe items = (lane_manager_base.recipe) parent.getItemAtPosition( position );
      tableview_item param = new tableview_item( items, this.m_manager );
      Bundle b = new Bundle(  );
      
      b.putParcelable( FrameTableViewItem.IDS_PARAMETERS, param );
      frame.putExtras( b );
      this.m_wnd
          .startActivity( frame );
   }
}
