/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.horizontal_scrollview_impl;
import br.com.tk.mcs.components.imagebutton;
import br.com.tk.mcs.dialogs_operations.DialogChangeOperator;
import br.com.tk.mcs.dialogs_operations.DialogCloseLane;
import br.com.tk.mcs.dialogs_operations.DialogOpenLane;
import br.com.tk.mcs.dialogs_operations.DialogPaymentMoney;
import br.com.tk.mcs.dialogs_operations.DialogPaymentTag;
import br.com.tk.mcs.dialogs_operations.DialogSearchFreeVehicle;
import br.com.tk.mcs.dialogs_operations.DialogSearchTag;
import br.com.tk.mcs.dialogs_operations.DialogSimulatePassenger;
import br.com.tk.mcs.dialogs_operations.DialogViolation;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.State.LaneState;
import br.com.tk.mcs.main.TrackManager;

/**
 * Created by wilsonsouza on 24/01/17.
 */

public class ProcessLaneActions extends horizontal_scrollview_impl implements View.OnClickListener
{
	public final static String               TAG            = ProcessLaneActions.class.getName ( );
	protected           imagebutton          Search         = null;
	protected           imagebutton          OpenLane       = null;
	protected           imagebutton          CloseLane      = null;
	protected           imagebutton          ChangeOperator = null;
	protected           imagebutton          PaymentMoney   = null;
	protected           imagebutton          PaymentTag     = null;
	protected           imagebutton          Violation      = null;
	protected           imagebutton          Free           = null;
	protected           imagebutton          Simulation     = null;
	private             int                  m_Mode         = imagebutton.UP;
	private             Point                m_offset       = config_display_metrics.ToolbarIcon;
	private             TrackManager         m_wnd          = null;
	private             DialogChangeOperator m_pDlgChangeOp = null;
	private             ProcessManager       m_Manager      = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public ProcessLaneActions ( Activity context )
	{
		super ( context,
		        false,
		        Color.TRANSPARENT,
		        null,
		        config_display_metrics.Padding
		      );
		
		this.m_wnd = ( TrackManager ) context;
		this.Search = new imagebutton ( this.getContext ( ),
		                                R.string.button_search,
		                                m_offset,
		                                R.drawable.ic_search_plate,
		                                false,
		                                m_Mode
		);
		this.OpenLane = new imagebutton ( this.getContext ( ),
		                                  R.string.button_open,
		                                  m_offset,
		                                  R.drawable.ic_openlane,
		                                  false,
		                                  m_Mode
		);
		this.CloseLane = new imagebutton ( this.getContext ( ),
		                                   R.string.button_close,
		                                   m_offset,
		                                   R.drawable.ic_closelane,
		                                   false,
		                                   m_Mode
		);
		this.ChangeOperator = new imagebutton ( this.getContext ( ),
		                                        R.string.button_responsible,
		                                        m_offset,
		                                        R.drawable.ic_operator,
		                                        false,
		                                        m_Mode
		);
		this.PaymentMoney = new imagebutton ( this.getContext ( ),
		                                      R.string.button_pay_money,
		                                      m_offset,
		                                      R.drawable.ic_paymoney,
		                                      false,
		                                      m_Mode
		);
		this.PaymentTag = new imagebutton ( this.getContext ( ),
		                                    R.string.button_pay_tag,
		                                    m_offset,
		                                    R.drawable.ic_paytag,
		                                    false,
		                                    m_Mode
		);
		this.Violation = new imagebutton ( this.getContext ( ),
		                                   R.string.button_violation,
		                                   m_offset,
		                                   R.drawable.violation,
		                                   false,
		                                   m_Mode
		);
		this.Free = new imagebutton ( this.getContext ( ),
		                              R.string.button_isento,
		                              m_offset,
		                              R.drawable.ic_free,
		                              false,
		                              m_Mode
		);
		this.Simulation = new imagebutton ( this.getContext ( ),
		                                    R.string.button_passenger_simulation,
		                                    m_offset,
		                                    R.drawable.ic_passenger_simulation,
		                                    false,
		                                    m_Mode
		);
		
		this.prepare_actions ( this.Search );
		this.prepare_actions ( this.OpenLane );
		this.prepare_actions ( this.CloseLane );
		/* remove button change operator resposanble */
		if ( company_setup.is_arteris )
		{
			this.prepare_actions ( this.ChangeOperator );
		}
		
		this.prepare_actions ( this.PaymentMoney );
		this.prepare_actions ( this.PaymentTag );
		
		if ( company_setup.is_tamoios || company_setup.is_tecsidel || company_setup.is_cro || company_setup.is_via_rio )
		{
			this.prepare_actions ( this.Simulation );
		}
		
		if ( company_setup.is_arteris )
		{
			this.prepare_actions ( this.Violation );
			this.prepare_actions ( this.Free );
		}
		
		this.Params.gravity = Gravity.BOTTOM;
		this.m_wnd.get_layout_handle ().addView ( this, this.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public ProcessLaneActions set_manager_pointer ( ProcessManager manager )
	{
		this.m_Manager = manager;
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	void prepare_actions ( imagebutton pView )
	{
		try
		{
			pView.setOnClickListener ( this );
			pView.set_margins ( new Rect ( 8,
			                               0,
			                               0,
			                               8
			) );
			
			this.get_contaneir_handle ( )
			    .addView ( pView, pView.Params );
		}
		catch ( Exception e )
		{
			Log.e ( TAG,
			        e.getMessage ( )
			      );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View pView )
	{
	   /* source code here */
		final Lane pLane = m_Manager.get_verify_lane_state_handle ( )
		                            .get_lane ( );
		final LaneState State = m_Manager.get_verify_lane_state_handle ( )
		                                 .get_state ( );
		
		if ( pView == this.Search )
		{
			new DialogSearchTag ( this.getContext ( ), m_Manager.get_builder_lanes_view_handle ( )
			                                                    .get_lane_items ( )
			);
		}
		else if ( pView == this.PaymentMoney )
		{
			new DialogPaymentMoney ( this.getContext ( ), pLane, m_Manager.get_printer_manager_handle ( ) );
		}
		else if ( pView == this.PaymentTag )
		{
			new DialogPaymentTag ( this.getContext ( ), pLane );
		}
		else if ( pView == this.ChangeOperator )
		{
			this.m_pDlgChangeOp = new DialogChangeOperator ( this.getContext ( ), pView, pLane, State );
			m_Manager.get_verify_lane_state_handle ( )
			         .set_change_operator ( this.m_pDlgChangeOp.is_change_operator ( ) );
			this.m_pDlgChangeOp = null;
		}
		else if ( pView == this.OpenLane )
		{
			new DialogOpenLane ( this.getContext ( ), pView, pLane, m_Manager.get_user_name_logged ( ), State );
		}
		else if ( pView == this.CloseLane )
		{
			new DialogCloseLane ( this.getContext ( ), pView, pLane, State );
		}
		else if ( pView == this.Violation )
		{
			new DialogViolation ( this.getContext ( ), R.string.dialog_violation, pLane );
		}
		else if ( pView == this.Free )
		{
			new DialogSearchFreeVehicle ( this.getContext ( ), R.string.dialog_isento, pLane );
		}
		else if ( pView == this.Simulation )
		{
			new DialogSimulatePassenger ( this.getContext ( ), R.string.dialog_simulation, pLane );
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
