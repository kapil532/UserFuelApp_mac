package packag.nnk.com.userfuelapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.transaction.TransactionActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = MyFirebaseMessagingService.class.getSimpleName();
    // This is the Notification Channel ID. More about this in the next section
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        {amount=200.0, message=Amount debited}
        Log.e("REMOTE MESSAGE", "----" + remoteMessage.getData());
//        Log.e("REMOTE CHE", "----" +  CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED","checkBox2_NOTIFICATION_CHECKED_PRE",this));
//        Log.e("REMOTE NOTI", "----" +CommonClass.returnGenericData("NOTIFICATION_CHECKED","NOTIFICATION_CHECKED_PRE",this));

        if(CommonClass.returnGenericData("NOTIFICATION_CHECKED","NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("true"))
        {
            if(  CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED","checkBox2_NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("true"))
            {
                showNotification("goFuels", "Hi your goFuel wallet " + remoteMessage.getData().get("message") + " " + getResources().getString(R.string.symbol_rs)
                        + " " + remoteMessage.getData().get("amount") + ".");
            }

        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "Token " + s);
        AppSharedPreUtils.getInstance(getApplicationContext()).saveStringValues("FIREBASE_KEY", s);
    }

    int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

    void showNotification(String title, String description) {


        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder drivingNotifBldr = new NotificationCompat.Builder(getApplicationContext(), "Message")
                .setContentTitle(title)
                .setContentText(description)
                .setLights(Color.BLUE, 500, 500)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.gofuels))
                .setSmallIcon(R.drawable.ic_about)
                .setSound(soundUri)
                .setAutoCancel(true);
        Intent  openTrans = new Intent(getApplicationContext(), TransactionActivity.class);
//        openTrans.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openTrans.putExtra("screen",true);
        PendingIntent activity = PendingIntent.getActivity(getApplicationContext(), 0, openTrans, PendingIntent.FLAG_ONE_SHOT);

        drivingNotifBldr.setContentIntent(activity);


        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Message", "Message", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(iUniqueId, drivingNotifBldr.build());
    }
}
