#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform float date;
uniform vec2 resolution;
uniform float loaded;

varying vec2 vTextureCoord;

float stuff(float c1, float size, float radius, float time, float dir){
	float c = c1;
	
	float h = size / 2.0;
	float r = 0.8660254 * size;
	size *= dir;
	
	vec2 pos = gl_FragCoord.xy - resolution.xy / 2.0;
	pos += smoothstep(0.0, 1.0, time) * vec2(0.0, -size);
	pos += smoothstep(1.0, 2.0, time) * vec2(-r, -h);
	pos += smoothstep(2.0, 3.0, time) * vec2(r, -h);
	pos += smoothstep(3.0, 4.0, time) * vec2(r, h);
	pos += smoothstep(4.0, 5.0, time) * vec2(-r, h);
	pos += smoothstep(5.0, 6.0, time) * vec2(0.0, size);
	c = min(c, smoothstep(radius - 2.0, radius, length(pos)));
	
	pos = gl_FragCoord.xy - resolution.xy / 2.0;
	pos += smoothstep(0.0, 1.0, time) * vec2(0.0, -size);
	pos += smoothstep(1.0, 2.0, time) * vec2(r, h);
	pos += smoothstep(2.0, 3.0, time) * vec2(r, -h);
	pos += smoothstep(3.0, 4.0, time) * vec2(-r, -h);
	pos += smoothstep(4.0, 5.0, time) * vec2(-r, h);
	pos += smoothstep(5.0, 6.0, time) * vec2(0.0, size);
	c = min(c, smoothstep(radius - 2.0, radius, length(pos)));
	
	pos = gl_FragCoord.xy - resolution.xy / 2.0;
	pos += smoothstep(0.0, 1.0, time) * vec2(0.0, -size);
	pos += smoothstep(1.0, 2.0, time) * vec2(r, h);
	pos += smoothstep(2.0, 3.0, time) * vec2(-r, h);
	pos += smoothstep(3.0, 4.0, time) * vec2(-r, -h);
	pos += smoothstep(4.0, 5.0, time) * vec2(r, -h);
	pos += smoothstep(5.0, 6.0, time) * vec2(0.0, size);
	c = min(c, smoothstep(radius - 2.0, radius, length(pos)));
	
	pos = gl_FragCoord.xy - resolution.xy / 2.0;
	pos += smoothstep(0.0, 1.0, time) * vec2(0.0, -size);
	pos += smoothstep(1.0, 2.0, time) * vec2(-r, -h);
	pos += smoothstep(2.0, 3.0, time) * vec2(-r, h);
	pos += smoothstep(3.0, 4.0, time) * vec2(r, h);
	pos += smoothstep(4.0, 5.0, time) * vec2(r, -h);
	pos += smoothstep(5.0, 6.0, time) * vec2(0.0, size);
	c = min(c, smoothstep(radius - 2.0, radius, length(pos)));
	
	return c;
}

void main(void){
	float time = fract(date / 3.0) * 6.0;
	vec2 pos;
	float radius = 50.0;
	float c = 1.0;
	float transitionTime = 0.4;
	
	float size = 50.0;
	
	
	radius -= smoothstep(0.0, transitionTime, time) * 10.0;
	radius -= smoothstep(1.0, 1.0 + transitionTime, time) * 10.0;
	radius -= smoothstep(2.0, 2.0 + transitionTime, time) * 10.0;
	radius += smoothstep(4.0 - transitionTime, 4.0, time) * 10.0;
	radius += smoothstep(5.0 - transitionTime, 5.0, time) * 10.0;
	radius += smoothstep(5.4 - transitionTime, 6.0, time) * 10.0;
	
	c = stuff(c, size, radius, time, 1.0);
	c = stuff(c, size, radius, time, -1.0);
	
	
	pos = vec2(1.0, 1.0) - gl_FragCoord.xy / resolution;
	
	float bg = 0.2;
	vec3 background = vec3(bg, bg, bg);
	
	
	float g = length(pos);
	
	vec3 foreground = vec3(g, g, g);

	vec3 color = background * c + foreground * (1.0 - c);
	
	if(pos.x > 1.0 - loaded){
		color = 1.0 - color;
	}
	
	gl_FragColor = vec4(color, 1.0);
}