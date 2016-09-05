package dist.purify.air.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshine on 8/29/16.
 */
public class QRCodeUtil {

    public static String produce(String content, String parent) {
        if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(parent)) {
            String result = generate(content, parent);
            return result;
        }
        return "";
    }

    private static String generate(String content, String parent) {
        final int QRCODE_WIDTH = 300, QRCODE_HEIGHT = 300;
        final String format = "png";
        Map<EncodeHintType, String> config = new HashMap<EncodeHintType, String>();
        config.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, config);
            BufferedImage image = new BufferedImage(QRCODE_WIDTH, QRCODE_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Calendar calendar = Calendar.getInstance();
            String time = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
            String relative = new StringBuffer(File.separator).append("material").append(File.separator).append(time).append(File.separator).append(IDGenerator.generate("SN")).append(".png").toString();
            String whole = new StringBuffer(parent).append(relative).toString();
            File file = new File(whole);
            ImageIO.write(image, format, file);
            Path path = FileSystems.getDefault().getPath(parent, relative);
            MatrixToImageWriter.writeToPath(matrix, format, path);
            return relative;
        } catch (Exception e) {
            return "";
        }
    }
}
