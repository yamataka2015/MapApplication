package com.example.MapApplication;

import java.io.Serializable;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceInfo implements Serializable {

	public Marker mMarker;

	public int mPlaceId;
	public String mPlacename;
	public String mPlacename_en;
	public double mLatitude;
	public double mLongitude;
	public int mLikeCount;
	public String mAddress;
	public String mPhonenumber;
	public String mRemark;
	public String mProjectCode;
    public int mDisLikeCount;
	public int mCommentid;

	public PlaceInfo() {
		// TODO:Initialize arguments
	}

	@Override
	public boolean equals(Object obj){
		// Compare by 'Marker.getId()'
		PlaceInfo pInfo = (PlaceInfo)obj;
		if(pInfo.mMarker == null)
		{
			return false;
		}
		else if(mMarker.getId().equals(pInfo.mMarker.getId()))
		{
			return true;
		}
	    return false;
	}

	@Override
	protected void finalize() throws Throwable {
		if (mMarker != null) {
			mMarker.remove();
		}
		super.finalize();
	}

	public LatLng getLatLng() {
		return new LatLng(mLatitude, mLongitude);
	}

	public void SetMarker(GoogleMap map, List<PlaceInfo> list) {
		mMarker = map.addMarker(new MarkerOptions().position(getLatLng())
				.title(mPlacename).snippet(mPlacename_en));
		list.add(this);
	}

	//場所の文字列を返す
	public String toString(){
		return this.mPlacename;
	}

	public PlaceInfo getSeralizable() {
		PlaceInfo pClone = new PlaceInfo();
		// mMarker:null
        pClone.mPlaceId = this.mPlaceId;
		pClone.mPlacename = this.mPlacename;
        pClone.mPlacename_en = this.mPlacename_en;
    	pClone.mLatitude = this.mLatitude;
    	pClone.mLongitude = this.mLongitude;
        pClone.mAddress = this.mAddress;
        pClone.mPhonenumber = this.mPhonenumber;
        pClone.mLikeCount = this.mLikeCount;
		pClone.mDisLikeCount = this.mDisLikeCount;
        pClone.mProjectCode = this.mProjectCode;
        pClone.mRemark = this.mRemark;

		pClone.mCommentid = this.mCommentid;

		return pClone;
	}

}