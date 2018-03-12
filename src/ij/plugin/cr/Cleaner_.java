package ij.plugin.cr;

import ij.IJ;
import ij.measure.ResultsTable;


public class Cleaner_ {
	ResultsTable rt = new ResultsTable();

	public void clearResult() {
		int nrow = rt.getCounter();
		for(int i=0; i<nrow;i++) {
			rt.deleteRow(i);
		}
		IJ.log("result from 1 to "+String.valueOf(nrow)+"deleted.");
	}

/*	public void closeImps() {
		int[] imps_ids = WindowManager.getIDList();
		for(int i=0; i< imps_ids.length;i++) {

		}
		ImagePlus imp = WindowManager.getCurrentImage();
		imp.close();
	}*/

}
