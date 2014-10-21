package com.example.filmmuseum;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class SysApplication extends Application {
	//����list���ϱ���activity
	private List<Activity> mList = new LinkedList<Activity>();
	//Ϊ��ʵ��ÿ��ʹ�ø���ʱ�������µĶ���������ľ�̬����
	private static SysApplication instance;
	//���췽��
	public SysApplication() {}
	//ʵ����һ��
	public synchronized static SysApplication getInstance()
	{
		if(null == instance)
		{
			instance = new SysApplication();
		}
		return instance;
	}
	//��activityװ��list����
	public void addActivity(Activity activity)
	{
		mList.add(activity);
	}
	
	//�ر����е�activity
	public void exit()
	{
		try {
			for(Activity activity:mList)
			{
				if(activity != null)
				{
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}
	
	public void onLowMemory()
	{
		super.onLowMemory();
		System.gc();
	}
}
