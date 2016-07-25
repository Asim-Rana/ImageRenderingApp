package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader 
{
	final static String path = "C:\\Images";
	static final String[] EXTENSIONS = new String[]{"png", "jpg" , "bmp" , "gif"};
	public boolean DownloadImages(ArrayList<String> imagesList)
	{
		
		boolean result = new File(path).mkdir();
		if(result == false)
		{
			System.out.print("Error in creation of directory, Images are already downloaded");
			return false;
		}
			
		for(String url : imagesList)
		{
			try
			{
				SaveImage(url);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			} 
				
		}
		return true;
	}
	void SaveImage(String imageUrl) throws IOException
	{
		URL url = new URL(imageUrl);
		String fileName = url.getFile();
		String destName = path + fileName.substring(fileName.lastIndexOf("/"));
	 
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destName);
	 
		byte[] b = new byte[2048];
		int length;
	 
		while ((length = is.read(b)) != -1) 
		{
			os.write(b, 0, length);
		}
	 
		is.close();
		os.close();
	}
	
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
	public HashMap<String , BufferedImage> CacheImages()
	{
		File dir = new File(path);
		HashMap<String , BufferedImage> cacheImages = new HashMap<String , BufferedImage>();
		if(dir.isDirectory())
		{
			for (final File f : dir.listFiles(IMAGE_FILTER)) 
			{
                BufferedImage img = null;

                try 
                {
                    img = ImageIO.read(f);
                    int pos = f.getName().lastIndexOf(".");
                    String name = pos > 0 ? f.getName().substring(0, pos) : f.getName();
                    cacheImages.put(name, img);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
		}
		return cacheImages;
	}
}
