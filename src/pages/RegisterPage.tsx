
import { Layout } from "@/components/layout/Layout";
import { RegisterForm } from "@/components/auth/RegisterForm";
import { useAuth } from "@/contexts/AuthContext";
import { Navigate } from "react-router-dom";

const RegisterPage = () => {
  const { isAuthenticated, isLoading } = useAuth();
  
  // Redirect if already authenticated
  if (isAuthenticated && !isLoading) {
    return <Navigate to="/" replace />;
  }
  
  return (
    <Layout>
      <div className="flex min-h-[calc(100vh-16rem)] items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <RegisterForm />
      </div>
    </Layout>
  );
};

export default RegisterPage;
