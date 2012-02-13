package com.bakes.aqacomp4.imagetools;

public enum ImageTypes {
BITMAP, JPEG, BITMAP_STYLE;

	@Override
	public String toString()
	{
		switch(this)
		{
		case BITMAP:
			return "Bitmap";
		case JPEG:
			return "JPEG";
		case BITMAP_STYLE:
			return "Bitmap Style";
		default:
			return null;
		}
	}
}