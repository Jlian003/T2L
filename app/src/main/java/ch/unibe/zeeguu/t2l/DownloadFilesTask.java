package ch.unibe.zeeguu.t2l;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFilesTask extends AsyncTask<URL, Integer, String> {
    protected String doInBackground(URL... urls) {
        String text = "";
        try {
            URL url = urls[0];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                text = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Long result) {
    }
}
