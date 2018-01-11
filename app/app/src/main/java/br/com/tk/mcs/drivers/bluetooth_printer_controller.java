/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description: managed bluetooth printer and print ticket about payment.
 */
package br.com.tk.mcs.drivers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.datecs.api.printer.Printer;
import com.datecs.api.printer.ProtocolAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.display_message;

/**
 * Created by wilsonsouza on 10/6/16.
 */
public class bluetooth_printer_controller implements Runnable
{
	//printer name
	public static final String                  DEVICE_NAME         = "DPP-350";
	protected           Printer                 m_printer           = null;
	protected           ProtocolAdapter         m_protocol_adapter  = null;
	protected           ProtocolAdapter.Channel m_printer_channel   = null;
	protected           BluetoothSocket         m_bluetooth_socket  = null;
	protected           BluetoothAdapter        m_bluetooth_adapter = null;
	protected           device_adapter          m_device_adapter    = null;
	protected           Activity                m_pWnd              = null;
	protected           String                  m_szTitle           = null;
	protected           BroadcastReceiverImpl   m_pReceiver         = new BroadcastReceiverImpl ( );
	
	//-----------------------------------------------------------------------------------------------------------------//
	public bluetooth_printer_controller ( Context pWnd ) throws
	                                                     Exception
	{
		super ( );
		this.m_pWnd = ( Activity ) pWnd;
		this.m_szTitle = this.m_pWnd.getTitle ( )
		                            .toString ( );
		//this.setPriority(Thread.MIN_PRIORITY);
		//this.setName(this.getClass().getName());
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.ICE_CREAM_SANDWICH )
	protected bluetooth_printer_controller update_status ( )
	{
		boolean bIsOnLine = false;
		String  szData;
		//
		if ( this.m_bluetooth_socket != null )
		{
			bIsOnLine = this.m_bluetooth_socket.isConnected ( );
		}
		//
		if ( bIsOnLine )
		{
			szData = "Impressora conectada.";
		}
		else
		{
			szData = "Impressora disconectada.";
		}
		//
		status ( szData );
		return this;
	}
	
