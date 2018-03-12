CropReader
============

imageJ plugin

Auto cropping out image via bioformats.

This enable you to cropping out image from large than 2G pixel.

For example, TCGA or Genomic data commons degital slides, usually over 2G px svs formats.

ImageJ fail to import such a large image data with "java.lang.IllegalArgumentException: Array size too large".

You can crop out tissue image from them by this plugin.

![motive](https://github.com/fk506cni/ij_plgin/blob/master/motiv.png)

![resol](https://github.com/fk506cni/ij_plgin/blob/master/resol.png)

Installion
============

put CropReader.jar in your imageJ plugin directory.

It require bioformats, loci, and auto thresholding.

Simply, use fiji and put this into fiji's plugin directory.


Caution
============

This require larger memory against large image.

I recommend over 16G memory for a file over 2G pix.

Be carefully about imageJ with this plugin making your workstation unstable.


Process mode
============

If an input file was chosen, this processes the single file.

Else, an input dir was chosen, this processes all of file in the input dir.

If both file and dir was given, this process process the file, not the directory.

If you want to process all files in an directory, leave "input file" empty.

Dir mode try process all files in the directory. If not appropriate files are in the directory, Error will happen.

Be carefully about the contents of the directory.


Args
============
1stCr size: x

large image file are divide by many square of length x. X, 1stCR size is square length size.

Bioformats take many time for large tif. Big x is not always mean to fast processing.

I recommend x as multiple number of resize rate.



thumnail comression rate: y

Each cropped out square will resize by BILINEAR method under dicided resize rate, y.

thumbnail with grayscale will go autothresholding for binarization.


Auto thoresholding method: method for binarization. If tissue space was denied, change method as more area be chosen.

See Autothresholdin documentation. https://imagej.net/Auto_Threshold



Additional margin: z

Combinde rectangles will be cropped out from original image with additional margin.

In this phase, overlap of margin may be happlen.


minimum image size: m

if size of designed cropped out area is less than m, cropping out will be canceled.

This is due to omit noise or small debris.

If you want all small objects, put zero in here.


Allowed size: n

If maximum size of largest cropped out area is over than n, resize by minimum number of 2 to the n-th power.





License
============

Plugin: GPLv3

Cartoons above: from irasutoya, "http://www.irasutoya.com/". Copyright is belong to Irasutoya.

Digital slide above: from Genomic data commons, legacy archive.



Written by

fk506cni == unkodaisuki!

since 2018.1