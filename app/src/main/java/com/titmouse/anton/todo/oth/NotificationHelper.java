package com.titmouse.anton.todo.oth;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.ui.MainActivity;

public class NotificationHelper {
	
	private Context mContext;
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder mBuilder;
	public static final String NOTIFICATION_CHANNEL_ID = "10001";
	
	public NotificationHelper(Context context) {
		mContext = context;
	}
	
	/**
	 * Create and push the notification
	 */
	public Notification createNotification(final String title, final String message)
	{
		/**Creates an explicit intent for an Activity in your app**/
		final Intent resultIntent = new Intent(mContext , MainActivity.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		final PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
			0 /* Request code */, resultIntent,
			PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder = new NotificationCompat.Builder(mContext);
		mBuilder.setSmallIcon(R.mipmap.ic_launcher);
		mBuilder.setContentTitle(title)
			.setContentText(message)
			.setAutoCancel(false)
			.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
			.setContentIntent(resultPendingIntent);
		
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
		{
			final int importance = NotificationManager.IMPORTANCE_HIGH;
			final NotificationChannel notificationChannel =
				new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.RED);
			notificationChannel.enableVibration(true);
			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			assert mNotificationManager != null;
			mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
			mNotificationManager.createNotificationChannel(notificationChannel);
		}
		assert mNotificationManager != null;

		return mBuilder.build();
		
	}
}
