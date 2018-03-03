package ij.plugin.cr;

import java.util.ArrayList;

//import fiji.threshold.Auto_Threshold;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;

public class PartAnal_ {
	private ImagePlus imp = new ImagePlus();
	private String thr_method = "Default";
	private ResultsTable rt = new ResultsTable();
	//private ParticleAnalyzer pa = new ParticleAnalyzer();
	Mscs_ ms = new Mscs_();
	private ResultParser_ rp = new ResultParser_();

	ArrayList<ArrayList> rectlist = new ArrayList<ArrayList>();

	public void setImp(ImagePlus imp) {
		this.imp = imp;
	}

	public void setThrMethod(String method) {
		this.thr_method = method;
	}

	public void autoThd() {
		//Auto_Threshold ath = new Auto_Threshold();
		ImagePlus imp = this.imp;
		IJ.setAutoThreshold(imp, this.thr_method);
		ij.Prefs.blackBackground = true;
		IJ.run(imp, "Convert to Mask", "only");
		imp.show();
		this.imp = imp;
	}

	public void partAnal() {
		ImagePlus imp = this.imp;
		ResultsTable rt = this.rt;
		ParticleAnalyzer pa = new ParticleAnalyzer(ParticleAnalyzer.SHOW_NONE, ParticleAnalyzer.RECT, rt, 0.0, Double.POSITIVE_INFINITY);
		pa.analyze(imp);
		rt.show("result of unko");

		this.rp.setTable(rt);
	}

	public void trunkRect() {
		ArrayList<ArrayList> rectlist =  this.rp.getList();
		ArrayList<Integer> rect_element = new ArrayList<Integer>();
		ms.int2ijlog(rectlist.size());
		ms.int2ijlog(rect_element.size());
		//length to rim position
		for(int i=0; i<rectlist.size();i++) {
			rect_element = rectlist.get(i);
			int x_lim = rect_element.get(1);
			int x_rim = rect_element.get(1) + rect_element.get(3) -1;
			int y_lim = rect_element.get(2);
			int y_rim = rect_element.get(2) + rect_element.get(4) -1;
			rect_element.set(1, x_lim);
			rect_element.set(2, y_lim);
			rect_element.set(3, x_rim);
			rect_element.set(4, y_rim);
			rectlist.set(i, rect_element);
			ms.arlist2log(rectlist.get(i));
		}
		//ms.arar2log(rectlist);
		this.rectlist = rectlist;
	}

	public ArrayList<ArrayList> getRectList() {
		return this.rectlist;
	}
}
