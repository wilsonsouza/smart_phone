package br.com.tk.mcs.remote.response;

/**
 * Created by revolution on 05/02/16.
 */
public class DeviceResponse
{
	private String lightStateCanopy;
	private int    lightStateEntry;
	private int    lightStateExit;
	private String antennaOBU;
	private String antennaOBU2;
	private int    barrierExit;
	private String aspectCameraEntry;
	private String aspectCameraEntry2;
	private String aspectCameraExit;
	private String aspectCameraExit2;
	private String zoneEntry;
	private String zoneExit;
	private String lastPaymentType;
	private String lastPaymentMeans;
	private String currentOcr;
	private int    totalVehicles;
	private int    vehicleStopped;
	
	public DeviceResponse ( )
	{
		setLightStateCanopy ( "" );
		setLightStateEntry ( 0 );
		setLightStateExit ( 0 );
		setAntennaOBU ( "" );
		setAntennaOBU2 ( "" );
		setBarrierExit ( 1 );
		setAspectCameraEntry ( "" );
		setAspectCameraEntry2 ( "" );
		setAspectCameraExit ( "" );
		setAspectCameraExit2 ( "" );
		setZoneEntry ( "" );
		setZoneExit ( "" );
		setLastPaymentType ( "" );
		setLastPaymentMeans ( "" );
		setCurrentOcr ( "" );
		setTotalVehicles ( 0 );
		setVehicleStopped ( 0 );
	}
	
	public String getZoneEntry ( )
	{
		return zoneEntry;
	}
	
	public void setZoneEntry ( String zoneEntry )
	{
		this.zoneEntry = zoneEntry;
	}
	
	public int getTotalVehicles ( )
	{
		return totalVehicles;
	}
	
	public void setTotalVehicles ( int totalVehicles )
	{
		this.totalVehicles = totalVehicles;
	}
	
	public String getZoneExit ( )
	{
		return zoneExit;
	}
	
	public void setZoneExit ( String zoneExit )
	{
		this.zoneExit = zoneExit;
	}
	
	public String getLastPaymentType ( )
	{
		return lastPaymentType;
	}
	
	public void setLastPaymentType ( String lastPaymentType )
	{
		this.lastPaymentType = lastPaymentType;
	}
	
	public String getLastPaymentMeans ( )
	{
		return lastPaymentMeans;
	}
	
	public void setLastPaymentMeans ( String lastPaymentMeans )
	{
		this.lastPaymentMeans = lastPaymentMeans;
	}
	
	public String getAntennaOBU2 ( )
	{
		return antennaOBU2;
	}
	
	public void setAntennaOBU2 ( String antennaOBU2 )
	{
		this.antennaOBU2 = antennaOBU2;
	}
	
	public int getVehicleStopped ( )
	{
		return vehicleStopped;
	}
	
	public void setVehicleStopped ( int vehicleStopped )
	{
		this.vehicleStopped = vehicleStopped;
	}
	
	public String getAntennaOBU ( )
	{
		return antennaOBU;
	}
	
	public void setAntennaOBU ( String antennaOBU )
	{
		this.antennaOBU = antennaOBU;
	}
	
	public String getCurrentOcr ( )
	{
		return currentOcr;
	}
	
	public void setCurrentOcr ( String currentOcr )
	{
		this.currentOcr = currentOcr;
	}
	
	public String getLightStateCanopy ( )
	{
		return lightStateCanopy;
	}
	
	public void setLightStateCanopy ( String lightStateCanopy )
	{
		this.lightStateCanopy = lightStateCanopy;
	}
	
	public int getLightStateEntry ( )
	{
		return lightStateEntry;
	}
	
	public void setLightStateEntry ( int lightStateEntry )
	{
		this.lightStateEntry = lightStateEntry;
	}
	
	public int getLightStateExit ( )
	{
		return lightStateExit;
	}
	
	public void setLightStateExit ( int lightStateExit )
	{
		this.lightStateExit = lightStateExit;
	}
	
	public int getBarrierExit ( )
	{
		return barrierExit;
	}
	
	public void setBarrierExit ( int barrierExit )
	{
		this.barrierExit = barrierExit;
	}
	
	public String getAspectCameraEntry ( )
	{
		return aspectCameraEntry;
	}
	
	public void setAspectCameraEntry ( String aspectCameraEntry )
	{
		this.aspectCameraEntry = aspectCameraEntry;
	}
	
	public String getAspectCameraEntry2 ( )
	{
		return aspectCameraEntry2;
	}
	
	public void setAspectCameraEntry2 ( String aspectCameraEntry2 )
	{
		this.aspectCameraEntry2 = aspectCameraEntry2;
	}
	
	public String getAspectCameraExit ( )
	{
		return aspectCameraExit;
	}
	
	public void setAspectCameraExit ( String aspectCameraExit )
	{
		this.aspectCameraExit = aspectCameraExit;
	}
	
	public String getAspectCameraExit2 ( )
	{
		return aspectCameraExit2;
	}
	
	public void setAspectCameraExit2 ( String aspectCameraExit2 )
	{
		this.aspectCameraExit2 = aspectCameraExit2;
	}
}
