/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.main;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.tk.mcs.R;
import br.com.tk.mcs.database.nosql_persistence_controller_square_ip;
import br.com.tk.mcs.generic.field_text_set_max_length;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.tools.Utils;
import br.com.tk.mcs.tools.network;

import static android.graphics.Bitmap.createScaledBitmap;
import static android.widget.GridLayout.ALIGN_BOUNDS;

public class CaptureImagesOfMCS extends AppCompatActivity implements android.view.View.OnClickListener,
                                                                     java.io.Serializable
{
	TextView             m_GroupView;
	TextView             m_NumberView;
	TextView             m_TagView;
	TextView             m_NumberView1;
	Spinner              m_Group;
	EditText             m_NumberEdit;
	EditText             m_NumberEdit1;
	EditText             m_TagEdit;
	AppCompatRadioButton m_Isento;
	AppCompatRadioButton m_Tag;
	AppCompatRadioButton m_Money;
	ImageView            m_bmp;
	Button m_Accept = null;
	Button                                 m_Cancel;
	GridLayout                             m_Child;
	RelativeLayout                         m_Main;
	LinearLayout                           m_Left;
	nosql_persistence_controller_square_ip m_DbSquare;
	Thread                 m_Check = null;
	android.graphics.Point m_area  = new android.graphics.Point ( 800,
	                                                              600
	);
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onCreate ( Bundle pSavedInstanceState )
	{
		super.onCreate ( pSavedInstanceState );
		setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
		this.setTitle ( R.string.app_name );
		this.setContentView ( R.layout.activity_get_image_of_msc );
		this.getWindow ( )
		    .setSoftInputMode ( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
		//
		m_DbSquare = new nosql_persistence_controller_square_ip ( this );
		//
		this.InitializeViews ( );
		this.LoadPanel ( );
		this.LoadImageView ( );
		this.LoadButtons ( );
		this.CheckFields ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void InitializeViews ( )
	{
		m_Main = ( RelativeLayout ) findViewById ( R.id.id_get_image );
		m_Child = new GridLayout ( this );
		{
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams ( m_Main.getLayoutParams ( ) );
			m_Child.setColumnCount ( 2 );
			m_Child.setRowCount ( 2 );
			m_Main.addView ( m_Child,
			                 lp
			               );
		}
		m_Left = new LinearLayout ( this );
		{
			m_Left.setLayoutParams ( new LinearLayout.LayoutParams ( LinearLayout.LayoutParams.MATCH_PARENT,
			                                                         LinearLayout.LayoutParams.MATCH_PARENT
			) );
			m_Left.setOrientation ( LinearLayout.VERTICAL );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void CheckFields ( )
	{
		this.m_Check = new Thread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				while ( m_Accept != null )
				{
					final boolean bIsento = m_NumberEdit.getText ( )
					                                    .length ( ) > 0 && m_Isento.isChecked ( );
					final boolean bTag = m_NumberEdit1.getText ( )
					                                  .length ( ) > 0 || m_TagEdit.getText ( )
					                                                              .length ( ) > 0;
					
					runOnUiThread ( new Runnable ( )
					{
						@Override
						public void run ( )
						{
							m_Accept.setEnabled ( bIsento || ( bTag && m_Tag.isChecked ( ) ) );
						}
					} );
					
					try
					{
						Thread.sleep ( 0x32 );
					}
					catch ( Exception e )
					{
						Log.e ( getClass ( ).getName ( ),
						        e.getMessage ( )
						      );
					}
				}
			}
		} );
		
		this.m_Check.start ( );
		this.m_Check.setName ( "FIELDS_MONITOR" );
		this.m_Check.setPriority ( Thread.MIN_PRIORITY );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	String MountImageLink ( )
	{
		String szIP = m_DbSquare.select ( )
		                        .get_ip ( ) + ":8181/mcs/GetImage.aspx?name=";
		{
			szIP += this.getIntent ( )
			            .getStringExtra ( Utils.MSC_IMAGE ) + "-F01.JPG";
		}
		return szIP;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( 19 )
	void LoadImageView ( )
	{
		android.graphics.Point pt = new android.graphics.Point ( );
		this.getWindowManager ( )
		    .getDefaultDisplay ( )
		    .getSize ( pt );
		//
		if ( pt.x < ( m_area.x + ( m_area.x / 2 ) ) )
		{
			m_area.x /= 2;
			m_area.y /= 2;
		}
		m_bmp = new ImageView ( this );
		{
			GridLayout.LayoutParams p = new GridLayout.LayoutParams ( );
			{
				p.setGravity ( Gravity.CENTER );
				p.width = m_area.x;
				p.height = m_area.y;
			}
			
			String szDir = MountImageLink ( );
			//"http://172.16.62.62:8181/mcs/GetImage.aspx?name=060115SAU201612141054094641-F01.JPG";
			DownloadTask pTask = new DownloadTask ( );
			{
				pTask.execute ( szDir );
				
				try
				{
					//Bitmap pScale = createScaledBitmap(bitmap, 800, 600, true);
					m_bmp.setPadding ( 2,
					                   2,
					                   0,
					                   2
					                 );
					m_bmp.setMaxHeight ( m_area.x );
					m_bmp.setMaxWidth ( m_area.y );
					//m_bmp.setImageBitmap(pScale);
					DrawBorder ( m_bmp );
				}
				catch ( NullPointerException e )
				{
					e.printStackTrace ( );
				}
			}
			m_Child.addView ( m_bmp,
			                  p
			                );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void SetFieldEditState ( boolean bValue,
	                         int nID
	                       )
	{
		switch ( nID )
		{
			case 0:
				this.m_Group.setEnabled ( bValue );
				this.m_NumberEdit.setEnabled ( bValue );
				this.m_TagEdit.setEnabled ( ! bValue );
				this.m_NumberEdit1.setEnabled ( ! bValue );
				break;
			case 1:
				this.m_TagEdit.setEnabled ( bValue );
				this.m_NumberEdit1.setEnabled ( bValue );
				this.m_Group.setEnabled ( ! bValue );
				this.m_NumberEdit.setEnabled ( ! bValue );
				break;
			case 2:
				this.m_Group.setEnabled ( bValue );
				this.m_NumberEdit.setEnabled ( bValue );
				this.m_NumberEdit1.setEnabled ( bValue );
				this.m_TagEdit.setEnabled ( bValue );
				this.m_Isento.setChecked ( bValue );
				this.m_Tag.setChecked ( bValue );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void LoadPanel ( )
	{
		int        nInput = android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;
		GridLayout pPanel = new GridLayout ( this );
		{
			pPanel.setBackgroundColor ( Color.WHITE );
			pPanel.setColumnCount ( 2 );
			pPanel.setRowCount ( 7 );
			pPanel.setAlignmentMode ( ALIGN_BOUNDS );
			pPanel.setPadding ( 0,
			                    0,
			                    8,
			                    0
			                  );
			
			GridLayout.LayoutParams p = new GridLayout.LayoutParams ( );
			{
				p.setGravity ( Gravity.CENTER | Gravity.CENTER_VERTICAL );
			}
			pPanel.setLayoutParams ( p );
		}
		//
		m_Isento = new AppCompatRadioButton ( this );
		{
			m_Isento.setText ( getString ( R.string.ids_isento ) );
			this.SetColorStateList ( m_Isento );
			pPanel.addView ( m_Isento );
			pPanel.addView ( new Space ( this ) );
			m_Isento.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( )
			{
				@Override
				public void onCheckedChanged ( CompoundButton buttonView,
				                               boolean isChecked
				                             )
				{
					m_Tag.setChecked ( false );
					m_Money.setChecked ( false );
					SetFieldEditState ( false,
					                    1
					                  );
				}
			} );
		}
		m_GroupView = new TextView ( this );
		{
			m_GroupView.setText ( getString ( R.string.ids_group ) );
			m_GroupView.setPadding ( 0x30,
			                         8,
			                         8,
			                         8
			                       );
			pPanel.addView ( m_GroupView );
		}
		m_Group = new Spinner ( this );
		{
			m_Group.setGravity ( Gravity.RIGHT );
			m_Group.setPadding ( 8,
			                     8,
			                     8,
			                     0
			                   );
			pPanel.addView ( m_Group );
		}
		m_NumberView = new TextView ( this );
		{
			m_NumberView.setText ( getString ( R.string.ids_number_id ) );
			m_NumberView.setPadding ( 0x30,
			                          8,
			                          8,
			                          8
			                        );
			pPanel.addView ( m_NumberView );
		}
		m_NumberEdit = new EditText ( this );
		{
			m_NumberEdit.setWidth ( 220 );
			m_NumberEdit.setPadding ( 8,
			                          8,
			                          8,
			                          0
			                        );
			m_NumberEdit.setSingleLine ( );
			DrawBorder ( m_NumberEdit );
			field_text_set_max_length.set_length ( m_NumberEdit,
			                                       7,
			                                       nInput
			                                     );
			this.m_NumberEdit.addTextChangedListener ( new android.text.TextWatcher ( )
			{
				@Override
				public void beforeTextChanged ( CharSequence s,
				                                int start,
				                                int count,
				                                int after
				                              )
				{
				
				}
				
				@Override
				public void onTextChanged ( CharSequence s,
				                            int start,
				                            int before,
				                            int count
				                          )
				{
					m_Accept.setEnabled ( s.length ( ) > 0 && m_Isento.isChecked ( ) );
				}
				
				@Override
				public void afterTextChanged ( android.text.Editable s )
				{
				
				}
			} );
			pPanel.addView ( m_NumberEdit );
		}
		m_Tag = new AppCompatRadioButton ( this );
		{
			m_Tag.setPadding ( 8,
			                   8,
			                   8,
			                   8
			                 );
			m_Tag.setText ( getString ( R.string.ids_tag ) );
			this.SetColorStateList ( m_Tag );
			pPanel.addView ( m_Tag );
			pPanel.addView ( new Space ( this ) );
			m_Tag.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					m_Isento.setChecked ( false );
					m_Money.setChecked ( false );
					SetFieldEditState ( false,
					                    0
					                  );
				}
			} );
		}
		m_TagView = new TextView ( this );
		{
			m_TagView.setText ( getString ( R.string.ids_tag ) );
			m_TagView.setPadding ( 0x30,
			                       8,
			                       8,
			                       8
			                     );
			pPanel.addView ( m_TagView );
		}
		m_TagEdit = new EditText ( this );
		{
			m_TagEdit.setWidth ( 220 );
			m_TagEdit.setPadding ( 8,
			                       8,
			                       8,
			                       0
			                     );
			m_TagEdit.setSingleLine ( );
			this.DrawBorder ( m_TagEdit );
			field_text_set_max_length.set_length ( this.m_TagEdit,
			                                       10,
			                                       nInput
			                                     );
			pPanel.addView ( m_TagEdit );
			m_TagEdit.addTextChangedListener ( new android.text.TextWatcher ( )
			{
				@Override
				public void beforeTextChanged ( CharSequence s,
				                                int start,
				                                int count,
				                                int after
				                              )
				{
				
				}
				
				@Override
				public void onTextChanged ( CharSequence s,
				                            int start,
				                            int before,
				                            int count
				                          )
				{
					m_Accept.setEnabled ( s.length ( ) > 0 && m_Tag.isChecked ( ) );
				}
				
				@Override
				public void afterTextChanged ( android.text.Editable s )
				{
				
				}
			} );
		}
		m_NumberView1 = new TextView ( this );
		{
			m_NumberView1.setText ( getString ( R.string.ids_number_id ) );
			m_NumberView1.setPadding ( 0x30,
			                           8,
			                           8,
			                           8
			                         );
			pPanel.addView ( m_NumberView1 );
		}
		m_NumberEdit1 = new EditText ( this );
		{
			m_NumberEdit1.setWidth ( 220 );
			m_NumberEdit1.setPadding ( 8,
			                           8,
			                           8,
			                           0
			                         );
			m_NumberEdit1.setSingleLine ( );
			this.DrawBorder ( m_NumberEdit1 );
			field_text_set_max_length.set_length ( this.m_NumberEdit1,
			                                       7,
			                                       nInput
			                                     );
			this.m_NumberEdit1.addTextChangedListener ( new android.text.TextWatcher ( )
			{
				@Override
				public void beforeTextChanged ( CharSequence s,
				                                int start,
				                                int count,
				                                int after
				                              )
				{
				
				}
				
				@Override
				public void onTextChanged ( CharSequence s,
				                            int start,
				                            int before,
				                            int count
				                          )
				{
					m_Accept.setEnabled ( s.length ( ) > 0 && m_Tag.isChecked ( ) );
				}
				
				@Override
				public void afterTextChanged ( android.text.Editable s )
				{
				
				}
			} );
			pPanel.addView ( m_NumberEdit1 );
		}
		this.m_Money = new AppCompatRadioButton ( this );
		{
			this.m_Money.setText ( getString ( R.string.ids_money ) );
			this.m_Money.setPadding ( 8,
			                          8,
			                          8,
			                          8
			                        );
			this.SetColorStateList ( this.m_Money );
			pPanel.addView ( this.m_Money );
			pPanel.addView ( new Space ( this ) );
			this.m_Money.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					SetFieldEditState ( false,
					                    2
					                  );
				}
			} );
			this.m_Money.setOnCheckedChangeListener ( new android.widget.CompoundButton.OnCheckedChangeListener ( )
			{
				@Override
				public void onCheckedChanged ( android.widget.CompoundButton buttonView,
				                               boolean isChecked
				                             )
				{
				
				}
			} );
		}
		LinearLayout c = new LinearLayout ( this );
		{
			LinearLayout.LayoutParams p = this.GetLinearParams ( ViewGroup.LayoutParams.WRAP_CONTENT,
			                                                     ViewGroup.LayoutParams.WRAP_CONTENT
			                                                   );
			{
				p.gravity = Gravity.FILL;
			}
			
			c.setLayoutParams ( p );
			c.setOrientation ( LinearLayout.VERTICAL );
			c.addView ( pPanel );
			m_Child.addView ( c );
		}
		
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void SetColorStateList ( final AppCompatRadioButton pOwner )
	{
		synchronized ( pOwner )
		{
			final ColorStateList pClrState = new ColorStateList ( new int[][]{ new int[]{ - android.R.attr.state_enabled
			},
			                                                                   new int[]{ android.R.attr.state_enabled
			                                                                   }
			},
			                                                      new int[]{
				
				                                                      Color.WHITE,
				                                                      Color.BLACK
			                                                      }
			);
			
			pOwner.setSupportButtonTintList ( pClrState );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( 21 )
	void LoadButtons ( )
	{
		m_Accept = new Button ( this );
		{
			m_Accept.setPadding ( 8,
			                      8,
			                      8,
			                      8
			                    );
			m_Accept.setEnabled ( false );
			m_Accept.setText ( getString ( R.string.ids_accept ) );
			m_Accept.setBackgroundResource ( br.com.tk.mcs.R.drawable.button_selector );
			m_Accept.setOnClickListener ( this );
		}
		m_Cancel = new Button ( this );
		{
			m_Cancel.setPadding ( 8,
			                      8,
			                      8,
			                      8
			                    );
			m_Cancel.setBackgroundResource ( R.drawable.button_selector );
			m_Cancel.setText ( getString ( R.string.ids_cancel ) );
			m_Cancel.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					finish ( );
				}
			} );
		}
		//
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams ( );
		{
			lp.setGravity ( Gravity.CENTER );
			
			LinearLayout pLinear = new LinearLayout ( this );
			{
				pLinear.setPadding ( 8,
				                     8,
				                     8,
				                     8
				                   );
				pLinear.setOrientation ( LinearLayout.HORIZONTAL );
				pLinear.addView ( m_Accept );
				pLinear.addView ( new Space ( this ),
				                  GetLinearParams ( 8,
				                                    8
				                                  )
				                );
				pLinear.addView ( m_Cancel );
				m_Child.addView ( new Space ( this ),
				                  GetLinearParams ( 8,
				                                    8
				                                  )
				                );
				m_Child.addView ( pLinear,
				                  lp
				                );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	LinearLayout.LayoutParams GetLinearParams ( int nWidth,
	                                            int nHeight
	                                          )
	{
		return new LinearLayout.LayoutParams ( nWidth,
		                                       nHeight
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void DrawBorder ( View pView )
	{
		GradientDrawable pDraw = new GradientDrawable ( );
		{
			pDraw.setColor ( Color.WHITE );
			pDraw.setStroke ( 1,
			                  Color.BLACK
			                );
			pView.setBackground ( pDraw );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onDestroy ( )
	{
		if ( this.m_Check != null )
		{
			this.m_Check.interrupt ( );
		}
		super.onDestroy ( );
		//add action after
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public synchronized void onResume ( )
	{
		super.onResume ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v )
	{
		final android.content.Context pWnd = this;
		//
		if ( v == this.m_Accept )
		{
			String szBoard = new String ( );
			//
			if ( ! this.m_NumberEdit.getText ( )
			                        .toString ( )
			                        .isEmpty ( ) )
			{
				szBoard = this.m_NumberEdit.getText ( )
				                           .toString ( );
			}
			//
			if ( ! this.m_NumberEdit1.getText ( )
			                         .toString ( )
			                         .isEmpty ( ) )
			{
				szBoard = this.m_NumberEdit1.getText ( )
				                            .toString ( )
				                            .trim ( );
			}
			//
			if ( szBoard.length ( ) != 7 )
			{
				android.support.v7.app.AlertDialog.Builder pDlg = new android.support.v7.app.AlertDialog.Builder ( pWnd );
				{
					pDlg.setTitle ( br.com.tk.mcs.R.string.manager_tag_field );
					pDlg.setMessage ( br.com.tk.mcs.remote.response.TagPlateResponse.get_text ( this,
					                                                                            br.com.tk.mcs.remote.response.TagPlateResponse.SizeError
					                                                                          ) );
					pDlg.setCancelable ( false );
					pDlg.setPositiveButton ( br.com.tk.mcs.R.string.button_ok,
					                         new android.content.DialogInterface.OnClickListener ( )
					                         {
						                         @Override
						                         public void onClick ( android.content.DialogInterface dialog,
						                                               int which
						                                             )
						                         {
							                         dialog.dismiss ( );
						                         }
					                         }
					                       );
				}
				return;
			}
			//
			final android.app.ProgressDialog pProgressDlg = new android.app.ProgressDialog ( this );
			{
				pProgressDlg.setTitle ( br.com.tk.mcs.R.string.manager_assign_title );
				pProgressDlg.setMessage ( this.getString ( br.com.tk.mcs.R.string.manager_assign_process ) );
				pProgressDlg.setIndeterminate ( true );
				pProgressDlg.setCancelable ( false );
				pProgressDlg.show ( );
			}
			//
			final String szPlate = szBoard;
			new Thread ( new Runnable ( )
			{
				@Override
				public void run ( )
				{
					br.com.tk.mcs.remote.response.RemotePaymentResponse pResult = br.com.tk.mcs.remote.response.RemotePaymentResponse.ResponseERROR;
					//
					try
					{
						br.com.tk.mcs.lane.Operations                                pOp  = GetCurrentOperations ( );
						br.com.tk.mcs.remote.response.RemotePaymentPermittedResponse pPay = pOp.is_remote_payment_permitted ( );
						{
							//pResult = pOp.make_remote_payment(pPay, szPlate);
						}
					}
					catch ( Exception e )
					{
						e.printStackTrace ( );
					}
					//
					pProgressDlg.dismiss ( );
					final br.com.tk.mcs.remote.response.RemotePaymentResponse pSuccess = pResult;
					//
					if ( pSuccess != br.com.tk.mcs.remote.response.RemotePaymentResponse.ResponseOK )
					{
						m_NumberEdit.post ( new Runnable ( )
						{
							@Override
							public void run ( )
							{
								android.support.v7.app.AlertDialog.Builder pAlert = new android.support.v7.app.AlertDialog.Builder ( pWnd );
								{
									pAlert.setTitle ( br.com.tk.mcs.R.string.manager_assign_title );
									pAlert.setMessage ( br.com.tk.mcs.remote.response.RemotePaymentResponse.get_text ( pWnd,
									                                                                                   pSuccess
									                                                                                 ) );
									pAlert.setCancelable ( false );
									pAlert.setPositiveButton ( R.string.button_ok,
									                           new DialogInterface.OnClickListener ( )
									                           {
										                           @Override
										                           public void onClick ( android.content.DialogInterface dialog,
										                                                 int which
										                                               )
										                           {
											                           dialog.dismiss ( );
										                           }
									                           }
									                         );
								}
							}
						} );
					}
				}
			} ).start ( );
			
			finish ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	Operations GetCurrentOperations ( )
	{
		Lane pLane = ( Lane ) this.getIntent ( )
		                          .getSerializableExtra ( Utils.LANE );
		return pLane.get_operations ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class DownloadTask extends android.os.AsyncTask<String, android.content.Intent, android.graphics.Bitmap>
	{
		@Override
		protected android.graphics.Bitmap doInBackground ( String... pUrl )
		{
			Bitmap bmp = null;
			try
			{
				bmp = network.download_bitmap ( pUrl[ 0 ] );
			}
			catch ( Exception e )
			{
				android.util.Log.d ( "Background task",
				                     e.toString ( )
				                   );
			}
			return bmp;
		}
		
		@Override
		protected void onPostExecute ( android.graphics.Bitmap bmp )
		{
			if ( bmp != null )
			{
				Bitmap img = createScaledBitmap ( bmp,
				                                  m_area.x,
				                                  m_area.y,
				                                  true
				                                );
				m_bmp.setImageBitmap ( img );
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
