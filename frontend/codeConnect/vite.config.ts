import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from "path"
import tailwindcss from 'tailwindcss'

export default defineConfig({
    plugins: [react()],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
        },
    },
    css: {
        postcss: {
            plugins: [tailwindcss()],
        },
    },
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8083',
                rewrite: (path) => path.replace(/^\/api/, '/api/v1'),
                changeOrigin: true,
                secure: false,
            },
        },
    },
});
