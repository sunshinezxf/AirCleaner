package dist.purify.air.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunshine on 16/9/18.
 */
public class ZipUtil {
    private static final int BUFFER = 8192;

    public static ResultData compress(List<String> path) {
        ResultData result = new ResultData();
        String base = GlobalUtil.retrivePath();
        File file = new File(new StringBuffer(base).append("/material/qrcode/download").toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        Timestamp time = new Timestamp(System.currentTimeMillis());
        File zip = new File(new StringBuffer(base).append("/material/qrcode/download/").append(time.getTime()).append(".zip").toString());
        ZipOutputStream out;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zip);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i = 0; i < path.size(); i++) {
                compress(new File(new StringBuffer(GlobalUtil.retrivePath()).append(path.get(i)).toString()), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result.setData(new StringBuffer("/material/qrcode/download/").append(time.getTime()).append(".zip").toString());
        return result;
    }

    private static void compress(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            compressDirectory(file, out, basedir);
        } else {
            compressFile(file, out, basedir);
        }
    }

    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    private static void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
