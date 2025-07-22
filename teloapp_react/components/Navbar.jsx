import React from 'react';

// Íconos SVG simples para los botones
const MapIcon = () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>;
const ListIcon = () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>;


function Navbar({ activeView, setActiveView }) {
  return (
    <nav className="navbar">
      <button
        className={`nav-button ${activeView === 'map' ? 'active' : ''}`}
        onClick={() => setActiveView('map')}
      >
        <MapIcon />
        Mapa
      </button>
      <button
        className={`nav-button ${activeView === 'list' ? 'active' : ''}`}
        onClick={() => setActiveView('list')}
      >
        <ListIcon />
        Lista
      </button>
    </nav>
  );
}

export default Navbar;
