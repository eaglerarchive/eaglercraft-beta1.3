package net.lax1dude.eaglercraft.glemu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import net.minecraft.client.Minecraft;

import static net.lax1dude.eaglercraft.glemu.EaglerAdapterGL30.*;

public class EffectPipelineFXAA {

	private static FramebufferGL framebuffer = null;
	private static RenderbufferGL framebuffer_depth = null;

	private static ProgramGL fxaaProgram = null;
	private static TextureGL fxaaSourceTexture = null;
	private static UniformGL fxaaScreenSize = null;
	
	private static BufferArrayGL renderQuadArray = null;
	private static BufferGL renderQuadBuffer;

	public static int displayWidth = -1;
	public static int displayHeight = -1;
	public static int width = -1;
	public static int height = -1;

	private static int[] originalViewport = new int[4];
	
	private static boolean enabled = false;
	
	private static void initFXAA() {
		renderQuadArray = _wglCreateVertexArray();
		renderQuadBuffer = _wglCreateBuffer();
		
		IntBuffer upload = (isWebGL ? IntBuffer.wrap(new int[12]) : ByteBuffer.allocateDirect(12 << 2).order(ByteOrder.nativeOrder()).asIntBuffer());
		upload.put(Float.floatToRawIntBits(0.0f)); upload.put(Float.floatToRawIntBits(0.0f));
		upload.put(Float.floatToRawIntBits(0.0f)); upload.put(Float.floatToRawIntBits(1.0f));
		upload.put(Float.floatToRawIntBits(1.0f)); upload.put(Float.floatToRawIntBits(0.0f));
		upload.put(Float.floatToRawIntBits(0.0f)); upload.put(Float.floatToRawIntBits(1.0f));
		upload.put(Float.floatToRawIntBits(1.0f)); upload.put(Float.floatToRawIntBits(1.0f));
		upload.put(Float.floatToRawIntBits(1.0f)); upload.put(Float.floatToRawIntBits(0.0f));
		upload.flip();
		
		_wglBindVertexArray(renderQuadArray);
		_wglBindBuffer(_wGL_ARRAY_BUFFER, renderQuadBuffer);
		_wglBufferData0(_wGL_ARRAY_BUFFER, upload, _wGL_STATIC_DRAW);
		_wglEnableVertexAttribArray(0);
		_wglVertexAttribPointer(0, 2, _wGL_FLOAT, false, 8, 0);
		
		ShaderGL pvert_shader = _wglCreateShader(_wGL_VERTEX_SHADER);

		_wglShaderSource(pvert_shader, _wgetShaderHeader() + "\n" + fileContents("/glsl/pvert.glsl"));
		_wglCompileShader(pvert_shader);

		if (!_wglGetShaderCompiled(pvert_shader)) System.err.println(("\n" + _wglGetShaderInfoLog(pvert_shader)).replace("\n", "\n[/glsl/pvert.glsl] ") + "\n");
		
		ShaderGL fxaa_shader = _wglCreateShader(_wGL_FRAGMENT_SHADER);
		_wglShaderSource(fxaa_shader, _wgetShaderHeader() + "\n" + fileContents("/glsl/fxaa.glsl"));
		_wglCompileShader(fxaa_shader);
		
		if (!_wglGetShaderCompiled(fxaa_shader)) System.err.println(("\n" + _wglGetShaderInfoLog(fxaa_shader)).replace("\n", "\n[/glsl/fxaa.glsl] ") + "\n");
		
		fxaaProgram = _wglCreateProgram();
		_wglAttachShader(fxaaProgram, pvert_shader);
		_wglAttachShader(fxaaProgram, fxaa_shader);
		_wglLinkProgram(fxaaProgram);
		_wglDetachShader(fxaaProgram, pvert_shader);
		_wglDetachShader(fxaaProgram, fxaa_shader);
		_wglDeleteShader(pvert_shader);
		_wglDeleteShader(fxaa_shader);
		
		if(!_wglGetProgramLinked(fxaaProgram)) {
			System.err.println(("\n"+_wglGetProgramInfoLog(fxaaProgram)).replace("\n", "\n[/glsl/fxaa.glsl][LINKER] ") + "\n");
			fxaaProgram = null;
			throw new RuntimeException("Invalid shader code");
		}
		
		_wglUseProgram(fxaaProgram);
		
		UniformGL c = _wglGetUniformLocation(fxaaProgram, "f_color");
		if(c != null) _wglUniform1i(c, 0);
		
		fxaaScreenSize = _wglGetUniformLocation(fxaaProgram, "screenSize");
		
		framebuffer = _wglCreateFramebuffer();
		fxaaSourceTexture = _wglGenTextures();
		
		_wglBindTexture(_wGL_TEXTURE_2D, fxaaSourceTexture);
		_wglTexParameteri(_wGL_TEXTURE_2D, _wGL_TEXTURE_MAG_FILTER, _wGL_NEAREST);
		_wglTexParameteri(_wGL_TEXTURE_2D, _wGL_TEXTURE_MIN_FILTER, _wGL_NEAREST);
		_wglTexParameteri(_wGL_TEXTURE_2D, _wGL_TEXTURE_WRAP_S, _wGL_CLAMP);
		_wglTexParameteri(_wGL_TEXTURE_2D, _wGL_TEXTURE_WRAP_T, _wGL_CLAMP);
		_wglTexImage2D(_wGL_TEXTURE_2D, 0, _wGL_RGB8, width, height, 0, _wGL_RGB, _wGL_UNSIGNED_BYTE, (ByteBuffer)null);
		
		framebuffer_depth = _wglCreateRenderBuffer();
		_wglBindRenderbuffer(framebuffer_depth);
		_wglRenderbufferStorage(_wGL_DEPTH_COMPONENT32F, width, height);
		
		_wglBindFramebuffer(_wGL_FRAMEBUFFER, framebuffer);
		_wglFramebufferTexture2D(_wGL_COLOR_ATTACHMENT0, fxaaSourceTexture);
		_wglFramebufferRenderbuffer(_wGL_DEPTH_ATTACHMENT, framebuffer_depth);
	}

