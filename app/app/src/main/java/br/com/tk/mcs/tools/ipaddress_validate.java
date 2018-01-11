/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
   This class provides methods to validate a condidate IP address

 */
package br.com.tk.mcs.tools;

/**
 * Created by wilsonsouza on 11/17/16.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ipaddress_validate implements Serializable
{
	public static final  String             TAG                           = ipaddress_validate.class.getName ( );
	private static final int                MAX_UNSIGNED_SHORT            = 0xffff;
	private static final int                BASE_16                       = 16;
	private static final String             IPV4_REGEX                    = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
	// Max number of hex groups (separated by :) in an IPV6 address
	private static final int                IPV6_MAX_HEX_GROUPS           = 8;
	// Max hex digits in each IPv6 group
	private static final int                IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;
	/**
	 * Singleton instance of this class.
	 */
	private static final ipaddress_validate VALIDATOR                     = new ipaddress_validate ( );
	
	/**
	 * Returns the singleton instance of this validator.
	 *
	 * @return the singleton instance of this validator
	 */
	public static ipaddress_validate get_instance ( )
	{
		return VALIDATOR;
	}
	
	/**
	 * Checks if the specified string is a valid IP address.
	 *
	 * @param inetAddress the string to validate
	 * @return true if the string validates as an IP address
	 */
	public boolean is_valid ( String inetAddress )
	{
		return is_valid_inet4_address ( inetAddress ) || is_valid_inet6_address ( inetAddress );
	}
	
	/**
	 * Validates an IPv4 address. Returns true if valid.
	 *
	 * @param inet4Address the IPv4 address to validate
	 * @return true if the argument contains a valid IPv4 address
	 */
	public boolean is_valid_inet4_address ( String inet4Address )
	{
		// verify that address conforms to generic IPv4 format
		return Pattern.matches ( IPV4_REGEX,
		                         inet4Address
		                       );
	}
	
	/**
	 * Validates an IPv6 address. Returns true if valid.
	 *
	 * @param inet6Address the IPv6 address to validate
	 * @return true if the argument contains a valid IPv6 address
	 * @since 1.4.1
	 */
	public boolean is_valid_inet6_address ( String inet6Address )
	{
		boolean containsCompressedZeroes = inet6Address.contains ( "::" );
		if ( containsCompressedZeroes && ( inet6Address.indexOf ( "::" ) != inet6Address.lastIndexOf ( "::" ) ) )
		{
			return false;
		}
		if ( ( inet6Address.startsWith ( ":" ) && ! inet6Address.startsWith ( "::" ) ) || ( inet6Address.endsWith ( ":" ) && ! inet6Address.endsWith ( "::" ) ) )
		{
			return false;
		}
		String[] octets = inet6Address.split ( ":" );
		
		if ( containsCompressedZeroes )
		{
			List<String> octetList = new ArrayList<String> ( Arrays.asList ( octets ) );
			if ( inet6Address.endsWith ( "::" ) )
			{
				// String.split() drops ending empty segments
				octetList.add ( "" );
			}
			else if ( inet6Address.startsWith ( "::" ) && ! octetList.isEmpty ( ) )
			{
				octetList.remove ( 0 );
			}
			octets = octetList.toArray ( new String[ octetList.size ( ) ] );
		}
		if ( octets.length > IPV6_MAX_HEX_GROUPS )
		{
			return false;
		}
		int validOctets = 0;
		int emptyOctets = 0;
		
		for ( int index = 0; index < octets.length; index++ )
		{
			String octet = octets[ index ];
			if ( octet.length ( ) == 0 )
			{
				emptyOctets++;
				if ( emptyOctets > 1 )
				{
					return false;
				}
			}
			else
			{
				emptyOctets = 0;
				if ( octet.contains ( "." ) )
				{ // contains is Java 1.5+
					if ( ! inet6Address.endsWith ( octet ) )
					{
						return false;
					}
					if ( index > octets.length - 1 || index > 6 )
					{  // CHECKSTYLE IGNORE MagicNumber
						// IPV4 occupies last two octets
						return false;
					}
					if ( ! is_valid_inet4_address ( octet ) )
					{
						return false;
					}
					validOctets += 2;
					continue;
				}
				if ( octet.length ( ) > IPV6_MAX_HEX_DIGITS_PER_GROUP )
				{
					return false;
				}
				int octetInt = 0;
				try
				{
					octetInt = Integer.valueOf ( octet,
					                             BASE_16
					                           )
					                  .intValue ( );
				}
				catch ( NumberFormatException e )
				{
					return false;
				}
				if ( octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT )
				{
					return false;
				}
			}
			validOctets++;
		}
		
		if ( validOctets < IPV6_MAX_HEX_GROUPS && ! containsCompressedZeroes )
		{
			return false;
		}
		return true;
	}
}