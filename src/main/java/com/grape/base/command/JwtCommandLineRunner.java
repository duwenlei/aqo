package com.grape.base.command;

import com.grape.base.cert.CertificateGenerate;
import com.grape.base.cert.csr.CertificateSigningRequest;
import com.grape.base.cert.root.KeyStoreConst;
import com.grape.base.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * JWT 运行前配置初始化
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class JwtCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ConfigService configService;

    @Override
    public void run(String... args) throws Exception {
        String aqoTmpFilePath = configService.getAqoValue("aqo.tmp.file.path", System.getProperty("user.home"));
        String aqoJksFileName = configService.getAqoValue("aqo.jks.file.name", "aqo.jks");
        File userJksFile = new File(String.format("%s/%s", aqoTmpFilePath, aqoJksFileName));
        if (userJksFile.exists() && userJksFile.isFile()) {
            log.info("{} file is ready.", aqoJksFileName);
            return;
        }

        // 生成 JWT 的公私钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // CSR 后期参数化
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.dwl.com")
                .addC("CN")
                .addST("NMG")
                .addL("CF")
                .addO("dwl")
                .addOU("IT")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(keyPair.getPrivate())
                .addPublicKey(keyPair.getPublic())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();

        // root CA
        File jksFile = ResourceUtils.getFile(String.format("%s%s", ResourceUtils.CLASSPATH_URL_PREFIX, "config/root.jks"));
        KeyStore store = KeyStore.getInstance("jks");
        FileInputStream inputStream = new FileInputStream(jksFile);
        store.load(inputStream, KeyStoreConst.PASSWORD.toCharArray());

        X509Certificate issueCert = (X509Certificate) store.getCertificate(KeyStoreConst.ROOT_CA_ALIAS);
        PrivateKey issuePrivateKey = (PrivateKey) store.getKey(KeyStoreConst.ROOT_PRIVATE_KEY_ALIAS, KeyStoreConst.PASSWORD.toCharArray());
        // close stream
        inputStream.close();

        Date notBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notBefore);
        calendar.add(Calendar.YEAR, 10);
        Date notAfter = calendar.getTime();


        // Cert
        CertificateGenerate certificateGenerate = new CertificateGenerate();
        Certificate userCert = certificateGenerate.generate(issueCert, csr.getEncoded(), "SHA256WithRSA", issuePrivateKey, System.currentTimeMillis(), notBefore, notAfter);

        // save jks
        KeyStore userKeyStore = KeyStore.getInstance("jks");
        userKeyStore.load(null);
        userKeyStore.setCertificateEntry(KeyStoreConst.SUB_CA_ALIAS, userCert);
        userKeyStore.setKeyEntry(KeyStoreConst.SUB_PRIVATE_KEY_ALIAS, keyPair.getPrivate(), KeyStoreConst.SUB_PASSWORD.toCharArray(), new Certificate[]{userCert});

        FileOutputStream outputStream = new FileOutputStream(userJksFile);
        userKeyStore.store(outputStream, KeyStoreConst.SUB_PASSWORD.toCharArray());

        // close stream
        outputStream.close();
        log.info("CA 相关信息初始化成功。");
    }
}
