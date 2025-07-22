import { useState, useEffect } from 'react';
import { useJsApiLoader } from '@react-google-maps/api';
import axios from 'axios';

// Importar los nuevos componentes
import MapComponent from './components/MapComponent.jsx';
import MotelListComponent from './components/MotelListComponent.jsx';
import MotelDetailView from './components/MotelDetailView.jsx';
import Navbar from './components/Navbar.jsx';

import './App.css';

const API_BASE_URL = 'https://teloapp-backend.onrender.com'; // <-- URL de tu backend

function App() {
  const [motels, setMotels] = useState([]);
  const [selectedMotel, setSelectedMotel] = useState(null);
  const [activeView, setActiveView] = useState('map'); // 'map', 'list', o 'detail'

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY
  });

  useEffect(() => {
    axios.get(`${API_BASE_URL}/api/motels`)
      .then(response => setMotels(response.data))
      .catch(error => {
        console.error('Error al obtener los datos de moteles:', error);
        alert("Error al cargar los moteles. Revisa la consola para más detalles.");
      });
  }, []);

  const handleSelectMotel = (motel) => {
    setSelectedMotel(motel);
    setActiveView('detail');
  };

  const handleBack = () => {
    setSelectedMotel(null);
    setActiveView('map'); // Vuelve a la vista de mapa por defecto
  };

  const renderContent = () => {
    if (activeView === 'detail' && selectedMotel) {
      return <MotelDetailView motel={selectedMotel} onBack={handleBack} />;
    }
    if (activeView === 'map') {
      return <MapComponent motels={motels} onMotelSelect={handleSelectMotel} />;
    }
    if (activeView === 'list') {
      return <MotelListComponent motels={motels} onMotelSelect={handleSelectMotel} />;
    }
    return <MapComponent motels={motels} onMotelSelect={handleSelectMotel} />; // Vista por defecto
  };
  
  if (!isLoaded) {
    return <div className="loading-screen">Cargando TeloApp...</div>;
  }

  return (
    <div className="app-container">
      <main className="main-content">
        {renderContent()}
      </main>
      {activeView !== 'detail' && <Navbar activeView={activeView} setActiveView={setActiveView} />}
    </div>
  );
}

export default App;
