package com.example.arthighlights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.view.SurfaceHolder.Callback;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtContent;
import com.example.util.ArtContentVideo;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VideoFragment extends Fragment implements Callback,
		OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener {

	private TextView tv, tv2, tv3;

	private ImageView iv1, iv2, iv3;

	private ImageView ivReturn, ivMenu;

	private SeekBar seekBar; // 进度条
	private upDateSeekBar update; // 更新进度条用
	private boolean flag = true; // 用于判断视频是否在播放中

	private String path = null; // 播放路径
	// 申请的微信 appid
	private static final String APP_ID = "wx6462caed59df1b17";
	private int x, y;
	private IWXAPI api;
	private PopupWindow pop;

	private Display currentDisplay;
	private SurfaceView surface;
	private SurfaceHolder holder;
	// 使用media播放视频
	private MediaPlayer mediaPlayer;
	// 视频的宽高
	private int videoWidth = 0;
	private int videoHeight = 0;

    private int id;
    private LinearLayout seekbarContainer;
    private List<ArtContent> list;
    private List<ArtContentVideo> video;

    public VideoFragment(int id){
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video,null);
		WindowManager wm = (WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE);
		x = wm.getDefaultDisplay().getWidth();
		y = wm.getDefaultDisplay().getHeight();

        seekbarContainer = (LinearLayout) view.findViewById(R.id.seekbarContainer);
		seekBar = (SeekBar) view.findViewById(R.id.sb_player);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress() * mediaPlayer.getDuration() // 计算进度条需要前进的位置数据大小
                        / seekBar.getMax();
                mediaPlayer.seekTo(value);
            }
        });

		tv = (TextView) view.findViewById(R.id.tv_title);
		tv2 = (TextView) view.findViewById(R.id.tv_video);
		tv3 = (TextView) view.findViewById(R.id.tv2_video);
		iv1 = (ImageView) view.findViewById(R.id.iv_player);
		iv2 = (ImageView) view.findViewById(R.id.iv_suki);
		iv3 = (ImageView) view.findViewById(R.id.iv_download);

		tv.setText("动画电影工作室");

        list = MagicFactory.getArtContents();
        video = MagicFactory.getArtContentVideos();

        initTxt();


		ivReturn = (ImageView) view.findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) view.findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);

		surface = (SurfaceView) view.findViewById(R.id.surface);
		holder = surface.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		try {
            mediaPlayer.setLooping(true);
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			mediaPlayer.start();
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
		currentDisplay = getActivity().getWindowManager().getDefaultDisplay();
		iv1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (!mediaPlayer.isPlaying()) {
					mediaPlayer.start();
					// 播放开始后让进度条更新
					new Thread(update).start();
					iv1.setImageResource(R.drawable.pause);
					flag = true;
				} else {
					mediaPlayer.pause();
					new Thread(update).start();
					iv1.setImageResource(R.drawable.player);
					flag = false;
				}
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				iv1.setImageResource(R.drawable.player);
				seekBar.setProgress(0);
                mediaPlayer.start();
			}
		});
        return view;
	}

    private void initTxt() {
        for (ArtContentVideo vi : video) {
            if (vi.getType().equals("image")) {
                if (vi.getId() == 1) {
                    LayoutParams params = new LayoutParams(vi.getWidth(),
                            vi.getHeight());
                    iv1.setImageBitmap(MagicFactory.getBitmap(vi.getSrc()));
                    iv1.setLayoutParams(params);
                }
//				if (vi.getId() == 2) {
//					Bitmap bm = BitmapFactory
//							.decodeFile(getExternalStoragePath()
//									+ "/FilmMuseum/system/image/" + vi.getSrc());
//					iv2.setImageBitmap(bm);
//					iv2.setX(vi.getX());
//					iv2.setY(vi.getY());
//				}
//				if (vi.getId() == 3) {
//					Bitmap bm = BitmapFactory
//							.decodeFile(getExternalStoragePath()
//									+ "/FilmMuseum/system/image/" + vi.getSrc());
//					iv3.setImageBitmap(bm);
//					iv3.setX(vi.getX());
//					iv3.setY(vi.getY());
//				}
            }
            if (vi.getType().equals("text")) {
                if (vi.getId() == 4) {
                    tv2.setX(vi.getX());
                    tv2.setY(vi.getY());
                    tv2.setWidth(vi.getWidth());
                    tv2.setTextSize(vi.getTextsize());
                }
                if (vi.getId() == 5) {
                    tv3.setX(vi.getX());
                    tv3.setY(vi.getY());
                    tv3.setWidth(vi.getWidth());
                    tv3.setTextSize(vi.getTextsize());
                }
            }
        }

        for (ArtContent art : list) {
            if (id == art.getId()) {
                tv.setText(art.getTitle());
                tv2.setText(art.getTitle());
                tv3.setText(Html.fromHtml(art.getContent()));
                path = MagicFactory.getPlayUrl(art.getSrc());
            }
        }

    }


    @SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mediaPlayer == null) {
				flag = false;
			} else if (mediaPlayer.isPlaying() == true) {
				flag = true;
				int position = mediaPlayer.getCurrentPosition();
				int mMax = mediaPlayer.getDuration();
				int sMax = seekBar.getMax();
                if(mMax>0)
				seekBar.setProgress(position * sMax / mMax);
			} else {
				return;
			}
		};
	};

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

	private static boolean isExit = false;

	// 每秒更新一次进度条
	class upDateSeekBar implements Runnable {
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
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
		ImageView iv2 = (ImageView) view.findViewById(R.id.share_iv2);
		iv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://www.baidu.com";
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = "测试";
				msg.description = "made in HTTWs";
				SendMessageToWX.Req req = new SendMessageToWX.Req();
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
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void regToWx() {
		// 创建WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(getActivity(), APP_ID, true);
		boolean bl = api.registerApp(APP_ID);
		Log.v("HTTWs", "bl " + bl);
	}

    @Override
    public void onDestroyView() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
            System.gc();
        }

        super.onDestroyView();
    }


	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
	}

	public void onSeekComplete(MediaPlayer arg0) {
	}

	public void onPrepared(MediaPlayer arg0) {
		videoWidth = 1600;
		videoHeight = 1200;
		if (videoWidth > currentDisplay.getWidth()
				|| videoHeight > currentDisplay.getHeight()) {
			float heightRatio = (float) videoHeight
					/ (float) currentDisplay.getHeight();
			float widthRatio = (float) videoWidth
					/ (float) currentDisplay.getWidth();
			if (heightRatio > 1 || widthRatio > 1) {
				if (heightRatio > widthRatio) {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) heightRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) heightRatio);
				} else {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) widthRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) widthRatio);
				}
			}
		}
        seekbarContainer.setY(videoHeight+20);
		seekBar.setX(100);
		seekBar.setY(videoHeight+55);
		iv1.setX(10);
		iv1.setY(videoHeight+20);
		surface.setLayoutParams(new LayoutParams(videoWidth,
				videoHeight));
		mediaPlayer.start();
	}

	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	public void onCompletion(MediaPlayer arg0) {
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) {
        if(mediaPlayer!=null)
		mediaPlayer.setDisplay(holder);
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

    public void switchURL(int _id){
        if(mediaPlayer==null)return;
        List<ArtContent> list = MagicFactory.getArtContents();
        id = _id;
        for (ArtContent art : list) {
            if (id == art.getId()) {
                path = MagicFactory.getPlayUrl(art.getSrc());
            }
        }
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTxt();

    }

    @Override
    public void onStop() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onStop();
    }
}
