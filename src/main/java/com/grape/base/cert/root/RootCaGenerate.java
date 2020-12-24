package com.grape.base.cert.root;

import com.grape.base.cert.CertificateGenerate;
import com.grape.base.cert.csr.CertificateSigningRequest;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Hex;
import sun.security.tools.keytool.CertAndKeyGen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成 root.jks
 *
 * @author duwenlei
 **/
public class RootCaGenerate {
    public void generateRootCa() throws NoSuchAlgorithmException, InvalidKeyException, OperatorCreationException, IOException, CertificateException, KeyStoreException {
        // CSR
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA256WithRSA");
        certAndKeyGen.generate(2048);
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.hiooih.com")
                .addC("CN")
                .addST("NMG")
                .addL("CF")
                .addO("hiooih")
                .addOU("IT")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(certAndKeyGen.getPrivateKey())
                .addPublicKey(certAndKeyGen.getPublicKeyAnyway())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();

        // Certificate
        Date notBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date notAfter = calendar.getTime();

        CertificateGenerate certificateGenerate = new CertificateGenerate();
        Certificate certificate = certificateGenerate.generateRootCa(csr.getEncoded(),
                "SHA256WithRSA",
                certAndKeyGen.getPrivateKey(),
                System.currentTimeMillis(),
                notBefore,
                notAfter
        );
        MessageDigest md = MessageDigest.getInstance("md5");
        md.update("abdp".getBytes());
        Hex.toHexString(md.digest());

        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(null);
        keyStore.setCertificateEntry(KeyStoreConst.ROOT_CA_ALIAS, certificate);
        keyStore.setKeyEntry(KeyStoreConst.ROOT_PRIVATE_KEY_ALIAS, certAndKeyGen.getPrivateKey(), KeyStoreConst.PASSWORD.toCharArray(), new Certificate[]{certificate});
        keyStore.store(new FileOutputStream("root.jks"), KeyStoreConst.PASSWORD.toCharArray());
    }
}
