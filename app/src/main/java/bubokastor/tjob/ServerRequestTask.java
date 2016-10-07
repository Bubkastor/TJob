package bubokastor.tjob;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import bubokastor.tjob.Items.ArticleContent;

/**
 * Created by bubok on 07.10.2016.
 */

public class ServerRequestTask extends AsyncTask<String, Void, JSONArray> {

    private ArticleContent mArticleCont;
    public ServerRequestTask(ArticleContent articleCont) {
        this.mArticleCont = articleCont;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        JSONArray result = new JSONArray();
        try {
            String str = "http://tjob.azurewebsites.net/api/JSON";
            URL url = new URL(str);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONArray tempArray = new JSONArray(response.toString());
                JSONArray convertRes = new JSONArray();
                for(int i = 0; i < tempArray.length(); i++){
                    convertRes.put(tempArray.getJSONObject(i).getJSONObject("value"));
                }

                urlConnection.disconnect();
                return convertRes;
            } else {
                urlConnection.disconnect();
                return result;
            }

        } catch (Exception ex) {
            Log.i("asd", ex.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONArray answer) {
        for (int i = 0; i < answer.length();i++) {
            try {
                JSONObject it = answer.getJSONObject(i);
                mArticleCont.addItem(new ArticleContent.Article(it.getString("id"),
                        it.getString("name"),
                        it.getString("time"),
                        it.getString("author"),
                        it.getString("description"),
                        it.getInt("count_like"),
                        it.getBoolean("is_like_me"),
                        it.getString("img_url")));
                ArticleListActivity.adapter.notifyDataSetChanged();
            }
            catch (Exception e) {
                Log.d("JSON", e.getMessage());
            }
        }
    }


}
