package com.cc.xsl.coolweather.util;


import android.text.TextUtils;
import android.util.Log;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

public class ZipUtil {
    private static final String TAG = "ZipUtil_zip4j";
    private static volatile ZipUtil zipUtil;

    private ZipUtil() {
    }

    public static ZipUtil getInstance() {
        if (zipUtil == null) {
            synchronized (ZipUtil.class) {
                if (zipUtil == null) {
                    zipUtil = new ZipUtil();
                }
            }
        }
        return zipUtil;
    }

    /**
     * zip4j压缩
     *
     * @param filePath    要压缩的文件路径，可文件，可目录
     * @param zipFilePath zip生成的文件路径
     * @param password    密码
     * @return 状态返回值
     */
    public int compressZip4j(String filePath, String zipFilePath, String password) {
        File sourceFile = new File(filePath);
        File zipFile_ = new File(zipFilePath);
        try {
            ZipFile zipFile = new ZipFile(zipFile_);
            zipFile.setFileNameCharset("UTF-8");//设置编码格式（支持中文）
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);//压缩方式
            zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);//压缩机别
            if (!TextUtils.isEmpty(password)) {//是否要加密（加密会影响压缩速度）
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);//加密方式
                zipParameters.setPassword(password.toCharArray());
            }
            if (zipFile_.isDirectory()) {
                String sourceFileName = checkString(sourceFile.getName());//文件校验
                zipFilePath = zipFilePath + "/" + sourceFileName + ".zip";
                Log.e(TAG, "保存压缩文件的路径： " + zipFilePath);
                compressZip4j(filePath, zipFilePath, password);
            }
            if (sourceFile.isDirectory()) {
                zipFile.addFolder(sourceFile, zipParameters);
            } else {
                zipFile.addFile(sourceFile, zipParameters);
            }
            Log.e(TAG, "compressZip4j:压缩成功");

        } catch (ZipException e) {
            e.printStackTrace();
            Log.e(TAG, "compressZip4j:压缩异常: " + e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * zip4j解压
     *
     * @param zipFilePath 待解亚的zip文件（目录）路径
     * @param filePath    解压到的保存路径
     * @param password    密码
     * @return 状态返回值
     */
    public int uncompressZip4j(String zipFilePath, String filePath, String password) {
        File zipFile_ = new File(zipFilePath);
        File sourceFile = new File(filePath);

        try {
            ZipFile zipFile = new ZipFile(zipFile_);
            zipFile.setFileNameCharset("UTF-8");
            if (!zipFile.isValidZipFile()) { //检查输入的zip文件是否是有效的zip文件
                throw new ZipException("压缩文件不合法,可能被损坏");
            }
            if (sourceFile.isDirectory() && !sourceFile.exists()) {
                sourceFile.mkdir();
            }
            if (zipFile.isEncrypted() && !TextUtils.isEmpty(password)) {
                zipFile.setPassword(password.toCharArray());
            }
            zipFile.extractAll(filePath);//解压
            Log.e(TAG, "uncompressZip4j:解压成功");

        } catch (ZipException e) {
            e.printStackTrace();
            Log.e(TAG, "uncompressZip4j:解压异常: " + e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * 校验提取出的源文件名是否带格式
     *
     * @param sourceFileName 要压缩的文件名
     * @return
     */
    private String checkString(String sourceFileName) {
        if (sourceFileName.indexOf(".") > 0) {
            sourceFileName = sourceFileName.substring(0, sourceFileName.indexOf("0"));
            Log.e(TAG, "checkString 校验过的sourceFileName是： " + sourceFileName);
        }
        return sourceFileName;
    }


}
