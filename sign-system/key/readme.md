## 说明

- platform.pk8 和 platform.x509.pem 是Google原生系统app签名的私钥和证书

- system.keystore 是用 platform.pk8 和 platform.x509.pem 合在一起生成的秘钥库，
里面只有一个叫 system 的 key，密码都是 a123456