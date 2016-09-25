package dist.purify.air.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dist.purify.air.dao.QRCodeDao;
import dist.purify.air.model.goods.AbstractGoods;
import dist.purify.air.model.goods.Goods4Customer;
import dist.purify.air.model.qrcode.QRCode;
import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.GlobalUtil;
import dist.purify.air.utils.PlatformConfig;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by sunshine on 8/28/16.
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {
    private final int DEFAULT_WIDTH = 135, DEFAULT_HEIGHT = 135;

    private final static String SAVE_PATH = "/material/qrcode/";

    private final static String TEMPLATE_BG_PATH = "/material/backend/image/qrcode_bg.png";

    private final static int FORE_GROUND = 0xFF50507D, BACK_GROUND = 0xFFA1A4AE;

    private Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    private Object lock = new Object();

    @Autowired
    private QRCodeDao qrCodeDao;

    public ResultData create(String prefix, int num, String goodsId) {
        ResultData result = new ResultData();
        PlatformConfig config = PlatformConfig.instance();
        if (num <= 0) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
            result.setDescription("需要批量生成的二维码的数量为:" + num + ", 无需进行处理");
            return result;
        }
        DecimalFormat principle = new DecimalFormat("000");
        List<String> path = new ArrayList<>();
        synchronized (lock) {
            StringBuffer message = new StringBuffer();
            AbstractGoods goods = new Goods4Customer();
            goods.setGoodsId(goodsId);
            for (int i = 0; i < num; i++) {
                String value = new StringBuffer(prefix).append(principle.format(i + 1)).toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(Calendar.getInstance().getTime());
                File file = new File(new StringBuffer(GlobalUtil.retrivePath()).append(SAVE_PATH).append(date).append(File.separator).toString());
                if (!file.exists()) {
                    file.mkdirs();
                }
                //生成购买的二维码图片文件
                QRCode purchase = new QRCode(value, new StringBuffer(SAVE_PATH).append(date).append(File.separator).append(value).append("_machine.png").toString(), goods);
                String text = new StringBuffer("http://").append(config.getValue("server_url")).append("/goods").append("/purchase/").append(purchase.getGoods().getGoodsId()).append("?client=").append(purchase.getValue()).toString();
                purchase.setUrl(text);
                ResultData response = generate(purchase);
                if (response.getResponseCode() == ResponseCode.RESPONSE_ERROR) {
                    message.append(purchase.getValue()).append(", error message: ").append(response.getDescription());
                }
                combine(purchase.getPath());
                path.add(purchase.getPath());
                //保存到数据库
                response = qrCodeDao.insertQRCode(purchase);
                if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
                    logger.error("购买二维码信息写入数据库异常, " + response.getDescription());
                }
                result.setData(path);
            }
            result.setDescription(message.toString());
        }
        return result;
    }

    @Override
    public ResultData query(Map<String, Object> condition) {
        ResultData result = new ResultData();
        ResultData response = qrCodeDao.queryQRCode(condition);
        result.setResponseCode(response.getResponseCode());
        return result;
    }

    private ResultData generate(QRCode code) {
        ResultData result = new ResultData();
        //保存图片
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            MatrixToImageConfig config = new MatrixToImageConfig(FORE_GROUND, BACK_GROUND);
            BitMatrix matrix = new MultiFormatWriter().encode(code.getUrl(), BarcodeFormat.QR_CODE, DEFAULT_WIDTH, DEFAULT_HEIGHT, hints);
            File file = new File(new StringBuffer(GlobalUtil.retrivePath()).append(code.getPath()).toString());
            MatrixToImageWriter.writeToPath(matrix, QRCode.DEFAULT_FORMAT, file.toPath(), config);
            result.setData(code);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        } finally {
            return result;
        }
    }

    private ResultData combine(String path) {
        ResultData result = new ResultData();
        try {
            StringBuffer bg = new StringBuffer(GlobalUtil.retrivePath()).append(TEMPLATE_BG_PATH);
            StringBuffer qrcode = new StringBuffer(GlobalUtil.retrivePath()).append(path);
            BufferedImage big = ImageIO.read(new File(bg.toString()));
            BufferedImage small = ImageIO.read(new File(qrcode.toString()));
            Graphics2D g = big.createGraphics();
            int x = 151;
            int y = 157;
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, "png", new File(qrcode.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
