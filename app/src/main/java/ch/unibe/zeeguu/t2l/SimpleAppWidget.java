package ch.unibe.zeeguu.t2l;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SimpleAppWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        String languages = "[]";
        try {
            URL url = new URL("https://zeeguu.unibe.ch/api/available_languages");
            languages = new DownloadFilesTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);
        updateViews.setTextViewText(R.id.tvWidget, languages);
        System.out.println("Onenabled: " + languages);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        String languages = "[]";
        try {
            URL url = new URL("https://zeeguu.unibe.ch/api/available_languages");
            languages = new DownloadFilesTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);
        updateViews.setTextViewText(R.id.tvWidget, languages);
        System.out.println("OnUpdate: " + languages);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);
        // Construct an Intent object includes web adresss.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://erenutku.com"));
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        System.out.println("Updateappwidget: ");
    }

}