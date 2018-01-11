/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:

   Changes: replace all Map and HashMap class by ConcurrentMap and ConcurrentHashMap (thread safe)
   Change Object[] to ArrayList<Object> and convert toArray() -> Object[]
 */
package br.com.tk.mcs.lane;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.remote.XMLRPCInfo;
import br.com.tk.mcs.remote.XMLRPCManager;
import br.com.tk.mcs.remote.response.GetLongStatusResponse;
import br.com.tk.mcs.remote.response.GetShortStatusResponse;
import br.com.tk.mcs.remote.response.RemotePaymentPermittedResponse;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;
import br.com.tk.mcs.remote.response.TagPlateResponse;
import br.com.tk.mcs.remote.response.UserRequestResponse;

/**
 * Created by revolution on 12/02/16.
 */

public class Operations implements Serializable
{
   private int m_timeout = 0x100;
   private String m_url;
   private String m_operator;
   private final static String m_sPAYMENTTYPE_PAYMENT = "P";
   private final static String m_sPAYMENTMEANS_CASH = "CA";
   private final static String m_sPAYMENTMEANS_TAG = "TG";

   public enum Tasks
   {
      opChangeResponsable(0),
      opChangeState(1),
      opBarrierExit(2),
      eOperCambiarBarreraEntrada(3),
      opTrafficLightPurple(4),
      opTrafficLightGreen(5),
      eOperSimula(6),
      eOperResetSas(7),
      eOperPagoRemoto(8),
      eOperReclasificacion(9),
      eOperConfAlarmasLevel1(10),
      eOperResetPC(11),
      eOperCambiarBarrerasOversized(12),
      eOperCambiarSemaforoSalida(13),
      eOpercommutarCofre(14),
      eOperCambiarBarreraEscape(15),
      eOperCargaHoppers(16),
      eOperDevuelveCambio(17),
      eOperCambiaSentidoVia(18),
      eOperImprimirRecibo(21);

      private final int m_id;

      Tasks(int id)
      {
         this.m_id = id;
      }

      public int getValue()
      {
         return m_id;
      }
   }

