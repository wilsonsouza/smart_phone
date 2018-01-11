/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Point;

import com.google.zxing.Result;

import br.com.tk.mcs.layouts.linear_layout_impl;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by wilsonsouza on 6/7/17.
 */

public class scannerbarcode_impl extends linear_layout_impl implements ZXingScannerView.ResultHandler
{
   private final ZXingScannerView m_handle;
   private on_scannerbarcode_listener m_fn_proc = null; /* m_fn_proc_dispatch */

   //-----------------------------------------------------------------------------------------------------------------//
   public scannerbarcode_impl(Context pContext,
                              final Point area,
                              final boolean bBorder
   )
   {
      super(pContext);
      this.Params.set_dimension(area);
      this.m_handle = new ZXingScannerView(pContext);
      this.m_handle.setResultHandler(this);
      this.m_handle.setMinimumHeight(area.y);
      this.m_handle.setMinimumWidth(area.x);
      this.addView(this.m_handle, this.Params);
      this.set_border(bBorder);
      this.invalidate(true);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public void start()
   {
      this.m_handle.startCamera();
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public void stop()
   {
      this.m_handle.stopCamera();
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public void set_on_scannerbarcode_listener(final on_scannerbarcode_listener pDispatch)
   {
      this.m_fn_proc = pDispatch;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void handleResult(final Result result)
   {
      /* put your code here */
      if (this.m_fn_proc != null)
      {
         m_fn_proc.on_has_bufferdata(result);
      }
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ZXingScannerView get_handle()
   {
      return this.m_handle;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public interface on_scannerbarcode_listener
   {
      public void on_has_bufferdata(final Result pResult);
   }
}
