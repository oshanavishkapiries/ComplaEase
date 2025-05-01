
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Layout } from "@/components/layout/Layout";

const NotFound = () => {
  return (
    <Layout>
      <div className="min-h-[60vh] flex flex-col items-center justify-center text-center px-4">
        <h1 className="text-6xl font-bold gradient-text mb-4">404</h1>
        <h2 className="text-3xl font-bold mb-6">Page Not Found</h2>
        <p className="text-xl text-muted-foreground max-w-md mb-8">
          Oops! The page you're looking for doesn't seem to exist.
        </p>
        <Button asChild size="lg">
          <Link to="/">Back to Homepage</Link>
        </Button>
      </div>
    </Layout>
  );
};

export default NotFound;
