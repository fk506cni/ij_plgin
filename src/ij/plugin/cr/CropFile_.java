package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import loci.common.Region;
import loci.plugins.BF;
import loci.plugins.in.ImporterOptions;;

public class CropFile_ {
	private String file;
	private int[] args = {0,0,1,1};
	private ImagePlus imp;
	private String color_mode="gray8bit";
	private Mscs_ ms = new Mscs_();

	public void setfile(String file) {
		this.file = file;
	}

	public void setColMode(String mode) {
		this.color_mode = mode;
	}

	public void setargs(int[] args) {
		this.args = args;
	}

	public ImagePlus args2imp() {
		try {
			IJ.log("crop_out from "+ms.ints2String(this.args) +" _color mode:" +this.color_mode+"\n");
			ImporterOptions options = new ImporterOptions();
			options.setId(this.file);
			options.setAutoscale(true);
			options.setCrop(true);
			options.setCropRegion(0, new Region(this.args[0],this.args[1],this.args[2],this.args[3]));

			if(this.color_mode=="gray8bit") {
				options.setColorMode(ImporterOptions.COLOR_MODE_GRAYSCALE);
			}else if(this.color_mode=="color") {
				options.setColorMode(ImporterOptions.COLOR_MODE_COMPOSITE);
			}

			//...etc.
			this.imp = BF.openImagePlus(options)[0];
			if(this.color_mode=="color") {
				ImageConverter icn = new ImageConverter(this.imp);
				icn.convertRGBStackToRGB();
				//IJ.run(this.imp, "Stack to RGB", "");
			}
			return this.imp;
		}catch(Exception e){
			System.out.println( "何かの例外が発生したので処理を続行できませんでした" );
			IJ.log("何かの例外が発生したので処理を続行できませんでした");
			this.imp = IJ.openImage(file);
			return this.imp;
		}
	}

}
