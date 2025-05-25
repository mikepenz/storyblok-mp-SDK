import { instantiate } from './storyblok.uninstantiated.mjs';

await wasmSetup;

instantiate({ skia: Module['asm'] });
