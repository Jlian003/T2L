package ch.unibe.zeeguu.t2l;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import ch.unibe.zeeguu.t2l.api.ApiInterface;
import ch.unibe.zeeguu.t2l.net.DataDownloader;
import ch.unibe.zeeguu.t2l.net.DataReceiver;

public class Widget2Learn extends AppWidgetProvider implements DataReceiver {
    // Intent actions, these must be declared in the AndroidManifest.xml
    public static String W_LANGUAGE_SELECTION = "ch.unibe.zeeguu.t2l.WDG_LANGUAGE_SELECTION";

    @Override
    public void onEnabled(Context context) {
        System.out.println("onEnabled()");

        new DataDownloader(this).execute(ApiInterface.URL_LIST_LANGUAGES);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        System.out.println("onUpdate()");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    @Override
    public void onReceive(Context context, Intent intent){
        System.out.println("onReceive triggered! Intent action: " + intent.getAction());

        if(W_LANGUAGE_SELECTION.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.language_grid);

            int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(
                    new ComponentName(context, Widget2Learn.class));


            appWidgetManager.updateAppWidget(appWidgetIds, views);
        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_loader);
        // Construct an Intent object includes web adresss.
        Intent langSelection = new Intent(context, Widget2Learn.class);
        langSelection.setAction(W_LANGUAGE_SELECTION);
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, langSelection, PendingIntent.FLAG_UPDATE_CURRENT);
        // Here the basic operations the remote view can do.

        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    public void setData(Object data){
        String languages = (String) data;
        ApiInterface.setLanguages(languages);
    }

}