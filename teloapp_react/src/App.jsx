import { useState, useEffect } from 'react';
import { GoogleMap, useJsApiLoader, MarkerF, InfoWindowF } from '@react-google-maps/api';
import './App.css';

const center = {
  lat: -32.8908,
  lng: -68.8272
};

function App() {
  const [motels, setMotels] = useState([]);
  const [selectedMotel, setSelectedMotel] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: import.meta.env.VITE_Maps_API_KEY
  });

  useEffect(() => {
    const fetchMotels = async () => {
      try {
        setLoading(true);
        
        // URL del API desde variables de entorno
        const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';
        
        // Intentar obtener datos del API real
        try {
          const response = await fetch(`${API_URL}/api/motels`);
          if (response.ok) {
            const data = await response.json();
            setMotels(data);
            setError(null);
            setLoading(false);
            return;
          }
        } catch (apiError) {
          console.warn('API no disponible, usando datos de prueba:', apiError);
        }
        
        // Fallback: usar datos simulados si el API no está disponible
        const mockMotels = [
          {
            id: 1,
            name: "Motel Paradise",
            address: "Av. San Martín 1234, Mendoza",
            latitude: -32.8895,
            longitude: -68.8258,
            phone: "+54 261 123-4567",
            rating: 4.5
          },
          {
            id: 2,
            name: "Hotel Romance",
            address: "Calle Las Heras 567, Mendoza",
            latitude: -32.8920,
            longitude: -68.8285,
            phone: "+54 261 234-5678",
            rating: 4.2
          },
          {
            id: 3,
            name: "Motel Luna",
            address: "Av. Mitre 890, Mendoza",
            latitude: -32.8880,
            longitude: -68.8290,
            phone: "+54 261 345-6789",
            rating: 4.0
          },
          {
            id: 4,
            name: "Hotel Dreams",
            address: "Calle Sarmiento 345, Mendoza",
            latitude: -32.8910,
            longitude: -68.8240,
            phone: "+54 261 456-7890",
            rating: 4.3
          },
          {
            id: 5,
            name: "Motel Estrella",
            address: "Av. Belgrano 678, Mendoza",
            latitude: -32.8925,
            longitude: -68.8275,
            phone: "+54 261 567-8901",
            rating: 3.9
          }
        ];

        // Simulamos un delay de red
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        setMotels(mockMotels);
        setError(null);
      } catch (err) {
        console.error('Error al obtener los datos:', err);
        setError('Error al cargar los moteles. Por favor, intenta nuevamente.');
      } finally {
        setLoading(false);
      }
    };

    fetchMotels();
  }, []);

  // Filtramos los moteles basándonos en el texto de búsqueda
  const filteredMotels = motels.filter(motel =>
    motel.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    motel.address.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleMarkerClick = (motel) => {
    setSelectedMotel(motel);
  };

  const handleMotelSelect = (motel) => {
    setSelectedMotel(motel);
    // Opcional: centrar el mapa en el motel seleccionado
  };

  const clearSearch = () => {
    setSearchTerm('');
  };

  if (!isLoaded) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Cargando Google Maps...</p>
      </div>
    );
  }

  return (
    <div className="app-container">
      <div className="sidebar">
        <div className="sidebar-header">
          <h2>Moteles Cercanos</h2>
          <p className="subtitle">Mendoza, Argentina</p>
        </div>
        
        <div className="search-container">
          <input
            type="text"
            placeholder="Buscar por nombre o dirección..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          {searchTerm && (
            <button 
              onClick={clearSearch}
              className="clear-search-btn"
              aria-label="Limpiar búsqueda"
            >
              ×
            </button>
          )}
        </div>

        <div className="results-info">
          <span className="results-count">
            {filteredMotels.length} de {motels.length} moteles
          </span>
        </div>

        {loading && (
          <div className="loading-state">
            <div className="loading-spinner small"></div>
            <p>Cargando moteles...</p>
          </div>
        )}

        {error && (
          <div className="error-state">
            <p className="error-message">{error}</p>
            <button 
              onClick={() => window.location.reload()} 
              className="retry-btn"
            >
              Reintentar
            </button>
          </div>
        )}

        {!loading && !error && (
          <ul className="motels-list">
            {filteredMotels.length === 0 ? (
              <li className="no-results">
                <p>No se encontraron moteles que coincidan con tu búsqueda.</p>
              </li>
            ) : (
              filteredMotels.map(motel => (
                <li 
                  key={motel.id} 
                  onClick={() => handleMotelSelect(motel)}
                  className={`motel-item ${selectedMotel?.id === motel.id ? 'selected' : ''}`}
                >
                  <div className="motel-info">
                    <h3 className="motel-name">{motel.name}</h3>
                    <p className="motel-address">{motel.address}</p>
                    {motel.phone && (
                      <p className="motel-phone">{motel.phone}</p>
                    )}
                    {motel.rating && (
                      <div className="motel-rating">
                        <span className="stars">
                          {'★'.repeat(Math.floor(motel.rating))}
                          {'☆'.repeat(5 - Math.floor(motel.rating))}
                        </span>
                        <span className="rating-value">({motel.rating})</span>
                      </div>
                    )}
                  </div>
                </li>
              ))
            )}
          </ul>
        )}
      </div>

      <div className="map-container">
        <GoogleMap
          mapContainerStyle={{ width: '100%', height: '100%' }}
          center={center}
          zoom={13}
          options={{
            zoomControl: true,
            streetViewControl: false,
            mapTypeControl: false,
            fullscreenControl: true,
          }}
        >
          {filteredMotels.map(motel => (
            <MarkerF
              key={motel.id}
              position={{ lat: motel.latitude, lng: motel.longitude }}
              title={motel.name}
              onClick={() => handleMarkerClick(motel)}
              animation={selectedMotel?.id === motel.id ? window.google?.maps?.Animation?.BOUNCE : null}
            />
          ))}

          {selectedMotel && (
            <InfoWindowF
              position={{ lat: selectedMotel.latitude, lng: selectedMotel.longitude }}
              onCloseClick={() => setSelectedMotel(null)}
            >
              <div className="info-window">
                <h4 className="info-title">{selectedMotel.name}</h4>
                <p className="info-address">{selectedMotel.address}</p>
                {selectedMotel.phone && (
                  <p className="info-phone">
                    <strong>Teléfono:</strong> {selectedMotel.phone}
                  </p>
                )}
                {selectedMotel.rating && (
                  <div className="info-rating">
                    <span className="stars">
                      {'★'.repeat(Math.floor(selectedMotel.rating))}
                      {'☆'.repeat(5 - Math.floor(selectedMotel.rating))}
                    </span>
                    <span className="rating-value">({selectedMotel.rating})</span>
                  </div>
                )}
              </div>
            </InfoWindowF>
          )}
        </GoogleMap>
      </div>
    </div>
  );
}

export default App;