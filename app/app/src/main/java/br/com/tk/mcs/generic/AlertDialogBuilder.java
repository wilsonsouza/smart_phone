/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

/**
 * Created by revolution on 23/01/16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;

import br.com.tk.mcs.R;

public class AlertDialogBuilder
{
	public static final  String TAG            = AlertDialogBuilder.class.getName ( );
	private static final int    MESSAGE_ALERT  = 1;
	private static final int    CONFIRM_ALERT  = 2;
	private static final int    DECISION_ALERT = 3;
	private static final int    INPUT_ALERT    = 4;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void messageAlert ( Context ctx,
	                                  String title
	                                )
	{
		showAlertDialog ( MESSAGE_ALERT,
		                  ctx,
		                  title,
		                  "",
		                  TLayout.NONE,
		                  TButtons.JUST_OK,
		                  null
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void messageAlert ( Context ctx,
	                                  String title,
	                                  String message
	                                )
	{
		showAlertDialog ( MESSAGE_ALERT,
		                  ctx,
		                  title,
		                  message,
		                  TLayout.NONE,
		                  TButtons.JUST_OK,
		                  null
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void confirmationAlert ( Context ctx,
	                                       String title,
	                                       DialogInterface.OnClickListener callBack
	                                     )
	{
		showAlertDialog ( CONFIRM_ALERT,
		                  ctx,
		                  title,
		                  "",
		                  TLayout.NONE,
		                  TButtons.JUST_OK,
		                  callBack
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void confirmationAlert ( Context ctx,
	                                       String title,
	                                       String message,
	                                       DialogInterface.OnClickListener callBack
	                                     )
	{
		showAlertDialog ( CONFIRM_ALERT,
		                  ctx,
		                  title,
		                  message,
		                  TLayout.NONE,
		                  TButtons.JUST_OK,
		                  callBack
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void decisionAlert ( Context ctx,
	                                   String title,
	                                   TButtons btn,
	                                   DialogInterface.OnClickListener posCallback
	                                 )
	{
		showAlertDialog ( DECISION_ALERT,
		                  ctx,
		                  title,
		                  null,
		                  TLayout.NONE,
		                  btn,
		                  posCallback
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void decisionAlert ( Context ctx,
	                                   String title,
	                                   String message,
	                                   TButtons btn,
	                                   DialogInterface.OnClickListener posCallback
	                                 )
	{
		showAlertDialog ( DECISION_ALERT,
		                  ctx,
		                  title,
		                  message,
		                  TLayout.NONE,
		                  btn,
		                  posCallback
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void inputAlert ( Context ctx,
	                                String title,
	                                TLayout lyt,
	                                TButtons btn,
	                                DialogInterface.OnClickListener posCallback
	                              )
	{
		showAlertDialog ( INPUT_ALERT,
		                  ctx,
		                  title,
		                  "",
		                  lyt,
		                  btn,
		                  posCallback
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void inputAlert ( Context ctx,
	                                String title,
	                                String message,
	                                TLayout lyt,
	                                TButtons btn,
	                                DialogInterface.OnClickListener posCallback
	                              )
	{
		showAlertDialog ( INPUT_ALERT,
		                  ctx,
		                  title,
		                  message,
		                  lyt,
		                  btn,
		                  posCallback
		                );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static String[] getButtonsNames ( TButtons btn )
	{
		final String[] buttonNames;
		
		switch ( btn )
		{
			
			case JUST_OK:
				buttonNames = new String[]{ "OK" };
				break;
			
			case CONFIRM_AND_CANCEL:
				buttonNames = new String[]{ "CONFIRMAR",
				                            "CANCELAR"
				};
				break;
			
			case CONFIRM_READBARCODE_CANCEL:
				buttonNames = new String[]{ "CONFIRMAR",
				                            "LER BARCODE",
				                            "CANCELAR"
				};
				break;
			
			case SEARCH_AND_CANCEL:
				buttonNames = new String[]{ "CONSULTAR",
				                            "CANCELAR"
				};
				break;
			
			default:
				buttonNames = new String[]{ "OK" };
				break;
		}
		
		return buttonNames;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void showAlertDialog ( int alertType,
	                                     Context ctx,
	                                     String title,
	                                     String message,
	                                     TLayout lyt,
	                                     TButtons btn,
	                                     DialogInterface.OnClickListener posCallback
	                                   )
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder ( ctx );
		final String[]            buttonNames;
		
		if ( ! title.isEmpty ( ) )
		{
			builder.setTitle ( title );
		}
		
		if ( ! message.isEmpty ( ) )
		{
			builder.setMessage ( Html.fromHtml ( message ) );
		}
		
		builder.setCancelable ( false );
		buttonNames = getButtonsNames ( btn );
		
		switch ( alertType )
		{
			case MESSAGE_ALERT:
				break;
			
			case CONFIRM_ALERT:
				break;
			
			case DECISION_ALERT:
				builder.setPositiveButton ( buttonNames[ 0 ],
				                            posCallback
				                          );
				break;
			
			case INPUT_ALERT:
				LayoutInflater inflater = LayoutInflater.from ( ctx );
				
				switch ( lyt )
				{
					case RDMONEY:
						builder.setView ( inflater.inflate ( R.layout.rdmoney_view,
						                                     null
						                                   ) );
						break;
					case RDTAG:
						builder.setView ( inflater.inflate ( R.layout.rdtag_view,
						                                     null
						                                   ) );
						break;
					default:
						builder.setView ( inflater.inflate ( R.layout.tagplate_view,
						                                     null
						                                   ) );
						break;
				}
				
				builder.setPositiveButton ( buttonNames[ 0 ],
				                            posCallback
				                          );
				/*
		      if(lyt == TLayout.RDTAG)
            {
               builder.setNeutralButton(buttonNames[1], posCallback);
            }*/
		}
		//
		builder.setNegativeButton ( buttonNames[ buttonNames.length - 1 ],
		                            new DialogInterface.OnClickListener ( )
		                            {
			                            @Override
			                            public void onClick ( DialogInterface dialogInterface,
			                                                  int i
			                                                )
			                            {
				                            dialogInterface.dismiss ( );
			                            }
		                            }
		                          );
		
		AlertDialog alert = builder.create ( );
		alert.show ( );
	}
	
	public enum TButtons
	{
		JUST_OK,
		CONFIRM_AND_CANCEL,
		SEARCH_AND_CANCEL,
		CONFIRM_READBARCODE_CANCEL
	}
	
	public enum TLayout
	{
		NONE,
		TAGPLATE,
		RDMONEY,
		RDTAG
	}
}