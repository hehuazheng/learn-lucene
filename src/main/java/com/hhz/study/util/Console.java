package com.hhz.study.util;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Console {
	public static void writeTime(Stopwatch watch) {
		writeTime("", watch);
	}

	public static void writeTime(String message, Stopwatch watch) {
		System.out.println(message + "耗时 "
				+ watch.elapsed(TimeUnit.MICROSECONDS));
	}

	public static void writeTime(long time) {
		System.out.println("耗时 " + time);
	}
}
