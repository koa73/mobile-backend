package ru.mobile.lib.service.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mobile.lib.rest.exception.RestApiException;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * Created by OAKutsenko on 31.08.2017.
 */

@Service
public class EncryptionServiceImpl implements EncryptionService{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${security.dbencrypt.key}")
    private String strKey;

    @Override
    public String encryptValue(String value, String userName) throws RestApiException {

        String strData="";
        try {
            final IvParameterSpec iv = new IvParameterSpec(userName.substring(0,16).getBytes());
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec, iv);
            byte[] encrypted=cipher.doFinal(value.getBytes());
            strData=new String(Base64.encodeBase64(encrypted));

        } catch (Exception e) {

            log.error("Ecryption error. Reason: "+e.getMessage());

        }
        return strData;
    }

    @Override
    public String decryptValue(String encrypted) throws RestApiException{

        String decrypted = null;

        try {
            byte[] bb = Base64.decodeBase64(encrypted.getBytes());

            // decrypt the text
            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(),"AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            decrypted = new String(cipher.doFinal(bb));

        } catch (Exception e){

            log.error("Decryption error. Reason: "+e.getMessage());
        }

        return decrypted;
    }
}
