import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
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
