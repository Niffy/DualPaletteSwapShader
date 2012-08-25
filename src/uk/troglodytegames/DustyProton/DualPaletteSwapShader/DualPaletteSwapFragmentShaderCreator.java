package uk.troglodytegames.DustyProton.DualPaletteSwapShader;

import java.util.UUID;

import org.andengine.opengl.shader.constants.ShaderProgramConstants;

/**
 * This is a helper class for {@link DualPaletteSwap}. It produces the
 * Fragment Shader string, which searches for an RGBA colour value then replaces
 * it. <br>
 * 
 * You must first create and set this object up in order for the shader to
 * correctly work. <br>
 * You then pass this object to the shader directly. All RGBA values are to
 * decimals places e.g 0.1234f. If you do pass more decimal places than there is
 * the possibility of the shader not affecting the chosen colours, which is why
 * a epsilon value is used. Epsilon values give us a range, an upper and lower
 * bound to search within when detecting for a colour. So if you tweak the
 * sensitivity for each value separately. The default epsilon values can be
 * found at <li> {@link #eps_default_r} <li>
 * {@link #eps_default_g} <li> {@link #eps_default_b} <li>{@link #eps_default_a}
 * <br>
 * <br>
 * 
 * You first set the first palette swap using one of the following methods <li>
 * {@link #setFirstPaletteSwap(float, float, float, float, float, float, float, float, float, float, float, float)}
 * <li>{@link #setFirstPaletteSwap(float[], float[], float[])} <br>
 * If you want you can also set up a second palette swap using one of the
 * following methods. <li>
 * {@link #setSecondPaletteSwap(float, float, float, float, float, float, float, float, float, float, float, float)}
 * <li>{@link #setSecondPaletteSwap(float[], float[], float[])} <br>
 * <b>Note:</b> <li>The default single palette swap is white replaced with
 * black. <li>When you set the second palette swap it is assumed the first
 * palette swap has been set to your requirements. <li>You can set the first
 * palette swap again after you set the second palette swap. <li>You can change
 * the values at any time!!!!
 * 
 * @author Paul Robinson
 * 
 */
