/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
   Changed by wilson.souza Nov 11 2016 added more two constants definitions (get mcs details & ticket details)
 */
package br.com.tk.mcs.remote;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by revolution on 02/02/16.
 */

public class XMLRPCInfo
{
   public enum XMLRPCMethods
   {
      getShortStatus,
      getLongStatus,
      operationRequest,
      tablesRequest,
      alarmsRequest,
      paramRequest,
      setParam,
      isRemotePaymentPermitted,
      remotePayment,
      changeClass,
      userRequest,
      fareRequest,
      tagPlateRequest,
      remoteRDPayment,
      paymentRD
   }

   private XMLRPCMethods m_method;
   private Object m_values;
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCInfo(final XMLRPCMethods method)
   {
      m_method = method;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCInfo(final XMLRPCMethods method, final ArrayList<String> values)
   {
      m_method = method;
      m_values = values;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCInfo(final XMLRPCMethods method, final Object[] values)
   {
      m_method = method;
      m_values = values;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCInfo(final XMLRPCMethods method, final String[] values)
   {
      m_method = method;
      m_values = values;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public XMLRPCInfo(final XMLRPCMethods method, final Map<String, Object> values)
   {
      m_method = method;
      m_values = values;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   XMLRPCMethods getMethod()
   {
      return m_method;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   void setMethod(final XMLRPCMethods method)
   {
      m_method = method;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   Object getValues()
   {
      return m_values;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   void setValues(final Object values)
   {
      m_values = values;
   }
}