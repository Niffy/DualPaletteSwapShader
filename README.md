DualSwapPaletteShader
===================

#Requirements
* AndEngine

##What does it do?
Basicly you can set a shader to swap two colours with another two colours, hence the name dual palette swap!
A palette is where you swap one colour with another colour.  We can do this twice!

###How to use
To use the shader, simply copy the 2 Java source files or reference the library.

To reference the library,
* In eclipse have the library open.
* Right click on your project properties > Android > Add Library and select the library project.
* While still in the properties, go to Java Build Path > Projects and make sure the library is not referenced in there, 
if so click remove

To use the shader you first must create a DualPaletteSwapFragmentShaderCreator and set the first palette swap, and if you want a
second palette swap as well.
You then pass this object to DualPaletteSwap constructor.

The creator needs RGBA values, that is Red Green Blue Alpha. These values which are to 4 decimal places. 
So if you have an R value of 65, you do 65 divide 255 equals 0.2549019607843137, thus you pass 0.2549f.

You can pass the whole value, but you may want to make sure your set the epsilon value to compensate

An epsilon value gives us an upper and lower bounds to check between, this then allows us room in case the colour 
values change slighty then what they are meant to be. the default value is RGBA 0.09f 0.09f 0.09f 1.0f

After the shader has been attached you can change the values by simplying calling the same methods on a 
DualPaletteSwapFragmentShaderCreator object.
 
#Be sure to check the javadoc and example! I went to a lot of effort to do it all!

[DualPaletteSwapShaderExample](https://github.com/Niffy/DualPaletteSwapShaderExample)
