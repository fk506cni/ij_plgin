CropReader
============

imageJ plugin

Auto cropping out image via bioformats.

This enable you to crop out image from larger image file than 2G pixel.

For example, TCGA or Genomic data commons digital slides, usually over 2G px svs formats.

ImageJ fail to import such a large image data with "java.lang.IllegalArgumentException: Array size too large".

You can crop out tissue image from them by this plugin.

![motive](https://github.com/fk506cni/ij_plgin/blob/master/motiv.png)

![resol](https://github.com/fk506cni/ij_plgin/blob/master/resol.png)

Installation
============

Put CropReader.jar in your imageJ plugin directory.

It require bioformats, loci, genericDialogPlus,and auto thresholding.

Simply, use fiji and put this into fiji's plugin directory.


Caution
============

This require larger memory against larger image.

I recommend over 32G memory + addequate swap for a file over 2G pix.

Be careful about imageJ with this plugin not to make your workstation unstable due to memory consumption.


Process mode
============

If an input file was chosen, this processes the single file.

Else, an input dir was chosen, this processes all of file in the input dir.

If both file and dir was given, this process process the file, not the directory.

If you want to process all files in an directory, leave "input file" empty.

Dir mode try to process all files in the directory. If not appropriate files are in the directory, Error will happen.

Be careful about the contents of the directory and permission.


Args
============
## 1stCr size: x

At 1st step, large image file are divide by many square of length x like a grid. X, 1stCR size is square length.

Larger x links with fewer square splition.

However, Bioformats take many time for handling with large tif. Big x does not always linked with fast processing.

I recommend x as multiple number of resize rate.


## thumbnail comression rate: y

Each cropped out square will resize by BILINEAR method under dicided resize rate, y.

thumbnail with grayscale will go autothresholding for binarization.

## Auto thoresholding method: method for binarization.

This plugin designs area to crop out with binarized thumbnail.

You should choice proper method as tissue you want chosen.

If tissue space was not selected, change method as more area be chosen.

See Autothresholdin documentation. https://imagej.net/Auto_Threshold

You can try all auto thresholding method by "\_gray_thumbnail" file.

Binarized image is "\_thrMsk." file.


## Additional margin: z

Binarized image will go particle, rectangle analysis.

After decision of rectangle for each particle, particles with overlap area is combilned.

Combinde rectangles will be cropped out from original image with additional margin, Z.

In this phase, overlap of margin may be happlen.


## minimum image size: m

if size of designed cropped out area is less than m, cropping out will be canceled.

This is due to omit noise or small debris.

If you want all small objects, put zero in here.


## Allowed size: n

If maximum size of largest cropped out area is over than n, resize by minimum number of 2 to the n-th power.

Default is 2^31


## Format: f

image saving format.

Equal to SaveAs(f).

If you use Jpeg, quality score should be specified by EDIT > OPTION > INPUT/OUTPUT.

ImageJ's principle does not recommend you to use Jpeg.

See document

https://imagej.net/Principles




License
============

Plugin: GPLv3

Cartoons above: from irasutoya, "http://www.irasutoya.com/". Copyright is belong to Irasutoya.

Digital slide above: from Genomic data commons, legacy archive.



Written by

fk506cni == unkodaisuki!

since 2018.1


Citation
============

Under construction