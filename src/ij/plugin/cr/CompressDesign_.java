package ij.plugin.cr;

import java.util.ArrayList;

import ij.IJ;

public class CompressDesign_ {
	private ArrayList<ArrayList> area_list= new ArrayList<ArrayList>();
	private ArrayList<ArrayList> trunked_area = new ArrayList<ArrayList>();

	Mscs_ ms = new Mscs_();

	public void setAreas(ArrayList<ArrayList> arealist) {
		this.area_list = arealist;
	}


	public boolean isCover(ArrayList<Integer> area1, ArrayList<Integer> area2) {
		//area info is list(anreanumbaer, x_lim, y_lim, x_rim, x_rim)
		boolean x_bool;
		boolean y_bool;

		int x_sub_lr = area1.get(1) - area2.get(3);
		int x_sub_rl = area1.get(3) - area2.get(1);
		x_bool = ( x_sub_lr * x_sub_rl ) <= 0;

		int y_sub_lr = area1.get(2) - area2.get(4);
		int y_sub_rl = area1.get(4) - area2.get(2);
		y_bool = ( y_sub_lr * y_sub_rl ) <= 0;

		return x_bool && y_bool;
	}


	public ArrayList<Integer> makeNewArea(ArrayList<Integer> area1, ArrayList<Integer> area2) {
		ArrayList<Integer> newArea = new ArrayList<Integer>();
			newArea.add(area1.get(0)+area2.get(0));
			newArea.add(Math.min(area1.get(1), area2.get(1)));
			newArea.add(Math.min(area1.get(2), area2.get(2)));
			newArea.add(Math.max(area1.get(3), area2.get(3)));
			newArea.add(Math.max(area1.get(4), area2.get(4)));

		return newArea;
	}

	public void trunkArea() {
		ArrayList<ArrayList> area = this.area_list;
		int area_size = area.size();
		int trunk_size = 0;

		ArrayList<Integer> area_i = new ArrayList<Integer>();
		ArrayList<Integer> area_j = new ArrayList<Integer>();
		ArrayList<Integer> newArea = new ArrayList<Integer>();

		outloop:while(area_size != trunk_size) {
			area_size = area.size();
			IJ.log(String.valueOf(area_size)+" is area size");
			IJ.log(String.valueOf(trunk_size)+" is trunck size");

			inloop: for(int i = 0; i < area_size; i++) {
				for(int j=0; j < area_size; j++) {
					if(i < j) {
						area_i = area.get(i);
						area_j = area.get(j);
						if(isCover(area_i, area_j)) {
							newArea = makeNewArea(area_i, area_j);
							ms.arlist2log(newArea);
							area.add(newArea);
							area.remove(area_i);
							area.remove(area_j);
							trunk_size = area.size();
							IJ.log("bellow area i and j was combined.");
							IJ.log(String.valueOf(i)+" is i of area index");
							IJ.log(String.valueOf(j)+" is j of area index");
							break inloop;
						}
					}
				}
			}
			if(trunk_size ==0) {
				IJ.log("no comibination was done.");
				break outloop;
			}
		}

		ms.arar2log(area);
		this.trunked_area = area;
	}

	public ArrayList<ArrayList> getTrunckedArea(){
		return this.trunked_area;
	}

}
