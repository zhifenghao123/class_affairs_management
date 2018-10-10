/**
 * 
 */
package com.classaffairs.framework.core.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Haozhifeng
 *
 */
public class CompressPic {
	public static boolean compressPic(String srcFilePath, String newFilePath, int outputWidth, int outputHeight, boolean proportion)
	  {
	    try
	    {
	      Image srcFile = ImageIO.read(new File(srcFilePath));
	      BufferedImage image = ImageIO.read(new File(srcFilePath));
	      int new_h;
	      int new_w;
	      //int new_h;
	      if (proportion)
	      {
	        double rate1 = image.getWidth(null) / outputWidth + 0.1D;
	        double rate2 = image.getHeight(null) / outputHeight + 0.1D;
	        
	        double rate = rate1 > rate2 ? rate1 : rate2;
	       // int new_w = (int)(image.getWidth(null) / rate);
	        new_w = (int)(image.getWidth(null) / rate);
	        new_h = (int)(image.getHeight(null) / rate);
	      }
	      else
	      {
	        new_w = outputWidth;
	        new_h = outputHeight;
	      }
	      BufferedImage tag = new BufferedImage(new_w, new_h, 1);
	      Graphics2D g2d = (Graphics2D)tag.getGraphics();
	      g2d.drawImage(srcFile.getScaledInstance(new_w, new_h, 4), 0, 0, null);
	      FileOutputStream out = new FileOutputStream(new File(newFilePath));
	      
	      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	      encoder.encode(tag);
	      out.close();
	      return true;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public static boolean compressPic(String srcFilePath, String newFilePath)
	  {
	    try
	    {
	      Image srcFile = ImageIO.read(new File(srcFilePath));
	      BufferedImage image = ImageIO.read(new File(srcFilePath));
	      
	      int outputWidth = image.getWidth();
	      
	      int outputHeight = image.getHeight();
	      BufferedImage tag = new BufferedImage(outputWidth, outputHeight, 1);
	      Graphics2D g2d = (Graphics2D)tag.getGraphics();
	      g2d.drawImage(srcFile.getScaledInstance(outputWidth, outputHeight, 4), 0, 0, null);
	      FileOutputStream out = new FileOutputStream(new File(newFilePath));
	      
	      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	      encoder.encode(tag);
	      out.close();
	      return true;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public static void main(String[] arg)
	  {
	    String srcFilePath = "D:/imgs/俱乐部主页.bmp";
	    String newFilePath = "D:/imgs/俱乐部主页sss1.bmp";
	    int outputWidth = 50;
	    int outputHeight = 50;
	    boolean proportion = false;
	    if (compressPic(srcFilePath, newFilePath, outputWidth, outputHeight, proportion)) {
	      System.out.println("压缩成功！");
	    } else {
	      System.out.println("压缩失败！");
	    }
	  }
}
