/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.spinner_impl;
import br.com.tk.mcs.database.nosql_persistence_controller_square_ip;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_layout_impl;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.main.Main;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Login extends linear_vertical implements TextWatcher,
                                                      View.OnClickListener,
                                                      AdapterView.OnItemSelectedListener
{
	public static final  String        TAG              = Login.class.getName ( );
	private static final int           LENGTH           = 0x1e;
	private              edittext_impl m_login          = new edittext_impl ( getContext ( ), R.string.main_user, LENGTH );
	private              edittext_impl m_pass           = new edittext_impl ( getContext ( ), R.string.main_pass, LENGTH );
	private              spinner_impl  m_square_list    = new spinner_impl ( getContext ( ), R.string.ids_login_square_ip );
	private              Main          m_handle         = null;
	private              String        m_square_list_ip = "";
	
	//--------------------------------------------------------------------------------------------//
	public Login ( Context context,
	               final linear_layout_impl layout
	             )
	{
		super ( context );
		
		this.m_handle = ( Main ) ( ( AppCompatActivity ) context );
		this.Params.gravity = Gravity.CENTER;
		
		m_login.set_border ( true )
		       .set_border ( true )
		       .set_input_type ( InputType.TYPE_CLASS_NUMBER );
		m_pass.set_border ( true )
		      .set_enabled ( true )
		      .set_input_type ( InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD );
		m_pass.get_handle ( )
		      .addTextChangedListener ( this );
		m_login.set_border ( true )
		       .set_enabled ( true )
		       .get_handle ( )
		       .addTextChangedListener ( this );
		/**/
		m_login.Params.gravity = Gravity.RIGHT;
		m_pass.Params.gravity = Gravity.RIGHT;
		m_square_list.Params.gravity = Gravity.RIGHT;
		/**/
		this.m_square_list.set_default_items ( this.plazas_to_arraylist ( ) )
		                  .set_border ( true )
		                  .set_enabled ( true );
		this.m_square_list.get_handle ( )
		                  .setOnItemSelectedListener ( this );
		
		layout.addView ( m_login );
		layout.addView ( m_login.alloc_space ( 0, config_display_metrics.space_between_controls ) );
		layout.addView ( m_pass );
		layout.addView ( m_pass.alloc_space ( 0, config_display_metrics.space_between_controls ) );
		layout.addView ( this.m_square_list );
		
		this.m_pass.set_cursor ( true );
		this.m_login.set_cursor ( true );
		this.set_ip_selection ( );
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public void beforeTextChanged ( CharSequence s,
	                                int start,
	                                int count,
	                                int after
	                              )
	{
	
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public void onTextChanged ( CharSequence s,
	                            int start,
	                            int before,
	                            int count
	                          )
	{
		String user = m_login.get_data ( );
		String pass = m_pass.get_data ( );
		   /**/
		m_handle.get_manager_handle ( )
		        .get_actions ( )
		        .get_clear ( )
		        .set_enabled ( user.length ( ) > 0 );
		m_handle.get_manager_handle ( )
		        .get_actions ( )
		        .get_login ( )
		        .set_enabled ( user.length ( ) > 0 && pass.length ( ) > 0 );
	}
	
	//--------------------------------------------------------------------------------------------//
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Login set_ip_selection ( )
	{
		nosql_persistence_controller_square_ip square_ip = new nosql_persistence_controller_square_ip ( this.getContext ( ) );
		company_setup.plazas                   square    = square_ip.select ( );
		ArrayList<String>                      base      = this.plazas_to_arraylist ( );
		
		this.m_square_list.get_handle ( )
		                  .setSelection ( 0 );
		
		for ( int i = 0; i < base.size ( ); i++ )
		{
			String name = base.get ( i );
			
			if ( name.equals ( square.get_name ( ) ) )
			{
				this.m_square_list.get_handle ( )
				                  .setSelection ( i );
				break;
			}
		}
		
		return this;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final edittext_impl get_password ( )
	{
		//
		return m_pass;
	}
	
	//--------------------------------------------------------------------------------------------//
	public final edittext_impl get_user ( )
	{
		//
		return m_login;
	}
	
	//--------------------------------------------------------------------------------------------//
	public void clear_fields ( )
	{
		m_pass.get_handle ( )
		      .setText ( "" );
		m_login.get_handle ( )
		       .setText ( "" );
		m_login.get_handle ( )
		       .requestFocus ( );
		m_login.get_handle ( )
		       .setCursorVisible ( true );
		m_square_list_ip = "";
	}
	
	//--------------------------------------------------------------------------------------------//
	private final ArrayList<String> plazas_to_arraylist ( )
	{
		ArrayList<String> array_list = new ArrayList<> ( );
		//
		for ( company_setup.plazas plazas : company_setup.get_plazas_list ( ) )
		{
			array_list.add ( plazas.get_name ( ) );
		}
		return array_list;
	}
	
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick ( View v )
	{
		final int position = this.m_square_list.get_handle ( )
		                                       .getSelectedItemPosition ( );
		final String select_item = ( String ) this.m_square_list.get_handle ( )
		                                                        .getItemAtPosition ( position );
		company_setup.plazas plazas = company_setup.find_plazas ( select_item );
		
		if ( plazas != null )
		{
			nosql_persistence_controller_square_ip square_ip = new nosql_persistence_controller_square_ip ( this.getContext ( ) );
			
			this.m_square_list_ip = plazas.get_ip ( );
			square_ip.update ( this.m_square_list_ip, plazas.get_name ( ), plazas.get_alias ( ) );
		}
	}
	
	/**
	 * <p>Callback method to be invoked when an item in this view has been
	 * selected. This callback is invoked only when the newly selected
	 * position is different from the previously selected position or if
	 * there was no selected item.</p>
	 * <p>
	 * Impelmenters can call getItemAtPosition(position) if they need to access the
	 * data associated with the selected item.
	 *
	 * @param parent   The AdapterView where the selection happened
	 * @param view     The view within the AdapterView that was clicked
	 * @param position The position of the view in the adapter
	 * @param id       The row id of the item that is selected
	 */
	@Override
	public void onItemSelected ( AdapterView<?> parent,
	                             View view,
	                             int position,
	                             long id
	                           )
	{
		this.m_square_list_ip = ( String ) m_square_list.get_handle ( )
		                                                .getItemAtPosition ( position );
		this.onTextChanged ( this.m_square_list_ip, 1, 1, 1 );
	}
	
	/**
	 * Callback method to be invoked when the selection disappears from this
	 * view. The selection can disappear for instance when touch is activated
	 * or when the adapter becomes empty.
	 *
	 * @param parent The AdapterView that now contains no selected item.
	 */
	@Override
	public void onNothingSelected ( AdapterView<?> parent )
	{
		//
		this.m_square_list_ip = "";
	}
}
