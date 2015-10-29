package com.example.MapApplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

public class SearchPlaceTask extends AsyncTask<String, Integer, JSONObject> {

	private GoogleMap mMap;
	private Context mContext;
	private List<PlaceInfo> mPlaceInfoList;

	public SearchPlaceTask(GoogleMap map, List<PlaceInfo> placeInfoList,Context context) {
		super();
		mMap = map;
		mPlaceInfoList = placeInfoList;
		mContext = context;
	}

	@Override
	protected JSONObject doInBackground(String... contents) {
		HttpURLConnection connection = null;
		DataOutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(contents[0]);

			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			// データ作成
			String postData = "";
			postData += "keyword=" + contents[1];
			postData += "&" + "projectcode=" + contents[2];

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
			if (jsonObject.getInt("count") > 0) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);

					PlaceInfo pInfo = new PlaceInfo();
					pInfo.mPlaceId = obj.getInt("placeid");
					pInfo.mPlacename = obj.getString("placename");
					pInfo.mPlacename_en = obj.getString("placename_en");
					pInfo.mLatitude = obj.getDouble("latitude");
					pInfo.mLongitude = obj.getDouble("longitude");
					pInfo.mLikeCount = obj.getInt("likecount");
					pInfo.mAddress = obj.getString("address");
					pInfo.mPhonenumber = obj.getString("phonenumber");
					pInfo.mRemark = obj.getString("remark");
					pInfo.mProjectCode = obj.getString("projectcode");
					pInfo.SetMarker(mMap, mPlaceInfoList);
				}
			}
			else
			{
                Toast toast = Toast.makeText(mContext, mContext.getApplicationContext().getString(R.string.dialog_NoData), Toast.LENGTH_LONG);
                toast.show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
