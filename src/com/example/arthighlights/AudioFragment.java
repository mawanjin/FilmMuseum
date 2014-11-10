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
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioFragment extends Fragment implements OnSeekBarChangeListener {
	private TextView tv, tv2, tv3;
	@SuppressWarnings("unused")
	private ImageView ivReturn, ivMenu, iv, iv2, iv3, iv4, iv5;
    private LinearLayout seekbarContainer;
	private PopupWindow pop;
	private MediaPlayer player = null;
	private SeekBar seekbar;
	private int x, y;
	private boolean flag = true;
	private upDateSeekBar update; // 更新进度条用
	private String path = null;
	private Bitmap bm;
	// 申请的微信 appid
	private static final String APP_ID = "wx6462caed59df1b17";
	// IWXAPI是第三方app和微信通信的openaip接口
	private IWXAPI api;
    private int id;
    private List<ArtContentAudio> cons;

    public AudioFragment(int id){
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.audio,null);
		WindowManager wm = (WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE);
		x = wm.getDefaultDisplay().getWidth();
		y = wm.getDefaultDisplay().getHeight();
		SysApplication.getInstance().addActivity(getActivity());
        seekbarContainer = (LinearLayout) view.findViewById(R.id.seekbarContainer);
		tv = (TextView) view.findViewById(R.id.tv_title);
		tv2 = (TextView) view.findViewById(R.id.tv_Audio);
		tv3 = (TextView) view.findViewById(R.id.tv2_Audio);

		iv = (ImageView) view.findViewById(R.id.audio_iv);
		iv2 = (ImageView) view.findViewById(R.id.audio_iv2);
		iv3 = (ImageView) view.findViewById(R.id.audio_iv3);
//		iv4 = (ImageView) view.findViewById(R.id.audio_iv4);
//		iv5 = (ImageView) view.findViewById(R.id.audio_iv5);

		ivReturn = (ImageView) view.findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) view.findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		player = new MediaPlayer();
        player.setLooping(true);
		seekbar = (SeekBar) view.findViewById(R.id.audio_seekbar);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekbar.getProgress() * player.getDuration() // 计算进度条需要前进的位置数据大小
                        / seekbar.getMax();
                player.seekTo(value);
            }
        });

        cons = MagicFactory.getAudios();
        initTxt();

		try {
			player.setDataSource(path);
			player.prepare();
			player.start();
			update = new upDateSeekBar();
			new Thread(update).start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				iv2.setImageResource(R.drawable.player);
				seekbar.setProgress(0);
                player.start();
			}
		});
		iv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (!player.isPlaying()) {
					player.start();
					new Thread(update).start();
					iv2.setImageResource(R.drawable.pause);
					flag = true;
				} else {
					player.pause();
					new Thread(update).start();
					iv2.setImageResource(R.drawable.player);
					flag = false;
				}
			}
		});
		Log.v("HTTWs", "-------------->3 " + player.isPlaying());

        return view;
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (player == null) {
				flag = false;
			} else if (player.isPlaying()) {
				flag = true;
				int position = player.getCurrentPosition();
				int mMax = player.getDuration();
				int sMax = seekbar.getMax();
				seekbar.setProgress(position * sMax / mMax);
			} else {
				return;
			}
		};
	};

	// 每秒更新一次进度条
	class upDateSeekBar implements Runnable {
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
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

    @Override
    public void onDestroyView() {
        if(player!=null){
            player.release();
            player = null;
        }

        super.onDestroyView();
    }



	private void showPopUp(View v) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.share, null);
		Button btn = (Button) view.findViewById(R.id.share_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (pop != null && pop.isShowing() == true) {
					pop.dismiss();
				}
			}
		});
		ImageView iv1 = (ImageView) view.findViewById(R.id.share_iv);
		iv1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.v("HTTWs", "000");
				String text = "微信分享";
				WXTextObject textObj = new WXTextObject();
				textObj.text = text;
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = text;
				SendMessageToWX.Req req = new SendMessageToWX.Req();

				req.transaction = buildTransaction("text");
				req.message = msg;

				api.sendReq(req);

				SysApplication.getInstance().exit();
			}
		});
		ImageView iv2=(ImageView) view.findViewById(R.id.share_iv2);
		iv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl="http://www.baidu.com";
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title ="测试";
				msg.description ="made in HTTWs";
				SendMessageToWX.Req req=new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				api.sendReq(req);
			}
		});
		pop = new PopupWindow(view, LayoutParams.FILL_PARENT, 600);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, (int) y);
	}
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		Log.v("HTTWs", "----------> " + progress);
	}

	public void onStartTrackingTouch(SeekBar arg0) {

	}

	public void onStopTrackingTouch(SeekBar arg0) {

	}

    @Override
    public void onStop() {
        if(player!=null){
            player.release();
            player = null;
        }
        super.onStop();
    }

    public void switchURL(int _id){
        if(player==null)return;
        List<ArtContent> list = MagicFactory.getArtContents();
        id = _id;
        for (ArtContent art : list) {
            if (id == art.getId()) {
                path = MagicFactory.getPlayUrl(art.getSrc());
            }
        }
        try {
            player.stop();
            player.reset();
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTxt();

    }

    private void initTxt() {

        for (ArtContentAudio arts : cons) {
            if (arts.getVisible() == 0) {
                iv.setVisibility(View.GONE);
            } else {
                if (arts.getType().equals("image")) {
                    if (arts.getId() == 1) {
                        iv.setImageBitmap(MagicFactory.getBitmap(arts.getSrc()));
                        iv.setImageBitmap(bm);
                        iv.setX(arts.getX());
                        iv.setY(arts.getY());
                    }
                    if (arts.getId() == 3) {
                        iv2.setImageBitmap(MagicFactory.getBitmap(arts.getSrc()));
                        LayoutParams params = new LayoutParams(arts.getWidth(),
                                arts.getHeight());
                        iv2.setImageBitmap(bm);
                        iv2.setX(arts.getX());
                        iv2.setY(arts.getY());
                        iv2.setLayoutParams(params);
                    }
                    if (arts.getId() == 6) {
                        iv2.setImageBitmap(MagicFactory.getBitmap(arts.getSrc()));
//						iv5.setImageBitmap(bm);
//						iv5.setX(arts.getX());
//						iv5.setY(arts.getY());
                    }
                } else if (arts.getType().equals("text")) {
                    if (arts.getId() == 7) {
                        tv2.setX(arts.getX());
                        tv2.setY(arts.getY());
                        tv2.setTextSize(arts.getTextsize());
                    }
                    if (arts.getId() == 8) {
                        tv3.setX(arts.getX());
                        tv3.setY(arts.getY());
                        tv3.setTextSize(arts.getTextsize());
                    }
                } else if (arts.getType().equals("seekbar")) {
                    seekbarContainer.setY(arts.getY()-25);
                    seekbar.setX(arts.getX());
                    seekbar.setY(arts.getY());
                }
            }
        }

//		iv4.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				showPopUp(v);
//			}
//		});


        ArtContent c = MagicFactory.getPlay(id);
        tv.setText(c.getTitle());
        tv2.setText(c.getTitle());
        tv3.setText(c.getContent());
        path = MagicFactory.getPlayUrl(c.getSrc());
    }

}
