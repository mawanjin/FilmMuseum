package com.example.arthighlights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtContent;
import com.example.util.ArtContentAudio;
import com.example.util.ArtMenu;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ImgFragment extends Fragment {
	private TextView title, contentTitle, content;
	@SuppressWarnings("unused")
	private ImageView ivReturn, ivMenu, iv, img;
    private int id;
	private ArtMenu menu;

    public ImgFragment(int id){
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.img,null);

		SysApplication.getInstance().addActivity(getActivity());
		title = (TextView) view.findViewById(R.id.tv_title);
		contentTitle = (TextView) view.findViewById(R.id.contentTitle);
		content = (TextView) view.findViewById(R.id.content);

		img = (ImageView) view.findViewById(R.id.img);

		ivReturn = (ImageView) view.findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) view.findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		menu = MagicFactory.getArtMenu(id);

        initTxt();

        return view;
	}


	private static boolean isExit = false;

	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getActivity(), "再按一次推出程序",
						Toast.LENGTH_SHORT).show();
				exit = new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			} else {
				getActivity().finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}


    private void initTxt() {
		img.setImageBitmap(MagicFactory.getBitmap(menu.getSrc()));
		title.setText(menu.getTitle());
		contentTitle.setTextSize(35);
		contentTitle.setText(menu.getTitle());
		content.setTextSize(27);
		content.setText(Html.fromHtml(menu.getText()));
    }

}
