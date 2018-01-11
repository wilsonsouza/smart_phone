package br.com.tk.mcs.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.textview_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.main.TrackManager;
import br.com.tk.mcs.manager.ProcessManager;
import br.com.tk.mcs.manager.ProcessTableView;
import br.com.tk.mcs.remote.response.GetLongStatusResponse;

/**
 * Created by wilsonsouza on 12/18/17.
 */

public class lane_manager_base extends BaseAdapter
{
   private Context m_context = null;
   private ArrayList<recipe> m_recipe = null;
   
   public lane_manager_base( Context context,
                             ArrayList<lane_manager_base.recipe> queue
   )
   {
      this.m_context = context;
      this.m_recipe = queue;
   }
   
   /**
    * How many items are in the data set represented by this Adapter.
    *
    * @return Count of items.
    */
   @Override
   public int getCount( )
   {
      //
      return this.m_recipe.size( );
   }
   
   /**
    * Get the data item associated with the specified position in the data set.
    *
    * @param position Position of the item whose data we want within the adapter's
    *                 data set.
    * @return The data at the specified position.
    */
   @Override
   public Object getItem( int position )
   {
      //
      return this.m_recipe.get( position );
   }
   
   /**
    * Get the row id associated with the specified position in the list.
    *
    * @param position The position of the item within the adapter's data set whose row id we want.
    * @return The id of the item at the specified position.
    */
   @Override
   public long getItemId( int position )
   {
      //
      return position;
   }
   
   /**
    * Get a View that displays the data at the specified position in the data set. You can either
    * create a View manually or inflate it from an XML layout file. When the View is inflated, the
    * parent View (GridView, ListView...) will apply default layout parameters unless you use
    * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
    * to specify a root view and to prevent attachment to the root.
    *
    * @param position    The position of the item within the adapter's data set of the item whose view
    *                    we want.
    * @param convertView The old view to reuse, if possible. Note: You should check that this view
    *                    is non-null and of an appropriate type before using. If it is not possible to convert
    *                    this view to display the correct data, this method can create a new view.
    *                    Heterogeneous lists can specify their number of view types, so that this View is
    *                    always of the right type (see {@link #getViewTypeCount()} and
    *                    {@link #getItemViewType(int)}).
    * @param parent      The parent that this view will eventually be attached to
    * @return A View corresponding to the data at the specified position.
    */
   @Override
   public View getView( int position,
                        View view,
                        ViewGroup parent
   )
   {
      view_holder holder = null;
      
      if (view == null)
      {
         holder = new view_holder( this.m_context ).builder( );
         view = holder;
         view.setId( position );
         view.setTag( holder );
      }
      else
      {
         holder = (view_holder) view.getTag( );
      }
      
      recipe data = (recipe) getItem( position );
      
      holder.update( data );
      return view;
   }
   
   public static class view_holder extends linear_horizontal implements View.OnClickListener
   {
      public textview_impl m_date_time = null;
      public textview_impl m_lane_name = null;
      public textview_impl m_tag_name = null;
      public imageview_impl m_photo_link = null;
      public textview_impl m_payment_means = null;
      
      public view_holder( Context context )
      {
         super( context );
      }
      
      public view_holder builder( )
      {
         Point point = new Point( config_display_metrics.small_context_icons_size,
                                  config_display_metrics.small_context_icons_size
         );
         int width = (this.Params.get_display( ).widthPixels / 5);
         //
         m_date_time = new textview_impl( this.getContext( ) );
         m_date_time.get_handle( )
            .setLines( 2 );
         m_date_time.Params.width = width + config_display_metrics.space_between_controls;
         
         m_lane_name = new textview_impl( this.getContext( ) );
         m_lane_name.Params.width = width + config_display_metrics.space_between_controls;
         m_lane_name.set_font_size( View.NO_ID );
         
         m_payment_means = new textview_impl( this.getContext( ) );
         m_payment_means.Params.width = width;
         
         m_tag_name = new textview_impl( this.getContext( ) );
         m_tag_name.Params.width = width + config_display_metrics.space_between_controls;
         
         m_photo_link = new imageview_impl( this.getContext( ),
                                            point,
                                            R.drawable.ic_tbl_photo_image,
                                            false );
         m_photo_link.Params.width = width + config_display_metrics.space_between_controls;
         m_photo_link.Params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
         
         this.addView( m_date_time,
                       m_date_time.Params );
         this.addView( this.alloc_space( config_display_metrics.space_between_controls,
                                         0 ) );
         this.addView( m_lane_name,
                       m_lane_name.Params );
         this.addView( this.alloc_space( config_display_metrics.space_between_controls,
                                         0 ) );
         this.addView( m_tag_name,
                       m_tag_name.Params );
         this.addView( this.alloc_space( config_display_metrics.space_between_controls,
                                         0 ) );
         this.addView( m_payment_means,
                       m_payment_means.Params );
         this.addView( this.alloc_space( config_display_metrics.space_between_controls,
                                         0 ) );
         this.addView( m_photo_link,
                       m_photo_link.Params );
         
         this.invalidate( true );
         this.setOnClickListener( this );
         return this;
      }
      
      public view_holder update( recipe data )
      {
         if (data != null)
         {
            String date = String.copyValueOf( data.m_date_time.toCharArray( ),
                                              0,
                                              10 );
            String time = String.copyValueOf( data.m_date_time.toCharArray( ),
                                              11,
                                              8 );
            
            m_date_time.get_handle( )
               .setText( String.format( "%s\n%s",
                                        date,
                                        time ) );
            
            m_lane_name.get_handle( )
               .setText( data.m_lane_name );
            m_payment_means.get_handle( )
               .setText( data.m_payment_means );
            m_photo_link.get_handle( )
               .setTag( data.m_photo_link );
            m_tag_name.get_handle( )
               .setText( data.m_tag_name );
         }
         return this;
      }
      
      @Override
      public void onClick( View v )
      {
         TrackManager context = (TrackManager) this.getContext( );
         
         if (context != null)
         {
            ProcessTableView table = context.get_manager_handle( ).get_builder_table_view_handle( );
            int position = v.getId( );
            
            table.onItemClick( table.get_handle( ),
                               v,
                               position,
                               position );
         }
      }
   }
   
   public static class recipe implements Serializable
   {
      public String m_date_time = null;
      public String m_lane_name = null;
      public String m_tag_name = null;
      public String m_photo_link = null;
      public String m_payment_means = null;
      public String m_payment_type = null;
      public view_holder m_holder = null;
      public Lane m_lane = null;
      public String m_vechicle_class = null;
      public GetLongStatusResponse.DetailArray m_detail_array = null;
      
      public recipe( String date_time,
                     String lane_name,
                     String tag_name,
                     String photo_link,
                     String payment_type,
                     String payment_means,
                     String vechicle_class,
                     Lane lane_handle,
                     GetLongStatusResponse.DetailArray detail_array
      )
      {
         this.m_date_time = date_time;
         this.m_holder = null;
         this.m_lane_name = lane_name;
         this.m_tag_name = tag_name;
         this.m_photo_link = photo_link;
         this.m_payment_type = payment_type;
         this.m_payment_means = payment_means;
         this.m_vechicle_class = vechicle_class;
         this.m_lane = lane_handle;
         this.m_detail_array = detail_array;
      }
   }
}
