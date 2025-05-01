
import { Layout } from "@/components/layout/Layout";
import { CourseFilters } from "@/components/courses/CourseFilters";
import { CourseList } from "@/components/courses/CourseList";
import { useCourses } from "@/contexts/CoursesContext";
import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Filter } from "lucide-react";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";
import { useLocation } from "react-router-dom";

type FilterOptions = {
  search: string;
  instructorId?: string;
  category?: string;
  tags: string[];
  sortBy?: 'newest' | 'popular' | 'rating';
};

const CoursesPage = () => {
  const location = useLocation();
  const [showMobileFilters, setShowMobileFilters] = useState(false);
  const { filterCourses } = useCourses();
  const [filters, setFilters] = useState<FilterOptions>({
    search: "",
    tags: [],
  });
  const [filteredCourses, setFilteredCourses] = useState(filterCourses(filters));
  
  // Check if there's a search query in the URL
  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const searchQuery = searchParams.get("search") || "";
    
    if (searchQuery) {
      setFilters((prevFilters) => ({
        ...prevFilters,
        search: searchQuery,
      }));
    }
  }, [location.search]);
  
  // Update filtered courses when filters change
  useEffect(() => {
    setFilteredCourses(filterCourses(filters));
  }, [filters, filterCourses]);
  
  const handleFilterChange = (newFilters: FilterOptions) => {
    setFilters(newFilters);
  };
  
  return (
    <Layout>
      <div className="container px-4 py-8 md:py-12">
        <div className="flex flex-col md:flex-row justify-between items-start mb-8">
          <div>
            <h1 className="text-3xl font-bold">Course Catalog</h1>
            <p className="text-muted-foreground">
              Browse our collection of university lectures
            </p>
          </div>
          
          {/* Mobile filter button */}
          <Sheet open={showMobileFilters} onOpenChange={setShowMobileFilters}>
            <SheetTrigger asChild>
              <Button
                variant="outline"
                className="flex items-center gap-2 mt-4 md:hidden"
              >
                <Filter size={16} />
                Filters
              </Button>
            </SheetTrigger>
            <SheetContent side="left" className="w-full max-w-sm sm:max-w-md">
              <div className="h-full py-6 pr-6">
                <CourseFilters onFilterChange={handleFilterChange} />
              </div>
            </SheetContent>
          </Sheet>
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Desktop filters sidebar */}
          <div className="hidden md:block">
            <CourseFilters onFilterChange={handleFilterChange} />
          </div>
          
          {/* Course grid */}
          <div className="md:col-span-3">
            <CourseList courses={filteredCourses} />
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default CoursesPage;
