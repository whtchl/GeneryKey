package key.tchl.com.generykey;

import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static final String KEY_ALGORITHM = "AES";
	
	public static final String CIPHER_ALGORITHM ="AES/CBC/PKCS5Padding";
	
	//public static final String CIPHER_ALGORITHM ="AES/ECB/PKCS5Padding";

	public static final String VIPARA   = "0102030405060708"; //iv参数设置 （ios、android、wp7、java 统一）
	
	public static final String ENCODING = "UTF-8";
	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param password  加密密码
	 * @return
	 */ 
	public static byte[] encrypt(String content, String password)  { 
		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(VIPARA.getBytes());
			
			//byte[] ivByte = {(byte)0XCA,(byte)0X8A,(byte)0X88,(byte)0X78,(byte)0XF1,(byte)0X45,(byte)0XC9,(byte)0XB9,(byte)0XB3,(byte)0XC3,(byte)0X1A,(byte)0X1F,(byte)0X15,(byte)0XC3,(byte)0X4A,(byte)0X6D};
			//IvParameterSpec iv = new IvParameterSpec(ivByte);
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes());
			return encrypted ;

		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;

	} 
	public static byte[] encrypt1(String content, String password)  { 
		try {
			SecretKeySpec skeySpec = getKey1(password); //生成密钥
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); //根据CIPHER_ALGORITHM加密类型实例化加密算法对象 
			//IvParameterSpec iv = new IvParameterSpec(VIPARA.getBytes());
			
			byte[] ivByte = {(byte)0XCA,(byte)0X8A,(byte)0X88,(byte)0X78,(byte)0XF1,(byte)0X45,(byte)0XC9,(byte)0XB9,(byte)0XB3,(byte)0XC3,(byte)0X1A,(byte)0X1F,(byte)0X15,(byte)0XC3,(byte)0X4A,(byte)0X6D};
			IvParameterSpec iv = new IvParameterSpec(ivByte);//IV偏转向量，由8位字节数组通过IvParameterSpec类转换而成。 
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); //根据类型（加密或解密）与密钥初始化加密算法对象
			byte[] encrypted = cipher.doFinal(content.getBytes());
			return encrypted ;

		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;

	} 
	/**解密
	 * @param content  待解密内容
	 * @param password 解密密钥
	 * @return
	 */ 
	public static byte[] decrypt(byte[] content, String password) { 
		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(VIPARA.getBytes());
			//byte[] ivByte = {(byte)0XCA,(byte)0X8A,(byte)0X88,(byte)0X78,(byte)0XF1,(byte)0X45,(byte)0XC9,(byte)0XB9,(byte)0XB3,(byte)0XC3,(byte)0X1A,(byte)0X1F,(byte)0X15,(byte)0XC3,(byte)0X4A,(byte)0X6D};
			//IvParameterSpec iv = new IvParameterSpec(ivByte);
			
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(content);
			return original; 
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	public static byte[] decrypt1(byte[] content, String password) { 
		try {
			SecretKeySpec skeySpec = getKey1(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			//IvParameterSpec iv = new IvParameterSpec(VIPARA.getBytes());
			byte[] ivByte = {(byte)0XCA,(byte)0X8A,(byte)0X88,(byte)0X78,(byte)0XF1,(byte)0X45,(byte)0XC9,(byte)0XB9,(byte)0XB3,(byte)0XC3,(byte)0X1A,(byte)0X1F,(byte)0X15,(byte)0XC3,(byte)0X4A,(byte)0X6D};
			IvParameterSpec iv = new IvParameterSpec(ivByte);
			
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(content);
			return original; 
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	} 


	/**
	 * 字节数组转化为16进制字符串
	 * @param src
	 * @return
	 * 
	 */
	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * 16进制字符串转化成字节数组
	 * @param hexString
	 * @return
	 * 
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 加密 后 将字节数组转化成16进制字符串
	 * @param srcStr   需要加密的内容
	 * @param password 加密的key
	 * @return
	 */
	public static String encryptString (String srcStr ,String password) {
		String resultStr ="";
		byte[] result = encrypt(srcStr, password);
		resultStr = bytesToHexString(result);
		System.out.println("####加密时#########srcStr:"+srcStr+"##########password:"+password);
		System.out.println("####加密后#########result:"+resultStr);
		return resultStr;
	}
	//刘总加密方式
	public static String encryptString1(String srcStr ,String password) {
		String resultStr ="";
		byte[] result = encrypt1(srcStr, password);
		resultStr = bytesToHexString(result);
		System.out.println("####加密时#########srcStr:"+srcStr+"##########password:"+password);
		System.out.println("####加密后#########result:"+resultStr);
		return resultStr;

	}

	/**
	 * 解密 先将16进制字符串 转化成字节数组
	 * @param destStr  需要解密的内容
	 * @param password 解密的key
	 * @return
	 */
	public static String decryptString(String destStr ,String password) {
		String resultStr ="";
		byte[] result = hexStringToBytes(destStr);
		result = decrypt(result, password);
		resultStr = new String(result);
		
		resultStr = resultStr.replace("&", "&amp;");
		//resultStr = resultStr.replace(">", "&gt;");
		//resultStr = resultStr.replace("<", "&lt;");
		resultStr = resultStr.replace("'", "&apos;");
	//	resultStr = resultStr.replace("\"", "&quot;");
		
		return resultStr;

	}
	public static String decryptString1(String destStr ,String password) {
		String resultStr = "";
		byte[] result = hexStringToBytes(destStr);
		result = decrypt1(result, password);
		resultStr = new String(result);
		
		resultStr = resultStr.replace("&", "&amp;");
		//resultStr = resultStr.replace(">", "&gt;");
		//resultStr = resultStr.replace("<", "&lt;");
		resultStr = resultStr.replace("'", "&apos;");
		resultStr = resultStr.replace("ufeff", "");
		return resultStr;

	}

	private static SecretKeySpec getKey(String strKey) {
		byte[] arrBTmp = strKey.getBytes();
		 //byte[] arrBTmp = {(byte)0xB0,(byte)0x0D,(byte)0xDF,(byte)0x9D,(byte)0x93,(byte)0xE1,(byte)0x99,(byte)0xEF,(byte)0xEA,(byte)0xE9,(byte)0x67,(byte)0x80,(byte)0x5E,(byte)0x0A,(byte)0x52,(byte)0x28};
		//byte[] arrBTmp = {(byte)0xAA,(byte)0x70,(byte)0xCD,0x77,0x12,0x5F,(byte)0xC3,0x04,(byte)0xFE,(byte)0xBF,0x5E,0x3E,(byte)0xA4,(byte)0xE9,(byte)0xAF,(byte)0xBD};//{0xAA,0x70,0xCD,0x77,0x12,0x5F,0xC3,0x04,0xFE,0xBF,0x5E,0x3E,0xA4,0xE9,0xAF,0xBD};
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, KEY_ALGORITHM);

		return skeySpec;
	}

	private static SecretKeySpec getKey1(String strKey) {
		//byte[] arrBTmp = strKey.getBytes();
		// byte[] arrBTmp = {(byte)0xB0,(byte)0x0D,(byte)0xDF,(byte)0x9D,(byte)0x93,(byte)0xE1,(byte)0x99,(byte)0xEF,(byte)0xEA,(byte)0xE9,(byte)0x67,(byte)0x80,(byte)0x5E,(byte)0x0A,(byte)0x52,(byte)0x28};
		byte[] arrBTmp = {(byte)0xAA,(byte)0x70,(byte)0xCD,0x77,0x12,0x5F,(byte)0xC3,0x04,(byte)0xFE,(byte)0xBF,0x5E,0x3E,(byte)0xA4,(byte)0xE9,(byte)0xAF,(byte)0xBD};//{0xAA,0x70,0xCD,0x77,0x12,0x5F,0xC3,0x04,0xFE,0xBF,0x5E,0x3E,0xA4,0xE9,0xAF,0xBD};
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, KEY_ALGORITHM);

		return skeySpec;
	}
	public static void main(String[] args){

		String content = "2013-05-10"; 
		String password="dynamicode";
		String result = "";
		//加密 
		System.out.println("加密前 ：" + content); 
		result = encryptString(content, DES.StringToMD5(password));
		System.out.println("加密后："+result);
		System.out.println("加密后长度："+result.length());
//		//解密 
//		password="0306080603030408";
//		result="dd8ff57e2d47d3b044136c7b3bf307dabd6738885478f2127534fff0f0d5ba73";
//		result = decryptString(result,CryptUtils.encryptToMD5(password)); 
//		System.out.println(CryptUtils.encryptToMD5("www.epos.com"));
//		System.out.println("解密后：" + result); 
		//CryptUtils.encryptToMD5(
	}


}
