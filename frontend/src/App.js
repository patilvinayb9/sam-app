import { useEffect, useState } from 'react';

function App() {
  const [greeting, setGreeting] = useState();

  useEffect(() => {
    fetch(process.env.REACT_APP_BASE_URL + '/api/sample')
      .then(res => res.json())
      .then(greeting => setGreeting(greeting.message))
  }, [setGreeting]);

  return (
    <div>Greeting: {greeting}</div>
  );
}

export default App;