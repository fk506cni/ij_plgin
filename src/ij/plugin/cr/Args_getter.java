package ij.plugin.cr;

import java.io.File;

import fiji.util.gui.GenericDialogPlus;
import ij.IJ;
//import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.plugin.cr.Mscs_;

public class Args_getter implements PlugIn {
	//parameter requirement
	private int big_sqlen = 1024;
	private int big_divide = 32;
	private int additional_margin =64;
	private int min_size = 1000000;
	private long allowed_pixels = (long)Math.pow(2d, 31d);
	private String thr_method ="Default";
    public String title="CR entry";
    public int width=512,height=512;
    public String file ="";
    public File file__Asfile;

    public String input_dir ="";
    public File input_dir_Asfile;


    public String[] input_files;
    public File[] input_files__Asfile;

    public String output_dir ="";
    public File output_dir_Asfile;

    public String process_mode = "file";
    private Mscs_ ms = new Mscs_();

    public void run(String arg) {
      GenericDialogPlus gdp = new GenericDialogPlus("CR entry");
      gdp.addNumericField("1stCR size", (double)this.big_sqlen, 0);
      gdp.addNumericField("tbm size_rate", (double)this.big_divide, 0);
      gdp.addNumericField("additional_margin", (double)this.additional_margin, 0);
      gdp.addNumericField("mimum_img size(px)", (double)this.min_size, 0);
      gdp.addNumericField("allowed_size(px)", (double)this.allowed_pixels, 0);

      String[] choice = {"Default","Huang","Huang2","Intermodes","IsoData","Li","MaxEntropy",
    		  "Mean","MinError(I)","Minimum","Moments","Otsu","Percentile","RenyiEntropy",
    		  "Shanbhag","Triangle","Yen"};
      gdp.addChoice("Athr_meth",choice , choice[0]);
      gdp.addFileField("input file", "");
      gdp.addDirectoryField("input dir", "");
      gdp.addDirectoryField("output dir", "");
      gdp.showDialog();
      if (gdp.wasCanceled()) return;

      this.big_sqlen = (int)gdp.getNextNumber();
      IJ.log(String.valueOf(this.big_sqlen)+": big sqare length.");

      this.big_divide = (int)gdp.getNextNumber();
      IJ.log(String.valueOf(this.big_divide)+" : thumbnail comression rate.");

      this.additional_margin = (int)gdp.getNextNumber();
      IJ.log(String.valueOf(this.additional_margin)+": additional margin with output images.");

      this.min_size  = (int)gdp.getNextNumber();
      IJ.log(String.valueOf(this.min_size)+": minimal mixel size included in output size");

      this.allowed_pixels = (long)gdp.getNextNumber();
      IJ.log(String.valueOf(this.allowed_pixels)+": allowd output size(used 4 output resize)");

      this.thr_method = gdp.getNextChoice();
      IJ.log(String.valueOf(this.thr_method)+": auto thresholding method");

      this.file = gdp.getNextString();
      this.file__Asfile = new File(this.file);
      IJ.log(this.file +": target file");
      IJ.log(String.valueOf(this.file.equals("")));

      this.input_dir = gdp.getNextString();
      this.input_dir_Asfile = new File(this.input_dir);
      if(!this.input_dir.endsWith("\\") &&  "\\".equals(File.separator)) {
    	  this.input_dir = this.input_dir+"\\";
      }
      IJ.log(this.input_dir+": input directory");

      this.output_dir = gdp.getNextString();
      this.output_dir_Asfile = new File(this.output_dir);
      if(!this.output_dir.endsWith("\\") &&  "\\".equals(File.separator)) {
    	  this.output_dir = this.output_dir+"\\";
      }
      IJ.log(this.output_dir+": output directory");

      if(!isCorrectArgs()) {
    	  IJ.log("not correct args");
    	  return;
      }

      setMode();

    }

