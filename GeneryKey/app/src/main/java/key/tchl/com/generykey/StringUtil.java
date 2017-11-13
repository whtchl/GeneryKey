package key.tchl.com.generykey;

import android.util.Log;

/**
 * Created by happen on 2017/11/6.
 */

public class StringUtil {
    // char a = 0x03
    public static byte hex2byte(char hex)
    {
        if ((hex <= '9') && (hex >= '0')) {
            return (byte)(hex - '0');
        }

        if ((hex <= 'f') && (hex >= 'a')) {
            return (byte)(hex - 'a' + 10);
        }

        if ((hex <= 'F') && (hex >= 'A')) {
            return (byte)(hex - 'A' + 10);
        }

        return 0;
    }
    /**
     * 十六进制字符串转换为二进制byte数据.若不是偶数个右补'0'
     */
    public static byte[] hexString2Bytes(String data)
    {
        StringBuilder buffer = new StringBuilder(data);

        byte[] result = new byte[(buffer.length() + 1) / 2];
        if ((buffer.length() & 0x01) == 1) {
            buffer.append( '0' );
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = ((byte)(hex2byte(buffer.charAt(i * 2 + 1))
                    | hex2byte(buffer.charAt(i * 2)) << 4));
        }
        return result;
    }


    public static byte[] parseHexStr2Byte(String hexStr) {

        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    public static  String Hex2String(byte [] byteInfo){
        String strDisp="";
        for(int i=0;i<byteInfo.length;i++){
            strDisp=strDisp+String.format("%2X", byteInfo[i]).replace(" ","0")+" ";
        }

        return strDisp;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @param bArray
     * @return HexString
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 传入str，str的长度小于length，那么不足的部分用add进行补全。
     * @param str
     * @param length
     * @param add
     * @param b
     * @return
     */
    public static String lengthFix(String str,int length,String add,boolean b){

        StringBuffer temp = new StringBuffer(str);
        if(add.length()>1){
            Log.e("lengthFix:","lengthFix: 第三个参数长度必须是1");
            return null;
        }
        if(str.length()>length) return str.substring(0,length-1);
        else {
            for(int i=0;i<length-str.length();i++){
                temp = temp.append(add);
            }
            return temp.toString();
        }
    }
}
