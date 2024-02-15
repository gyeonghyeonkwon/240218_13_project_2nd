import './App.css';
import './index.css';
import root from './router/root';

function App() {
  return (
    <RouterProvider router={root}></RouterProvider>//라우터 , root.js

  );
}

export default App;
