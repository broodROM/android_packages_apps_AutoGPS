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

public class Restore extends Activity {
	
	public String temp; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MAKEGPS();
		finish(); 
		
	}

	
	public void MAKEGPS() {
		
		Toast.makeText(getApplicationContext(), 
	     		  "Restoring original gps.conf", Toast.LENGTH_SHORT).show();
		
		CommandCapture command = new CommandCapture(0, "mount -o remount rw /system", "busybox rm -f /system/etc/gps.conf", "touch /system/etc/gps.conf", "echo -e 'XTRA_SERVER_1=http://xtra1.gpsonextra.net/xtra.bin\nXTRA_SERVER_2=http://xtra2.gpsonextra.net/xtra.bin\nXTRA_SERVER_3=http://xtra3.gpsonextra.net/xtra.bin\nSUPL_HOST=supl.google.com\nSUPL_PORT=7276' > /system/etc/gps.conf", "busybox chmod 644 /system/etc/gps.conf"); {
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
