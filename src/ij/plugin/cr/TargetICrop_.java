package ij.plugin.cr;

import java.util.ArrayList;

import ij.ImagePlus;

public class TargetICrop_ {
	private ArrayList<Integer> crop_area_i;
	private int Xdiv;
	private String file;
	private ImagePlus[] imps;
	//private ArrayList<ImagePlus> imp_list = new ArrayList<ImagePlus>();
	private CropFile_ cf = new CropFile_();
	private Mscs_ ms = new Mscs_();
	private ArrayList<ArrayList> divided_areas = new ArrayList<ArrayList>();
	private ImagePlus resultImp = new ImagePlus();

	public void setCropAreaI(ArrayList<Integer> crop_area_i) {
		this.crop_area_i = crop_area_i;
	}

	public void setXdiv(int Xdiv) {
		this.Xdiv = Xdiv;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void makeImps2Imp() {
		//make divide area list
		ArrayList<Integer> area = this.crop_area_i;
		//ArrayList<ArrayList> div_area = new ArrayList<ArrayList>();
		//int[] div_area = new int[4];
		ArrayList<int[]> div_areas = new ArrayList<int[]>();
		ImagePlus[] imps = new ImagePlus[this.Xdiv];


		int Xdiv = this.Xdiv;
		int Wdiv = area.get(3) /Xdiv;
		for(int j=0; j<Xdiv; j++) {
			int[] area_j = new int[4];
			area_j[0] = area.get(1) + j*Wdiv;
			area_j[1] = area.get(2);
			area_j[2] = Wdiv;
			area_j[3] = area.get(4);
			this.ms.ints2ijlog(area_j);
			div_areas.add(area_j);
		}


		CropFile_ cf = this.cf;
		cf.setfile(this.file);
		cf.setColMode("color");
		for(int j=0; j<Xdiv; j++) {
			cf.setargs(div_areas.get(j));
			imps[j] = cf.args2imp();
			//imps[j].show();
		}

		SmallImps_ sis = new SmallImps_();
		sis.setBigImps(imps);
		sis.setRrate(Xdiv);
		sis.runResize();

		CombineImps_ cmp = new CombineImps_();
		cmp.setImpsXY(sis.getSmallImps(), Xdiv, 1);
		cmp.combineXY();
		cmp.getImp().show();

		this.resultImp = cmp.getImp();
		//
	}

	public ImagePlus getResultImp() {
		return this.resultImp;
	}
}
