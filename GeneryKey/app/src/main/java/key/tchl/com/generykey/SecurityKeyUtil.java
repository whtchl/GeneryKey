package key.tchl.com.generykey;

import android.util.Log;


import java.util.Map;

import static key.tchl.com.generykey.config.ToastAndLog;

/**
 * Created by happen on 2017/11/6.
 */

public class SecurityKeyUtil {


    /**
     * 生成Key
     */
    public static String generateKey() {
        //暂时使用随机数
        String r1 = (long) (Math.random() * 90000000 + 10000000) + "";
        String r2 = (long) (Math.random() * 90000000 + 10000000) + "";
        String r3 = (long) (Math.random() * 90000000 + 10000000) + "";
        String r4 = (long) (Math.random() * 90000000 + 10000000) + "";
        String key = r1 + r2 + r3 + r4;
        JUtils.Log(key);
        return key;
    }


    /**
     * 不同型号的POS机，芯片秘钥写入方式不一样
     */
    public static String encrypt(Map<String, Object> req, String data, String key, boolean misMainKey) {
        boolean isMainKey = misMainKey;
        String _result = data;
        //String DEVICETYPE = (String) req.get("DEVICETYPE");
        if (data == null || data.length() == 0) {
            return _result;
        }
        //String dev_type = DEVICETYPE.toUpperCase();
        _result = encryptKey(req, data, key, isMainKey);
        /*if(dev_type.startsWith("F300") || dev_type.startsWith("S600") || dev_type.startsWith("P2000") || dev_type.startsWith("P8000")){
            //通用算法
            _result = encryptKey(req, data, key, isMainKey);
        }else if(dev_type.startsWith("S300") || dev_type.startsWith("WPOS")){
            //通用算法
            _result = encryptKey(req, data, key, isMainKey);
        }else{
            //通用算法
            _result = encryptKey(req, data, key, isMainKey);
        }*/

        return _result;
    }


