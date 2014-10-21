package com.example.filmmuseum.wxapi;

import com.example.filmmuseum.R;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private IWXAPI api;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wxentry);
		api = WXAPIFactory.createWXAPI(this, "wx6462caed59df1b17", false);
		api.handleIntent(getIntent(), this);
	}
	public void onReq(BaseReq arg0) {
	}

	public void onResp(BaseResp resp) {
		int result = 0;

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = 1;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = 2;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = 3;
			break;
		default:
			result = 4;
			break;
		}
	}
	

}
