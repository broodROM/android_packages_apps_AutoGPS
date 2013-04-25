package com.broodplank.autogps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.stericson.RootTools.CommandCapture;
import com.stericson.RootTools.RootTools;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public String temp; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GETLANG();
		MAKEGPS();
		 
		finish();
		
	}

	
	public void GETLANG() {
		
		
	
		 CommandCapture command = new CommandCapture(0, "busybox chmod 666 /data/property/persist.sys.language", "cat /data/property/persist.sys.language > /mnt/sdcard/gpslang"); {
		 		try {
		 	
		 			RootTools.getShell(true).add(command).waitForFinish();
	
		 		} catch (InterruptedException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		} catch (IOException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		} catch (TimeoutException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		}
		 		
		 	   temp = readOneLine("/mnt/sdcard/gpslang");
		 	   
		       Toast.makeText(getApplicationContext(), 
		     		  "Language code is: "+temp, Toast.LENGTH_SHORT).show();
		 }
	
		
	}
	public void MAKEGPS() {
		
		Toast.makeText(getApplicationContext(), 
	     		  "Making new gps.conf file", Toast.LENGTH_SHORT).show();
		
		CommandCapture command = new CommandCapture(0, "mount -o remount rw /system", "busybox rm -f /system/etc/gps.conf", "touch /system/etc/gps.conf", "echo -e 'NTP_SERVER=0.world.pool.ntp.org\nNTP_SERVER=1.world.pool.ntp.org\nNTP_SERVER=2.world.pool.ntp.org\nNTP_SERVER=3.world.pool.ntp.org\nNTP_SERVER=0."+temp+".pool.ntp.org\nNTP_SERVER=1."+temp+".pool.ntp.org\nNTP_SERVER=2."+temp+".pool.ntp.org\nNTP_SERVER=3."+temp+".pool.ntp.org\n\nXTRA_SERVER_1=http://xtra1.gpsonextra.net/xtra.bin\nXTRA_SERVER_2=http://xtra2.gpsonextra.net/xtra.bin\nXTRA_SERVER_3=http://xtra3.gpsonextra.net/xtra.bin\n\nDEBUG_LEVEL = 5\nINTERMEDIATE_POS=1\nACCURACY_THRES=0\nSUPL_HOST=supl.google.com\nSUPL_PORT=7276\nENABLE_WIPER=1\nCURRENT_CARRIER=common\n\nDEFAULT_AGPS_ENABLE=TRUE\nDEFAULT_SSL_ENABLE=FALSE\nDEFAULT_USER_PLANE=TRUE\nC2K_HOST=c2k.pde.com\nC2K_PORT=1234' > /system/etc/gps.conf", "busybox chmod 644 /system/etc/gps.conf"); {
	 		try {
	 	
	 			RootTools.getShell(true).add(command).waitForFinish();
	 			
	 		
	 			
	 		} catch (InterruptedException e1) {
	 			// TODO Auto-generated catch block
	 			e1.printStackTrace();
	 		} catch (IOException e1) {
	 			// TODO Auto-generated catch block
	 			e1.printStackTrace();
	 		} catch (TimeoutException e1) {
	 			// TODO Auto-generated catch block
	 			e1.printStackTrace();
	 		}
	 		
	 	   
	       Toast.makeText(getApplicationContext(), 
	     		  "Done!", Toast.LENGTH_SHORT).show();

	       
		}
		
		}

	
	public static String readOneLine(String fname) {
        BufferedReader br;
        String line = null;

        try {
            br = new BufferedReader(new FileReader(fname), 512);
            try {
                line = br.readLine();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            Log.e("AutoGPS", "IO Exception when reading /sys/ file", e);
        }
        return line;
    }

}
