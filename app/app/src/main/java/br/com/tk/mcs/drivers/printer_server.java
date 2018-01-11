/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description: managed bluetooth device node.
 */
package br.com.tk.mcs.drivers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wilsonsouza on 10/10/16.
 */

public class printer_server implements Runnable
{
	private static final int                     DEFAULT_PORT              = 8006;
	private              ServerSocket            m_socket                  = null;
	private              printer_server_listener m_printer_server_listener = null;
	private              boolean                 m_is_running              = false;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public printer_server ( printer_server_listener listener ) throws
	                                                            IOException
	{
		this ( DEFAULT_PORT,
		       listener
		     );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public printer_server ( int default_port,
	                        printer_server_listener listener
	                      ) throws
	                        IOException
	{
		if ( listener == null )
		{
			throw new NullPointerException ( "parameter listener is null!" );
		}
		//
		m_printer_server_listener = listener;
		m_socket = new ServerSocket ( default_port,
		                              1
		);
		m_is_running = true;
		//
		Thread pt = new Thread ( );
		{
			pt.start ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void run ( )
	{
		while ( m_is_running )
		{
			Socket ps = null;
			//
			try
			{
				ps = m_socket.accept ( );
				ps.setKeepAlive ( true );
				ps.setTcpNoDelay ( true );
			}
			catch ( IOException e )
			{
				break;
			}
			//
			try
			{
				m_printer_server_listener.OnConnect ( ps );
			}
			catch ( Exception e )
			{
			
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void close ( ) throws
	                      IOException
	{
		m_is_running = false;
		m_socket.close ( );
	}
}
