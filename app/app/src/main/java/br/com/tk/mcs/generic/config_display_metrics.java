/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import br.com.tk.mcs.R;

/**
 * Created by wilsonsouza on 17/01/17.
 */
@TargetApi(22)
public class config_display_metrics
{
   private static final String TAG = config_display_metrics.class.getName( );
   public static int Wallpaper = R.drawable.ic_company_web;
   public static Point WallpaperOffset = null;
   public static int ButtonStyle = 0;
   public static int TextStyle = 0;
   public static int EditStyle = 0;
   public static int CaptionStyle = 0;
   public static int ImageStyle = 0;
   public static int DialogStyle = 0;
   public static int DialogMessageStyle = 0;
   public static int RadioButtonStyle = 0;
   public static int CheckButtonStyle = 0;
   public static int SpinnerStyle = 0;
   public static Point LaneIcon = new Point( 48,
                                             48
   );
   public static Point ButtonImageIcon = new Point( 36,
                                                    36
   );
   public static Point DialogIcon = new Point( 36,
                                               36
   );
   public static Point ToolbarIcon = new Point( 36,
                                                36
   );
   public static Point HomeIndicator = new Point( 56,
                                                  56
   );
   public static Point ScannerPoint = null;
   public static Point VehicleImage = null;
   public static float FontSize = 17f;
   public static float CaptionSize = 24f;
   public static Rect EditRect = new Rect( 8,
                                           8,
                                           8,
                                           8
   );
   public static Rect Padding = new Rect( 8,
                                          8,
                                          8,
                                          8
   );
   public static int actionbar_dialog_tab_icons_size = 32;
   public static int small_context_icons_size = 16;
   public static int notifican_icons_size = 24;
   public static int minimum_control_width = 300;
   public static int minimum_control_height = 36;
   public static int space_between_controls = 8;
   public static int small_context_size = 64;
   
   //--------------------------------------------------------------------------------------------//
   public static void builder( Context pWnd )
   {
      Configuration cf = Resources.getSystem( )
                                  .getConfiguration( );
      {
         Log.i( TAG,
                cf.toString( )
         );
         cf.setTo( cf );
      }
      DisplayMetrics dm = Resources.getSystem( )
                                   .getDisplayMetrics( );
      {
         Log.i( TAG,
                dm.toString( )
         );
         dm.setTo( dm );
      }
      /**/
		/*
		if( detect_device_type.is_tablet ( pWnd ))
      {
         s_nUnits = TypedValue.COMPLEX_UNIT_PX;
         FontSize = 32f;
         CaptionSize = 48f;
      }
      else
      {
         s_nUnits = TypedValue.COMPLEX_UNIT_DIP;
         FontSize = 32f;
         CaptionSize = 16f;
      }
      */
      /* assign changes */
      Resources.getSystem( )
               .updateConfiguration( cf,
                                     dm );

      /* define company logo */
      if (company_setup.is_arteris)
      {
         Wallpaper = R.drawable.logo_arteris;
         WallpaperOffset = new Point( 300,
                                      125 );
      }
      else if (company_setup.is_tamoios)
      {
         Wallpaper = R.drawable.logotamoios03;
         WallpaperOffset = new Point( 200,
                                      100 );
      }
      else if (company_setup.is_cro)
      {
         Wallpaper = R.drawable.cro_logo_1;
         WallpaperOffset = new Point( 200,
                                      100 );
      }
      else if (company_setup.is_via_rio)
      {
         Wallpaper = R.drawable.vrio;
         WallpaperOffset = new Point( 200,
                                      100 );
      }
      else if (company_setup.is_tecsidel)
      {
         Wallpaper = R.drawable.ic_company_web;
         WallpaperOffset = new Point( 300,
                                      128 );
      }
      else if (company_setup.is_040)
      {
         Wallpaper = R.drawable.logo_via040;
         WallpaperOffset = new Point( 200,
                                      173 );
      }
		/* definie vechicle 748*/
      VehicleImage = new Point( pWnd.getResources( )
                                    .getDisplayMetrics( ).widthPixels,
                                pWnd.getResources( )
                                    .getDisplayMetrics( ).heightPixels / 2 );
      
      if (dm.widthPixels < 748)
      {
         ScannerPoint = new Point( 320,
                                   220 );
      }
      else
      {
         ScannerPoint = new Point( 800,
                                   400 );
      }
		/* set toolbar and others icons */
      switch (pWnd.getResources( )
                  .getDisplayMetrics( ).densityDpi)
      {
         case DisplayMetrics.DENSITY_LOW:
            actionbar_dialog_tab_icons_size = 32;
            small_context_icons_size = 16;
            notifican_icons_size = 24;
            break;
         case DisplayMetrics.DENSITY_MEDIUM:
            actionbar_dialog_tab_icons_size = 48;
            small_context_icons_size = 24;
            notifican_icons_size = 24;
            break;
         case DisplayMetrics.DENSITY_HIGH:
            actionbar_dialog_tab_icons_size = 64;
            small_context_icons_size = 24;
            notifican_icons_size = 36;
            break;
         case DisplayMetrics.DENSITY_XHIGH:
            actionbar_dialog_tab_icons_size = 72;
            small_context_icons_size = 32;
            notifican_icons_size = 48;
            break;
         case DisplayMetrics.DENSITY_XXHIGH:
            actionbar_dialog_tab_icons_size = 96;
            small_context_icons_size = 48;
            notifican_icons_size = 72;
            break;
         case DisplayMetrics.DENSITY_XXXHIGH:
            actionbar_dialog_tab_icons_size = 128;
            small_context_icons_size = 64;
            notifican_icons_size = 96;
            break;
      }

      /* recalcule wallpaper size again */
      WallpaperOffset = new Point( WallpaperOffset.x + actionbar_dialog_tab_icons_size,
                                   WallpaperOffset.y + actionbar_dialog_tab_icons_size
      );

      /* set default icon size */
      Log.i( TAG,
             String.format( "DPI %d, IconSize %d",
                            dm.densityDpi,
                            actionbar_dialog_tab_icons_size
             )
      );
      
      LaneIcon = new Point( actionbar_dialog_tab_icons_size,
                            actionbar_dialog_tab_icons_size
      );
      HomeIndicator = new Point( actionbar_dialog_tab_icons_size * 2,
                                 actionbar_dialog_tab_icons_size * 2
      );
      DialogIcon = new Point( actionbar_dialog_tab_icons_size,
                              actionbar_dialog_tab_icons_size
      );
      ToolbarIcon = new Point( actionbar_dialog_tab_icons_size,
                               actionbar_dialog_tab_icons_size
      );
      ButtonImageIcon = new Point( notifican_icons_size,
                                   notifican_icons_size
      );

      /* define icon offset */
      //		ButtonStyle = R.style.button;
      //		TextStyle = R.style.TextAppearance_AppCompat_Caption;
      //		CaptionStyle = R.style.caption;
      //		ImageStyle = android.R.attr.imageWellStyle;
      //		EditStyle = R.style.Widget_AppCompat_EditText;
      //		RadioButtonStyle = R.style.Widget_AppCompat_CompoundButton_RadioButton;
      //		CheckButtonStyle = R.style.Widget_AppCompat_CompoundButton_CheckBox;
      //		SpinnerStyle = android.R.attr.spinnerItemStyle | android.R.attr.dropDownSpinnerStyle;
      		DialogStyle = R.style.Theme_AppCompat_Light_Dialog_Alert;
      		DialogMessageStyle = R.style.Theme_AppCompat_Light_Dialog_Alert;
		/**/

      /* register density */
      if (company_setup.is_debug)
      {
         Log.i( TAG,
                String.format( "Density %f",
                               dm.density
                )
         );
      }
   }
   
