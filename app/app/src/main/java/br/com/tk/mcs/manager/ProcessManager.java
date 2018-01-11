/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.manager;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.lanewidget_impl;
import br.com.tk.mcs.components.toolbar_impl;
import br.com.tk.mcs.dialogs_operations.DialogPrinterManager;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.main.TrackManager;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 3/30/17.
 */

public class ProcessManager implements View.OnClickListener
{
   public static final String TAG = ProcessManager.class.getName();
   private static final int NMAXTASKS = 0xA;
   private final List<ProcessLaneStateMonitor> m_monitoring_lanes = new ArrayList<>();
   private ProcessLanesView m_lanes_view = null;
   private ProcessTableView m_table_view = null;
   private ProcessLaneActions m_lanes_actions = null;
   private String m_user_name_logged = null;
   private ProcessLaneState m_verify_lanes = null;
   private PrinterManagerController m_printer_controller = null;
   private ExecutorService m_threads = null;
   private toolbar_impl m_toolbar_impl = null;
   private TrackManager m_handle = null;

   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessManager(final Activity context,
                         final ArrayList<Lane> list_lanes,
                         final String user_name
   ) throws
     Exception
   {
      this.m_handle = (TrackManager) context;

      this.m_toolbar_impl = this.m_handle.get_toolbar_handle();
      this.m_user_name_logged = user_name;
      this.m_lanes_view = new ProcessLanesView(this.m_handle).create(list_lanes);
      this.m_table_view = new ProcessTableView(this.m_handle);
      this.m_lanes_actions = new ProcessLaneActions(this.m_handle);
      this.m_verify_lanes = new ProcessLaneState(this.m_user_name_logged, this.m_handle);
      //
      this.prepate_monitoring_lanes();
      //
      int nCount = this.m_lanes_view.get_contaneir_handle()
         .getChildCount() + NMAXTASKS;

      this.m_threads = Executors.newFixedThreadPool(nCount);
      this.m_printer_controller = new PrinterManagerController(this.m_handle, this.m_toolbar_impl);

      /* change caption */
      this.set_composite_caption(Utils.USER + this.m_user_name_logged);
      /* invalidate all components */
      this.m_table_view.invalidate(true);
      this.m_lanes_actions.invalidate(true);
      this.m_lanes_view.invalidate(true);

      /* check display height */
      this.set_composite_caption(Utils.USER + this.m_user_name_logged)
         .update_controls();
		
		/* set pointer */
      this.m_lanes_view.set_manager_pointer(this);
      this.m_lanes_actions.set_manager_pointer(this);
      this.m_verify_lanes.set_buildermanager_pointer(this);
      this.m_table_view.set_manager_pointer(this);
      //
      for (ProcessLaneStateMonitor process_handle : this.m_monitoring_lanes)
      {
         process_handle.set_buildermanager_pointer(this);
      }
      //
      m_handle.get_layout_handle()
         .invalidate(true);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   protected ProcessManager prepate_monitoring_lanes()
   {
      int offset = this.m_lanes_view.get_contaneir_handle()
         .getChildCount();

      for (int i = 0; i < offset; i++)
      {
         final lanewidget_impl lane_view = (lanewidget_impl) this.m_lanes_view.get_contaneir_handle()
            .getChildAt(i);
         final ProcessLaneStateMonitor lane_task = new ProcessLaneStateMonitor(this.m_user_name_logged,
            lane_view,
            this.m_handle
         );
         //
         m_monitoring_lanes.add(lane_task);
      }
      return this;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLanesView get_builder_lanes_view_handle()
   {
      //
      return this.m_lanes_view;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public Activity get_handle()
   {
      //
      return this.m_handle;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneActions get_builder_lane_actions_handle()
   {
      return this.m_lanes_actions;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final List<ProcessLaneStateMonitor> get_monitoring_lane_state_list()
   {
      return this.m_monitoring_lanes;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneActions get_builder_lanes_actions_handle()
   {
      return this.m_lanes_actions;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessTableView get_builder_table_view_handle()
   {
      return this.m_table_view;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final PrinterManagerController get_printer_manager_handle()
   {
      return this.m_printer_controller;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final toolbar_impl get_customer_toolbar_handle()
   {
      return this.m_toolbar_impl;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ProcessLaneState get_verify_lane_state_handle()
   {
      return this.m_verify_lanes;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final String get_user_name_logged()
   {
      return this.m_user_name_logged;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public final ExecutorService get_runnings()
   {
      return this.m_threads;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessManager set_composite_caption(String szData) throws
                                                              Exception
   {
      int[] paButtons = new int[]{R.drawable.printer_error,
                                  R.drawable.cctv_camera_icon,
                                  R.drawable.his_operator
      };

      this.m_toolbar_impl.set_icon_list(paButtons, this);
      this.m_handle.get_layout_handle()
         .invalidate(true);
      return this;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessManager update_controls() throws
                                           Exception
   {
      //
      return this;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   protected void show_current_lane_selected() throws
                                               Exception
   {
      String fmt = String.format("Pista corrente %s selecionada.",
         this.m_verify_lanes.m_pLane.get_name()
      );
      //new DialogCurrentSquare(this.getContext(), this);
      new message_box(this.m_handle, R.string.ids_warning, fmt, message_box.IDOK);
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessManager finalize_process() throws
                                            Exception
   {
      m_verify_lanes.interrupt();
      //
      for (ProcessLaneStateMonitor p : this.m_monitoring_lanes)
      {
         p.interrupt();
      }
      //
      m_printer_controller.interrupt();
      this.m_threads.shutdown();
      return this;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   public ProcessManager start_process()
   {
      try
      {
		   /* check company to active printer use */
         this.m_threads.execute(m_printer_controller);

         for (ProcessLaneStateMonitor p : this.m_monitoring_lanes)
         {
            this.m_threads.execute(p);
         }
         //
         this.m_threads.execute(m_verify_lanes);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return this;
   }

   //-----------------------------------------------------------------------------------------------------------------//
   @Override
   public void onClick(View v)
   {
      try
      {
         int nID = v.getId();

         switch (nID)
         {
            case R.drawable.printer_ok:
               //new message_box(this.getContext(), R.string.ids_warning, R.string.printer_online, message_box.IDOK);
               //break;
            case R.drawable.print_superhot:
               //new message_box(this.getContext(), R.string.ids_warning, R.string.printer_superhot, message_box.IDWARNING);
               //break;
            case R.drawable.printer_error:
               //new message_box(this.getContext(), R.string.ids_warning, R.string.printer_offline, message_box.IDWARNING);
               //break;
            case R.drawable.printer_nopaper:
               //new message_box(this.getContext(), R.string.ids_warning, R.string.printer_nopaper, message_box.IDWARNING);
               //break;
            case R.drawable.printer_without_batery:
               //new message_box(this.getContext(), R.string.ids_warning, R.string.printer_batery_weak, message_box.IDWARNING);
               new DialogPrinterManager(this.m_handle, this, nID);
               break;
            case R.drawable.her_operator:
            case R.drawable.his_operator:
               new message_box(this.m_handle,
                  R.string.current_operator,
                  Utils.USER + this.m_user_name_logged,
                  message_box.IDOK
               );
               break;
            case R.drawable.cctv_camera_icon:
               this.show_current_lane_selected();
               break;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
