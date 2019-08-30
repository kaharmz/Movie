package com.example.root.moviestwo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.root.moviestwo.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMovieWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.example.root.EXTRA_ITEM";
    private static final String TOAST_ACTION = "com.example.root.TOAST_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent mIntent = new Intent(context, FavoriteStackWidgetService.class);
        mIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        mIntent.setData(Uri.parse(mIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_movie_widget);
        views.setRemoteAdapter(R.id.favorite_stack_image, mIntent);
        views.setEmptyView(R.id.favorite_stack_image, R.id.favorite_text_empty);

        Intent mToast = new Intent(context, FavoriteMovieWidget.class);
        mToast.setAction(FavoriteMovieWidget.TOAST_ACTION);
        mToast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        mIntent.setData(Uri.parse(mIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent mToastPending = PendingIntent.getBroadcast(context, 0, mToast, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.favorite_stack_image, mToastPending);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                //int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName componentName = new ComponentName(context, FavoriteMovieWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.favorite_stack_image);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }
        super.onReceive(context, intent);
    }
}

