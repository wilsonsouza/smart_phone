/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dispatch;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.tk.mcs.drivers.bluetooth_printer_controller;
import br.com.tk.mcs.lane.Lane;

/**
 * Created by wilsonsouza on 12/8/17.
 */

public class vehicle_parameters implements Parcelable
{
	public final static Parcelable.Creator CREATOR = new Parcelable.Creator ( )
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
		public Object createFromParcel ( Parcel source )
		{
			//
			return new vehicle_parameters ( source );
		}
		
		/**
		 * create a new array of the Parcelable class.
		 *
		 * @param size Size of the array.
		 * @return Returns an array of the Parcelable class, with every entry
		 * initialized to null.
		 */
		@Override
		public Object[] newArray ( int size )
		{
			//
			return new vehicle_parameters[ 0 ];
		}
	};
	
	public Lane                         m_current_lane    = null;
	public String                       m_mcs_bitmap_link = null;
	
	public vehicle_parameters ( Parcel in )
	{
		this.m_mcs_bitmap_link = in.readString ( );
		this.m_current_lane = ( Lane ) in.readValue ( Lane.class.getClassLoader ( ) );
	}
	
	public vehicle_parameters ( Lane current_lane,
	                            String bmp_link
	                          )
	{
		this.m_current_lane = current_lane;
		this.m_mcs_bitmap_link = bmp_link;
	}
	
	/**
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation.
	 *
	 * @return a bitmask indicating the set of special object types marshalled
	 * by the Parcelable.
	 */
	@Override
	public int describeContents ( )
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
	public void writeToParcel ( Parcel dest,
	                            int flags
	                          )
	{
		dest.writeString ( this.m_mcs_bitmap_link );
		dest.writeValue ( this.m_current_lane );
	}
}
