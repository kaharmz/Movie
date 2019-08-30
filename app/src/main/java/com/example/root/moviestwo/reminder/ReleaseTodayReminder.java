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
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.R;
import com.example.root.moviestwo.activities.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ReleaseTodayReminder extends BroadcastReceiver {

    public static final String TYPE_RELEASE = "ReleaseTodayAlarm";
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String MOVIE_URL = BuildConfig.MOVIE_URL;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String RELEASE_URL = BuildConfig.RELEASE_TODAY_URL;
    private static final String RELEASE_URLS = BuildConfig.RELEASE_TODAY_URLS;
    private final int REMINDER_ID_RELEASE = 100;
    private final int REMINDER_ID_REPEATING = 101;

    String CHANNEL_ID = "Channel_1";
    String CHANNEL_NAME = "AlarmManager channel";

    private int mReminderId = 0;

    @Override
    public void onReceive(final Context mContext, Intent mIntent) {

        final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        final Date mDate = new Date();
        final String mCurrentDate = mDateFormat.format(mDate);

        Log.d(TAG, " " + mCurrentDate);

        AsyncHttpClient mClient = new AsyncHttpClient();
        String mUrl = BASE_URL + MOVIE_URL + API_KEY + RELEASE_URL + mCurrentDate + RELEASE_URLS + mCurrentDate;

        mClient.get(mUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String mResult = new String(responseBody);
                    JSONObject mJsonObject = new JSONObject(mResult);
                    JSONArray mListRelease = mJsonObject.getJSONArray("results");
                    for (int i = 0; i < mListRelease.length(); i++) {
                        JSONObject mMovies = mListRelease.getJSONObject(i);
                        String mTitle = mMovies.getString("title");
                        String mDates = mMovies.getString("release_date");

                        if (mCurrentDate.equals(mDates)) {
                            String mMessage = mTitle + ", " + mContext.getResources().getString(R.string.pop_up_reminder);
                            showAlarmNotification(mContext, mTitle, mMessage, mReminderId);
                        }
                        //mReminderId += 1;
                    }

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showAlarmNotification(Context mContext, String mMessage, String mTitle, int mReminderId) {

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri mAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent mIntent = new Intent(mContext, MainActivity.class);

        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent mPendingIntent = PendingIntent.getActivity(mContext, mReminderId, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.movie_icon_white)
                .setContentTitle(mTitle)
                .setContentText(mMessage)
                .setColor(ContextCompat.getColor(mContext, android.R.color.transparent))
                .setContentIntent(mPendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 10001, 1000, 1000, 1000})
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

    public void setRepeatingAlarm(Context mContext, String mType, String mTime) {

        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(mContext, ReleaseTodayReminder.class);

        String mTimeArray[] = mTime.split(":");

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mTimeArray[0]));
        mCalendar.set(Calendar.MINUTE, Integer.parseInt(mTimeArray[1]));
        mCalendar.set(Calendar.SECOND, 0);

        if (mCalendar.before(Calendar.getInstance())) mCalendar.add(Calendar.DATE, 1);

        int mRequestCode = mType.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_ID_RELEASE : REMINDER_ID_REPEATING;
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(mContext, mRequestCode, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPendingIntent);
    }

    public void cancelAlarmReminder(Context mContext, String mType) {

        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(mContext, ReleaseTodayReminder.class);
        int mRequestCode = mType.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_ID_RELEASE : REMINDER_ID_REPEATING;
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(mContext, mRequestCode, mIntent, 0);
        mAlarmManager.cancel(mPendingIntent);
    }
}
