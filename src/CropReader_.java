import ij.IJ;
import ij.plugin.PlugIn;
import ij.plugin.cr.Process_manager;

public class CropReader_ implements PlugIn{
	public void run (String arg) {
		IJ.error("Hello World");

		Process_manager pm = new Process_manager();
		pm.process_file();
	}
}