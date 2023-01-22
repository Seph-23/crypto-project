import axios from 'axios';
import React, {useState, useEffect} from 'react';

const Child01 = ({name, onNameChange}) => {

  useEffect(() => {

    axios.post('http://localhost:8080/api/user2').then(data => {
      console.log(data);
    });
    
  }, []);

  return (
    <div>
      <h2>child</h2>
      <input value={name} onChange={onNameChange}></input>
      name: {name}
    </div>
  );
};

export default Child01;