    /**
     *
     */
    public static String encryptKey(Map<String, Object> req, String data, String key, boolean isMainKey) {
        isMainKey = false;
        String _result = data;
        String key32 = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
        String key16 = "0000000000000000";
        if (key != null) {
            key32 = key;
        }
        byte[] _key32 = StringUtil.hexString2Bytes(key32);
        byte[] _data = StringUtil.hexString2Bytes(data);
        //Log.e("wang","encryptKey  _key32:"+_key32);
        String value1 = null;
        try {
            value1 = StringUtil.Hex2String(
                    DES.des3encrypt(
                            _key32,
                            _data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] _key16 = StringUtil.hexString2Bytes(key16);

        String value2 = null;
        try {
            value2 = StringUtil.Hex2String(
                    DES.des3encrypt(
                            _data,
                            _key16));
        } catch (Exception e) {
            e.printStackTrace();
        }

        value1 = value1.replace(" ", "");
        value2 = value2.replace(" ", "");
        //JUtils.Log("value1:"+value1 + " value2:"+value2+ " value2 size:"+value2.length());
        Log.e("wang", "value1:" + value1 + " value2:" + value2 + " value2 size:" + value2.length());
        _result = value1 + value2.substring(0, 8);
        return _result;
    }

    public static String decrypt(String data, String key) {
        String _result = data;
        _result = decryptKey(data, key);
        return _result;
    }

    public static String decryptKey(String data, String key) {
        String str1 = "";

        String key32 = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
        String key16 = "0000000000000000";
        if (key != null) {
            key32 = key;
        }
        byte[] _key32 = StringUtil.hexString2Bytes(key32);
        // byte[] _data =StringUtil.hexString2Bytes(data);

        String data32 = data.substring(0, 32);
        //JUtils.Log("data32:"+data32+  " size:"+data32.length());
        Log.e("wang", "data32:" + data32 + " size:" + data32.length());
        String data8 = data.substring(32, data.length());
        byte[] _data32 = StringUtil.hexString2Bytes(data32);
        try {
            str1 = StringUtil.Hex2String(DES.des3decrypt(_key32, _data32));
            //JUtils.Log(str1);
            Log.e("wang", str1);
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  byte[] _data = StringUtil.hexStrToBytes(data);
        byte[] pin_key = StringUtil.hexStrToBytes(pinkey);
        result = TriDes.decrypt(pin_key, _data);*/
        return str1;
    }

    public static String decryptPin(String data, String pinkey, String cardAsn) {
        byte[] result = new byte[8];

        byte[] _data = StringUtil.hexString2Bytes(data);
        byte[] pin_key = StringUtil.hexString2Bytes(pinkey);
        try {
            result = DES.des3decrypt(pin_key, _data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String cardPanStr = (new StringBuilder("0000")).append(cardAsn.substring(cardAsn.length() - 13, cardAsn.length() - 1)).toString();
        byte[] cardPan = StringUtil.hexString2Bytes(cardPanStr);
        for (int i = 0; i < 8; i++) {
            result[i] = (byte) (result[i] ^ cardPan[i]);
        }
        int len = result[0];
        boolean isJ = false;
        if (len % 2 != 0) {
            len++;
            isJ = true;
        }
        int _len = len / 2;
        byte[] pinResult = new byte[_len];
        System.arraycopy(result, 1, pinResult, 0, _len);
        String pin = StringUtil.bytesToHexString(pinResult);
        if (isJ) {
            pin = pin.substring(0, len - 1);
        }
        return pin;
    }


    public static String encryptPin(String pinKey, String pin, String cardAsn) {
        byte[] result = new byte[8];
        byte[] cardPan = new byte[8];
        String cardPanStr = (new StringBuilder("0000")).append(cardAsn.substring(cardAsn.length() - 13, cardAsn.length() - 1)).toString();
        cardPan = StringUtil.hexString2Bytes(cardPanStr);
//        String pinKey = getPinKey(posId);
        byte[] pin_key = StringUtil.hexString2Bytes(pinKey);
        int pinLen = pin.length();
        String strPinBlock = (new StringBuilder(String.valueOf(pinLen))).append(pin).toString();
        if (pinLen < 10)
            strPinBlock = (new StringBuilder("0")).append(strPinBlock).toString();
        strPinBlock = StringUtil.lengthFix(strPinBlock, 16, "F", true);
        byte[] pinBlock = StringUtil.hexString2Bytes(strPinBlock);
        for (int i = 0; i < 8; i++)
            result[i] = (byte) (pinBlock[i] ^ cardPan[i]);

        if (pin_key.length == 8) {
            try {
                result = DES.encrypt(pin_key, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (pin_key.length == 16) {
            try {
                result = DES.des3encrypt(pin_key, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return StringUtil.bytesToHexString(result);
    }


    //磁道加密
    public static String decryptTrack(Map<String, Object> req, String track, String key, boolean isData55) {
        /*if (!track) {
            return track;
        }*/
        String DEVICETYPE = "HI98";//req.get("DEVICETYPE");
        String dev_type = DEVICETYPE.toUpperCase();
        String TRACK_ENCRYPT_TYPE = "";//req.get("TRACK_ENCRYPT_TYPE");

        if (isData55) {
            if (dev_type.startsWith("F300") || dev_type.startsWith("S300") || dev_type.startsWith("WPOS")) {
                return track;
            }
        }

        //未加密
        if (TRACK_ENCRYPT_TYPE == "0" || dev_type.startsWith("TRADITION-POS") || dev_type.startsWith("RK312X") || dev_type.startsWith("HI98")) {
            return track;
        }

        //全部加密
        if (TRACK_ENCRYPT_TYPE == "2" || dev_type.startsWith("P2000") || dev_type.startsWith("P8000")) {
            return decryptTrack2(track, key, isData55);
        }

        //默认部分加密
        return decryptTrack1(track, key, isData55);
    }

    /**
     * 部分加密
     */
    public static String decryptTrack1(String track, String key, boolean isData55) {
        int len = track.length();
        int offset = 2;
        if (len % 2 != 0) {
            offset = 1;
        }
        int offset1 = len - 16 - offset;
        String etrack = track.substring(offset1, len - offset);
        String dtrack = null;
        try {
            dtrack = StringUtil.bytesToHexString(DES.des3decrypt(StringUtil.hexString2Bytes(key), StringUtil.hexString2Bytes(etrack)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dtrack = track.substring(0, offset1) + dtrack + track.substring(len - offset);
        return dtrack;
    }
    /**
     * 全部加密
     */
    public static String decryptTrack2(String track, String key, boolean isData55){
        /*if(!track){
            return track;
        }*/
        String dtrack = null;
        try {
            dtrack = StringUtil.bytesToHexString(DES.des3decrypt(StringUtil.hexString2Bytes(key),StringUtil.hexString2Bytes(track) ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int start = isData55 ? 4 : 2;
        int len = Integer.parseInt(dtrack.substring(0, start));
        String _track = dtrack.substring(start, start+len);
        return _track;
    }


    public static String encryptTrack(String track,String key){
        /*if(!track){
            return track;
        }*/
        int len = track.length();
        int offset = 2;
        if(len % 2 != 0){
            offset = 1;
        }
        int offset1 = len - 16 - offset;
        String dtrack = track.substring(offset1,len-offset);
        String etrack = null;
        try {
            etrack = StringUtil.bytesToHexString(DES.des3decrypt(StringUtil.hexString2Bytes(key),StringUtil.hexString2Bytes(dtrack)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        etrack = track.substring(0,offset1) + etrack + track.substring(len-offset);
        return etrack;
    }

    /**
     * 使用方法
     * @param l
     * @return
     */
    /*long order_time = new Date().getTime();
    long verify_key = SecurityKeyUtil.genVerifyKey(order_time);*/
    public static long genVerifyKey(long l){
        return (l * 2016) % 878787 + 100000;
    }

    public static boolean checkVerifyKey(long l, long k){
        if(l > 0 && ((l * 2016) % 878787 + 100000) == k){
            return true;
        }
        return false;
    }
}
