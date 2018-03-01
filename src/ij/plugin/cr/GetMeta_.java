package ij.plugin.cr;

import ij.IJ;
import ij.plugin.PlugIn;
import loci.common.services.ServiceFactory;
import loci.formats.IFormatReader;
import loci.formats.ImageReader;
import loci.formats.meta.IMetadata;
import loci.formats.services.OMEXMLService;

public class GetMeta_  implements PlugIn {
	String file = null;
	int[] size = new int[2];

	public static void printPixelDimensions (IFormatReader reader) {
		// output dimensional information
		int sizeX = reader.getSizeX();
		int sizeY = reader.getSizeY();
		int sizeZ = reader.getSizeZ();
		int sizeC = reader.getSizeC();
		int sizeT = reader.getSizeT();
		int imageCount = reader.getImageCount();
		System.out.println();
		System.out.println("Pixel dimensions:");
		System.out.println("\tWidth = " + sizeX);
		System.out.println("\tHeight = " + sizeY);
		System.out.println("\tFocal planes = " + sizeZ);
		System.out.println("\tChannels = " + sizeC);
		System.out.println("\tTimepoints = " + sizeT);
		System.out.println("\tTotal planes = " + imageCount);
	}
	public void readmeta() {
		try {
			if(this.file == null) {
				IJ.log("ぬるぽ");
			}else {
				// create OME-XML metadata store
				ServiceFactory factory = new ServiceFactory();
				OMEXMLService service = factory.getInstance(OMEXMLService.class);
				IMetadata meta = service.createOMEXMLMetadata();

				// create format reader
				IFormatReader reader = new ImageReader();
				reader.setMetadataStore(meta);

				// initialize file
				//System.out.println("Initializing " + file);
				reader.setId(this.file);

				//printPixelDimensions(reader);
				int sizeX = reader.getSizeX();
				int sizeY = reader.getSizeY();
				//printPhysicalDimensions(meta, series);
				this.size[0] = sizeX;
				this.size[1] = sizeY;

				reader.close();
			}
		} catch ( Exception e ) {
			System.out.println( "何かの例外が発生したので処理を続行できませんでした" );
		}
	}

	public void setfile (String file) {
		this.file = file;
	}

	public int[] getsize() {
		return this.size;
	}

	public int getX() {
		return this.size[0];
	}

	public int getY() {
		return this.size[1];
	}

	public void run (String arg) {
		String file ="D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-AAC9-01A-01-TSA.tif";
		//int[] result = new int[2];
		//result = readmeta(file);
		//System.out.println(result);
		setfile(file);
		readmeta();
		int[] result = new int[2];
		result = getsize();
		IJ.log(String.valueOf(result[0]));
		IJ.log(String.valueOf(result[1]));
		IJ.log("unko432");
	}

}
