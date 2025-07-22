import React, { useState } from 'react';

function MotelListComponent({ motels, onMotelSelect }) {
  const [searchTerm, setSearchTerm] = useState('');
  // Aquí iría la lógica de filtros más compleja en el futuro

  const filteredMotels = motels.filter(motel =>
    motel.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="motel-list-container">
      <input
        type="text"
        placeholder="Buscar motel por nombre..."
        value={searchTerm}
        onChange={e => setSearchTerm(e.target.value)}
        className="search-input"
      />
      
      {/* Aquí se añadiría la UI de los filtros */}
      
      <h3>Resultados ({filteredMotels.length})</h3>
      <ul className="motel-list">
        {filteredMotels.length > 0 ? (
          filteredMotels.map(motel => (
            <li key={motel.id} onClick={() => onMotelSelect(motel)} className="motel-list-item">
              <span>{motel.name}</span>
              <span className="motel-rating">{motel.rating || 'N/A'} ⭐</span>
            </li>
          ))
        ) : (
          <li className="no-results">No se encontraron moteles.</li>
        )}
      </ul>
    </div>
  );
}

export default MotelListComponent;
