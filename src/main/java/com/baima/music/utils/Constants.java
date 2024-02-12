package com.baima.music.utils;

import java.nio.file.Path;

/**
 * 一些常量
 *
 * @author FlapyPan
 */
public abstract class Constants {
    // 文件上传路径
    public static final String UPLOAD_PATH = Path.of(System.getProperty("user.dir"), "upload").toString();
    public static final String PROJECT_NAME = "白马音乐";
    public static final String SWAGGER_INFO_DESCRIPTION = "白马音乐的API文档";
    public static final String SWAGGER_INFO_VERSION = "0.0.1";
    public static final String SWAGGER_INFO_LICENSE_NAME = "MIT";
    public static final String SWAGGER_INFO_LICENSE_URL = "https://mit-license.org/";
    // TODO JWT密钥应该从配置文件读取
    public static final byte[] JWT_SECRET = "BaimaMusic".getBytes();
    // 登录token有效期30天
    public static final long TOKEN_EXP_SEC = 2_592_000L;
    // 验证码token有效期5分钟
    public static final long CAPTCHA_TOKEN_EXP_SEC = 300L;
    // TODO 邮件模板从外部配置读取
    public static final String CAPTCHA_TEMPLATE = """
            <table dir="ltr" style="text-align:center">
                <tbody>
                <tr><td style="padding:0;font-size:18px;color:#707070">白马音乐</td></tr>
                <tr><td style="padding:16px 0 0;font-size:16px;color:#2a2a2a">%s</td></tr>
                <tr>
                    <td style="padding:25px 0 0">
                        <span style="font-size:48px;font-weight:bold;color:#66ccff">%s</span>
                    </td>
                </tr>
                <tr>
                    <td style="padding:25px 0 0;font-size:16px;color:#2a2a2a">
                        如果这不是您的操作，请无视此邮件，并且不要向他人透露此邮件内容。
                    </td>
                </tr>
                <tr><td style="padding:25px 0 0;font-size:14px;color:#2a2a2a">谢谢!</td></tr>
                </tbody>
            </table>""";
}
