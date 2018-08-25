package com.hufei.runner.utils.crypto;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.hufei.client.util.RSAUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

public class RSAKeyTest {
    public static void main(String[] args) throws IOException {
        String private_key_path = "/Users/hufei/Desktop/pyrsa2javarsa/private_key.pem";
        String public_key_path = "/Users/hufei/Desktop/pyrsa2javarsa/public_key.pem";
        String java_sign_path = "/Users/hufei/Desktop/pyrsa2javarsa/sign.txt";
        File private_key_file = new File(private_key_path);
        File public_key_file = new File(public_key_path);
        List<String> privateLines = FileUtils.readLines(private_key_file, "UTF-8");
        List<String> publicLines = FileUtils.readLines(public_key_file, "UTF-8");

        String private_key_str = "";
        for(int i=1; i < privateLines.size()-1; i++){
            private_key_str += privateLines.get(i);
        }

        String public_key_str = "";
        for(int i=1; i < publicLines.size()-1; i++){
            public_key_str += publicLines.get(i);
        }

        Base64 b64 = new Base64();
        byte[] decoded = b64.decode(private_key_str);
        String hex_private_key = String.valueOf(Hex.encodeHex(decoded));

        decoded = b64.decode(public_key_str);
        String hex_public_key = String.valueOf(Hex.encodeHex(decoded));

        String message = "胡斐封疆大吏分";
        String sign = RSAUtil.sign(message, hex_private_key, "UTF-8");
        FileUtils.write(new File(java_sign_path), sign);

        boolean isVerfity = RSAUtil.verify(message, sign, hex_public_key, "UTF-8");
        System.out.println(String.format("PRIVATE_KEY=%s",hex_private_key));
        System.out.println(String.format("PUBLIC_KEY =%s",hex_public_key));
        System.out.println(isVerfity);
    }
}
