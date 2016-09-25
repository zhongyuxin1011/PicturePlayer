package com.zyx1011.pictureplayer;

import android.content.Context;

/**
 * 屏幕适配相关工具类
 * 
 * @author zhongyuxin
 */
public class DisplayUtils {

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static float px2dp(Context context, int px) {
		float density = context.getResources().getDisplayMetrics().density;
		return px / density + 0.5f;
	}

}
