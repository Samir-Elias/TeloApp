import React, { useState } from 'react';
import { GoogleMap, MarkerF, InfoWindowF } from '@react-google-maps/api';

const containerStyle = {
  width: '100%',
  height: '100%'
};

const defaultMapOptions = {
  center: { lat: -32.8890, lng: -68.8450 },
  zoom: 12,
  options: {
    disableDefaultUI: true,
    zoomControl: true,
  }
};

function MapComponent({ motels, onMotelSelect }) {
  const [activeInfoWindow, setActiveInfoWindow] = useState(null);

  const handleMarkerClick = (motel) => {
    setActiveInfoWindow(motel);
  };

  return (
    <div className="map-container">
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={defaultMapOptions.center}
        zoom={defaultMapOptions.zoom}
        options={defaultMapOptions.options}
      >
        {motels.map(motel => (
          <MarkerF
            key={motel.id}
            position={{ lat: motel.latitude, lng: motel.longitude }}
            title={motel.name}
            onClick={() => handleMarkerClick(motel)}
          />
        ))}

        {activeInfoWindow && (
          <InfoWindowF
            position={{ lat: activeInfoWindow.latitude, lng: activeInfoWindow.longitude }}
            onCloseClick={() => setActiveInfoWindow(null)}
          >
            <div className="info-window-content">
              <h4 className="info-window-title">{activeInfoWindow.name}</h4>
              <button
                onClick={() => onMotelSelect(activeInfoWindow)}
                className="info-window-button"
              >
                Ver Detalles
              </button>
            </div>
          </InfoWindowF>
        )}
      </GoogleMap>
    </div>
  );
}

export default MapComponent;
