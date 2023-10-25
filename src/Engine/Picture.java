package Engine;

import java.awt.image.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.logging.Logger;

public class Picture {

    private static final Logger LOG = LogManager.getLogger(Picture.class);
    private boolean visible;
    private int width;
    private int height;
    private int x = 0;
    private int y = 0;

    private final String fileName;
    private java.awt.Image image;
    private final AffineTransform transform = new AffineTransform();

    private static final Hashtable<String, java.awt.Image> imageTable = new Hashtable<>();

    public Picture(String newFileName) {
        fileName = newFileName;

        if (imageTable.containsKey(fileName)) {
            image = imageTable.get(fileName);
        } else {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            try {
                assert url != null;
                image = ImageIO.read(url);
                imageTable.put(fileName, image);
            } catch (Exception e) {
                LOG.warning("Picture " + newFileName + " could not be loaded");
            }
        }
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        updateAffine();
    }

    public void draw(Graphics2D graphics) {
        synchronized (this) {
            graphics.drawImage(image, transform, null);
        }
    }

    private void updateAffine() {
        double w = (double) width / (double) image.getWidth(null);
        double h = (double) height / (double) image.getHeight(null);
        transform.setToIdentity();

        transform.translate(x, y);
        transform.scale(w, h);
    }

    public void setX(int newX) {
        synchronized (this) {
            x = newX;
            updateAffine();
        }
    }

    public void setY(int newY) {
        synchronized (this) {
            y = newY;
            updateAffine();
        }
    }

    public void setWidth(int newWidth) {
        synchronized (this) {
            width = newWidth;
            updateAffine();
        }
    }

    public void setHeight(int newHeight) {
        synchronized (this) {
            height = newHeight;
            updateAffine();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return fileName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Calculates the distance between the center of this image and the center of the given image
     */
    public double getDistance(Picture picture) {
        //ax&ay are the center of this image
        double ax = x + width / 2.0;
        double ay = y + height / 2.0;
        //bx&by are the center of the other image
        double bx = picture.x + picture.width / 2.0;
        double by = picture.y + picture.height / 2.0;

        double distanceX = ax - bx;
        double distanceY = ay - by;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Calculates the distance between the center of this image and the given point
     */
    public double getDistance(Point point) {
        //ax&ay are the center of this image
        double ax = x + width / 2.0;
        double ay = y + height / 2.0;
        double bx = point.x;
        double by = point.y;

        double distanceX = ax - bx;
        double distanceY = ay - by;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Determines, whether the given point is within the bounds of this image
     */
    public boolean pointWithin(Point p) {
        return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }

    public void setTransparency(int red, int green, int blue) {
        final Color color = new Color(red, green, blue);

        ImageFilter filter = new RGBImageFilter() {
            public final int markerRGB = color.getRGB() | 0xFF000000;

            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        image = Toolkit.getDefaultToolkit().createImage(ip);

    }
}
