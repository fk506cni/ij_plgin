package ij.plugin.cr;

import java.util.ArrayList;

import ij.measure.ResultsTable;


public class ResultParser_ {
	private ResultsTable rt;
	private String[] colnames;
	private int ncol = 1;
	private int nrow = 1;
	private int[][] resultarray;
	//private ArrayList<ArrayList<>> reslutlist= new ArrayList<Arraylist<>>();
	private ArrayList<ArrayList> reslutlist= new ArrayList<ArrayList>();
	Mscs_ ms = new Mscs_();

	public void setTable(ResultsTable rt) {
		this.rt = rt;
		this.colnames = rt.getHeadings();
		this.ms.strs2log(this.colnames);

		this.ncol = this.colnames.length+1;
		ms.int2ijlog(this.ncol);

		this.nrow = rt.getCounter();
		this.resultarray = new int[this.nrow][this.ncol];
		ms.int2ijlog(this.nrow);

		for(int i=0; i < this.nrow ; i++) {
			this.reslutlist.add(row2Vals(i));
			ms.arlist2log(reslutlist.get(i));
			this.resultarray[i] = row2valsArray(i);
		}

		//IJ.log("kokomade kita ");

		//ms.arar2log(reslutlist);;

	}

	public ArrayList<Integer> row2Vals(int rowindex) {
		String rowstr = rt.getRowAsString(rowindex);
		//IJ.log(rowstr);
		String[] strs = rowstr.split("\t");
		ArrayList<Integer> rowVals = new ArrayList<Integer>();
		for(int i=0; i < strs.length; i++) {
			rowVals.add(Integer.parseInt(strs[i]));
		}
		//This make index to 1. which make it easy to understand comressdesign phase.
		//But it may make it difficult to understand area index. if you not to want. remove bellow sentece.
		rowVals.set(0, 1);

		//ms.strs2log(strs);
		//ms.arlist2log(rowVals);

		return rowVals;
	}

	public int[] row2valsArray(int rowindex) {
		int[] vals = new int[this.ncol];
		String rowstr = rt.getRowAsString(rowindex);
		String[] strs = rowstr.split("\t");
		for(int i=0; i < strs.length; i++) {
			vals[i] = Integer.parseInt(strs[i]);
		}

		//This make index to 1. which make it easy to understand comressdesign phase.
		//But it may make it difficult to understand area index. if you not to want. remove bellow sentece.
		vals[0] = 1;
		//ms.ints2ijlog(vals);
		return vals;
	}

	public ArrayList<ArrayList> getList(){
		return this.reslutlist;
	}

	public int[][] getArray(){
		return this.resultarray;
	}

}