    public boolean isCorrectArgs() {
        if(this.allowed_pixels <= (long)this.min_size) {
      	  IJ.log("allowed size shoould be min size");
      	  return false;
        }

        if(this.file== "" || this.output_dir=="") {
      	  IJ.log("require in file and out dir");
      	  return false;
        }

        if(this.big_sqlen==0 || this.big_divide ==0 || this.allowed_pixels==0) {
        	IJ.log(String.valueOf(this.big_sqlen!=0)+"::big sq length");
        	IJ.log(String.valueOf(this.big_divide!=0)+"::thmb rate length");
        	IJ.log(String.valueOf(this.allowed_pixels!=0)+"::allowed size");
        	IJ.log(String.valueOf(this.big_sqlen==0 || this.big_divide ==0 || this.allowed_pixels==0));
        	IJ.log("1st CR, tbm size rate, allowed size should be not zero.");
        	return false;
        }

        return true;
    }

    public String retrieve2bool(boolean cond1, boolean cond2) {
    	String log = "condition1 :"+String.valueOf(cond1)+", and condition2:"+String.valueOf(cond2);
    	return log;
    }

    public void setMode() {
    	IJ.log(retrieve2bool((!this.file.equals("")), (!this.input_dir.equals("\\"))));

    	if((!this.file.equals("")) && (!this.input_dir.equals("\\"))) {
    		this.process_mode = "file";
    		IJ.log(this.file+"is file string");
    		IJ.log("running "+this.process_mode+" mode by"+retrieve2bool((!this.file.equals("")), (!this.input_dir.equals("\\"))));

    	}else if((this.file.equals("")) &&  (!this.input_dir.equals("\\"))) {
    		this.process_mode = "dir";
    		IJ.log("running "+this.process_mode+" mode by"+retrieve2bool((!this.file.equals("")), (!this.input_dir.equals("\\"))));

    	}else if((!this.file.equals("")) &&  (this.input_dir.equals("\\"))) {
    		this.process_mode = "file";
    		IJ.log("running "+this.process_mode+" mode by"+retrieve2bool((!this.file.equals("")), (!this.input_dir.equals("\\"))));

    	}else if((this.file.equals("")) &&  (this.input_dir.equals("\\"))) {
    		IJ.log("running "+this.process_mode+" mode by"+retrieve2bool((!this.file.equals("")), (!this.input_dir.equals("\\"))));
    		IJ.log("inputs were empty.");
    	}

    	//set input files
    	if(this.process_mode == "dir") {
    		File dir = new File(this.input_dir);
    		File[] input_list = dir.listFiles();

    		if(input_list == null) {
    			IJ.log("input dir is empty.");
    		}else {
    			for(int i = 0; i < input_list.length;i++) {
    				IJ.log(input_list[i].getAbsolutePath());
    				this.input_files[i] = input_list[i].getAbsolutePath();
    			}
    		}

    	}
    }

    public void ArgsViaGui() {
    	run("");

    }

    public void setIntArgs(int big_sqlen, int big_divide, int additional_margin, int min_size, long allowed_pixels) {
    	this.big_sqlen = big_sqlen;
    	this.big_divide = big_divide;
    	this.additional_margin = additional_margin;
    	this.min_size = min_size;
    	this.allowed_pixels = allowed_pixels;
    }

    public void setInOut(String file, String output_dir) {

    }

    public int getBigSqlen() {
    	return this.big_sqlen;
    }

    public int getBigDivide() {
    	return this.big_divide;
    }

    public int getAddMargin() {
    	return this.additional_margin;
    }

    public int getMinSize() {
    	return this.min_size;
    }

    public long getAllowedPx() {
    	return this.allowed_pixels;
    }

    public String getAMethod() {
    	return this.thr_method;
    }

    public String getFilePath() {
    	return this.file;
    }

    public String getOutDir() {
    	return this.output_dir;
    }

    public int[] getIntArgs() {
    	int[] ints_res = {this.big_sqlen, this.big_sqlen, this.additional_margin, this.min_size, (int)this.allowed_pixels};
    	return ints_res;
    }

    public String[] getMethInOut() {
    	String[] str_res = {this.thr_method, this.file, this.output_dir};
    	return str_res;
    }

    public String getMode() {
    	return this.process_mode;
    }
}
