import flowbitePlugin from 'flowbite/plugin'
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./src/*.{js,jsx,ts,tsx}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      colors: {
        primary: {"50":"#eff6ff","100":"#dbeafe","200":"#bfdbfe","300":"#93c5fd","400":"#60a5fa","500":"#3b82f6","600":"#2563eb","700":"#1d4ed8","800":"#1e40af","900":"#1e3a8a","950":"#172554"},
        'nutmeg': '#854632',
        'dark-raspberry': '#7b284f',
        'dark-raspberry-700': '#541b36',
        // Neutral
        'rose-white': '#fff5fa',
        'eggshell': '#f3e6d8',
        'light-gray': '#e4ded8',
        'wenge-brown': '#5f574e',
        'dark-charcoal': '#302d2c',
        'water': '#327185',
        'water-700': '#22596a',
        'pine': '#287B54',
        'pine-700': '#1e5f40',
        'pale-mint': '#F5FFFA',
        'perriwinkle': '#D8E5F3',
        'blue-gray': '#D8DEE4',
        'wenge-gray': '#4E565F'
      },
      fontFamily: {
        fancy: ['"Young Serif"', 'sans-serif'],
        outfit: ['"Outfit"', 'serif']
      }
    },
    fontFamily: {
      'body': [
        'Inter', 
        'ui-sans-serif', 
        'system-ui', 
        '-apple-system', 
        'system-ui', 
        'Segoe UI', 
        'Roboto', 
        'Helvetica Neue', 
        'Arial', 
        'Noto Sans', 
        'sans-serif', 
        'Apple Color Emoji', 
        'Segoe UI Emoji', 
        'Segoe UI Symbol', 
        'Noto Color Emoji'
      ],
      'sans': [
        'Inter', 
        'ui-sans-serif', 
        'system-ui', 
        '-apple-system', 
        'system-ui', 
        'Segoe UI', 
        'Roboto', 
        'Helvetica Neue', 
        'Arial', 
        'Noto Sans', 
        'sans-serif', 
        'Apple Color Emoji', 
        'Segoe UI Emoji', 
        'Segoe UI Symbol', 
        'Noto Color Emoji'
      ]
    }
  },
  plugins: [
    flowbitePlugin
  ],
}

