package br.com.tk.mcs.remote.response;

/**
 * Created by revolution on 05/02/16.
 */

public class TransactionResponse
{
	public final static String             TAG            = TransactionResponse.class.getName ( );
	private             String             paymentType    = "";
	private             int                paidAmount     = 0;
	private             int                vatPercentage  = 0;
	private             PropertiesResponse properties     = null;
	private             String             paymentDetails = "";
	private             Integer            idZone         = 0;
	private             String             exitTime       = "";
	private             String             paymentMeans   = "";
	private             Integer            vatAmount      = 0;
	private             String             moment         = "";
	private             String             panNumber      = "";
	private             String             tagNumber      = "";
	private             String             vehicleClass   = "";
	private             Integer            fareAmount     = 0;
	private             String             transactionid  = "";
	
	public TransactionResponse ( )
	{
	}
	
	public String get_payment_type ( )
	{
		return paymentType;
	}
	
	public void set_payment_type ( String paymentType )
	{
		this.paymentType = paymentType;
	}
	
	public int get_paid_amount ( )
	{
		return paidAmount;
	}
	
	public void set_paid_amount ( int paidAmount )
	{
		this.paidAmount = paidAmount;
	}
	
	public int get_vat_percentage ( )
	{
		return vatPercentage;
	}
	
	public void set_vat_percentage ( int vatPercentage )
	{
		this.vatPercentage = vatPercentage;
	}
	
	public PropertiesResponse get_properties ( )
	{
		return properties;
	}
	
	public void set_properties ( PropertiesResponse properties )
	{
		this.properties = properties;
	}
	
	public String get_payment_details ( )
	{
		return paymentDetails;
	}
	
	public void set_payment_details ( String paymentDetails )
	{
		this.paymentDetails = paymentDetails;
	}
	
	public Integer get_zone_id ( )
	{
		return idZone;
	}
	
	public void set_zone_id ( Integer idZone )
	{
		this.idZone = idZone;
	}
	
	public String get_time_exit ( )
	{
		return exitTime;
	}
	
	public void set_time_exit ( String exitTime )
	{
		this.exitTime = exitTime;
	}
	
	public String get_payment_means ( )
	{
		return paymentMeans;
	}
	
	public void set_payment_means ( String paymentMeans )
	{
		this.paymentMeans = paymentMeans;
	}
	
	public Integer get_vat_amount ( )
	{
		return vatAmount;
	}
	
	public void set_vat_amount ( Integer vatAmount )
	{
		this.vatAmount = vatAmount;
	}
	
	public String get_moment ( )
	{
		return moment;
	}
	
	public void set_moment ( String moment )
	{
		this.moment = moment;
	}
	
	public String get_pan_number ( )
	{
		return panNumber;
	}
	
	public void set_pan_number ( String panNumber )
	{
		this.panNumber = panNumber;
	}
	
	public String get_tag_number ( )
	{
		return tagNumber;
	}
	
	public void set_tag_number ( String panNumber )
	{
		this.tagNumber = panNumber;
	}
	
	public String get_vehicle_class ( )
	{
		return vehicleClass;
	}
	
	public void set_vehicle_class ( String vehicleClass )
	{
		this.vehicleClass = vehicleClass;
	}
	
	public Integer get_fare_amount ( )
	{
		return fareAmount;
	}
	
	public void set_fare_amount ( Integer fareAmount )
	{
		this.fareAmount = fareAmount;
	}
	
	public String get_transaction_id ( )
	{
		return transactionid;
	}
	
	public void set_transaction_id ( String transactionid )
	{
		this.transactionid = transactionid;
	}
}
