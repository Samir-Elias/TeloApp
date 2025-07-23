import { useState, useEffect } from 'react';
import { useJsApiLoader } from '@react-google-maps/api';
import axios from 'axios';

// Importar los componentes
import MapComponent from './components/MapComponent.jsx';
import MotelListComponent from './components/MotelListComponent.jsx';
import MotelDetailView from './components/MotelDetailView.jsx';
import Navbar from './components/Navbar.jsx';

import './App.css';

// --- CAMBIO CLAVE ---
// La URL del backend ahora es dinámica. Usará localhost para desarrollo
// y la URL de Render para producción.
const API_BASE_URL = import.meta.env.DEV 
  ? 'http://localhost:8080' 
  : 'https://teloapp-backend.onrender.com';

function App() {
  const [motels, setMotels] = useState([]);
  const [selectedMotel, setSelectedMotel] = useState(null);
  const [activeView, setActiveView] = useState('map'); // 'map', 'list', o 'detail'
  
  const [filters, setFilters] = useState({
    searchTerm: '',
    showOpenOnly: false,
    showVerifiedOnly: false,
    showJacuzziOnly: false,
    showParkingOnly: false,
  });

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY
  });

  useEffect(() => {
    // --- INICIO DE LA SECCIÓN COMENTADA PARA PAUSAR LA CARGA DE MOTELES ---
    // La llamada a la API ahora usa la URL dinámica
    // axios.get(`${API_BASE_URL}/api/motels`)
    //   .then(response => setMotels(response.data))
    //   .catch(error => {
    //     console.error('Error al obtener los datos de moteles:', error);
    //     alert("Error al cargar los moteles. Asegúrate de que el backend esté corriendo en localhost:8080 y revisa la consola.");
    //   });
    // --- FIN DE LA SECCIÓN COMENTADA ---
  }, []);

  const filteredMotels = motels.filter(motel => {
    const nameMatches = motel.name.toLowerCase().includes(filters.searchTerm.toLowerCase());
    const isOpen = !filters.showOpenOnly || motel.openNow === true;
    const isVerified = !filters.showVerifiedOnly || motel.verified === true;
    const hasJacuzzi = !filters.showJacuzziOnly || (motel.services && motel.services.includes("Jacuzzi"));
    const hasParking = !filters.showParkingOnly || (motel.services && motel.services.includes("Cochera privada"));

    return nameMatches && isOpen && isVerified && hasJacuzzi && hasParking;
  });

  const handleSelectMotel = (motel) => {
    setSelectedMotel(motel);
    setActiveView('detail');
  };

  const handleBack = () => {
    setSelectedMotel(null);
    setActiveView('map');
  };

  const renderContent = () => {
    if (activeView === 'detail' && selectedMotel) {
      return <MotelDetailView motel={selectedMotel} onBack={handleBack} />;
    }
    if (activeView === 'map') {
      return <MapComponent motels={filteredMotels} onMotelSelect={handleSelectMotel} />;
    }
    if (activeView === 'list') {
      return (
        <MotelListComponent
          motels={filteredMotels}
          filters={filters}
          setFilters={setFilters}
          onMotelSelect={handleSelectMotel}
        />
      );
    }
    return <MapComponent motels={filteredMotels} onMotelSelect={handleSelectMotel} />;
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