通信端无需向对方证明自己的身份，则称该端处于“客户模式”，否则称其处于“服务器模式”，无论是客户端还是服务器端，都可处于“客户模式”或者“服务器模式”，但是对于通信双方来说，只能有一方处于“服务模式”，而另一方则必须处于“客户模式”

# 1 证书生成

首先使用java自带的keytool工具生成所需的证书：

## 1.1 创建服务端keystore

keytool -genkey -keystore D:\temp\socket\server.jks -storepass 1234sp -keyalg RSA -keypass 1234kp

## 1.2 创建客户端keystore

keytool -genkey -keystore D:\temp\socket\client.jks -storepass 1234sp -keyalg RSA -keypass 1234kp

## 1.3 导出服务端证书

keytool -export -keystore D:\temp\socket\server.jks -storepass 1234sp -file D:\temp\socket\server.cer

## 1.4 导出客户端证书

keytool -export -keystore D:\temp\socket\client.jks -storepass 1234sp -file D:\temp\socket\client.cer

## 1.5 将服务端证书导入到客户端trustkeystroe

keytool -import -keystore D:\temp\socket\clientTrust.jks -storepass 1234sp -file D:\temp\socket\server.cer

## 1.6 将客户端证书导入到服务端trustkeystroe

keytool -import -keystore D:\temp\socket\serverTrust.jks -storepass 1234sp -file D:\temp\socket\client.cer


# 2 注意点

## 2.1 不能随意的使用close()方法关闭socket输入输出流，使用close()方法关闭socket输入输出流会导致socket本身被关闭

## 2.2 字符串必须按照指定的编码转换为字节数组，字节数组也必须通过相同的编码转换为字符串，否则将会出现乱码

## 2.3 字符串的读取需要用input.readFully(bytes, 0, length)而不能直接用input.read(length)，否则取不到完整的字符串