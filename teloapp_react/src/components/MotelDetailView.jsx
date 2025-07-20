import React from 'react';

function MotelDetailView({ motel, onBack }) {
  if (!motel) return null;

  return (
    <div className="motel-details-view">
      <button onClick={onBack} className="back-button">
        ← Volver
      </button>
      <h2>{motel.name}</h2>
      <p><strong>Dirección:</strong> {motel.address}</p>
      <p><strong>Teléfono:</strong> {motel.phone}</p>
      <p><strong>Descripción:</strong> {motel.description}</p>
      <p><strong>Calificación:</strong> {motel.rating || 'N/A'} ⭐</p>
      <p><strong>Rango de Precios:</strong> {motel.priceRange || 'No disponible'}</p>
      <p><strong>Servicios:</strong> {motel.services ? motel.services.join(', ') : 'No disponibles'}</p>
      <p className={motel.openNow ? 'status-open' : 'status-closed'}>
        {motel.openNow ? 'Abierto Ahora' : 'Cerrado'}
      </p>
      {/* Aquí iría la galería de fotos y opciones de reserva */}
    </div>
  );
}

export default MotelDetailView;
