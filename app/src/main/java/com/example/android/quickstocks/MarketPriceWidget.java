package com.example.android.quickstocks;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.android.quickstocks.UI.MainActivity;

import static com.example.android.quickstocks.UI.MainListFragment.PREF_WIDGET;
import static com.example.android.quickstocks.UI.MainListFragment.PREF_WIDGET_CHNAGE;
import static com.example.android.quickstocks.UI.MainListFragment.PREF_WIDGET_MAIN;
import static com.example.android.quickstocks.UI.MainListFragment.PREF_WIDGET_PERCENT;

/**
 * Implementation of App Widget functionality.
 */
public class MarketPriceWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, MainActivity.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_WIDGET, 0);
        String price = sharedPreferences.getString(PREF_WIDGET_MAIN, "F");
        String change = sharedPreferences.getString(PREF_WIDGET_CHNAGE, "F");
        String percent = sharedPreferences.getString(PREF_WIDGET_PERCENT, "F");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.market_price_widget);

        views.setTextViewText(R.id.widget_main_text, price);
        views.setTextViewText(R.id.widget_change, change);
        views.setTextViewText(R.id.widget_percent, percent);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_main_text);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void update(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MarketPriceWidget.class));
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
}

