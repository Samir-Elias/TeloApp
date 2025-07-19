import { useState, useEffect } from 'react';
import { GoogleMap, useJsApiLoader, MarkerF, InfoWindowF } from '@react-google-maps/api';
import axios from 'axios';
import './App.css';

const center = {
  lat: -32.8908,
  lng: -68.8272
};

function App() {
  const [motels, setMotels] = useState([]);
  const [selectedMotel, setSelectedMotel] = useState(null);
  const [searchTerm, setSearchTerm] = useState(''); // <-- 1. Nuevo estado para el texto de búsqueda

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: import.meta.env.VITE_Maps_API_KEY
  });

  useEffect(() => {
    axios.get('http://localhost:8080/api/motels')
      .then(response => setMotels(response.data))
      .catch(error => console.error('Error al obtener los datos:', error));
  }, []);

  // 2. Filtramos los moteles basándonos en el texto de búsqueda
  const filteredMotels = motels.filter(motel =>
    motel.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleMarkerClick = (motel) => {
    setSelectedMotel(motel);
  };

  if (!isLoaded) return <div>Cargando...</div>;

  return (
    <div className="app-container">
      <div className="sidebar">
        <h2>Moteles Cercanos</h2>
        {/* 3. Input para la búsqueda */}
        <input
          type="text"
          placeholder="Buscar motel por nombre..."
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
          style={{ width: '90%', padding: '0.5rem', marginBottom: '1rem' }}
        />
        <ul>
          {/* 4. Usamos la lista filtrada para mostrar los moteles */}
          {filteredMotels.map(motel => (
            <li key={motel.id} onClick={() => handleMarkerClick(motel)}>
              {motel.name}
            </li>
          ))}
        </ul>
      </div>

      <div className="map-container">
        <GoogleMap
          mapContainerStyle={{ width: '100%', height: '100%' }}
          center={center}
          zoom={13}
        >
          {/* 4. Usamos la lista filtrada también para los marcadores */}
          {filteredMotels.map(motel => (
            <MarkerF
              key={motel.id}
              position={{ lat: motel.latitude, lng: motel.longitude }}
              title={motel.name}
              onClick={() => handleMarkerClick(motel)}
            />
          ))}

          {selectedMotel && (
            <InfoWindowF
              position={{ lat: selectedMotel.latitude, lng: selectedMotel.longitude }}
              onCloseClick={() => setSelectedMotel(null)}
            >
              <div>
                <h4>{selectedMotel.name}</h4>
                <p>{selectedMotel.address}</p>
              </div>
            </InfoWindowF>
          )}
        </GoogleMap>
      </div>
    </div>
  );
}

export default App;