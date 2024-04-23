//package testCases;
//
//import org.apache.tomcat.util.codec.binary.Base64;
//
//import javax.crypto.Cipher;
//import java.io.ByteArrayOutputStream;
//import java.security.*;
//import java.security.spec.EncodedKeySpec;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.X509EncodedKeySpec;
//
//public class RSAUtils {
//
//    private static final String KEY_ALGORITHM = "RSA";
//    private static final Integer KEY_SIZE = 2048;
//
//    private static final int MAX_ENCRYPT_BLOCK = 117;
//
//    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;
//
//    public RSAUtils() throws NoSuchAlgorithmException, InvalidKeySpecException {
//    }
//
//
//    public static String decryptByPublicKey(String encryptedData, String publicKey) {
//        try {
//            String a = new String(decryptByPublicKey(Base64.decodeBase64(encryptedData), publicKey));
//            System.out.println("decryptedDataNew: Start  >>>>>  " + a + "  <<<<<< decryptedDataNew: Finish");
//            return new String(a);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * <p>
//     * public key decryption
//     * </p>
//     *
//     * @param encryptedData encrypted data
//     * @param publicKey     Public key (BASE64 encoded)
//     * @return
//     * @throws Exception
//     */
//    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
//            throws Exception {
//        byte[] keyBytes = Base64.decodeBase64(publicKey);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key publicK = keyFactory.generatePublic(x509KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.DECRYPT_MODE, publicK);
//        int inputLen = encryptedData.length;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
//            }
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_DECRYPT_BLOCK;
//        }
//        byte[] decryptedData = out.toByteArray();
//        out.close();
//        System.out.println("decryptedData: Start  >>>>>  " + decryptedData + "  <<<<<< decryptedData: Finish");
//        return decryptedData;
//
//    }
//
//    public static void main(String[] args) {
//
//        String encryptedData = "I5Kzp+3QlmwywXIAuOw4g9CgJIf2cCunC2JO6haPqA1Q28DGGShMlpB4aQh+l2HjZzUPeYi3WS6SiGlqEc0InPTGBBmKB1h13M9os19yU7YnZ6C0KBAzc0yIgxzi/5gJKs6inqUETFe/tkbFJGy3sZaiqsOespkd33YJSreWRzj2un5YADac/AguNAwJstmWkfV5zqV5xN9WS9M/73SYJrI6tS2bfGiMstVi7bq66cBOcoDABh85AA/pYkaZM8zKv6yxUC1bwfRQrnUai/lH0D9bPrPOBtOQWZk6xYgUnujcdEX7uY/3xZ8BIOJQlM4VTvXIPVsZHphyDJv4eH3/EZBWszHcqB/GS+dZToZO21iWZ4HnURU/kT0jmncKhFJsLvob3tX7BZxIqqvHRK1vCnqPk2oAj+EiFloUfUF+oxlsmxT/uFmezHHHTr0UJ/4trwruXoruP5yo2GBAW/wP5EszZ6vBUp9sbigMslUQsBgLmcMEiKbKCvr/3YgfLrPWvDEpBx+1P6tUHS8rsD70nJF/vsJoalky2+XWiPF+zBtTi0Wa7DL3H5qsdEpoTGi+GFmW9/MtOKE3NSrsaSjHTCqJXYialKcpW4m3K9AURb9r8XVtzKETCHLZGt+GRXH0oatcbAAFjaLzbWm6Z+CxoLlQ1ecGSD8vMa25LhjGK2M=";
//        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkMsGgguLP0CvaYkoWlfGu4k7RxkimfR1bMsDZiCwg9M9ga+5TyDSq3y4rQwunAZBuYXbiOnIUpHMp4DUODLrRLTA/goh4ESbvxizr7HpSMIbWpuUmLthlB0Z1bk0Sa6xlmc6OXfat2LHL1G0nrfNeOQ0GSlI1vIcSjg4jhU3niq/cfjxf+YXXdQBnLsO/9pykE2W+3wppve07ZW9K5pvFHxBZmoKocgoL28lklvyUd8F1qj8nl2GYj7Y3ANsBmr5Taa/17oa4jaFeByn+Ike2qxpSJCRp50HZlDC46qpxnP0Q5+NiOu97Ywr8AkJ6b8ftJ0qKX97F8z1ZCUGx8Y8awIDAQAB";
//        decryptByPublicKey(encryptedData, publicKey);
//
//    }
//
//
//
//
//
//}
