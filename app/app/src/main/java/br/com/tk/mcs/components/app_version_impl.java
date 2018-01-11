/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 Tecsidel

   Created by wilson.souza (WR DevInfo (c) 2017)

   Description: get current application version information.
 */
package br.com.tk.mcs.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.util.Log;

/**
 * Created by wilsonsouza on 08/11/2017.
 */

public class app_version_impl extends Object
{
	public static final String TAG = app_version_impl.class.getName ( );
	
	private String m_VERSION_NAME;
	private int m_VERSION_CODE = 0;
	private String m_PACKAGE_NAME;
	private Context m_context = null;
	
	//--------------------------------------------------------------------------------------------//
	public app_version_impl ( Context context )
	{
		try
		{
			m_context = context;
			PackageInfo p = m_context.getPackageManager ( )
			                         .getPackageInfo ( m_context.getPackageName ( ),
			                                           0
			                                         );
			m_VERSION_CODE = p.versionCode;
			m_VERSION_NAME = p.versionName;
			m_PACKAGE_NAME = p.packageName;
		}
		catch ( Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//--------------------------------------------------------------------------------------------//
	@TargetApi( 21 )
	public final PackageInstaller get_package_installer ( )
	{
		return m_context.getPackageManager ( )
		                .getPackageInstaller ( );
	}
	
	//--------------------------------------------------------------------------------------------//
	public final String get_package_name ( )
	{
		return this.m_PACKAGE_NAME;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final PackageInfo get_active_package_name ( )
	{
		try
		{
			return m_context.getPackageManager ( )
			                .getPackageInfo ( m_context.getPackageName ( ),
			                                  0
			                                );
		}
		catch ( Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
		return null;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final int get_version_code ( )
	{
		return m_VERSION_CODE;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final String get_version_name ( )
	{
		return m_VERSION_NAME;
	}
}
