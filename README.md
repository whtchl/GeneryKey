# GeneryKey

Pos系统分为后端服务器收单系统和前端App。
--收单系统用32个F，3Des加密一个随机数生成的明文，加密后生成密文。明文（主秘钥）存储在数据库中。

--收单系统生成PIN 明文和Track明文（这两个都是随机数），存储在数据库中。然后用主秘钥明文  3DES加密,生成PIN和Track的密文。

--银行卡密码明文+银行卡卡号+PIN明文加密 生成银行卡密码密文。 这个密文可以在服务端解密出银行卡密码明文。



<img src="https://raw.githubusercontent.com/whtchl/GeneryKey/master/art/1.png"/>

<img src="https://raw.githubusercontent.com/whtchl/GeneryKey/master/art/2.png"/>

<img src="https://raw.githubusercontent.com/whtchl/GeneryKey/master/art/3.png"/>