   //--------------------------------------------------------------------------------------------//
   public static double get_device_inch( )
   {
      DisplayMetrics dm = Resources.getSystem( )
                                   .getDisplayMetrics( );
      double dWidthDP = Math.pow( (dm.widthPixels / dm.densityDpi),
                                  2
      );
      double dHeightDP = Math.pow( (dm.heightPixels / dm.densityDpi),
                                   2
      );
      double dInch = Math.sqrt( dWidthDP + dHeightDP );
      Log.i( TAG,
             String.format( "Device inch %f DPI %d density %f",
                            dInch,
                            dm.densityDpi,
                            dm.density
             )
      );
      return dInch;
   }
   
   //--------------------------------------------------------------------------------------------//
   public static int to_sp( int nValues )
   {
      DisplayMetrics display_m = Resources.getSystem( )
                                          .getDisplayMetrics( );
      return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_SP,
                                              nValues,
                                              display_m
      );
   }
   
   //--------------------------------------------------------------------------------------------//
   public static int to_dp( int nValues )
   {
      DisplayMetrics display_m = Resources.getSystem( )
                                          .getDisplayMetrics( );
      return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP,
                                              (nValues / display_m.density),
                                              display_m
      );
   }
   
   //--------------------------------------------------------------------------------------------//
   public static int get_attribute_size_horizontal( Context pWnd,
                                                    int attr
   )
   {
      TypedValue t = new TypedValue( );
      
      pWnd.getTheme( )
          .resolveAttribute( attr,
                             t,
                             true
          );
      return Resources.getSystem( )
                      .getDimensionPixelSize( t.resourceId );
   }
   
   //--------------------------------------------------------------------------------------------//
   public static Configuration get_configuration( )
   {
      return Resources.getSystem( )
                      .getConfiguration( );
   }
   
   //--------------------------------------------------------------------------------------------//
   public static DisplayMetrics get_display_metrics( )
   {
      return Resources.getSystem( )
                      .getDisplayMetrics( );
   }
}