	;
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.LOLLIPOP )
	public bluetooth_printer_controller initialize ( ) throws
	                                                   Exception
	{
		final BluetoothManager bm = ( BluetoothManager ) this.m_pWnd.getSystemService ( Context.BLUETOOTH_SERVICE );
		this.m_bluetooth_adapter = bm.getAdapter ( );
		this.m_device_adapter = new device_adapter ( );
		//
		this.m_pWnd.registerReceiver ( this.m_pReceiver,
		                               new IntentFilter ( BluetoothDevice.ACTION_FOUND )
		                             );
		this.m_pWnd.registerReceiver ( this.m_pReceiver,
		                               new IntentFilter ( BluetoothAdapter.ACTION_DISCOVERY_FINISHED )
		                             );
		//
		if ( ! this.m_bluetooth_adapter.isEnabled ( ) )
		{
			this.m_bluetooth_adapter.enable ( );
		}
		//
		if ( this.m_bluetooth_adapter.isEnabled ( ) )
		{
			java.util.Set<BluetoothDevice> pPairedDevices = this.m_bluetooth_adapter.getBondedDevices ( );
			//
			if ( pPairedDevices.size ( ) > 0 )
			{
				for ( BluetoothDevice p : pPairedDevices )
				{
					this.m_device_adapter.add ( p.getName ( ),
					                            p.getAddress ( ),
					                            true
					                          );
				}
			}
		}
		return this;
	}
	
	;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final bluetooth_printer_controller terminate ( ) throws
	                                                        Exception
	{
		if ( m_bluetooth_adapter != null )
		{
			m_bluetooth_adapter.cancelDiscovery ( );
		}
		//
		this.m_pWnd.unregisterReceiver ( this.m_pReceiver );
		this.close_active_connection ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private final bluetooth_printer_controller discover ( )
	{
		if ( ! m_bluetooth_adapter.isEnabled ( ) )
		{
			m_bluetooth_adapter.enable ( );
		}
		//
		if ( ! m_bluetooth_adapter.isDiscovering ( ) )
		{
			m_bluetooth_adapter.startDiscovery ( );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller toast ( final String szData )
	{
		m_pWnd.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				Toast.makeText (
					m_pWnd.getApplicationContext ( ),
					szData,
					Toast.LENGTH_LONG
				               )
				     .show ( );
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller status ( final String szData )
	{
		m_pWnd.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				if ( szData != null )
				{
               /* put text on title of Manager */
					final CharSequence csNewTitle = szData;
					m_pWnd.setTitle ( m_szTitle + " - " + csNewTitle );
				}
				else
				{
					m_pWnd.setTitle ( m_szTitle );
				}
			}
		} );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	@TargetApi( Build.VERSION_CODES.ICE_CREAM_SANDWICH )
	public void run ( )
	{
		try
		{
			this.initialize ( );
			
			device_node pNode     = m_device_adapter.search ( DEVICE_NAME );
			boolean     bIsOnLine = false;
			
			if ( m_bluetooth_socket != null )
			{
				bIsOnLine = m_bluetooth_socket.isConnected ( );
			}
			
			if ( pNode != null && ! bIsOnLine && m_bluetooth_adapter.isEnabled ( ) )
			{
				m_bluetooth_adapter.cancelDiscovery ( );
				estabablish_connection ( pNode.get_address ( ) );
			}
			//
			if ( ! bIsOnLine && m_bluetooth_adapter.isEnabled ( ) )
			{
				this.update_status ( );
				discover ( );
			}
			
			if ( ! m_bluetooth_adapter.isEnabled ( ) )
			{
				m_bluetooth_adapter.enable ( );
			}
		}
		catch ( Exception e )
		{
			this.update_status ( );
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void start ( )
	{
		//if(!isAlive())
		{
			try
			{
				this.initialize ( );
				//super.start();
			}
			catch ( Exception e )
			{
				this.update_status ( );
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected bluetooth_printer_controller initialize_printer ( InputStream in,
	                                                            OutputStream out
	                                                          ) throws
	                                                            IOException
	{
		try
		{
			this.m_protocol_adapter = new ProtocolAdapter ( in,
			                                                out
			);
			
			if ( this.m_protocol_adapter.isProtocolEnabled ( ) )
			{
				this.m_protocol_adapter.setPrinterListener ( new PrinterListenerImpl ( ) ); //this.m_PrinterListener);
				this.m_printer_channel = m_protocol_adapter.getChannel ( ProtocolAdapter.CHANNEL_PRINTER );
				this.m_printer = new Printer ( m_printer_channel.getInputStream ( ),
				                               m_printer_channel.getOutputStream ( )
				);
			}
			else
			{
				m_printer = new Printer ( m_protocol_adapter.getRawInputStream ( ),
				                          m_protocol_adapter.getRawOutputStream ( )
				);
			}
			
			m_printer.setConnectionListener ( new ConnectionListenerImpl ( ) ); //m_ConnectionListener);
			this.update_status ( );
		}
		catch ( Exception e )
		{
			this.update_status ( );
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller estabablish_connection ( final String szAddress ) throws
	                                                                                       Exception
	{
		try
		{
         /* display dialog to select printer address */
			final BluetoothAdapter pAdapter = BluetoothAdapter.getDefaultAdapter ( );
			final UUID             uuid     = UUID.fromString ( "00001101-0000-1000-8000-00805F9B34FB" );
			final BluetoothDevice  pDevice  = pAdapter.getRemoteDevice ( szAddress );
			InputStream            in       = null;
			OutputStream           out      = null;
         /**/
			try
			{
            /* create_and_prepare rfcomm connection service */
				BluetoothSocket pSocket = pDevice.createRfcommSocketToServiceRecord ( uuid );
				{
					pAdapter.cancelDiscovery ( );
					pSocket.connect ( );
				}
            /**/
				m_bluetooth_socket = pSocket;
				in = m_bluetooth_socket.getInputStream ( );
				out = m_bluetooth_socket.getOutputStream ( );

            /* start printer */
				initialize_printer ( in,
				                     out
				                   );
			}
			catch ( IOException e )
			{
				close_active_connection ( );
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
		}
		catch ( Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller close_bluetooth_connection ( )
	{
		try
		{
         /* close bluetooth connection */
			BluetoothSocket pBluetoothSocket = m_bluetooth_socket;
         /* free memory */
			if ( pBluetoothSocket != null )
			{
				m_bluetooth_socket = null;
				pBluetoothSocket.close ( );
			}
		}
		catch ( IOException e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller close_printer_connection ( )
	{
		try
		{
			if ( this.m_printer != null )
			{
				this.m_printer.close ( );
				this.m_printer = null;
			}
			//
			if ( this.m_protocol_adapter != null )
			{
				this.m_protocol_adapter.close ( );
				this.m_protocol_adapter = null;
			}
		}
		catch ( Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private bluetooth_printer_controller close_active_connection ( )
	{
		this.close_printer_connection ( );
		this.close_bluetooth_connection ( );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public bluetooth_printer_controller send_cupom_to_printer ( final StringBuffer pBuffer )
	{
		try
		{
			if ( pBuffer.length ( ) == 0 )
			{
				throw new Exception ( "no data to printer!" );
			}
			//
			PrinterCupomTask p = new PrinterCupomTask ( pBuffer );
			{
				p.start ( );
			}
		}
		catch ( Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class BroadcastReceiverImpl extends BroadcastReceiver
	{
		public BroadcastReceiverImpl ( )
		{
			super ( );
		}
		
		@Override
		public void onReceive ( Context context,
		                        Intent intent
		                      )
		{
			final String szAction = intent.getAction ( );
			//
			if ( BluetoothDevice.ACTION_FOUND.equals ( szAction ) )
			{
				BluetoothDevice pDevice = intent.getParcelableExtra ( BluetoothDevice.EXTRA_DEVICE );
				device_node     pNode   = m_device_adapter.find ( pDevice.getAddress ( ) );
				//
				if ( pNode == null )
				{
					m_device_adapter.add ( pDevice.getName ( ),
					                       pDevice.getAddress ( ),
					                       false
					                     );
				}
				else
				{
					pNode.set_name ( pDevice.getName ( ) );
					pNode.set_address ( pDevice.getAddress ( ) );
				}
			}
			else if ( BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals ( szAction ) )
			{
				//non code here
				m_bluetooth_adapter.cancelDiscovery ( );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected class PrinterListenerImpl implements ProtocolAdapter.PrinterListener
	{
		@Override
		public void onBatteryStateChanged ( boolean b )
		{
			if ( b )
			{
				status ( "Impressora com bateria fraca!" );
			}
			else
			{
				update_status ( );
			}
		}
		
		@Override
		public void onThermalHeadStateChanged ( boolean b )
		{
			if ( b )
			{
				status ( "Impressora super aquecida!" );
			}
			else
			{
				update_status ( );
			}
		}
		
		@Override
		public void onPaperStateChanged ( boolean b )
		{
			if ( b )
			{
				status ( "Impressora sem papel!" );
			}
			else
			{
				update_status ( );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public class ConnectionListenerImpl implements Printer.ConnectionListener
	{
		@Override
		public void onDisconnect ( )
		{
			new Thread ( new Runnable ( )
			{
				@Override
				public void run ( )
				{
					update_status ( );
					//
					if ( ! ( ( AppCompatActivity ) m_pWnd ).isFinishing ( ) )
					{
						close_active_connection ( );
					}
				}
			} ).start ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class PrinterCupomTask extends Thread
	{
		protected StringBuffer    m_pBuffer = new StringBuffer ( );
		protected display_message m_pDlg    = null;
		
		//-----------------------------------------------------------------------------------------------------------------//
		public PrinterCupomTask ( StringBuffer pData )
		{
			super ( );
			this.setName ( this.getClass ( )
			                   .getName ( ) );
			this.m_pBuffer.append ( pData );
			this.m_pDlg = new display_message ( m_pWnd,
			                                    R.string.ids_wait,
			                                    R.string.print_cupom
			);
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void run ( )
		{
			try
			{
				m_pWnd.runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						m_pDlg.show ( );
					}
				} );
				
				m_printer.reset ( );
				m_pBuffer.append ( "\n\n" );
	         /*printer.printSelfTest();*/
				m_printer.printText ( m_pBuffer.toString ( ) );
				m_printer.flush ( );
			}
			catch ( IOException e )
			{
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
			catch ( Exception e )
			{
				Log.e ( this.getClass ( )
				            .getName ( ),
				        e.getMessage ( )
				      );
			}
			
			m_pWnd.runOnUiThread ( new Runnable ( )
			{
				@Override
				public void run ( )
				{
					m_pDlg.dismiss ( );
				}
			} );
		}
	}
}
