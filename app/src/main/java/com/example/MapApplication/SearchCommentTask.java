package com.example.MapApplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchCommentTask extends AsyncTask<String, Integer, JSONObject> {
	private Activity mActivity;
	public SearchCommentTask(Activity activity) {
		super();
		mActivity = activity;
	}

	@Override
	protected JSONObject doInBackground(String... contents) {
		HttpURLConnection connection = null;
		DataOutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(contents[1]);

			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			// データ作成
			String postData = "";
			postData += "placeid=" + contents[0];

			// データを送信する
			os = new DataOutputStream(connection.getOutputStream());
			os.writeBytes(postData);

			// レスポンスを受信する
			int iResponseCode = connection.getResponseCode();

			// 接続が確立したとき
			if (iResponseCode == HttpURLConnection.HTTP_OK) {
				StringBuilder resultBuilder = new StringBuilder();
				String line = "";

				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				// レスポンスの読み込み
				while ((line = br.readLine()) != null) {
					resultBuilder.append(String.format("%s%s", line, "\r\n"));
				}
				String result = resultBuilder.toString();

				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}
			// 接続が確立できなかったとき
			else {
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			// 開いたら閉じる
			try {
				if (br != null) br.close();
				if (os != null) {
					os.flush();
					os.close();
				}
				if (connection != null) connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	protected void onPostExecute(JSONObject jsonObj) {
		try
		{
			if (jsonObj.getInt("count") > 0) {
				String[] items = new String[jsonObj.getInt("count")];
				JSONArray jsonArray = jsonObj.getJSONArray("comments");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					items[i] = obj.getString("comment");

				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						mActivity,
						android.R.layout.simple_list_item_1,
						items
				);
				ListView listview = (ListView) mActivity.findViewById(R.id.ListView_comment);
				listview.setAdapter(adapter);

			}
		}

		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

}
