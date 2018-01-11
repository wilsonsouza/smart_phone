/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dispatch;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import br.com.tk.mcs.adapter.lane_manager_base;
import br.com.tk.mcs.components.tablewidget_impl;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.manager.ProcessManager;
import br.com.tk.mcs.remote.response.GetLongStatusResponse;

/**
 * Created by wilsonsouza on 12/8/17.
 */

public class tableview_item implements Parcelable, Serializable
{
   public final static Parcelable.Creator CREATOR = new Parcelable.Creator( )
   {
      /**
       * create a new instance of the Parcelable class, instantiating it
       * from the given Parcel whose data had previously been written by
       * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
       *
       * @param source The Parcel to read the object's data from.
       * @return Returns a new instance of the Parcelable class.
       */
      @Override
      public Object createFromParcel( Parcel source )
      {
         //
         return new tableview_item( source );
      }
      
      /**
       * create a new array of the Parcelable class.
       *
       * @param size Size of the array.
       * @return Returns an array of the Parcelable class, with every entry
       * initialized to null.
       */
      @Override
      public Object[] newArray( int size )
      {
         //
         return new tableview_item[size];
      }
   };
   
   //
   public String m_date_time = null;
   public String m_lane_name = null;
   public String m_tag_name = null;
   public String m_photo_link = null;
   public String m_payment_means = null;
   public String m_payment_type = null;
   public lane_manager_base.view_holder m_holder = null;
   public Lane m_lane = null;
   public String m_vechicle_class = null;
   public GetLongStatusResponse.DetailArray m_details_array = null;
   public ProcessManager m_manager = null;
   
   public tableview_item( Parcel in )
   {
      this.m_date_time = in.readString( );
      this.m_lane_name = in.readString( );
      this.m_tag_name = in.readString( );
      this.m_photo_link = in.readString( );
      this.m_payment_means = in.readString( );
      this.m_payment_type = in.readString( );
      this.m_holder = (lane_manager_base.view_holder) in.readValue( lane_manager_base.view_holder.class.getClassLoader( ) );
      this.m_lane = (Lane) in.readValue( Lane.class.getClassLoader( ) );
      this.m_vechicle_class = in.readString( );
      this.m_details_array = (GetLongStatusResponse.DetailArray) in.readValue(
         GetLongStatusResponse.DetailArray.class.getClassLoader( ) );
      this.m_manager = (ProcessManager) in.readValue( ProcessManager.class.getClassLoader( ) );
   }
   
   public tableview_item( lane_manager_base.recipe items,
                          ProcessManager manager )
   {
      this.m_date_time = items.m_date_time;
      this.m_lane_name = items.m_lane_name;
      this.m_tag_name = items.m_tag_name;
      this.m_photo_link = items.m_photo_link;
      this.m_payment_means = items.m_payment_means;
      this.m_payment_type = items.m_payment_type;
      this.m_holder = items.m_holder;
      this.m_lane = items.m_lane;
      this.m_vechicle_class = items.m_vechicle_class;
      this.m_details_array = items.m_detail_array;
      this.m_manager = manager;
   }
   
   /**
    * Describe the kinds of special objects contained in this Parcelable's
    * marshalled representation.
    *
    * @return a bitmask indicating the set of special object types marshalled
    * by the Parcelable.
    */
   @Override
   public int describeContents( )
   {
      //
      return 0;
   }
   
   /**
    * Flatten this object in to a Parcel.
    *
    * @param dest  The Parcel in which the object should be written.
    * @param flags Additional flags about how the object should be written.
    *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
    */
   @Override
   public void writeToParcel( Parcel dest,
                              int flags
   )
   {
      dest.writeString( this.m_date_time );
      dest.writeString( this.m_lane_name );
      dest.writeString( this.m_tag_name );
      dest.writeString( this.m_photo_link );
      dest.writeString( this.m_payment_means );
      dest.writeString( this.m_payment_type );
      dest.writeValue( this.m_holder );
      dest.writeValue( this.m_lane );
      dest.writeString( this.m_vechicle_class );
      dest.writeValue( this.m_details_array );
      //dest.writeValue( this.m_manager );
   }
}
