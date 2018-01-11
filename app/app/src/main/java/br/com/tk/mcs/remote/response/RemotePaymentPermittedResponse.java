package br.com.tk.mcs.remote.response;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by revolution on 04/02/16.
 */

public class RemotePaymentPermittedResponse
{
	public final static String              TAG          = RemotePaymentPermittedResponse.class.getName ( );
	private             TransactionResponse transaction  = null;
	private             int                 responseCode = 0;
	private             int                 laneState    = 0;
	
	public RemotePaymentPermittedResponse ( )
	{
	}
	
	public static RemotePaymentPermittedResponse to_RemotePaymentPermittedResponse ( Map<String, Object> remoteValue )
	{
		RemotePaymentPermittedResponse remote      = new RemotePaymentPermittedResponse ( );
		TransactionResponse            transaction = new TransactionResponse ( );
		PropertiesResponse             properties  = new PropertiesResponse ( );
		
		if ( remoteValue.containsKey ( "responseCode" ) )
		{
			remote.set_response_code ( ( Integer ) remoteValue.get ( "responseCode" ) );
		}
		
		if ( remoteValue.containsKey ( "laneState" ) )
		{
			remote.set_lane_state ( ( Integer ) remoteValue.get ( "laneState" ) );
		}
		
		try
		{
			//if ((transaction != null) && remoteValue.containsKey("transaction"))
			if ( remoteValue.containsKey ( "transaction" ) )
			{
				Map<String, Object> transactionValue = ( HashMap<String, Object> ) remoteValue.get ( "transaction" );
				
				if ( transactionValue.containsKey ( "paymentType" ) )
				{
					transaction.set_payment_type ( transactionValue.get ( "paymentType" )
					                                               .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "paidAmount" ) )
				{
					transaction.set_paid_amount ( ( Integer ) transactionValue.get ( "paidAmount" ) );
				}
				
				if ( transactionValue.containsKey ( "vatPercentage" ) )
				{
					transaction.set_vat_percentage ( ( Integer ) transactionValue.get ( "vatPercentage" ) );
				}
				
				if ( transactionValue.containsKey ( "paymentDetails" ) )
				{
					transaction.set_payment_details ( transactionValue.get ( "paymentDetails" )
					                                                  .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "idZone" ) )
				{
					transaction.set_zone_id ( ( Integer ) transactionValue.get ( "idZone" ) );
				}
				
				if ( transactionValue.containsKey ( "exitTime" ) )
				{
					transaction.set_time_exit ( transactionValue.get ( "exitTime" )
					                                            .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "paymentMeans" ) )
				{
					transaction.set_payment_means ( transactionValue.get ( "paymentMeans" )
					                                                .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "vatAmount" ) )
				{
					transaction.set_vat_amount ( ( Integer ) transactionValue.get ( "vatAmount" ) );
				}
				
				if ( transactionValue.containsKey ( "moment" ) )
				{
					transaction.set_moment ( transactionValue.get ( "moment" )
					                                         .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "panNumber" ) )
				{
					transaction.set_pan_number ( transactionValue.get ( "panNumber" )
					                                             .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "vehicleClass" ) )
				{
					transaction.set_vehicle_class ( transactionValue.get ( "vehicleClass" )
					                                                .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "fareAmount" ) )
				{
					transaction.set_fare_amount ( ( Integer ) transactionValue.get ( "fareAmount" ) );
				}
				
				if ( transactionValue.containsKey ( "transactionId" ) )
				{
					transaction.set_transaction_id ( transactionValue.get ( "transactionId" )
					                                                 .toString ( ) );
				}
				
				if ( transactionValue.containsKey ( "properties" ) )
				{
					
					Map<String, Object> propertiesValue = ( HashMap<String, Object> ) transactionValue.get ( "properties" );
					
					if ( propertiesValue.containsKey ( "vehicleSubclass" ) )
					{
						properties.set_vehicle_subclass ( propertiesValue.get ( "vehicleSubclass" )
						                                                 .toString ( ) );
					}
				}
			}
			
			remote.set_transaction ( transaction );
			remote.get_transaction ( )
			      .set_properties ( properties );
		}
		catch ( Exception e )
		{
			//
			Log.e ( RemotePaymentPermittedResponse.class.getName ( ),
			        e.getMessage ( )
			      );
		}
		return remote;
	}
	
	public TransactionResponse get_transaction ( )
	{
		return transaction;
	}
	
	public void set_transaction ( TransactionResponse transaction )
	{
		this.transaction = transaction;
	}
	
	public int get_response_code ( )
	{
		return responseCode;
	}
	
	public void set_response_code ( int responseCode )
	{
		this.responseCode = responseCode;
	}
	
	public int get_lane_state ( )
	{
		return laneState;
	}
	
	public void set_lane_state ( int laneState )
	{
		this.laneState = laneState;
	}
}