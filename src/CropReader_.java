import ij.IJ;
import ij.plugin.PlugIn;
import ij.plugin.cr.FileManager_;

public class CropReader_ implements PlugIn{
	public void run (String arg) {
		String plg_ver = "1.3";
		IJ.log("starting CR plugin ver" +plg_ver);

		FileManager_ fm = new FileManager_();
		//fm.setInOut();
		fm.processFile();

	}
}