package com.example.root.moviestwo.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.activities.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DailyReminder extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private final int REMINDER_ID_ONETIME = 100;
    private final int REMINDER_ID_REPEATING = 101;

    String CHANNEL_ID = "Channel_1";
    String CHANNEL_NAME = "AlarmManager channel";
    String mTitle;

    public DailyReminder() {

    }

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        String mType = mIntent.getStringExtra(EXTRA_TYPE);
        String mMessage = mIntent.getStringExtra(EXTRA_MESSAGE);

        mTitle = mType.equalsIgnoreCase(TYPE_ONE_TIME) ? "One Time Alarm" : "Repeating Alarm";
        int mReminderId = mType.equalsIgnoreCase(TYPE_ONE_TIME) ? REMINDER_ID_ONETIME : REMINDER_ID_REPEATING;

        mTitle = mContext.getResources().getString(R.string.app_name);
        showAlarmReminder(mContext, mTitle, mMessage, mReminderId);
    }

    private void showAlarmReminder(Context mContext, String mTitle, String mMessage, int mReminderId) {
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(
                        Context.NOTIFICATION_SERVICE);
        Uri mAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent mIntent = new Intent(mContext, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(mContext,
                mReminderId,
                mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.movie_icon_white)
                        .setContentTitle(mTitle)
                        .setContentText(mMessage)
                        .setColor(ContextCompat.getColor(mContext, android.R.color.transparent))
                        .setContentIntent(mPendingIntent)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(mAlarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        Notification mNotification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(mReminderId, mNotification);
        }

    }

    public void setRepeatingAlarm(Context mContext, String mType, String mTime, String mMessage) {
        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(mTime, TIME_FORMAT)) return;

        Intent mIntent = new Intent(mContext, DailyReminder.class);
        mIntent.putExtra(EXTRA_MESSAGE, mMessage);
        mIntent.putExtra(EXTRA_TYPE, mType);

        String mTimeArray[] = mTime.split(":");

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mTimeArray[0]));
        mCalendar.set(Calendar.MINUTE, Integer.parseInt(mTimeArray[1]));
        mCalendar.set(Calendar.SECOND, 0);

        if (mCalendar.before(Calendar.getInstance())) mCalendar.add(Calendar.DATE, 1);

        int mRequestCode = REMINDER_ID_REPEATING;
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(mContext, mRequestCode, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPendingIntent);

    }

    public void cancelAlarm(Context mContext, String mType) {
        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(mContext, DailyReminder.class);

        int mRequestCode = mType.equalsIgnoreCase(TYPE_ONE_TIME) ? REMINDER_ID_ONETIME : REMINDER_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, mRequestCode, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (mAlarmManager != null) {
            mAlarmManager.cancel(pendingIntent);
        }

    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
