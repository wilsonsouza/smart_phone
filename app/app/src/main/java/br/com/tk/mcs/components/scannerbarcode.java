/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

/**
 * Created by wilsonsouza on 31/01/17.
 *

public class scannerbarcode extends linear_horizontal implements View.OnClickListener
{
	private final SurfaceView m_handle; //data
	private CameraSource              m_camera   = null;
	private BarcodeDetector           m_barcode  = null;
	private on_scannerbacode_listener m_dispatch = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public scannerbarcode ( Context context,
	                        Point area,
	                        boolean bBorder
	                      )
	{
		super ( context );
		this.m_handle = new SurfaceView ( getContext ( ) );
		this.m_handle.setMinimumWidth ( area.x );
		this.m_handle.setMinimumHeight ( area.y );
		this.create ( area );
		this.addView ( this.m_handle,
		               this.Params
		             );
		this.set_border ( bBorder );
		this.invalidate ( true );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private void create ( Point p )
	{
		linear_horizontal area = new linear_horizontal ( getContext ( ) );
		//use zero (0) to all formats supported by system.
		//final int nCode = Barcode.ALL_FORMATS;
		this.m_barcode = new BarcodeDetector.Builder ( getContext ( ) ).setBarcodeFormats ( Barcode.ALL_FORMATS )
		                                                               .build ( );
		
		if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT )
		{
			if ( ! this.m_barcode.isOperational ( ) )
			{
				new message_box ( getContext ( ),
				                  R.string.ids_warning,
				                  R.string.scanner_is_not_ok,
				                  message_box.IDWARNING
				);
				return;
			}
		}
		//prepare all methods set camera area and frames per seconds
		Point point = new Point ( area.Params.get_display ( ).widthPixels,
		                          area.Params.get_display ( ).heightPixels
		);
		{
			this.m_camera = new CameraSource.Builder ( getContext ( ),
			                                           m_barcode
			).setAutoFocusEnabled ( true )
			 .setFacing ( CameraSource.CAMERA_FACING_BACK )
			 .setRequestedPreviewSize ( point.x,
			                            point.y
			                          )
			 .build ( );
		}
		this.m_handle.getHolder ( )
		             .addCallback ( new surfaceholder_callback ( ) );
		this.m_handle.getHolder ( )
		             .setFixedSize ( p.x,
		                             p.y
		                           );
		this.m_barcode.setProcessor ( new detector_barcode_processor ( ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_on_scannerbarcode_listener ( on_scannerbacode_listener pDispatch )
	{
		//
		this.m_dispatch = pDispatch;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		if ( v == this.m_handle )
		{
			this.m_camera.takePicture ( null,
			                            new camerasource_picture_callback ( )
			                          );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final SurfaceView get_handle ( )
	{
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public interface on_scannerbacode_listener
	{
		public void on_has_bufferdata ( SparseArray<Barcode> pData );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class camerasource_picture_callback implements CameraSource.PictureCallback
	{
		@Override
		public void onPictureTaken ( byte[] bytes )
		{
			try
			{
				final Bitmap bmp = BitmapFactory.decodeByteArray ( bytes,
				                                                   0,
				                                                   bytes.length
				                                                 );
				final Frame f = new Frame.Builder ( ).setBitmap ( bmp )
				                                     .build ( );
				final SparseArray<Barcode> pData = m_barcode.detect ( f );
				
				if ( m_dispatch != null && pData.size ( ) > 0 )
				{
					new Thread ( new Runnable ( )
					{
						@Override
						public void run ( )
						{
							m_dispatch.on_has_bufferdata ( pData );
						}
					} ).start ( );
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class surfaceholder_callback implements SurfaceHolder.Callback
	{
		@Override
		public void surfaceCreated ( SurfaceHolder holder )
		{
			try
			{
				m_camera.start ( m_handle.getHolder ( ) );
			}
			catch ( SecurityException | IOException e )
			{
				e.printStackTrace ( );
			}
		}
		
		@Override
		public void surfaceChanged ( SurfaceHolder holder,
		                             int format,
		                             int width,
		                             int height
		                           )
		{
		
		}
		
		@Override
		public void surfaceDestroyed ( SurfaceHolder holder )
		{
			try
			{
				m_camera.stop ( );
				m_camera.release ( );
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class detector_barcode_processor implements Detector.Processor<Barcode>
	{
		@Override
		public void release ( )
		{
		
		}
		
		@Override
		public void receiveDetections ( Detector.Detections<Barcode> detections )
		{
			final SparseArray<Barcode> pQueue = detections.getDetectedItems ( );
			
			if ( pQueue.size ( ) > 0 && m_dispatch != null )
			{
				new Thread ( new Runnable ( )
				{
					//
					@Override
					public void run ( )
					{
						m_dispatch.on_has_bufferdata ( pQueue );
					}
				} ).start ( );
			}
		}
	}
}
*/