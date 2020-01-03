package packag.nnk.com.userfuelapp.base;

import android.location.Location;

/**
 * Callback that can be implemented in order to listen for events
 */

public interface Listener {
    public String TAG = "Location_Sample_Logs";
    void locationOn();

    void currentLocation(Location location);

    void locationCancelled();
}

