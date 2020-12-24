package com.grape.base.cert.csr;

import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import javax.security.auth.x500.X500Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * CSR 生成器
 *
 * @author duwenlei
 **/
public class CertificateSigningRequest {

    /**
     * 内容
     */
    private String principalName;

    /**
     * CN=%s,C=CN,ST=NMG,L=CF,O=HIOOIH,OU=IT,EMAIL=hiooih@163.com
     */
    private final String cn;
    private final String c;
    private final String st;
    private final String l;
    private final String o;
    private final String ou;


    /**
     * 签名算法
     */
    private final String signatureAlgorithm;

    /**
     * 公钥
     */
    private final PublicKey publicKey;

    /**
     * 私钥
     */
    private final PrivateKey privateKey;

    /**
     * CSR 生成
     *
     * @return P10 CSR
     * @throws OperatorCreationException
     */
    public PKCS10CertificationRequest getCSR() throws OperatorCreationException {
        Objects.requireNonNull(principalName, "内容不能为空");
        Objects.requireNonNull(signatureAlgorithm, "signatureAlgorithm 不能为空");
        Objects.requireNonNull(privateKey, "privateKey 不能为空");
        Objects.requireNonNull(publicKey, "publicKey 不能为空");

        // 生成 PKCS10CertificationRequestBuilder
        PKCS10CertificationRequestBuilder pkcs10CertificationRequestBuilder = new JcaPKCS10CertificationRequestBuilder(new X500Principal(principalName), publicKey);

        // TODO 扩展
        // 内容签名
        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(signatureAlgorithm);
        ContentSigner contentSigner = jcaContentSignerBuilder.build(privateKey);
        // 返回 P10 格式的 CSR
        return pkcs10CertificationRequestBuilder.build(contentSigner);
    }


    private CertificateSigningRequest(Builder builder) {
        this.signatureAlgorithm = builder.signatureAlgorithm;
        this.privateKey = builder.privateKey;
        this.publicKey = builder.publicKey;
        this.cn = builder.cn;
        this.c = builder.c;
        this.st = builder.st;
        this.l = builder.l;
        this.o = builder.o;
        this.ou = builder.ou;
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(this.cn)) {
            sb.append("CN=").append(this.cn).append(",");
        }
        if (Objects.nonNull(this.c)) {
            sb.append("C=").append(this.c).append(",");
        }
        if (Objects.nonNull(this.st)) {
            sb.append("ST=").append(this.st).append(",");
        }
        if (Objects.nonNull(this.l)) {
            sb.append("L=").append(this.l).append(",");
        }
        if (Objects.nonNull(this.o)) {
            sb.append("O=").append(this.o).append(",");
        }
        if (Objects.nonNull(this.ou)) {
            sb.append("OU=").append(this.ou).append(",");
        }
        this.principalName = sb.substring(0, sb.length() - 1);
    }

    public static class Builder {
        private String signatureAlgorithm;
        private PublicKey publicKey;
        private PrivateKey privateKey;

        private String cn;
        private String c;
        private String st;
        private String l;
        private String o;
        private String ou;

        public Builder addSignatureAlgorithm(String signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public Builder addPrivateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder addPublicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder addCN(String cn) {
            this.cn = cn;
            return this;
        }

        public Builder addC(String c) {
            this.c = c;
            return this;
        }

        public Builder addST(String st) {
            this.st = st;
            return this;
        }

        public Builder addL(String l) {
            this.l = l;
            return this;
        }

        public Builder addO(String o) {
            this.o = o;
            return this;
        }

        public Builder addOU(String ou) {
            this.ou = ou;
            return this;
        }


        public CertificateSigningRequest build() {
            return new CertificateSigningRequest(this);
        }
    }
}
