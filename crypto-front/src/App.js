import logo from './logo.svg';
import './App.css';
import Child01 from './components/Child01';
import React from 'react';

function App() {
  const [name, setName] = React.useState('');

  const onNameChange = (e) => {
    setName(e.target.value);
  };

  return (
    <>
      hello
      <Child01 name={name} onNameChange={onNameChange}></Child01>
    </>
  );
}

export default App;
