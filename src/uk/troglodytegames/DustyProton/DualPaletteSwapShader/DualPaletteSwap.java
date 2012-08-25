package uk.troglodytegames.DustyProton.DualPaletteSwapShader;

import org.andengine.opengl.shader.PositionTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramException;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

import android.opengl.GLES20;

/**
 * A Dual palette swap {@link ShaderProgram} which searches for an RGBA colour
 * value then replaces it. <br>
 * First you must create and setup an
 * {@link DualPaletteSwapFragmentShaderCreator} object, with either one or two
 * colour palette swaps and pass the object into the this object constructor. <br>
 * See note comment section for thanks and where the shader is taken from.
 * 
 * @author Paul Robinson
 * 
 */
public class DualPaletteSwap extends ShaderProgram {
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
	 * {@link DualPaletteSwapFragmentShaderCreator} creator object
	 */
	public DualPaletteSwapFragmentShaderCreator creator;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Create a new {@link DualPaletteSwap} shader, which can one or two palette
	 * swaps
	 * 
	 * @param pCreator
	 *            {@link DualPaletteSwapFragmentShaderCreator} object with
	 *            palette swap values and the fragment shader string
	 */
	public DualPaletteSwap(DualPaletteSwapFragmentShaderCreator pCreator) {
		super(PositionTextureCoordinatesShaderProgram.VERTEXSHADER, pCreator.getFragmentShader());
		this.creator = pCreator;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void link(final GLState pGLState) throws ShaderProgramLinkException {
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION,
				ShaderProgramConstants.ATTRIBUTE_POSITION);
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION,
				ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);

		super.link(pGLState);
		/*
		 * Get the locations of related tags in the shader.
		 */
		this.creator.sUniformModelViewPositionMatrixLocation = this
				.getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
		this.creator.sUniformTexture0Location = this.getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);

		this.creator.uniform_color_find_location_00 = this.getUniformLocation(this.creator.uniform_color_find_00);
		this.creator.uniform_color_replace_location_00 = this.getUniformLocation(this.creator.uniform_color_replace_00);
		this.creator.uniform_color_eps_location_00 = this.getUniformLocation(this.creator.uniform_color_eps_00);

		if (this.creator.isSecondPaletteSwapEnabled()) {
			this.creator.uniform_color_find_location_01 = this.getUniformLocation(this.creator.uniform_color_find_01);
			this.creator.uniform_color_replace_location_01 = this
					.getUniformLocation(this.creator.uniform_color_replace_01);
			this.creator.uniform_color_eps_location_01 = this.getUniformLocation(this.creator.uniform_color_eps_01);
		}
	}

	@Override
	public void bind(final GLState pGLState, final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);
		super.bind(pGLState, pVertexBufferObjectAttributes);

		GLES20.glUniformMatrix4fv(this.creator.sUniformModelViewPositionMatrixLocation, 1, false,
				pGLState.getModelViewProjectionGLMatrix(), 0);
		GLES20.glUniform1i(this.creator.sUniformTexture0Location, 0);
		GLES20.glUniform1i(this.creator.sUniformTexture0Location, 0);
		GLES20.glUniform4f(this.creator.uniform_color_find_location_00, this.creator.colour_find_value_00[0],
				this.creator.colour_find_value_00[1], this.creator.colour_find_value_00[2],
				this.creator.colour_find_value_00[3]);
		GLES20.glUniform4f(this.creator.uniform_color_replace_location_00, this.creator.colour_replace_value_00[0],
				this.creator.colour_replace_value_00[1], this.creator.colour_replace_value_00[2],
				this.creator.colour_replace_value_00[3]);
		GLES20.glUniform4f(this.creator.uniform_color_eps_location_00, this.creator.epsilon_value_00[0],
				this.creator.epsilon_value_00[1], this.creator.epsilon_value_00[2], this.creator.epsilon_value_00[3]);

		if (this.creator.isSecondPaletteSwapEnabled()) {
			GLES20.glUniform4f(this.creator.uniform_color_find_location_01, this.creator.colour_find_value_01[0],
					this.creator.colour_find_value_01[1], this.creator.colour_find_value_01[2],
					this.creator.colour_find_value_01[3]);
			GLES20.glUniform4f(this.creator.uniform_color_replace_location_01, this.creator.colour_replace_value_01[0],
					this.creator.colour_replace_value_01[1], this.creator.colour_replace_value_01[2],
					this.creator.colour_replace_value_01[3]);
			GLES20.glUniform4f(this.creator.uniform_color_eps_location_01, this.creator.epsilon_value_01[0],
					this.creator.epsilon_value_01[1], this.creator.epsilon_value_01[2],
					this.creator.epsilon_value_01[3]);
		}
	}

	@Override
	public void unbind(final GLState pGLState) throws ShaderProgramException {
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);
		super.unbind(pGLState);
	}
}
