package com.lsqboy.dev.utils;

import android.graphics.Color;

public class ColorUtil {
	
	private String red;
	private String green;
	private String blue;
	
	public String convertToARGB(int color) {
		// alpha = Integer.toHexString(Color.alpha(color));
		red = Integer.toString(Color.red(color));
		green = Integer.toString(Color.green(color));
		blue = Integer.toString(Color.blue(color));

//		ColorR.setText(red);
//		ColorG.setText(green);
//		ColorB.setText(blue);

		return "#" + red + green + blue;
	}

	public static int alpha(int color) {

		return color >>> 24;
	}

	/**
	 * Return the red component of a color int. This is the same as saying
	 * (color >> 16) & 0xFF
	 */
	public static int red(int color) {
		return (color >> 16) & 0xFF;
	}

	/**
	 * Return the green component of a color int. This is the same as saying
	 * (color >> 8) & 0xFF
	 */
	public static int green(int color) {
		return (color >> 8) & 0xFF;
	}

	/**
	 * Return the blue component of a color int. This is the same as saying
	 * color & 0xFF
	 */
	public static int blue(int color) {
		return color & 0xFF;
	}
}
