package key.tchl.com.generykey;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by happen on 2017/11/6.
 */

public class config {

   public static void ToastAndLog(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
        Log.e("wang",str);
    }
}
