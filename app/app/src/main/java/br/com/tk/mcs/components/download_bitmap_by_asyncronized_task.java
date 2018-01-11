/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;

import br.com.tk.mcs.R;
import br.com.tk.mcs.tools.network;

/**
 * Created by wilsonsouza on 01/02/17.
 */

public class download_bitmap_by_asyncronized_task extends AsyncTask<String, Void, Bitmap>
{
	private imageview_impl m_bitmap = null;
	private Point          m_point  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public download_bitmap_by_asyncronized_task ( imageview_impl bmp,
	                                              Point point
	                                            )
	{
		super ( );
		this.m_bitmap = bmp;
		this.m_point = point;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected Bitmap doInBackground ( String... params )
	{
		Bitmap bmp = null;
		try
		{
			bmp = network.download_bitmap ( params[ 0 ] );
		}
		catch ( final Exception e )
		{
			Log.e ( getClass ( ).getName ( ),
			        e.getMessage ( )
			      );
		}
		return bmp;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onPostExecute ( Bitmap bmp )
	{
		if ( bmp != null )
		{
			m_bitmap.resize ( bmp,
			                  m_point
			                );
		}
		else
		{
			m_bitmap.get_handle ( )
			        .setImageResource ( R.drawable.box_error );
		}
	}
}
