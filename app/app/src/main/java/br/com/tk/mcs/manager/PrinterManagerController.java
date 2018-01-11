/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.os.Parcel;

import com.datecs.api.printer.Printer;
import com.datecs.api.printer.ProtocolAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.toolbar_impl;
import br.com.tk.mcs.drivers.bluetooth_printer_controller;
import br.com.tk.mcs.main.TrackManager;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class PrinterManagerController extends bluetooth_printer_controller
{
	private boolean      m_bWhile         = true;
	private toolbar_impl m_toolbar_handle = null;
	private boolean      m_is_online      = false;
	private boolean      m_stop_and_wait  = false;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public PrinterManagerController ( TrackManager pWnd,
	                                  toolbar_impl pToolbar
	                                ) throws
	                        Exception
	{
		super ( pWnd );
		this.m_toolbar_handle = pToolbar;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	@TargetApi( Build.VERSION_CODES.ICE_CREAM_SANDWICH )
	protected bluetooth_printer_controller update_status ( )
	{
		try
		{
			if ( this.m_bluetooth_socket != null )
			{
				this.m_is_online = m_bluetooth_socket.isConnected ( );
			}
			else
			{
				this.m_is_online = false;
			}
			
			if ( this.m_is_online )
			{
				m_toolbar_handle.set_icon ( 0,
				                            R.drawable.printer_ok
				                          );
			}
			else
			{
				m_toolbar_handle.set_icon ( 0,
				                            R.drawable.printer_error
				                          );
			}
		}
		catch ( Exception e )
		{
			m_toolbar_handle.set_icon ( 0,
			                            R.drawable.printer_error
			                          );
			//Log.e(getClass().getName(), e.getMessage());
			e.printStackTrace ( );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected bluetooth_printer_controller initialize_printer ( InputStream in,
	                                                            OutputStream out
	                                                          ) throws
	                                                            IOException
	{
		try
		{
			super.m_protocol_adapter = new ProtocolAdapter ( in,
			                                                 out
			);
			
			if ( super.m_protocol_adapter.isProtocolEnabled ( ) )
			{
				super.m_protocol_adapter.setPrinterListener ( new PrinterManagerController.PrinterListenerEx ( ) );
				super.m_printer_channel = super.m_protocol_adapter.getChannel ( ProtocolAdapter.CHANNEL_PRINTER );
				super.m_printer = new Printer ( super.m_printer_channel.getInputStream ( ),
				                                super.m_printer_channel.getOutputStream ( )
				);
			}
			else
			{
				super.m_printer = new Printer ( super.m_protocol_adapter.getRawInputStream ( ),
				                                super.m_protocol_adapter.getRawOutputStream ( )
				);
			}
			
			super.m_printer.setConnectionListener ( new ConnectionListenerImpl ( ) );
			this.update_status ( );
		}
		catch ( Exception e )
		{
			m_toolbar_handle.set_icon ( 0,
			                            R.drawable.printer_error
			                          );
			//Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace ( );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void start ( )
	{
		this.m_bWhile = true;
		super.start ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final PrinterManagerController interrupt ( )
	{
		this.m_bWhile = false;
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void run ( )
	{
		while ( this.m_bWhile )
		{
			try
			{
				if ( ! m_stop_and_wait )
				{
					super.run ( );
				}
				else
				{
					this.m_bluetooth_adapter.cancelDiscovery ( );
					this.m_bluetooth_adapter.disable ( );
				}
				/* wait 15 seconds */
				Thread.sleep ( 0x400 );
			}
			catch ( Exception e )
			{
				this.update_status ( );
				//Log.e(this.getClass().getName(), e.getMessage());
				e.printStackTrace ( );
			}
		}
		
		try
		{
			this.terminate ();
		}
		catch ( Exception e )
		{
			//Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final boolean is_online ( )
	{
		return this.m_is_online;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final BluetoothAdapter get_device_adapter ( )
	{
		return this.m_bluetooth_adapter;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final PrinterManagerController set_stop_and_wait ( boolean bValue )
	{
		this.m_stop_and_wait = bValue;
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class PrinterListenerEx implements ProtocolAdapter.PrinterListener
	{
		@Override
		public void onBatteryStateChanged ( boolean b )
		{
			if ( b )
			{
				m_toolbar_handle.set_icon ( 0,
				                            R.drawable.printer_without_batery
				                          );
			}
			else
			{
				update_status ( );
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void onThermalHeadStateChanged ( boolean b )
		{
			if ( b )
			{
				m_toolbar_handle.set_icon ( 0,
				                            R.drawable.print_superhot
				                          );
			}
			else
			{
				update_status ( );
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void onPaperStateChanged ( boolean b )
		{
			if ( b )
			{
				m_toolbar_handle.set_icon ( 0,
				                            R.drawable.printer_nopaper
				                          );
			}
			else
			{
				update_status ( );
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
