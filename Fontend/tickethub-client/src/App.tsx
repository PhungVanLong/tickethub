import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomePage from './pages/home/Hompage';
import RegisterPage from './pages/register/Registerpage';
import LoginPage from './pages/login/LoginPage';

const App = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  </BrowserRouter>
);

export default App;