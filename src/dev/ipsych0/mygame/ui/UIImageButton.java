package dev.ipsych0.mygame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class UIImageButton extends UIObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient BufferedImage[] images;

	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images) {
		super(x, y, width, height);
		this.images = images;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(hovering)
			g.drawImage(images[1], (int) x + 1, (int) y + 1, width, height, null);
		else
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();
	    out.writeInt(images.length); // how many images are serialized?

	    for (BufferedImage eachImage : images) {
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        ImageIO.write(eachImage, "png", buffer);

	        out.writeInt(buffer.size()); // Prepend image with byte count
	        buffer.writeTo(out);         // Write image
	    }
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();

	    int imageCount = in.readInt();
	    this.images = new BufferedImage[imageCount];
	    for (int i = 0; i < imageCount; i++) {
	        int size = in.readInt(); // Read byte count

	        byte[] buffer = new byte[size];
	        in.readFully(buffer); // Make sure you read all bytes of the image

	        this.images[i] = ImageIO.read(new ByteArrayInputStream(buffer));
	    }
	}
	
}
