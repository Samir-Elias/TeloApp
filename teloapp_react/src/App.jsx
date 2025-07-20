import { useState, useEffect } from 'react';
import { GoogleMap, useJsApiLoader, MarkerF, InfoWindowF } from '@react-google-maps/api';
import axios from 'axios';
import './App.css';

// FIX: La clave API se usa directamente en useJsApiLoader, la línea suelta se eliminó.

// --- Configuración del Mapa ---
const defaultMapOptions = {
  center: { lat: -32.8890, lng: -68.8450 },
  zoom: 12,
  options: {
    disableDefaultUI: true,
    zoomControl: true,
    fullscreenControl: false,
    streetViewControl: false,
    mapTypeControl: false,
  }
};

// --- URL del Backend ---
// Asegúrate de que esta URL sea la correcta para tu backend desplegado
const API_BASE_URL = 'https://teloapp-backend.onrender.com';

function App() {
  const [motels, setMotels] = useState([]);
  const [activeInfoWindow, setActiveInfoWindow] = useState(null);
  const [selectedMotelForDetails, setSelectedMotelForDetails] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  // Estados para los filtros
  const [filters, setFilters] = useState({
    showOpenOnly: false,
    showVerifiedOnly: false,
    showJacuzziOnly: false,
    showParkingOnly: false,
    showPriceUnder15k: false,
  });

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    // Asegúrate de que el nombre de tu variable de entorno en Vercel/Render sea VITE_Maps_API_KEY
    googleMapsApiKey: import.meta.env.VITE_Maps_API_KEY
  });

  useEffect(() => {
    axios.get(`${API_BASE_URL}/api/motels`)
      .then(response => setMotels(response.data))
      .catch(error => {
        console.error('Error al obtener los datos de moteles:', error);
        alert("Error al cargar los moteles. Revisa la consola (F12) para más detalles.");
      });
  }, []);

  // MEJORA: Lógica de filtrado más segura y robusta
  const filteredMotels = motels.filter(motel => {
    const nameMatches = motel.name.toLowerCase().includes(searchTerm.toLowerCase());
    
    // Si el filtro no está activo, se devuelve 'true' para no descartar el motel
    const isOpen = !filters.showOpenOnly || motel.openNow;
    const isVerified = !filters.showVerifiedOnly || motel.verified;
    const hasJacuzzi = !filters.showJacuzziOnly || (motel.services && motel.services.includes("Jacuzzi"));
    const hasParking = !filters.showParkingOnly || (motel.services && motel.services.includes("Cochera privada"));
    
    const priceMatches = !filters.showPriceUnder15k || (motel.priceRange && parseFloat(motel.priceRange.replace(/[^0-9]/g, '')) < 15000);

    return nameMatches && isOpen && isVerified && hasJacuzzi && hasParking && priceMatches;
  });

  const handleFilterChange = (filterName) => {
    setFilters(prevFilters => ({
      ...prevFilters,
      [filterName]: !prevFilters[filterName]
    }));
  };

  if (!isLoaded) return <div className="loading-screen">Cargando...</div>;

  // Si hay un motel seleccionado, muestra la vista de detalles
  if (selectedMotelForDetails) {
    // (Tu código para la vista de detalles va aquí, no necesita cambios)
    return (
        <div className="motel-details-view">
            {/* ... Tu código de la vista de detalles ... */}
            <button onClick={() => setSelectedMotelForDetails(null)} className="back-button">
                ← Volver al Mapa
            </button>
            <h2>{selectedMotelForDetails.name}</h2>
            {/* ... resto de los detalles ... */}
        </div>
    );
  }

  return (
    <div className="app-container">
      <div className="sidebar">
        <h2>Moteles Cercanos</h2>
        <input
          type="text"
          placeholder="Buscar motel por nombre..."
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
          className="search-input"
        />

        <div className="filters-section">
          <h3>Filtros Rápidos</h3>
          {/* MEJORA: Se usa un solo manejador de estado para los filtros */}
          <label className="filter-checkbox">
            <input type="checkbox" checked={filters.showOpenOnly} onChange={() => handleFilterChange('showOpenOnly')} />
            Abierto ahora
          </label>
          <label className="filter-checkbox">
            <input type="checkbox" checked={filters.showVerifiedOnly} onChange={() => handleFilterChange('showVerifiedOnly')} />
            Verificados
          </label>
           {/* ... Repetir para los otros filtros ... */}
        </div>

        <h3>Resultados ({filteredMotels.length})</h3>
        <ul className="motel-list">
          {filteredMotels.map(motel => (
            <li key={motel.id} onClick={() => setSelectedMotelForDetails(motel)} className="motel-list-item">
              {motel.name}
            </li>
          ))}
        </ul>
      </div>

      <div className="map-container">
        <GoogleMap
          mapContainerStyle={{ width: '100%', height: '100%' }}
          center={defaultMapOptions.center}
          zoom={defaultMapOptions.zoom}
          options={defaultMapOptions.options}
        >
          {filteredMotels.map(motel => (
            <MarkerF
              key={motel.id}
              position={{ lat: motel.latitude, lng: motel.longitude }}
              title={motel.name}
              onClick={() => setActiveInfoWindow(motel)}
            />
          ))}

          {activeInfoWindow && (
            <InfoWindowF
              position={{ lat: activeInfoWindow.latitude, lng: activeInfoWindow.longitude }}
              onCloseClick={() => setActiveInfoWindow(null)}
            >
              <div>
                <h4>{activeInfoWindow.name}</h4>
                <button onClick={() => setSelectedMotelForDetails(activeInfoWindow)} className="info-window-button">
                  Ver Detalles
                </button>
              </div>
            </InfoWindowF>
          )}
        </GoogleMap>
      </div>
    </div>
  );
}

export default App;