   private String m_szCupom = "";
   //-----------------------------------------------------------------------------------------------------------------//
   public Operations(String url, final String szOperator)
   {
      this.m_url = url;
      this.m_operator = szOperator;
      this.m_szCupom = "";
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public void set_operator_id ( String szOperatorID ) //set_operator_id setOperatorId
   {
      this.m_operator = szOperatorID;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public void set_timeout ( int seconds )
   {
      this.m_timeout = seconds;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   @SuppressWarnings("unchecked")
   public GetLongStatusResponse get_long_status ( )
   {
      try
      {
         Map<String, Object> map = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.getLongStatus));
            map = (Map) net.get();
         }
         return GetLongStatusResponse.to_GetLongStatusResponse ( map );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return null;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   @SuppressWarnings("unchecked")
   public GetShortStatusResponse get_short_status ( )
   {
      try
      {
         Map<String, Object> map = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.getShortStatus));
            map = (Map) net.get();
         }
         return GetShortStatusResponse.to_GetShortStatusResponse ( map );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return null;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public UserRequestResponse user_request ( final String user, final String pass )
   {
      try
      {
         Object[] obj = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            this.m_operator = user;
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.userRequest, new String[]{user, pass}));
            obj = (Object[]) net.get();
         }
         return UserRequestResponse.from_value ( Integer.parseInt ( obj[0].toString ( ) ) );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return UserRequestResponse.Error;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public TagPlateResponse tag_plate_request ( final String tagPlate )
   {
      try
      {
         Object[] obj = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.tagPlateRequest, new String[]{tagPlate}));
            obj = (Object[]) net.get();
         }
         return TagPlateResponse.from_value ( Integer.parseInt ( obj[0].toString ( ) ) );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return null;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_lane_on ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opChangeState, m_operator, 2 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_lane_off ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opChangeState, m_operator, 0 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_barrier_on ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opBarrierExit, m_operator, 1 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_barrier_off ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opBarrierExit, m_operator, 0 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_traffic_light_on ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opTrafficLightGreen, m_operator, 1 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_traffic_light_off ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opTrafficLightPurple, m_operator, 0 )).get ( "responseCode" );
      return ((Integer) res == 0);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean set_change_operator_responsible ( )
   {
      Object res = ((HashMap) set_task ( Tasks.opChangeResponsable, m_operator )).get ( "responseCode" );
      return ((Integer) res == 0);
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public Boolean reset_sas_operation ( ) /*operation_reset_sas*/
   {
      Object pResult = ((HashMap) set_task ( Tasks.eOperResetSas, m_operator )).get ( "responseCode" );
      return ((Integer) pResult == 0);
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public boolean simulate_passage ( )
   {
      Object pSuccess = ((HashMap) set_task ( Tasks.eOperSimula, m_operator )).get ( "responseCode" );
      return Integer.parseInt(pSuccess.toString()) == 0;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   private Object set_task ( Tasks task, String code, int param, String payment )
   {
      return set_operations ( task, code, param, payment );
   }

   //-----------------------------------------------------------------------------------------------------------------//
   private Object set_task ( Tasks task, String code, int param )
   {
      return set_operations ( task, code, param, "" );
   }

   //-----------------------------------------------------------------------------------------------------------------//
   private Object set_task ( Tasks task, String code )
   {
      return set_operations ( task, code, 0, "" );
   }

   //-----------------------------------------------------------------------------------------------------------------//
   private Object set_operations ( Tasks task, String code, int param, String payment )
   {
      try
      {
         Object Result = null;
         Map<String, Object> map = new HashMap<>();
         {
            map.put("operationCode", task.getValue());
            map.put("operatorCode", code);
            map.put("parameter_VAL", param);
            map.put("payment_VAL", payment);
         }
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.operationRequest, map));
            Result = net.get();
         }
         return Result;
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return null;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   @SuppressWarnings("unchecked")
   public RemotePaymentPermittedResponse is_remote_payment_permitted ( )
   {
      try
      {
         Object obj = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.isRemotePaymentPermitted));
            obj = net.get();
         }
         return RemotePaymentPermittedResponse.to_RemotePaymentPermittedResponse ( ((Map) obj) );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return null;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   // release vehicle stopped on the lane
   @SuppressWarnings("unchecked")
   public RemotePaymentResponse make_remote_payment ( RemotePaymentPermittedResponse remote, String tagPlate, String szIsentoCode )
   {
      try
      {
         boolean bFree = (szIsentoCode != null);
         Map<String, Object> res = null;
         Map<String, Object> map = new HashMap();
         {
            map.put("pan", (tagPlate.length() > 7) ? tagPlate : "");
            map.put("plate", (tagPlate.length() == 7) ? tagPlate : "");

            if (bFree)
            {
               map.put("exemptGroup", szIsentoCode);
            }
         }
         ArrayList<Object> params = new ArrayList<>();
         {
            params.add(this.m_operator);
            params.add("");
            params.add (remote.get_transaction ( ).get_vehicle_class ( ) );
            params.add (remote.get_transaction ( ).get_transaction_id ( ) );
            //
            if(bFree)
            {
               params.add("E");
               params.add("NO");
            }
            else
            {
               params.add("P");
               params.add("TG");
            }
            //
            params.add(map);
            params.add (remote.get_transaction ( ).get_properties ( ).get_vehicle_subclass ( ) );
         }
         Object[] transaction = params.toArray();
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.remotePayment, transaction));
            res = (Map) net.get();
         }
         return RemotePaymentResponse.from_value ( Integer.parseInt ( res.get ( "responseCode" ).toString ( ) ) );
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
         //e.printStackTrace();
      }
      return RemotePaymentResponse.ResponseERROR;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public RemotePaymentResponse payment_rd_cash ( String id, String vehicleClass )
   {
      return payment_rd ( id, vehicleClass, "", m_sPAYMENTTYPE_PAYMENT, m_sPAYMENTMEANS_CASH );
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public RemotePaymentResponse payment_rd_tag ( String id, String tagPlate )
   {
      return payment_rd ( id, "", tagPlate, m_sPAYMENTTYPE_PAYMENT, m_sPAYMENTMEANS_TAG );
   }

   //-----------------------------------------------------------------------------------------------------------------//
   // process payment of RD within money or TAG
   private RemotePaymentResponse payment_rd ( String id, String vehicleClass, String tagPlate, String paymentType,
                                              String paymentMeans )
   {
      try
      {
         Map<String, Object> map = new HashMap<>();
         {
            map.put("pan", (tagPlate.length() > 7) ? tagPlate : "");
            map.put("plate", (tagPlate.length() == 7) ? tagPlate : "");
         }
         ArrayList<Object> params = new ArrayList<>();
         {
            /* if sp99 use old payment method only supported by sp99 in current version
            if(company_setup.is_tamoios)
            {
               params.add(this.m_operator);
               params.add("");
               params.add(vehicleClass);
               params.add(id);
               params.add(paymentType);
               params.add(paymentMeans);
               params.add(map);
            }
            else*/
            {
               params.add(id);
               params.add(vehicleClass);
               params.add(tagPlate);
               params.add(this.m_operator);
               params.add(paymentType);
               params.add(paymentMeans);
               params.add(map);
            }
         }
         this.m_szCupom = "";
         Object[] transaction = params.toArray();
         String fmt = "";

         if( company_setup.is_debug || company_setup.is_tecsidel )
         {
            if (vehicleClass.length() == 0)
            {
               fmt = String.format("ID: %s Plate: %s with TAG", id, tagPlate);
            }
            else
            {
               fmt = String.format("ID: %s ClassId: %s with Cash", id, vehicleClass);
            }

            Log.e("payment_rd", fmt);
         }

         Object[] result = null;
         XMLRPCManager net = new XMLRPCManager(m_url, m_timeout);
         {
            net.execute(new XMLRPCInfo(XMLRPCInfo.XMLRPCMethods.paymentRD, transaction));
            result = (Object[]) net.get();
         }
         RemotePaymentResponse hSuccess = RemotePaymentResponse.from_value ( Integer.parseInt ( result[0].toString ( ) ) );

         if(hSuccess == RemotePaymentResponse.ResponseOK && paymentMeans.equals(m_sPAYMENTMEANS_CASH) && result.length > 1)
         {
            this.m_szCupom = result[1].toString();
         }
         return hSuccess;
      }
      catch (Exception e)
      {
         Log.e(this.getClass().getName(), e.getMessage());
      }
      return RemotePaymentResponse.ResponseERROR;
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public final String get_cupom_id ( )
   {
      return this.m_szCupom;
   }
}