public class DualPaletteSwapFragmentShaderCreator {
	// ===========================================================
	// Notes
	// ===========================================================
	/*
	 * Big thanks to itmon on the andengine forums, his tutorial helped!
	 * Shader taken from http://www.gamedev.net/topic/571969-color-replacement-using-glsl/
	 */
	// ===========================================================
	// Fields
	// ===========================================================
	/**
	 * Fragment Shader in use.
	 */
	private String mFragmentShader = "precision lowp float;\n" + "uniform sampler2D "
			+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ";\n" + "varying mediump vec2 "
			+ ShaderProgramConstants.VARYING_TEXTURECOORDINATES + ";\n" + "uniform vec3 " + this.uniform_color_find_00
			+ ";\n" + "uniform vec3 " + this.uniform_color_replace_00 + ";\n" + "uniform vec3 "
			+ this.uniform_color_eps_00 + ";\n" + "void main() {\n" + "	vec4 pixel = texture2D("
			+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ", " + ShaderProgramConstants.VARYING_TEXTURECOORDINATES
			+ ");\n" + "if(all(greaterThanEqual(pixel, vec4(" + this.uniform_color_find_00 + " - "
			+ this.uniform_color_eps_00 + ", 1.0))) && all( lessThanEqual(pixel, vec4(" + this.uniform_color_find_00
			+ " + " + this.uniform_color_eps_00 + ", 1.0)) ))\n" + "	pixel = vec4(" + this.uniform_color_replace_00
			+ ",1.0);\n\n" + "	gl_FragColor = pixel;\n" + "}\n";
	/**
	 * First palette swap colour to find
	 */
	public String uniform_color_find_00 = "u_color_find_00_";
	/**
	 * Second palette swap colour to find
	 */
	public String uniform_color_find_01 = "u_color_find_01_";
	/**
	 * First palette swap colour replacement
	 */
	public String uniform_color_replace_00 = "u_color_replace_00_";
	/**
	 * Second palette swap colour replacement
	 */
	public String uniform_color_replace_01 = "u_color_replace_01";
	/**
	 * First palette swap epsilon
	 */
	public String uniform_color_eps_00 = "u_color_eps_00_";
	/**
	 * Second palette swap epsilon
	 */
	public String uniform_color_eps_01 = "u_color_eps_01_";
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap}
	 */
	public int sUniformModelViewPositionMatrixLocation = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap}
	 */
	public int sUniformTexture0Location = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap} <br>
	 * Related: {@link #uniform_color_find_00}
	 */
	public int uniform_color_find_location_00 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap}<br>
	 * Related: {@link #uniform_color_find_01}
	 */
	public int uniform_color_find_location_01 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap} <br>
	 * Related: {@link #uniform_color_replace_00}
	 */
	public int uniform_color_replace_location_00 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap} <br>
	 * Related: {@link #uniform_color_replace_01}
	 */
	public int uniform_color_replace_location_01 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap} <br>
	 * Related: {@link #uniform_color_eps_00}
	 */
	public int uniform_color_eps_location_00 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Location of related tag as used by the link and binding methods of
	 * {@link DualPaletteSwap} <br>
	 * Related: {@link #uniform_color_eps_01}
	 */
	public int uniform_color_eps_location_01 = ShaderProgramConstants.LOCATION_INVALID;
	/**
	 * Default value for epsilon R value <code>0.09f</code>
	 */
	public final float eps_default_r = 0.09f;
	/**
	 * Default value for epsilon G value <code>0.09f</code>
	 */
	public final float eps_default_g = 0.09f;
	/**
	 * Default value for epsilon B value <code>0.09f</code>
	 */
	public final float eps_default_b = 0.09f;
	/**
	 * Default value for epsilon A value <code>1.0f</code>
	 */
	public final float eps_default_a = 1.0f;
	/**
	 * First palette swap colour RGBA Values to find
	 */
	public float[] colour_find_value_00 = { 1.0f, 1.0f, 1.0f, 1.0f };
	/**
	 * Second palette swap colour RGBA Values to find
	 */
	public float[] colour_find_value_01 = { 0f, 0f, 0f, 1.0f };
	/**
	 * First palette swap colour replacement RGBA Values.
	 */
	public float[] colour_replace_value_00 = { 0f, 0f, 0f, 1.0f };
	/**
	 * Second palette swap colour replacement RGBA Values.
	 */
	public float[] colour_replace_value_01 = { 1.0f, 1.0f, 1.0f, 1.0f };
	/**
	 * First palette swap epsilon RGBA values
	 */
	public float[] epsilon_value_00 = { 0.09f, 0.09f, 0.09f, 1.0f };
	/**
	 * Second palette swap epsilon RGBA values
	 */
	public float[] epsilon_value_01 = { 0.09f, 0.09f, 0.09f, 1.0f };
	/**
	 * Is a second palette swap being used? <br>
	 * This is default <code>false</code> but will change to <code>true</code>
	 * when a second palette swap is set
	 */
	private boolean second_palette_swap_enabled = false;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Start creating a new fragment shader string. This creates the unique tags
	 */
	public DualPaletteSwapFragmentShaderCreator() {
		/*
		 * We append a randomUUID to each tag as we can have more than one DualPaletteSwap instance.
		 * If we use static variables then having more than one shader of the same shader in use 
		 * then it will cause quirks.
		 * Quirks like having two spites in a scene using their own instance of the shader, 
		 * while the shader does work correctly but on a pinch zoom scroll scene only one would zoom 
		 * and pinch while the other stayed static.
		 * These have to be referenced by the DualPaletteSwap shader.
		 */
		this.uniform_color_find_00 += UUID.randomUUID().toString();
		this.uniform_color_find_01 += UUID.randomUUID().toString();
		this.uniform_color_replace_00 += UUID.randomUUID().toString();
		this.uniform_color_replace_01 += UUID.randomUUID().toString();
		this.uniform_color_eps_00 += UUID.randomUUID().toString();
		this.uniform_color_eps_01 += UUID.randomUUID().toString();
		/*
		 * We replacing - in the UUID string with _ as - is subtraction and screws the shader up.
		 */
		this.uniform_color_find_00 = this.uniform_color_find_00.replace("-", "_");
		this.uniform_color_find_01 = this.uniform_color_find_01.replace("-", "_");
		this.uniform_color_replace_00 = this.uniform_color_replace_00.replace("-", "_");
		this.uniform_color_replace_01 = this.uniform_color_replace_01.replace("-", "_");
		this.uniform_color_eps_00 = this.uniform_color_eps_00.replace("-", "_");
		this.uniform_color_eps_01 = this.uniform_color_eps_01.replace("-", "_");
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * Returns a {@link String} of the fragment shader to use.
	 * 
	 * @return {@link String} of fragment shader GLSL ready for
	 *         {@link DualPaletteSwap}
	 */
	public String getFragmentShader() {
		return this.mFragmentShader;
	}

	/**
	 * Returns a {@link Boolean} determining if a second palette swap is in use.
	 * 
	 * @return {@link Boolean} <code>true</code> if a second palette swap is in
	 *         use. <code>false</code> if just the first palette swap
	 */
	public boolean isSecondPaletteSwapEnabled() {
		return this.second_palette_swap_enabled;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Set the first colour palette swap. Values as floating point value
	 * (value/255). <br>
	 * If a default value of -2 is used for any epsilon then the defaults is
	 * used for all epsilon values.
	 * 
	 * @param findR
	 *            {@link Float} of R value to be replaced
	 * @param findG
	 *            {@link Float} of G value to be replaced
	 * @param findB
	 *            {@link Float} of B value to be replaced
	 * @param findA
	 *            {@link Float} of A value to be replaced
	 * @param replaceR
	 *            {@link Float} of R value to swap with
	 * @param replaceG
	 *            {@link Float} of G value to swap with
	 * @param replaceB
	 *            {@link Float} of B value to swap with
	 * @param replaceA
	 *            {@link Float} of A value to swap with
	 * @param epsilonR
	 *            {@link Float} of epsilon R value to use as bounds or -2f for
	 *            default
	 * @param epsilonG
	 *            {@link Float} of epsilon G value to use as bounds or -2f for
	 *            default
	 * @param epsilonB
	 *            {@link Float} of epsilon B value to use as bounds or -2f for
	 *            default
	 * @param epsilonA
	 *            {@link Float} of epsilon A value to use as bounds or -2f for
	 *            default
	 */
	public void setFirstPaletteSwap(float findR, float findG, float findB, float findA, float replaceR, float replaceG,
			float replaceB, float replaceA, float epsilonR, float epsilonG, float epsilonB, float epsilonA) {
		this.colour_find_value_00 = new float[] { findR, findG, findB, findA };
		this.colour_replace_value_00 = new float[] { replaceR, replaceG, replaceB, replaceA };
		if (epsilonR == -2 || epsilonG == -2 || epsilonB == -2 || epsilonA == -2) {
			this.epsilon_value_00 = new float[] { this.eps_default_r, this.eps_default_g, this.eps_default_b,
					this.eps_default_a };
		} else {
			this.epsilon_value_00 = new float[] { epsilonR, epsilonG, epsilonB, epsilonA };
		}
		/*
		 * Second colour is enabled, so we compile with the second shader options.
		 * This will happen if we were to call a setFirstColour method after a call to setSecondColour
		 */
		if (this.second_palette_swap_enabled) {
			this.compileDualPaletteSwap();
		} else {
			this.compileSinglePaletteSwap();
		}
	}

	/**
	 * Set the first colour palette swap. Values as floating point value
	 * (value/255)
	 * 
	 * @param findRGBA
	 *            {@link Float} array of the RGBA value to be replaced. <br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i>
	 * @param replaceRGBA
	 *            {@link Float} array of the RGBA value to swap with, i.e the
	 *            new colour. <br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i>
	 * @param epsilonRGBA
	 *            {@link Float} array of the epsilon RGBA value. This broadens
	 *            the colour range to look for, helps take into account any tiny
	 *            changes. (So it gives us a bound to search between)<br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i> <br>
	 *            <i>Or pass <code>null</code></i> to stick to the default
	 *            epsilon of <i>Element [0] R = {@link #eps_default_r}</i>
	 *            <i>Element [1] G = {@link #eps_default_g}</i> <i>Element [2] B
	 *            = {@link #eps_default_b}</i> <i>Element [3] A =
	 *            {@link #eps_default_a}</i>
	 */
	public void setFirstPaletteSwap(float[] findRGBA, float[] replaceRGBA, float[] epsilonRGBA) {
		if (epsilonRGBA != null) {
			this.setFirstPaletteSwap(findRGBA[0], findRGBA[1], findRGBA[2], findRGBA[3], replaceRGBA[0],
					replaceRGBA[1], replaceRGBA[2], replaceRGBA[3], epsilonRGBA[0], epsilonRGBA[1], epsilonRGBA[2],
					epsilonRGBA[3]);
		} else {
			this.setFirstPaletteSwap(findRGBA[0], findRGBA[1], findRGBA[2], findRGBA[3], replaceRGBA[0],
					replaceRGBA[1], replaceRGBA[2], replaceRGBA[3], this.eps_default_r, this.eps_default_g,
					this.eps_default_b, this.eps_default_a);
		}
	}

	/**
	 * Set the second colour palette swap. Values as floating point value
	 * (value/255). <br>
	 * If a default value of -2 is used for any epsilon then the defaults is
	 * used for all epsilon values.
	 * 
	 * @param findR
	 *            {@link Float} of R value to be replaced
	 * @param findG
	 *            {@link Float} of G value to be replaced
	 * @param findB
	 *            {@link Float} of B value to be replaced
	 * @param findA
	 *            {@link Float} of A value to be replaced
	 * @param replaceR
	 *            {@link Float} of R value to swap with
	 * @param replaceG
	 *            {@link Float} of G value to swap with
	 * @param replaceB
	 *            {@link Float} of B value to swap with
	 * @param replaceA
	 *            {@link Float} of A value to swap with
	 * @param epsilonR
	 *            {@link Float} of epsilon R value to use as bounds or -2f for
	 *            default
	 * @param epsilonG
	 *            {@link Float} of epsilon G value to use as bounds or -2f for
	 *            default
	 * @param epsilonB
	 *            {@link Float} of epsilon B value to use as bounds or -2f for
	 *            default
	 * @param epsilonA
	 *            {@link Float} of epsilon A value to use as bounds or -2f for
	 *            default
	 */
	public void setSecondPaletteSwap(float findR, float findG, float findB, float findA, float replaceR,
			float replaceG, float replaceB, float replaceA, float epsilonR, float epsilonG, float epsilonB,
			float epsilonA) {
		this.colour_find_value_01 = new float[] { findR, findG, findB, findA };
		this.colour_replace_value_01 = new float[] { replaceR, replaceG, replaceB, replaceA };
		if (epsilonR == -2 || epsilonG == -2 || epsilonB == -2 || epsilonA == -2) {
			this.epsilon_value_01 = new float[] { this.eps_default_r, this.eps_default_g, this.eps_default_b,
					this.eps_default_a };
		} else {
			this.epsilon_value_01 = new float[] { epsilonR, epsilonG, epsilonB, epsilonA };
		}
		this.second_palette_swap_enabled = true;
		/*
		 * Here we're assuming the first colour has been set already.
		 */
		this.compileDualPaletteSwap();
	}

	/**
	 * Set the second colour palette swap. Values as floating point value
	 * (value/255)
	 * 
	 * @param findRGBA
	 *            {@link Float} array of the RGBA value to be replaced. <br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i>
	 * @param replaceRGBA
	 *            {@link Float} array of the RGBA value to swap with, i.e the
	 *            new colour. <br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i>
	 * @param epsilonRGBA
	 *            {@link Float} array of the epsilon RGBA value. This broadens
	 *            the colour range to look for, helps take into account any tiny
	 *            changes. (So it gives us a bound to search between)<br>
	 *            <i>Element [0] R</i> <i>- Element [1] R</i> <i> - Element [2]
	 *            B</i> <i>- Element [3] A</i> <br>
	 *            <i>Or pass <code>null</code></i> to stick to the default
	 *            epsilon of <i>Element [0] R = {@link #eps_default_r}</i>
	 *            <i>Element [1] G = {@link #eps_default_g}</i> <i>Element [2] B
	 *            = {@link #eps_default_b}</i> <i>Element [3] A =
	 *            {@link #eps_default_a}</i>
	 */
	public void setSecondPaletteSwap(float[] findRGBA, float[] replaceRGBA, float[] epsilonRGBA) {
		if (epsilonRGBA != null) {
			this.setSecondPaletteSwap(findRGBA[0], findRGBA[1], findRGBA[2], findRGBA[3], replaceRGBA[0],
					replaceRGBA[1], replaceRGBA[2], replaceRGBA[3], epsilonRGBA[0], epsilonRGBA[1], epsilonRGBA[2],
					epsilonRGBA[3]);
		} else {
			this.setSecondPaletteSwap(findRGBA[0], findRGBA[1], findRGBA[2], findRGBA[3], replaceRGBA[0],
					replaceRGBA[1], replaceRGBA[2], replaceRGBA[3], this.eps_default_r, this.eps_default_g,
					this.eps_default_b, this.eps_default_a);
		}
	}

	/**
	 * The compiles a single palette swap based on the first palette swap
	 * requirements.
	 */
	private void compileSinglePaletteSwap() {
		this.mFragmentShader = "precision lowp float;\n" + "uniform sampler2D "
				+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ";\n" + "varying mediump vec2 "
				+ ShaderProgramConstants.VARYING_TEXTURECOORDINATES + ";\n" + "uniform vec4 "
				+ this.uniform_color_find_00 + ";\n" + "uniform vec4 " + this.uniform_color_replace_00 + ";\n"
				+ "uniform vec4 " + this.uniform_color_eps_00 + ";\n" + "void main() {\n" + "	vec4 pixel = texture2D("
				+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ", " + ShaderProgramConstants.VARYING_TEXTURECOORDINATES
				+ ");\n" + "if(all(greaterThanEqual(pixel, " + this.uniform_color_find_00 + " - "
				+ this.uniform_color_eps_00 + ")) && all( lessThanEqual(pixel, " + this.uniform_color_find_00 + " + "
				+ this.uniform_color_eps_00 + ")))\n" + "	pixel = " + this.uniform_color_replace_00 + ";\n\n"
				+ "	gl_FragColor = pixel;\n" + "}\n";
	}

	/**
	 * The dual palette swap
	 */
	private void compileDualPaletteSwap() {
		this.mFragmentShader = "precision lowp float;\n" + "uniform sampler2D "
				+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ";\n" + "varying mediump vec2 "
				+ ShaderProgramConstants.VARYING_TEXTURECOORDINATES + ";\n" + "uniform vec4 "
				+ this.uniform_color_find_00 + ";\n" + "uniform vec4 " + this.uniform_color_replace_00 + ";\n"
				+ "uniform vec4 " + this.uniform_color_eps_00 + ";\n" + "uniform vec4 " + this.uniform_color_find_01
				+ ";\n" + "uniform vec4 " + this.uniform_color_replace_01 + ";\n" + "uniform vec4 "
				+ this.uniform_color_eps_01 + ";\n" + "void main() {\n" + "	vec4 pixel = texture2D("
				+ ShaderProgramConstants.UNIFORM_TEXTURE_0 + ", " + ShaderProgramConstants.VARYING_TEXTURECOORDINATES
				+ ");\n" + "if(all(greaterThanEqual(pixel, " + this.uniform_color_find_00 + " - "
				+ this.uniform_color_eps_00 + ")) && all( lessThanEqual(pixel, " + this.uniform_color_find_00 + " + "
				+ this.uniform_color_eps_00 + ")))\n" + "	pixel = " + this.uniform_color_replace_00 + ";\n\n"
				+ "if(all(greaterThanEqual(pixel, " + this.uniform_color_find_01 + " - " + this.uniform_color_eps_01
				+ ")) && all( lessThanEqual(pixel, " + this.uniform_color_find_01 + " + " + this.uniform_color_eps_01
				+ ")))\n" + "	pixel = " + this.uniform_color_replace_01 + ";\n\n" + "	gl_FragColor = pixel;\n" + "}\n";
	}

}
