
import { Layout } from "@/components/layout/Layout";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { CourseCard } from "@/components/courses/CourseCard";
import { useCourses } from "@/contexts/CoursesContext";
import { Link } from "react-router-dom";
import { Search, BookOpen, Users, Award, Clock } from "lucide-react";
import { useState } from "react";

const Index = () => {
  const { featuredCourses } = useCourses();
  const [searchQuery, setSearchQuery] = useState("");
  
  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      window.location.href = `/courses?search=${encodeURIComponent(searchQuery)}`;
    }
  };
  
  return (
    <Layout>
      {/* Hero Section */}
      <section className="relative bg-gradient-to-br from-lms-blue/10 via-lms-purple/5 to-background py-16 md:py-24">
        <div className="container px-4 md:px-6">
          <div className="flex flex-col items-center space-y-4 text-center">
            <div className="space-y-2">
              <h1 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl lg:text-6xl">
                Elevate Your Learning with{" "}
                <span className="gradient-text">LectureSphere</span>
              </h1>
              <p className="mx-auto max-w-[700px] text-muted-foreground md:text-xl">
                Access high-quality university lecture recordings, organize your learning journey,
                and connect with instructors and peers.
              </p>
            </div>
            
            <div className="w-full max-w-md space-y-2">
              <form onSubmit={handleSearch} className="relative">
                <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
                <Input
                  placeholder="Search for courses, lectures, instructors..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="pl-10 h-12 w-full"
                />
                <Button 
                  type="submit"
                  className="absolute right-1 top-1/2 h-10 -translate-y-1/2 btn-gradient"
                >
                  Search
                </Button>
              </form>
              <p className="text-xs text-muted-foreground text-center">
                Popular: Computer Science, Data Analysis, Quantum Physics
              </p>
            </div>
            
            <div className="flex flex-wrap justify-center gap-4 mt-8">
              <Link to="/courses">
                <Button className="btn-gradient">Browse Courses</Button>
              </Link>
              <Link to="/register">
                <Button variant="outline">Sign Up Free</Button>
              </Link>
            </div>
          </div>
        </div>
      </section>
      
      {/* Featured Courses Section */}
      <section className="py-16">
        <div className="container px-4 md:px-6">
          <div className="flex flex-col md:flex-row justify-between items-center mb-8">
            <div>
              <h2 className="text-3xl font-bold">Featured Courses</h2>
              <p className="text-muted-foreground">
                Expand your knowledge with our highest-rated courses
              </p>
            </div>
            <Link to="/courses">
              <Button variant="outline" className="mt-4 md:mt-0">View All Courses</Button>
            </Link>
          </div>
          
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {featuredCourses.map((course) => (
              <CourseCard key={course.id} course={course} />
            ))}
          </div>
        </div>
      </section>
      
      {/* Features Section */}
      <section className="py-16 bg-muted/30">
        <div className="container px-4 md:px-6">
          <div className="text-center mb-10">
            <h2 className="text-3xl font-bold">Why Choose LectureSphere</h2>
            <p className="text-muted-foreground mt-2">
              Discover the benefits of our learning platform
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            <div className="flex flex-col items-center text-center p-6 rounded-lg bg-background shadow-sm">
              <div className="rounded-full p-3 bg-primary/10 text-primary mb-4">
                <BookOpen size={28} />
              </div>
              <h3 className="text-xl font-semibold mb-2">Extensive Library</h3>
              <p className="text-muted-foreground">
                Access thousands of lectures from top universities and professors.
              </p>
            </div>
            
            <div className="flex flex-col items-center text-center p-6 rounded-lg bg-background shadow-sm">
              <div className="rounded-full p-3 bg-primary/10 text-primary mb-4">
                <Clock size={28} />
              </div>
              <h3 className="text-xl font-semibold mb-2">Learn at Your Pace</h3>
              <p className="text-muted-foreground">
                Study whenever and wherever with 24/7 access to all content.
              </p>
            </div>
            
            <div className="flex flex-col items-center text-center p-6 rounded-lg bg-background shadow-sm">
              <div className="rounded-full p-3 bg-primary/10 text-primary mb-4">
                <Users size={28} />
              </div>
              <h3 className="text-xl font-semibold mb-2">Community Learning</h3>
              <p className="text-muted-foreground">
                Engage with peers and instructors through our interactive platform.
              </p>
            </div>
            
            <div className="flex flex-col items-center text-center p-6 rounded-lg bg-background shadow-sm">
              <div className="rounded-full p-3 bg-primary/10 text-primary mb-4">
                <Award size={28} />
              </div>
              <h3 className="text-xl font-semibold mb-2">Quality Education</h3>
              <p className="text-muted-foreground">
                All content is curated and reviewed for academic excellence.
              </p>
            </div>
          </div>
        </div>
      </section>
      
      {/* CTA Section */}
      <section className="py-16">
        <div className="container px-4 md:px-6">
          <div className="rounded-xl bg-gradient-to-r from-lms-blue to-lms-purple p-8 md:p-12 shadow-lg">
            <div className="flex flex-col md:flex-row items-center justify-between gap-8">
              <div className="text-white md:max-w-[60%]">
                <h2 className="text-3xl font-bold">
                  Ready to Start Learning?
                </h2>
                <p className="mt-2 text-white/80 md:text-lg">
                  Join thousands of students already learning on LectureSphere.
                  Create your free account today and start exploring.
                </p>
              </div>
              
              <div className="flex flex-col sm:flex-row gap-4">
                <Button size="lg" className="bg-white text-lms-purple hover:bg-white/90">
                  <Link to="/register">Sign Up Free</Link>
                </Button>
                <Button size="lg" variant="outline" className="text-white border-white hover:bg-white/10">
                  <Link to="/courses">Browse Courses</Link>
                </Button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </Layout>
  );
};

export default Index;
