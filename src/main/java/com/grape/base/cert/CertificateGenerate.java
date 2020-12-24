package com.grape.base.cert;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * 证书生成工具类
 *
 * @author duwenlei
 **/
public class CertificateGenerate {
    /**
     * 颁发者
     */
    private X500Name ISSUE = new X500Name("CN=www.hiooih.com,C=CN,ST=NMG,L=CF,O=HIOOIH,OU=IT,E=hiooih@163.com");

    /**
     * Root 证书生成
     *
     * @return
     */
    public Certificate generateRootCa(byte[] csr, String signatureAlgorithm, PrivateKey privateKey, long serialNumber, Date notBefore, Date notAfter) throws IOException, OperatorCreationException, CertificateException {
        PKCS10CertificationRequest pkcs10CertificationRequest = new PKCS10CertificationRequest(csr);
        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(ISSUE,
                BigInteger.valueOf(serialNumber),
                notBefore,
                notAfter,
                pkcs10CertificationRequest.getSubject(),
                pkcs10CertificationRequest.getSubjectPublicKeyInfo()
        );

        // TODO 扩展
        x509v3CertificateBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
        x509v3CertificateBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));

        ContentSigner signer = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(new BouncyCastleProvider()).build(privateKey);
        X509CertificateHolder x509CertificateHolder = x509v3CertificateBuilder.build(signer);
        return new JcaX509CertificateConverter().getCertificate(x509CertificateHolder);
    }

    /**
     * Root 证书生成
     *
     * @return
     */
    public Certificate generate(X509Certificate issueCert, byte[] csr, String signatureAlgorithm, PrivateKey issuePrivateKey, long serialNumber, Date notBefore, Date notAfter) throws IOException, OperatorCreationException, CertificateException, NoSuchAlgorithmException, InvalidKeyException {
        JcaPKCS10CertificationRequest jcaCertRequest = new JcaPKCS10CertificationRequest(csr);
        JcaX509v3CertificateBuilder jcaX509v3CertificateBuilder = new JcaX509v3CertificateBuilder(
                issueCert,
                BigInteger.valueOf(serialNumber),
                notBefore,
                notAfter,
                jcaCertRequest.getSubject(),
                jcaCertRequest.getPublicKey()
        );

        // TODO 扩展
        jcaX509v3CertificateBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
        jcaX509v3CertificateBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));

        ContentSigner signer = new JcaContentSignerBuilder(signatureAlgorithm).build(issuePrivateKey);
        X509CertificateHolder x509CertificateHolder = jcaX509v3CertificateBuilder.build(signer);
        return new JcaX509CertificateConverter().getCertificate(x509CertificateHolder);
    }
}
