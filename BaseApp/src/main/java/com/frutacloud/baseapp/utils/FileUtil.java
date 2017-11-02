package com.frutacloud.baseapp.utils;

import com.frutacloud.baseapp.base.BaseField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * 文件夹操作
 */
public class FileUtil {

    private static FileUtil fileUtil;

    public static FileUtil getInstance() {
        if (null == fileUtil) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    /**
     * 文件复制
     *
     * @param srFile 源文件路径
     * @param dtFile 目标文件路径
     */
    public static void copyfile(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建指定名称的文件夹
     *
     * @param name 传null为根目录
     */
    public void createFiles(String name) {
        String path = BaseField.SDCARD_PATH;
        if (!Tools.isNull(name)) {
            path = BaseField.SDCARD_PATH + "/" + name;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取指定位置的文件
     *
     * @param name
     * @return
     */
    public Object getObject(String name) {
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(BaseField.SDCARD_PATH + "/" + name);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 保存文件到指定位置
     *
     * @param obj
     * @param name
     */
    public void saveObject(Object obj, String name) {
        try {
            File file = new File(BaseField.SDCARD_PATH + "/" + name);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(BaseField.SDCARD_PATH + "/" + name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
