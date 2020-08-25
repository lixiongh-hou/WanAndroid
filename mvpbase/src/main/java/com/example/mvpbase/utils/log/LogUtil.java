package com.example.mvpbase.utils.log;

import android.util.Log;

import androidx.annotation.IntDef;

import com.example.mvpbase.json.JsonUtil;
import com.example.mvpbase.utils.check.CheckUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * 打印Log
 *
 * @author 雄厚
 */
public class LogUtil {

    /**是否打印Log*/
    public static final boolean DEBUG = true;

    public static void e(String tag, String msg) {
        if (LogUtil.DEBUG) {
            if (CheckUtil.isEmpty(tag))
            {
                tag = "tag is null";
            }
            if (CheckUtil.isEmpty(msg))
            {
                msg = "msg is null";
            }
            Log.e(tag, msg);
        }
    }

    public static void e(Object msg) {
        log(LogUtil.E, false, msg);
    }

    public static void eSuper(Object msg) {
        log(LogUtil.E, true, msg);
    }

    private static final int I = 1;
    private static final int W = 2;
    private static final int E = 3;

    @IntDef({LogUtil.I, LogUtil.W, LogUtil.E})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final String SOLID_LINE = "────────────────────────────────────────────────────────────";
    private static final String DOTTED_LINE = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_LINE = "┌" + LogUtil.SOLID_LINE + LogUtil.SOLID_LINE;
    private static final String MIDDLE_LINE = "├" + LogUtil.DOTTED_LINE + LogUtil.DOTTED_LINE;
    private static final String BOTTOM_LINE = "└" + LogUtil.SOLID_LINE + LogUtil.SOLID_LINE;
    private static final String HEAD_LINE = "│ ";
    private static final int MAX_LENGTH = 3000;

    private static void log(@TYPE int type, boolean isSuper, Object msg) {

        if (!LogUtil.DEBUG) {
            return;
        }

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement ste = stackTrace[2];
        // 文件名
        String fileNameLong = ste.getFileName();
        String fileName = fileNameLong.substring(0, fileNameLong.lastIndexOf("."));
        // 行数
        int lineNumber = ste.getLineNumber();
        // 线程名
        String threadName = Thread.currentThread().getName();

        print(type, getRandom() + "\u3000" + fileName, LogUtil.TOP_LINE);

        if (isSuper) {
            print(type, getRandom() + "\u3000" + fileName, LogUtil.HEAD_LINE +
                    "Thread:" + threadName + "\u3000(" + fileNameLong + ":" + lineNumber + ")"
            );
            print(type, getRandom() + "\u3000" + fileName, LogUtil.MIDDLE_LINE);
        }

        String msgStr = objToString(msg);
        msgStr = msgStr.trim();

        int msgLength = msgStr.length();
        int start = 0;
        int end = LogUtil.MAX_LENGTH;
        for (int i = 0; i < 100; i++) {
            if (msgLength > end) {
                print(type, getRandom() + "\u3000" + fileName, LogUtil.HEAD_LINE + msgStr.substring(start, end));
                start = end;
                end = end + LogUtil.MAX_LENGTH;
            } else {
                print(type, getRandom() + "\u3000" + fileName, LogUtil.HEAD_LINE + msgStr.substring(start, msgLength));
                break;
            }
        }

        print(type, getRandom() + "\u3000" + fileName, LogUtil.BOTTOM_LINE);
    }

    private static void print(@TYPE int type, String tag, String msg) {
        switch (type) {
            case LogUtil.I: {
                Log.i(tag, msg);
                break;
            }
            case LogUtil.W: {
                Log.w(tag, msg);
                break;
            }
            case LogUtil.E: {
                Log.e(tag, msg);
                break;
            }
            default:
                break;
        }
    }

    private static String objToString(Object msg) {
        if (null == msg) {
            return "msg is null";
        }
        if (msg instanceof String) {
            return (String) msg;
        }
        if (msg.getClass().isArray()) {
            if (msg instanceof boolean[]) {
                return Arrays.toString((boolean[]) msg);
            }
            if (msg instanceof byte[]) {
                return Arrays.toString((byte[]) msg);
            }
            if (msg instanceof char[]) {
                return Arrays.toString((char[]) msg);
            }
            if (msg instanceof short[]) {
                return Arrays.toString((short[]) msg);
            }
            if (msg instanceof int[]) {
                return Arrays.toString((int[]) msg);
            }
            if (msg instanceof long[]) {
                return Arrays.toString((long[]) msg);
            }
            if (msg instanceof float[]) {
                return Arrays.toString((float[]) msg);
            }
            if (msg instanceof double[]) {
                return Arrays.toString((double[]) msg);
            }
            if (msg instanceof Object[]) {
                return Arrays.deepToString((Object[]) msg);
            }
        }
        return JsonUtil.toJson(msg);
    }

    private static int getRandom() {
        return (int) ((Math.random() * 9 + 1) * 10000);
    }

    /**
     * 打印堆栈跟踪
     */
    public static void printStackTrace() {

        if (!LogUtil.DEBUG) {
            return;
        }

        StackTraceElement[] trace = new Throwable().getStackTrace();
        print(LogUtil.E, getRandom() + "\u3000StackTrace", LogUtil.TOP_LINE);
        for (StackTraceElement e : trace) {
            print(LogUtil.E, getRandom() + "\u3000StackTrace", LogUtil.HEAD_LINE + e.toString());
        }
        print(LogUtil.E, getRandom() + "\u3000StackTrace", LogUtil.BOTTOM_LINE);
    }

}