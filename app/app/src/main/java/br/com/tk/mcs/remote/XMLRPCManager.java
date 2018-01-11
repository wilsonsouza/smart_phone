/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.remote;

/**
 * Created by revolution on 28/01/16.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import br.com.tk.mcs.generic.company_setup;
import de.timroes.axmlrpc.XMLRPCClient;

public class XMLRPCManager extends AsyncTask<XMLRPCInfo, Object, Object>
{
   private XMLRPCClient m_client = null;
   private int m_timeout = 0x64;
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCManager(String url, int timeout) throws MalformedURLException
   {
      int flags = /*XMLRPCClient.FLAGS_NO_STRING_ENCODE | */XMLRPCClient.FLAGS_DEFAULT_TYPE_STRING;

      m_client = new XMLRPCClient(new URL(url), flags);
      m_timeout = timeout;
      //
      if ( company_setup.is_debug || company_setup.is_tecsidel )
      {
         Log.e(getClass().getName(), String.format("Url:%s, Flags %d, Timeout %d ", url, flags, timeout));
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   protected Object doInBackground(XMLRPCInfo... params)
   {
      Object res = null;

      try
      {
         XMLRPCInfo info = params[0];

         if (m_timeout != 0)
         {
            m_client.setTimeout(m_timeout);
         }

         switch (info.getMethod())
         {
            case userRequest:
            case tagPlateRequest:
            {
               res = m_client.call(info.getMethod().name(), (Object[]) info.getValues());
               break;
            }
            case getShortStatus:
            case getLongStatus:
            case isRemotePaymentPermitted:
            {
               res = m_client.call(info.getMethod().name());
               break;
            }
            case paymentRD:
            case remotePayment:
            {
               res = m_client.call(info.getMethod().name(), (Object[]) info.getValues());
               break;
            }
            case operationRequest:
            {
               res = m_client.call(info.getMethod().name(), (HashMap) info.getValues());
               break;
            }
            default:
               break;
         }
      }
      catch (Exception e)
      {
         //e.printStackTrace();
         Log.e(this.getClass().getName(), e.getMessage());
      }
      return res;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   protected void onPostExecute(Object res)
   {
      if( company_setup.is_debug )
      {
         if(res != null)
         {
            Log.d(getClass().getName() + ".onPostExecute", res.toString());
         }
         else
         {
            Log.e(getClass().getName() + ".onPostExecute", "Result (res is NULL).");
         }
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
}