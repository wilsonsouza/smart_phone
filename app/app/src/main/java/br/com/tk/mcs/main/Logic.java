package br.com.tk.mcs.main;

import br.com.tk.mcs.R;

/**
 * Created by revolution on 25/02/16.
 */

public class Logic
{
   public static int getOperationButtonId(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.btTag;
         case 1:
            return R.id.btResponsable;
         case 2:
            return R.id.btClose;
         case 3:
            return R.id.btOpen;
         case 4:
            return R.id.btPayMoney;
         case 5:
            return R.id.btPayTag;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getLightButtonId(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.light0;
         case 1:
            return R.id.light1;
         case 2:
            return R.id.light2;
         case 3:
            return R.id.light3;
         case 4:
            return R.id.light4;
         case 5:
            return R.id.light5;
         case 6:
            return R.id.light6;
         case 7:
            return R.id.light7;
         case 8:
            return R.id.light8;
         case 9:
            return R.id.light9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getTrafficButtonById(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.traffic0;
         case 1:
            return R.id.traffic1;
         case 2:
            return R.id.traffic2;
         case 3:
            return R.id.traffic3;
         case 4:
            return R.id.traffic4;
         case 5:
            return R.id.traffic5;
         case 6:
            return R.id.traffic6;
         case 7:
            return R.id.traffic7;
         case 8:
            return R.id.traffic8;
         case 9:
            return R.id.traffic9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getBarrerButtonById(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.barrer0;
         case 1:
            return R.id.barrer1;
         case 2:
            return R.id.barrer2;
         case 3:
            return R.id.barrer3;
         case 4:
            return R.id.barrer4;
         case 5:
            return R.id.barrer5;
         case 6:
            return R.id.barrer6;
         case 7:
            return R.id.barrer7;
         case 8:
            return R.id.barrer8;
         case 9:
            return R.id.barrer9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getVehicleButtonId(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.vehicles0;
         case 1:
            return R.id.vehicles1;
         case 2:
            return R.id.vehicles2;
         case 3:
            return R.id.vehicles3;
         case 4:
            return R.id.vehicles4;
         case 5:
            return R.id.vehicles5;
         case 6:
            return R.id.vehicles6;
         case 7:
            return R.id.vehicles7;
         case 8:
            return R.id.vehicles8;
         case 9:
            return R.id.vehicles9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getLaneButtonId(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.radioButton0;
         case 1:
            return R.id.radioButton1;
         case 2:
            return R.id.radioButton2;
         case 3:
            return R.id.radioButton3;
         case 4:
            return R.id.radioButton4;
         case 5:
            return R.id.radioButton5;
         case 6:
            return R.id.radioButton6;
         case 7:
            return R.id.radioButton7;
         case 8:
            return R.id.radioButton8;
         case 9:
            return R.id.radioButton9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getLayoutById(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.layout0;
         case 1:
            return R.id.layout1;
         case 2:
            return R.id.layout2;
         case 3:
            return R.id.layout3;
         case 4:
            return R.id.layout4;
         case 5:
            return R.id.layout5;
         case 6:
            return R.id.layout6;
         case 7:
            return R.id.layout7;
         case 8:
            return R.id.layout8;
         case 9:
            return R.id.layout9;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getDivById(int id)
   {
      switch (id)
      {
         case 0:
            return R.id.div0;
         case 1:
            return R.id.div1;
         case 2:
            return R.id.div2;
         case 3:
            return R.id.div3;
         case 4:
            return R.id.div4;
         case 5:
            return R.id.div5;
         case 6:
            return R.id.div6;
         case 7:
            return R.id.div7;
         case 8:
            return R.id.div8;
         default:
            return -1;
      }
   }
   //-----------------------------------------------------------------------------------------------------------------//
   public static int getLaneByInterface(int id)
   {
      switch (id)
      {
         case R.id.radioButton0:
            return 0;
         case R.id.radioButton1:
            return 1;
         case R.id.radioButton2:
            return 2;
         case R.id.radioButton3:
            return 3;
         case R.id.radioButton4:
            return 4;
         case R.id.radioButton5:
            return 5;
         case R.id.radioButton6:
            return 6;
         case R.id.radioButton7:
            return 7;
         case R.id.radioButton8:
            return 8;
         case R.id.radioButton9:
            return 9;
         default:
            return -1;
      }
   }
}