	public static void beginPipelineRender() {
		if(displayWidth <= 0 || displayHeight <= 0) {
			return;
		}
		int mode = Minecraft.getMinecraft().gameSettings.antialiasing;
		enabled = false;
		if(mode == 1) enabled = Minecraft.getMinecraft().gameSettings.fancyGraphics;
		if(mode == 2) enabled = true;
		if(!enabled) {
			return;
		}
		//_wglGetParameter(_wGL_VIEWPORT, 4, originalViewport);
		if (displayWidth != width || displayHeight != height) {
			width = displayWidth;
			height = displayHeight;
			originalViewport[0] = 0;
			originalViewport[1] = 0;
			originalViewport[2] = width;
			originalViewport[3] = height;
			if(fxaaProgram == null) {
				initFXAA();
			}else {
				_wglBindTexture(_wGL_TEXTURE_2D, fxaaSourceTexture);
				_wglTexImage2D(_wGL_TEXTURE_2D, 0, _wGL_RGB8, width, height, 0, _wGL_RGB, _wGL_UNSIGNED_BYTE, (ByteBuffer)null);
				_wglBindRenderbuffer(framebuffer_depth);
				_wglRenderbufferStorage(_wGL_DEPTH_COMPONENT32F, width, height);
			}
		}
		_wglBindFramebuffer(_wGL_FRAMEBUFFER, framebuffer);
		_wglViewport(0, 0, width, height);
	}

	public static void endPipelineRender() {
		if(!enabled || displayWidth <= 0 || displayHeight <= 0) {
			return;
		}
		_wglBindFramebuffer(_wGL_FRAMEBUFFER, null);
		_wglClear(_wGL_COLOR_BUFFER_BIT | _wGL_DEPTH_BUFFER_BIT);
		_wglViewport(originalViewport[0], originalViewport[1], originalViewport[2], originalViewport[3]);
		_wglActiveTexture(_wGL_TEXTURE0);
		_wglBindTexture(_wGL_TEXTURE_2D, fxaaSourceTexture);
		_wglDisable(_wGL_DEPTH_TEST);
		_wglDisable(_wGL_CULL_FACE);
		_wglDepthMask(false);
		_wglUseProgram(fxaaProgram);
		_wglUniform2f(fxaaScreenSize, width, height);
		_wglBindVertexArray(renderQuadArray);
		_wglDrawArrays(_wGL_TRIANGLES, 0, 6);
		_wglEnable(_wGL_DEPTH_TEST);
		_wglDepthMask(true);
	}
	
}
