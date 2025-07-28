import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  
  // Configuración del servidor de desarrollo
  server: {
    port: 5173,
    host: true, // Necesario para Docker/contenedores
    cors: true,
    proxy: {
      // Proxy para desarrollo local (opcional)
      '/api': {
        target: process.env.VITE_API_URL || 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      }
    }
  },
  
  // Configuración de build
  build: {
    outDir: 'dist',
    sourcemap: false, // Deshabilitado para producción
    minify: 'terser',
    rollupOptions: {
      output: {
        manualChunks: {
          // Separar librerías grandes en chunks separados
          'google-maps': ['@react-google-maps/api'],
          'react-vendor': ['react', 'react-dom']
        }
      }
    },
    // Optimizaciones de build
    chunkSizeWarningLimit: 1000,
    target: 'es2015'
  },
  
  // Variables de entorno
  define: {
    // Asegurarse de que las variables de entorno estén disponibles
    __VITE_API_URL__: JSON.stringify(process.env.VITE_API_URL || 'http://localhost:8080'),
    __VITE_MAPS_API_KEY__: JSON.stringify(process.env.VITE_Maps_API_KEY || '')
  },
  
  // Optimizaciones de dependencias
  optimizeDeps: {
    include: [
      'react',
      'react-dom',
      '@react-google-maps/api'
    ]
  },
  
  // Configuración de preview (para testing de build)
  preview: {
    port: 4173,
    host: true
  }
})