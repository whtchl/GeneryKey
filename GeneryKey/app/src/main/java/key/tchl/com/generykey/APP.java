package key.tchl.com.generykey;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;


/**
 * Created by happen on 2017/11/6.
 */

public class APP extends Application {

    private static APP instance = null;

    public static APP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //if ("key.tchl.com.generykey".equals(getCurProcessName(getApplicationContext()))) {
            instance = this;
            JUtils.initialize(this);
            JUtils.setDebug(true, "wang");
       // }
    }



    /* 一个获得当前进程的名字的方法 */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
