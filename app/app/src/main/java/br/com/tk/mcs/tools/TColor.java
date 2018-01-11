/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.tools;

/**
 * Created by revolution on 25/02/16.
 */

public enum TColor
{
	Red ( "#cc0000" ),
	Green ( "#4ca64c" ),
	Black ( "#000000" );
	
	public static final String TAG    = TColor.class.getName ( );
	public static final int    YELLOW = 0xFFffff4c;
	public static final int    GREEN  = 0xFF4ca64c;
	public static final int    RED    = 0xFFcc0000;
	public static final int    BLUE   = 0xFF8b9dc3;
	public static final int    GRAY   = 0xFFb2b2b2;
	private final String m_Name;
	
	//-----------------------------------------------------------------------------------------------------------------//
	private TColor ( String name )
	{
		m_Name = name;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static String get_text ( TColor color,
	                                String text
	                              )
	{
		return "<font color='" + color + "'><big>" + text + "</big></font>";
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean name_is_equals ( String otherName )
	{
		if ( otherName != null )
		{
			return m_Name.equals ( otherName );
		}
		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public String toString ( )
	{
		return m_Name;
	}
}