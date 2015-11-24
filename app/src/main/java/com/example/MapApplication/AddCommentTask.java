package com.example.MapApplication;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddCommentTask extends AsyncTask<String, Integer, JSONObject> {

	private Context mContext;
    private ProgressDialog mDialog;
    private String mURL;
	public int  mCommentid;

	public AddCommentTask (Context context, String url) {
		super();
		mContext = context;
		mURL = url;
	}

	@Override
	protected void onPreExecute() {
		mDialog = new ProgressDialog(mContext);
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setTitle("登録中");  //TODO:string.xml化
		mDialog.setMessage("コメントの登録を行っています...");//TODO:string.xml化
		mDialog.setCancelable(false);
		mDialog.show();
	}

	@Override
	protected JSONObject doInBackground(String... contents) {
		HttpURLConnection connection = null;
		DataOutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(mURL);

			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			// データ作成
			String postData = "";
			postData += "placeid=" + contents[0];
			postData += "&" + "userid=" + contents[1];
			postData += "&" + "createtime=" + contents[2];
			postData += "&" + "comment=" + contents[3];

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

	protected void onPostExecute(JSONObject jsonObject) {

		try {
			PlaceInfo pInfo = new PlaceInfo();
			pInfo.mCommentid = jsonObject.getInt("commentid");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		mDialog.dismiss();
		//TODO:string.xml化
		Toast toast = Toast.makeText(mContext, "コメントを追加しました", Toast.LENGTH_LONG);
		toast.show();
	}
}
